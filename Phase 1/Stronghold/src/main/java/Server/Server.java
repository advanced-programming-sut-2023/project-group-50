package Server;

import controller.UserDatabase.User;
import model.Save.Loader;
import model.Save.Saver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends Thread {
    static final int userPort = 8080;
    static final int clientPort = 5050;
    static final int updatePort = 5051;
    private static final ConcurrentHashMap<String, Connection> online = new ConcurrentHashMap<>();
    private final ServerSocket serverSocket;
    private final int port;


    public Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
    }

    public synchronized static boolean isOnline(User user) {
        return getOnline().containsKey(user.getUserName());
    }

    public synchronized static Connection getConnection(User user) {
        return getOnline().get(user.getUserName());
    }

    public synchronized static void setOnline(User user, Connection connection) {
        getOnline().put(user.getUserName(), connection);
    }

    public synchronized static void setOffline(User user) {
        getOnline().get(user.getUserName()).interrupt();
        getOnline().remove(user);
    }

    public synchronized static ConcurrentHashMap<String, Connection> getOnline() {
        return online;
    }

    private synchronized static void handleUser(Socket socket) throws IOException, ClassNotFoundException {
        System.out.print("User connected: ");
        User user = (User) new ObjectInputStream(socket.getInputStream()).readObject();
        System.out.println(user.getUserName());
        Connection connection = new Connection(socket);
        connection.setUser(user);
        connection.start();
    }

    private synchronized void handleClient(Socket socket) throws IOException, InterruptedException {
        System.out.println("Client connected");
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(Saver.get());
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                Socket accepted = serverSocket.accept();
                accept(accepted);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private synchronized void accept(Socket socket) throws IOException, ClassNotFoundException, InterruptedException {
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
        if (packet.command.equals(ServerCommands.SENDING_DATA.getString()))
            Loader.loadSave((Saver) packet.args[0]);
        System.out.println("Updated");
    }
}
