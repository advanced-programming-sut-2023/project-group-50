package model.Save;

import controller.UserDatabase.Users;
import model.Massenger.Message;
import model.Massenger.PublicChat;
import view.show.OnlineMenu.ChatMenu.Chat;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class ChatSaver implements Serializable {
    @Serial
    private static final long serialVersionUID = -5142982528088815378L;
    static String path = "Stronghold/src/main/resources/Database/Users/chats.chd";
    LinkedHashSet<Message> publicMessages;
    HashMap<String, Chat> chats;


    public ChatSaver() {
        chats = Users.getChats();
        publicMessages = PublicChat.messages;
    }

    public static Saver get() {
        Saver saver = new Saver();
        saver.save();
        return saver;
    }

    public static boolean exists() {
        File dir = new File(path);
        return dir.exists();
    }

    public void save() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

            outputStream.writeObject(this);

            outputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
