package model.Massenger;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;

public abstract class Chat implements Serializable {
    private final HashSet<String> members;
    private final LinkedHashSet<Message> messages;
    private final String owner;
    private final String id;
    private final String name;
    private ChatType chatType;

    public Chat(String admin, String id, String name) {
        owner = admin;
        this.id = id;
        this.name = name;
        members = new HashSet<>();
        messages = new LinkedHashSet<>();
        chatType = ChatType.NOT_SET;
    }

    public void addMember(String user) {
        members.add(user);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public LinkedHashSet<Message> getMessages() {
        return messages;
    }

    public HashSet<String> getMembers() {
        return members;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public boolean hasMember(String id) {
        return members.contains(id);
    }

    public ChatType getChatType() {
        return chatType;
    }

    protected void setChatType(ChatType privateChat) {
        chatType = privateChat;
    }
}
