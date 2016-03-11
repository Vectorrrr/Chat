package runner;

/**
 * this class run chat server
 * Created by igladush on 09.03.16.
 */

import server.Server;
import java.util.Scanner;


public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input name port: ");
        int port = sc.nextInt();
        Server server =new Server(port);
        server.start();

        System.out.println("Input close for exit");
        while(true){
            String s=sc.nextLine();
            if("Close".equals(s)){
                break;
            }
        }

        server.close();
        server.interrupt();

        sc.close();

    }
}
