package client;

import closes.StreamClosers;
import property.PropertiesLoader;

import java.io.*;
import java.net.Socket;


/**
 * This class wait for message from user and send this message at server when user type message+enter
 * Created by igladush on 11.03.16.
 */
public class Sender implements Runnable {
    private static final String BYE = PropertiesLoader.getClientAnswerDisconnect();

    private boolean running = true;
    private Socket socket = new Socket();

    public Sender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream writer = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Type your message I send it to the server if you want exit write Bye");

            String message;
            while (running) {
                if (keyboard.ready()) {
                    message = keyboard.readLine();
                    writer.writeUTF(message);
                    writer.flush();
                    if (BYE.equals(message)) {
                        running = false;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            running = false;
            e.printStackTrace();
        }
        StreamClosers.closeStream(socket);
    }

    public void stopRunning() {
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
