package client;

import property.PropertiesLoader;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * This class wait for message from user and send this message at server when user type message+enter
 * Created by igladush on 11.03.16.
 */
public class Sender implements Runnable {
    private boolean running = true;
    private Socket socket = new Socket();

    public Sender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        try {
            String bye = PropertiesLoader.getClientAnswerDisconnect();
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());

            System.out.println("Type your message I send it to the server if you want exit write Bye");

            String message;
            while (running) {
                System.out.println(11);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ((message = keyboard.readLine()).length()>0) {
                    System.out.println(12);
                    writer.writeUTF(message);
                    writer.flush();
                    if (bye.equals(message)) {
                        running = false;
                        break;
                    }
                }
            }
            socket.shutdownOutput();
            running = false;
        } catch (IOException e) {
            running = false;
            e.printStackTrace();
        }

    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }
}
