package server;

import server.services.MessageService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by igladush on 07.03.16.
 */
public class Server  extends Thread {
    private final String ERROR_SOCKET_INIT = "I can't create socket";
    private final String ERROR_SOCKET_ACCEPT = "When I want wait error I have exception";

    private ConcurrentLinkedDeque<Compound> compounds = new ConcurrentLinkedDeque<>();
    private MessageService messageService = new MessageService();
    private int idNextUser = 0;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    //Method create server socket and listening port if it has new compound that add this compound
    //to all compounds list
    @Override
    public void run() {
        MessagesDistributor senderMessages = new MessagesDistributor(this, messageService);
        senderMessages.start();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(ERROR_SOCKET_INIT);
        }

        System.out.println("Server wait for first client");
        acceptUser(serverSocket);
    }

    private void acceptUser(ServerSocket serverSocket) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Compound comp = new Compound(socket, idNextUser, this, messageService);
                compounds.add(comp);
                comp.start();
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
}
