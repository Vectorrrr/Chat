package runner;

import client.Client;

import java.util.Scanner;
import java.util.Timer;

import static java.lang.Thread.yield;

/**
 * this class run client thread and always 100 second he run function checking correct state i
 * one of main stream
 * Created by igladush on 09.03.16.
 */
public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input ip address server");
        String address = sc.nextLine();
        System.out.println("Input port server");
        int serverPort = sc.nextInt();
        Client client = new Client();
        client.runClient(serverPort, address);

        Timer timer = new Timer();
        timer.schedule(client, 0, 100);

        while (client.isRunning()) {
            yield();
        }

        sc.close();
        client.cancel();
        timer.cancel();
    }
}
