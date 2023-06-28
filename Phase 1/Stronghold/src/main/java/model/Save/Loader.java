package model.Save;

import controller.UserDatabase.Shop;
import controller.UserDatabase.Users;
import model.Trade.TradeMarket;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Loader {
    public static void loadSave(Saver saver) {
        new Loader().loadUtil(saver);
    }

    public void load() {
        if (Saver.exists())
            loadUtil(getSaver());
    }

    private Saver getSaver() {
        Saver saver;

        try {
            FileInputStream fileInputStream = new FileInputStream(Saver.path);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);

            saver = (Saver) inputStream.readObject();

            inputStream.close();
            fileInputStream.close();
            return saver;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadUtil(Saver saver) {
        Users.setUsers(saver.users);
        Shop.setItems(saver.items);
        TradeMarket.setTrades(saver.trades);
        TradeMarket.setNextId(saver.nextId);
    }
}
