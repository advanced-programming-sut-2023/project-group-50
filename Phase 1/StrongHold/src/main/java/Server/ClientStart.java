package Server;

import view.show.Menus.ServerOfflineMenu;
import view.show.Menus.StartMenu;
import view.show.OnlineMenu.InitializationMenu;

public class ClientStart {
    public static void main(String[] args) throws Exception {
        Client.client = new Client();
        Client.client.start();
        if (Client.client.socket != null) {
            new StartMenu().start(InitializationMenu.stage);
        } else
            new ServerOfflineMenu().start(InitializationMenu.stage);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Client.sendData();
            Client.sendChatData();
        }));
    }
}
