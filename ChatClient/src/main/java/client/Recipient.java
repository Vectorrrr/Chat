package client;

import closes.StreamClosers;
import property.PropertiesLoader;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * this class create for read message from server
 * Created by igladush on 07.03.16.
 */
public class Recipient implements Runnable {

    private Socket socket;
    private boolean running = true;

    public Recipient(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        String exit = PropertiesLoader.getServerAnswerDisconect();
        DataInputStream reader = null;
        try {
            reader = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            running = false;
            e.printStackTrace();
        }

        while (running) {
//            try {
//                System.out.println(reader==null);
                String text = "asd";
                if (exit.equals(text)) {
                    running = false;
                    break;
                }
//                System.out.println(text);
//            } catch (IOException e) {
//                running = false;
//                e.printStackTrace();
//            }
        }
        StreamClosers.closeStream(reader);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}