package Server;

import controller.UserDatabase.User;
import model.Save.Loader;
import model.Save.Saver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends Thread {
    static final int userPort = 8080;
    static final int clientPort = 5050;
    static final int updatePort = 5051;
    private static final ConcurrentHashMap<String, Socket> online = new ConcurrentHashMap<>();
    private final ServerSocket serverSocket;


    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public synchronized static boolean isOnline(User user) {
        return getOnline().containsKey(user.getUserName());
    }

    public synchronized static Socket getConnection(User user) {
        return getOnline().get(user.getUserName());
    }

    public synchronized static void setOnline(User user, Socket socket) {
        getOnline().put(user.getUserName(), socket);
    }

    public synchronized static void setOffline(User user) throws IOException {
        getOnline().get(user.getUserName()).close();
        getOnline().remove(user);
    }

    public synchronized static ConcurrentHashMap<String, Socket> getOnline() {
        return online;
    }

    private synchronized static void handleUser(Socket socket) throws IOException, ClassNotFoundException {
        System.out.print("User connected: ");
        Packet packet = (Packet) new ObjectInputStream(socket.getInputStream()).readObject();
        if (!packet.command.equals(ServerCommands.INIT)) return;
        User user = (User) packet.args[0];
        System.out.println(user.getUserName());
        setOnline(user, socket);
        new ObjectOutputStream(socket.getOutputStream()).writeObject(new Packet(ServerCommands.INIT_DONE));
    }

    private synchronized void handleClient(Socket socket) throws IOException {
        System.out.println("Client connected");
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(Saver.get());
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                Socket accepted = serverSocket.accept();
                accepted.setTcpNoDelay(true);
                accept(accepted);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private synchronized void accept(Socket socket) throws IOException, ClassNotFoundException {
        int port = socket.getLocalPort();
        System.out.println(port);
        switch (port) {
            case userPort -> handleUser(socket);
            case clientPort -> handleClient(socket);
            case updatePort -> handleUpdate(socket);
            default -> System.out.println(port);
        }
    }

    private synchronized void handleUpdate(Socket socket) throws IOException, ClassNotFoundException {
        System.out.println("gotData");
        Packet packet = (Packet) new ObjectInputStream(socket.getInputStream()).readObject();
        if (Objects.requireNonNull(packet.command) == ServerCommands.SENDING_SAVE) {
            Loader.loadSave((Saver) packet.args[0]);
        }
        System.out.println("Updated");
    }
}
