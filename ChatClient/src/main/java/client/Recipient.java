package client;

import property.PropertiesLoader;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by igladush on 07.03.16.
 */
public class Recipient implements Runnable {
    private final String ERROR_CREATE_INPUT_STREAM = "When I create data input stream i catch exception!";
    private final String ERROR_READ_ANSWER = "When I read answer I have problem";
    private final String ERROR_STREAM_CLOSE = "Error when I close";

    private DataInputStream reader;
    private Socket socket;

    public Recipient(Socket socket) {
        this.socket = socket;
        try {
            reader = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(ERROR_CREATE_INPUT_STREAM);

        }
    }

    public void run() {
        String exit = PropertiesLoader.getProperties().getProperty("serverAnswerDisconnect");
        while (true) {
            try {
                String text = reader.readUTF();
                if (exit.equals(text)) {
                    break;
                }
                System.out.println(text);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(ERROR_READ_ANSWER);
            }
        }
        closeStream(reader);
        closeStream(socket);

    }

    private void closeStream(Closeable stream) {
        try {
            stream.close();
        } catch (IOException e) {
            System.out.println(ERROR_STREAM_CLOSE);
            e.printStackTrace();
        }
    }
}