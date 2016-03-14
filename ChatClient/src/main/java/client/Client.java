package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.TimerTask;

/**
 * this class can start two thread first thread
 * is recipient for read message from server
 * second thread for read and send server user message
 * that class also allow check working state this two streams
 * and when one of stream doesn't work this class interrupt
 * second
 * Created by igladush on 07.03.16.
 */
public class Client extends TimerTask {
    private Sender sender;
    private Recipient recipient;
    private boolean running = true;

    public void runClient(int serverPort, String address) {
        try {
            Socket socket = new Socket(InetAddress.getByName(address), serverPort);
            sender = new Sender(socket);
            recipient = new Recipient(socket);
            new Thread(sender).start();
            new Thread(recipient).start();
        } catch (IOException e) {
            running = false;
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (!recipient.isRunning()) {
            sender.stopRunning();
            running = false;
        }
        if (!sender.isRunning()) {
            recipient.stopRunning();
            running = false;
        }
    }

    public boolean isRunning() {
        return running;
    }
}