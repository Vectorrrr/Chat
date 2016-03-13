package server;

import closes.StreamClosers;
import model.Message;
import property.PropertiesLoader;
import server.services.MessageService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Class for which performs chat server functions
 * This class can create new server socket and listening some port
 * when he hear new client he add connection and start new compound
 * Created by igladush on 07.03.16.
 */
public class Server  extends Thread {
    private final String ERROR_SOCKET_INIT = "I can't create socket";
    private final String ERROR_SOCKET_ACCEPT = "When I want wait error I have exception";
    private final String SERVER_DISCONNECT_ANSWER = PropertiesLoader.getServerAnswerDisconnect();

    private ConcurrentLinkedDeque<Compound> compounds = new ConcurrentLinkedDeque<>();
    private MessageService messageService = new MessageService();
    private int idNextUser = 0;
    private int port;
    private boolean running=true;
    private MessagesDistributor senderMessages;
    private ServerSocketChannel serverSocket=null;

    public Server(int port) {
        this.port = port;
    }

    //Method create server serverSocket and listening port if it has new compound that add this compound
    //to all compounds list
    @Override
    public void run() {

        System.out.println();
        senderMessages = new MessagesDistributor(this, messageService);
        senderMessages.start();
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("172.18.107.158" ,port));
            serverSocket.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(ERROR_SOCKET_INIT);
        }

        System.out.println("Server wait for first client");
        acceptUser(serverSocket);
    }

    private void acceptUser(ServerSocketChannel serverSocket) {
        System.out.println(serverSocket);
        while (running) {
            try {
                if(!serverSocket.isOpen()){
                    running=false;
                    break;
                }
                SocketChannel socket = serverSocket.accept();
                if(socket==null){
                    continue;
                }
                Compound comp = new Compound( socket.socket(), idNextUser, this, messageService);
                compounds.add(comp);
                comp.start();
                System.out.println();
                System.out.println("add new compounds he has id" + idNextUser++);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(ERROR_SOCKET_ACCEPT);
            }
        }
    }

    public void removeCompound(Compound compound) {
        compounds.remove(compound);
    }

    public Iterator<Compound> getAllUsers() {
        return compounds.iterator();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void close() {
        System.out.println("exit1");
        senderMessages.setWorking(false);
        System.out.println("exit2");
        for(Compound c:compounds){
            c.close();
        }
//        System.out.println("exit3");
//        sleep();
        System.out.println(senderMessages.isAlive());
        setRunning(false);
       StreamClosers.closeStream(serverSocket);
    }
}
