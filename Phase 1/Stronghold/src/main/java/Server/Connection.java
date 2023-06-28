package Server;

import controller.UserDatabase.User;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Connection extends Thread implements Serializable {
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private User user;
    private final Socket socket;

    public Connection(Socket socket) throws IOException {
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.socket = socket;
        user = null;
    }

    @Override
    public synchronized void run() {
        while (!(socket.isClosed() || this.isInterrupted())) {
            try {
                handle();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("disconnected");
        disconnect();
    }

    public synchronized void handle() throws IOException, ClassNotFoundException {
        System.out.println(Server.getOnline());
        System.out.println("Reading...");
        Object object;
        try {
            object = inputStream.readObject();
        } catch (EOFException | SocketException exception) {
            disconnect();
            return;
        }

        Packet packet = (Packet) object;
        String command = packet.command;
        Object[] args = packet.args;
        Class<?>[] clazz = packet.argClass;

        if (command.equals(ServerCommands.INIT.getString())) {
            initUser(args);
            return;
        }

        throw new RuntimeException();
    }

    private void initUser(Object[] args) throws IOException {
        User user = (User) args[0];
        this.user = user;
        Server.setOnline(user, this);
        outputStream.writeObject(new Packet(ServerCommands.INIT_DONE.getString()));
        outputStream.flush();
        System.out.println("sent");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void send(Packet packet) throws IOException {
//        dataOutputStream.writeObject(packet);
//        dataOutputStream.flush();
//        System.out.println("wrote");
    }

    public void disconnect() {
        Server.setOffline(user);
        user.setSocket(null);
        this.interrupt();
    }

    public String getData() {
        return socket.toString();
    }
}
