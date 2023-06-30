package model.Save;

import controller.UserDatabase.Users;
import model.Massenger.PublicChat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ChatLoader {
    public static void loadSave(ChatSaver saver) {
        new ChatLoader().loadUtil(saver);
    }

    public void load() {
        if (ChatSaver.exists())
            loadUtil(getSaver());
    }

    private ChatSaver getSaver() {
        ChatSaver saver;

        try {
            FileInputStream fileInputStream = new FileInputStream(ChatSaver.path);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);

            saver = (ChatSaver) inputStream.readObject();

            inputStream.close();
            fileInputStream.close();
            return saver;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadUtil(ChatSaver saver) {
        Users.setChats(saver.chats);
        PublicChat.messages = saver.publicMessages;
    }
}
