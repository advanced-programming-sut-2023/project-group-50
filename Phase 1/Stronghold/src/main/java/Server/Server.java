package Server;

import controller.UserDatabase.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends Thread {
    private static final ConcurrentHashMap<String, Connection> online = new ConcurrentHashMap<>();
    private final int Port;
    private final ServerSocket socket;

    public Server(int Port) throws IOException {
        this.Port = Port;
        socket = new ServerSocket(Port);
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

    @Override
    public void run() {
        while (true) {
            try {
                Socket listen = socket.accept();
                System.out.println("Connected");
                Connection connection = new Connection(listen);
                connection.start();
            } catch (IOException e) {
            }
        }
    }
}
