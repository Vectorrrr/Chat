package runner;

/**
 * this class run chat server
 * Created by igladush on 09.03.16.
 */

import closes.StreamClosers;
import server.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.yield;


public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input address");
        String address = sc.nextLine();
        System.out.println("Input name port: ");
        int port = sc.nextInt();

        Server server = new Server(port);
        server.start();

        System.out.println("Input close for exit");
        while (true) {
            String s = sc.nextLine();
            if ("Close".equals(s)) {
                break;
            }
        }

        server.close();
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getByName(address), port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; ++i) {
            yield();
        }
        System.out.println(socket);


        StreamClosers.closeStream(socket);
    }
}
