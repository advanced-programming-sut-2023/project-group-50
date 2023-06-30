package Server;

import model.Save.ChatSaver;
import model.Save.Loader;
import model.Save.Saver;

import java.io.IOException;

public class ServerStart {
    public static void main(String[] args) throws IOException {
        new Loader().load();
        Server clientServer = new Server(Server.clientPort);
        Server userServer = new Server(Server.userPort);
        Server updateServer = new Server(Server.updatePort);
        Server chatReceiver = new Server(Server.chatReceiveUpdatePort);
        Server chatSender = new Server(Server.chatSendUpdatePort);
        clientServer.start();
        userServer.start();
        updateServer.start();
        chatReceiver.start();
        chatSender.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            new Saver().save();
            new ChatSaver().save();
        }));
    }


}
