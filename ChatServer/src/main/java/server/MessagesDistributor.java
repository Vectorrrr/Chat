package server;

import model.Message;
import property.PropertiesLoader;
import server.services.MessageService;

import java.util.Iterator;

/**
 * This class check all messages and if it find new message
 * it send message user with the exception of author this message
 * Created by igladush on 07.03.16.
 */
public class MessagesDistributor extends Thread {
    private static final String SERVER_DISCONNECT_WORD = PropertiesLoader.getServerAnswerDisconnect();
    private Server server;
    private MessageService messageService;
    private boolean running = true;

    public MessagesDistributor(Server server, MessageService messageService) {
        this.server = server;
        this.messageService = messageService;
    }

    /**
     * While work this method he send message every client in the chat
     * besides author this message
     */
    public void run() {
        Message m;
        while (running) {
            if (messageService.getCountMessage() > 0) {
                m = messageService.popFirstMessage();
                String text = m.getText();
                System.out.println(text);
                if (SERVER_DISCONNECT_WORD.equals(text)) {
                    running = false;
                }
                Iterator<Compound> it = server.getAllUsers();
                while (it.hasNext()) {
                    Compound compound = it.next();
                    if (m.getIdAuthor() != compound.getIdCompound()) {
                        compound.send(text);
                    }
                }
            }
        }
    }

    public void stopRunning() {
        this.running = false;
    }
}

