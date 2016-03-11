package client;

import property.PropertiesLoader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

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
        Scanner keyboard = new Scanner(System.in);
        try {
            String bye = PropertiesLoader.getClientAnswerDisconect();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            System.out.println("Type your message I send it to the server if you want exit write Bye");

            String message;
            while (running) {
                message = keyboard.nextLine();
                out.writeUTF(message);
                out.flush();
                if (bye.equals(message)) {
                    running=false;
                    break;
                }
            }
            socket.shutdownOutput();
            running = false;
        } catch (IOException e) {
            running = false;
            e.printStackTrace();
        }
        keyboard.close();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }
}
