package server;

import closes.StreamClosers;
import server.services.MessageService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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

    private ConcurrentLinkedDeque<Compound> compounds = new ConcurrentLinkedDeque<>();
    private MessageService messageService = new MessageService();
    private int idNextUser = 0;
    private int port;
    private boolean running = true;
    private MessagesDistributor senderMessages;
    private ServerSocket serverSocket = null;

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
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(ERROR_SOCKET_INIT);
        }

        System.out.println("Server wait for first client");
        acceptUser(serverSocket);
    }

    public void removeCompound(Compound compound) {
        compounds.remove(compound);
    }

    public Iterator<Compound> getAllUsers() {
        return compounds.iterator();
    }

    public void stopRunning() {
        this.running = false;
    }

    public void close() {
        stopRunning();
        senderMessages.stopRunning();
        for (Compound c : compounds) {
            c.close();
        }
    }

    private void acceptUser(ServerSocket serverSocket) {
        System.out.println(serverSocket);
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println(running);
                if (socket != null && running) {
                    System.out.println(running);
                    Compound comp = new Compound(socket, idNextUser, this, messageService);
                    compounds.add(comp);
                    comp.start();
                    System.out.println();
                    System.out.println("add new compounds he has id" + idNextUser++);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(ERROR_SOCKET_ACCEPT);
            }
        }
        StreamClosers.closeStream(serverSocket);
    }
}
