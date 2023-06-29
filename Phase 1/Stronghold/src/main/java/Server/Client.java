package Server;

import controller.UserDatabase.Users;
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
    }

    public static void getData() {
        try {
            System.out.println("getting data...");
            Socket socket = new Socket("127.0.0.1", Server.clientPort);
            Saver saver = (Saver) new ObjectInputStream(socket.getInputStream()).readObject();
            Loader.loadSave(saver);
            System.out.println(Users.getUsersAsString());
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
            new ObjectOutputStream(socket.getOutputStream()).writeObject(packet);
            System.out.println("data sent");
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        try {
            socket = new Socket("127.0.0.1", Server.clientPort);
            Saver saver = (Saver) new ObjectInputStream(socket.getInputStream()).readObject();
            Loader.loadSave(saver);
        } catch (IOException | ClassNotFoundException e) {
            socket = null;
            throw new RuntimeException(e);
        }
    }
}
