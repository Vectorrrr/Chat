package server;

import closes.StreamClosers;
import model.Message;
import property.PropertiesLoader;
import server.services.MessageService;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * This class model one compound with server
 * this class can send message use socked
 * this class can get new message from compound
 * Created by igladush on 07.03.16.
 */
public class Compound extends Thread {
    private final String ERROR_READ_OR_WRITE = "When I read or write I have error";
    private final String CLIENT_DISCONNECT_KEY = PropertiesLoader.getClientAnswerDisconnect();
    private final String SERVER_DISCONNECT_KEY = PropertiesLoader.getServerAnswerDisconnect();

    private MessageService messageService;
    private DataOutputStream writer;
    private Socket socket;
    private Server server;
    private int idCompound;
    private boolean running = true;

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
            writer.flush();
        } catch (IOException e) {
            running = false;
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        createStreams();
        readWhileNotInterupted();
        closeAllStreams();
        server.removeCompound(this);
        System.out.println("I closed the clint " + this.idCompound);
        StreamClosers.closeStream(socket);

    }

    public void close() {
        stopRunning();
        send(SERVER_DISCONNECT_KEY);
        running = false;
    }

    public void stopRunning() {
        this.running = false;
    }

    private void createStreams() {
        try {
            writer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method create for read all message from one compound
     * when co,pound send exit word, this method send secret word for client
     * this word signals client, that server stopped this serverSocket correct
     */
    private void readWhileNotInterupted() {
        String message;
        try (DataInputStream reader=new DataInputStream(socket.getInputStream())){
            while (running) {

                message = reader.readUTF();
                System.out.println(message);
                if (CLIENT_DISCONNECT_KEY.equals(message) && running) {
                    writer.writeUTF(SERVER_DISCONNECT_KEY);
                    running = false;
                    System.out.println(123123);
                    break;
                }
                messageService.addLastMessage(new Message(message, idCompound));

            }
        } catch (IOException e) {
            System.out.println(ERROR_READ_OR_WRITE);
            e.printStackTrace();
            server.removeCompound(this);

        }
    }

    //methods that close all stream
    private void closeAllStreams() {
        StreamClosers.closeStream(writer);
    }

}