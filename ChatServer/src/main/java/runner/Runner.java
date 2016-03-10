package runner;

/**
 * Created by igladush on 09.03.16.
 */

import server.Server;
import java.util.Scanner;

/**
 * Class for run server
 * */

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input name port: ");
        int port = sc.nextInt();
        new Server(port).start();

    }
}
