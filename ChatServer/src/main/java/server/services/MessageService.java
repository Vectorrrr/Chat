package server.services;

import model.Message;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * This class allow keep messages in safe list
 * and model secure deque
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

    public void pushMessage(Message message) {
        allMessage.push(message);
    }
}

