package server.services;

import model.Message;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by igladush on 07.03.16.
 */
public class MessageService {

    private ConcurrentLinkedDeque<Message> allMessage = new ConcurrentLinkedDeque<>();

    public int getCountMessage() {
        return this.allMessage.size();
    }

    public Message popFirstMessage() {
        return allMessage.pop();
    }

    public void addMessage(Message message) {
        allMessage.add(message);
    }
}

