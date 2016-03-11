package client;

import property.PropertiesLoader;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by igladush on 07.03.16.
 */
public class Client {


    public void runClient(int serverPort, String address) {

        try {
            Socket socket = new Socket(InetAddress.getByName(address), serverPort);
            new Thread(new Recipient(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}