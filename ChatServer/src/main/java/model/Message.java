package model;

/**
 * Created by igladush on 07.03.16.
 */
public class Message {
    private String text;
    private int idAuthor;


    public Message(String text, int idAuthor) {
        this.text = text;
        this.idAuthor = idAuthor;
    }

    public String getText() {
        return text;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

}