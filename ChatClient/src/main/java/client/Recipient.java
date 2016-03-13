package client;

import closes.StreamClosers;
import property.PropertiesLoader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
        String exit = PropertiesLoader.getServerAnswerDisconnect();
        DataInputStream reader = null;
        DataOutputStream writer=null;
        try {
            reader = new DataInputStream(socket.getInputStream());
            writer=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            running = false;
            e.printStackTrace();
        }

        while (running) {
            try {
                System.out.println(1);
                String text = reader.readUTF();
                if (exit.equals(text)) {
                    System.out.println(text);
                    writer.writeUTF("OK");
                    running = false;
                    break;
                }
                System.out.println(text);

            } catch (IOException e) {
                running = false;
                e.printStackTrace();
            }
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