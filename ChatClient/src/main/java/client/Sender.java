package client;

import property.PropertiesLoader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by igladush on 11.03.16.
 */
public class Sender implements Runnable {
    private boolean isRun=true;
    private Socket socket =new Socket();

    public Sender(Socket socket){
            this.socket=socket;
    }

    @Override
    public void run() {

        Scanner keyboard = new Scanner(System.in);
        try {
            String bye = PropertiesLoader.getClientAnswerDisconect();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());


            System.out.println("Type your message I send it to the server if you want exit write Bye");

            String message;
            while (true) {
                message = keyboard.nextLine();
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
        keyboard.close();
    }
}
