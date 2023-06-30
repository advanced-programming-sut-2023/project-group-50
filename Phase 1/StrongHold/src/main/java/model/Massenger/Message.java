package model.Massenger;

import java.io.Serializable;

public class Message implements Serializable {
    private final String owner;
    private final String Content;

    public Message(String owner, String content) {
        this.owner = owner;
        Content = content;
    }

    public String getOwner() {
        return owner;
    }

    public String getContent() {
        return Content;
    }
}
