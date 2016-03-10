package runner;

import client.Client;
import java.util.Scanner;

/**
 * Created by igladush on 09.03.16.
 */
public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input ip address server");
        String address = sc.nextLine();
        System.out.println("Input port server");
        int port = sc.nextInt();
        new Client().runClient(port, address);


    }
}
