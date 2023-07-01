package Server;

import model.Save.ChatLoader;
import model.Save.ChatSaver;
import model.Save.Loader;
import model.Save.Saver;

import java.io.IOException;

public class ServerStart {
    public static void main(String[] args) throws IOException {
        new Loader().load();
        new ChatLoader().load();
        Server clientServer = new Server(Server.clientPort);
        Server userServer = new Server(Server.userPort);
        Server updateServer = new Server(Server.updatePort);
        Server chatReceiver = new Server(Server.chatReceiveUpdatePort);
        Server chatSender = new Server(Server.chatSendUpdatePort);
        Server publicReceiver = new Server(Server.publicReceivingPort);
        clientServer.start();
        userServer.start();
        updateServer.start();
        chatReceiver.start();
        chatSender.start();
        publicReceiver.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            new Saver().save();
            new ChatSaver().save();
        }));
    }
}
