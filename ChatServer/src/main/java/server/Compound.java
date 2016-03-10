package server;

import model.Message;
import property.PropertiesLoader;
import server.services.MessageService;


import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;


/**
 * Created by igladush on 07.03.16.
 */
public class Compound extends Thread {
    private final String ERROR_READ_OR_WRITE = "When I read or write I have error";
    private final String ERROR_CLOSE_STREAM = "When I close stream  I have ERROR";

    private MessageService messageService;
    private DataInputStream reader;
    private DataOutputStream writer;
    private Socket socket;
    private Server server;
    private int idCompound;

    public int getIdCompound() {
        return this.idCompound;
    }

    public Compound(Socket socket, int Id, Server server, MessageService messageService) {
        this.socket = socket;
        this.idCompound = Id;
        this.server = server;
        this.messageService = messageService;
    }

    /**
     * This method send some string for this Compound
     */
    public void send(String s) {
        try {
            writer.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        createStreams();
        read();
        closeAllStreams();
        server.removeCompound(this);
        System.out.println("I closed the clint " + this.idCompound);
    }

    private void createStreams() {
        try {
            writer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * this method create for read all message from one compound
     * when co,pound send exit word, this method send secret word for client
     * this word signals client, that server stopped this socket correct
     */
    private void read() {
        Properties properties = PropertiesLoader.getProperties();
        String clientExitWord = properties.getProperty("clientAnswerDisconnect");
        String message;

        while (true) try {
            message = reader.readUTF();
            System.out.println(message);

            if (clientExitWord.equals(message)) {
                writer.writeUTF(properties.getProperty("serverAnswerDisconnect"));
                break;
            }
            messageService.addMessage(new Message(message, idCompound));
        } catch (IOException e) {
            System.out.println(ERROR_READ_OR_WRITE);
            e.printStackTrace();
        }

    }

    //methods that close all stream
    private void closeAllStreams() {
        closeStream(reader);
        closeStream(writer);
    }

    private void closeStream(Closeable stream) {
        try {
            stream.close();
        } catch (IOException e) {
            System.out.println(ERROR_CLOSE_STREAM);
            e.printStackTrace();
        }

    }

}