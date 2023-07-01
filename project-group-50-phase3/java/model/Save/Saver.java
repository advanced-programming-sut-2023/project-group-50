package model.Save;

import controller.UserDatabase.Shop;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import model.Item.Item;
import model.Request.Request;
import model.Save.MapSave.AnonymousMap;
import model.Trade.Trade;
import model.Trade.TradeMarket;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Saver implements Serializable {
    @Serial
    private static final long serialVersionUID = -5142982528088815368L;
    static String path = "Stronghold/src/main/resources/Database/Users/save.svd";


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

    /**
     * MapSaves
     */
    HashMap<String, HashSet<AnonymousMap>> maps;

    /**
     * Requests
     */
    HashSet<Request> requests;

    int nextId;

    public Saver() {
        users = Users.getUsers();
        items = Shop.getItems();
        trades = TradeMarket.getTrades();
        nextId = TradeMarket.getNextId();
        maps = Users.getMaps();
        requests = Users.getRequests();
    }

    public static Saver get() {
        Saver saver = new Saver();
        saver.save();
        return saver;
    }

    public static boolean exists() {
        File dir = new File(path);
        return dir.exists();
    }

    public void save() {
        try {
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
