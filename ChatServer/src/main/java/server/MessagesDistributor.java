package server;

import model.Message;
import server.services.MessageService;

import java.util.Iterator;

/**
 * Created by igladush on 07.03.16.
 */
public class MessagesDistributor extends Thread {
    private Server server;
    private MessageService messageService;
    private boolean working = true;

    public MessagesDistributor(Server server, MessageService messageService) {
        this.server = server;
        this.messageService = messageService;
    }

    /**
     * While work this method he send message every client in the chat
     * besides author this message
     */
    public void run() {
        while (working) {
            if (messageService.getCountMessage() > 0) {
                Message m = messageService.popFirstMessage();

                Iterator<Compound> it = server.getAllUsers();
                while (it.hasNext()) {
                    Compound compound = it.next();
                    if (m.getIdAuthor() != compound.getIdCompound()) {
                        compound.send(m.getText());
                    }
                }
            }
        }
    }
}

