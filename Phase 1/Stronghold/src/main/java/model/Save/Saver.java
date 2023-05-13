package model.Save;

import controller.UserDatabase.Shop;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import model.Item.Item;
import model.Trade.Trade;
import model.Trade.TradeMarket;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Saver implements Serializable {
    static String path = "stronghold\\src\\main\\java\\model\\Save\\LastGame\\LastSave.txt";


    /**
     * Users
     */
    HashMap<String, User> users;

    /**
     * Shop
     */
    HashSet<Item> items;

    /**
     * TradeMarket
     */
    LinkedHashMap<Integer, Trade> trades;
    int nextId;

    public Saver() {
        users = Users.getUsers();
        items = Shop.getItems();
        trades = TradeMarket.getTrades();
        nextId = TradeMarket.getNextId();
    }

    private static void makeLastGameTxtIfNotExists() {
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();
    }

    public void save() {
        try {
            makeLastGameTxtIfNotExists();

            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

            outputStream.writeObject(this);

            outputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
