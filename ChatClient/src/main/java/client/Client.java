package client;

import property.PropertiesLoader;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by igladush on 07.03.16.
 */
public class Client {

    private BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

    public void runClient(int serverPort, String address) {
        String bye = PropertiesLoader.getProperties().getProperty("clientAnswerDisconnect");

        try {
            Socket socket = new Socket(InetAddress.getByName(address), serverPort);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Recipient(socket)).start();
            System.out.println("Type your message I send it to the server if you want exit write Buy");

            String message;
            while (true) {
                message = keyboard.readLine();
                out.writeUTF(message);
                out.flush();
                if (bye.equals(message)) {
                    break;
                }
            }
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}