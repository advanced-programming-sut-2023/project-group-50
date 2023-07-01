package model.Massenger;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = -5242982528088815368L;

    private final String owner;
    private final HashMap<String, Reaction> reactions;
    private String Content;
    private boolean seen;

    public Message(String owner, String content) {
        this.owner = owner;
        Content = content;
        reactions = new HashMap<>();
        seen = false;
    }

    public String getOwner() {
        return owner;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public HashMap<String, Reaction> getReactions() {
        return reactions;
    }

    public boolean isSeen() {
        return seen;
    }

    public void see() {
        seen = true;
    }
}
