package client;

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
    private static final String CLIENT_EXIT_WORD = PropertiesLoader.getClientAnswerDisconnect();
    private static final String SERVER_EXIT_WORD = PropertiesLoader.getServerAnswerDisconnect();

    private Socket socket;
    private boolean running = true;

    public Recipient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            while (running) {
                String text = reader.readUTF();
                if (SERVER_EXIT_WORD.equals(text)) {
                    writer.writeUTF(CLIENT_EXIT_WORD);
                    writer.flush();
                    running = false;
                    break;
                }
                System.out.println(text);

            }
        } catch (IOException e) {
            running = false;
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void stopRunning() {
        this.running = false;
    }
}