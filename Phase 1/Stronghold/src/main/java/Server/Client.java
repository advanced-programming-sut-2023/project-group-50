package Server;

import model.Save.ChatLoader;
import model.Save.ChatSaver;
import model.Save.Loader;
import model.Save.Saver;
import view.show.Menus.ServerOfflineMenu;
import view.show.Menus.StartMenu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {
    public static Client client;
    private Socket socket;

    public static void main(String[] args) throws Exception {
        client = new Client();
        client.start();
        if (client.socket != null)
            StartMenu.main(null);
        else
            ServerOfflineMenu.main(null);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            sendData();
            sendChatData();
        }));
    }

    private static void sendChatData() {
        try {
            System.out.println("Sending chat data");
            Socket socket = new Socket("127.0.0.1", Server.chatReceiveUpdatePort);
            Packet packet = new Packet(ServerCommands.SENDING_CHAT, ChatSaver.get());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(packet);
            outputStream.flush();
            outputStream.close();
            System.out.println("chat data sent");
            socket.close();
        } catch (IOException e) {
            System.out.println("Server offline!");
        }
    }

    public static void getChatData() {
        try {
            System.out.println("getting chat data...");
            Socket socket = new Socket("127.0.0.1", Server.chatSendUpdatePort);
            ChatSaver saver = (ChatSaver) new ObjectInputStream(socket.getInputStream()).readObject();
            ChatLoader.loadSave(saver);
            socket.close();
        } catch (Exception e) {
            System.out.println("Server crashed...");
        }
    }

    public static void getData() {
        try {
            System.out.println("getting data...");
            Socket socket = new Socket("127.0.0.1", Server.clientPort);
            Saver saver = (Saver) new ObjectInputStream(socket.getInputStream()).readObject();
            Loader.loadSave(saver);
            socket.close();
        } catch (Exception e) {
            System.out.println("Server crashed...");
        }
    }

    public static void sendData() {
        try {
            System.out.println("Sending data");
            Socket socket = new Socket("127.0.0.1", Server.updatePort);
            Packet packet = new Packet(ServerCommands.SENDING_SAVE, Saver.get());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(packet);
            outputStream.flush();
            outputStream.close();
            System.out.println("data sent");
            socket.close();
        } catch (IOException e) {
            System.out.println("Server offline!");
        }
    }

    @Override
    public void start() {
        try {
            socket = new Socket("127.0.0.1", Server.clientPort);
            Saver saver = (Saver) new ObjectInputStream(socket.getInputStream()).readObject();
            Loader.loadSave(saver);

            getChatData();
        } catch (IOException | ClassNotFoundException e) {
            socket = null;
        }
    }
}
