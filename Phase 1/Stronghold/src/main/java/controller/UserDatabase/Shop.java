package controller.UserDatabase;

import model.Item.Item;

import java.util.ArrayList;
import java.util.HashSet;

public class Shop {
    private static HashSet<Item> items;

    public Shop() {
        items = new HashSet<>();
    }

    public static void buy(User buyer, Item item) {
        if (userCanAfford(buyer, item)) {
            buyer.getGovernment().setCoins(buyer.getGovernment().getCoins() - item.getPrice());
            buyer.addItem(item);
            items.remove(item);
        }
    }

    public static void sell(User seller, Item item) {
        seller.getGovernment().setCoins(seller.getGovernment().getCoins() + item.getPrice());
        items.add(item);
        seller.removeItem(item);
    }

    public static String show() {
        return String.join(",\n", getItemsAsString());
    }

    public static HashSet<Item> getItems() {
        return items;
    }

    public static void setItems(HashSet<Item> items) {
        Shop.items = items;
    }

    public static boolean userCanAfford(User buyer, Item item) {
        return buyer.getGovernment().getCoins() >= item.getPrice();
    }

    private static ArrayList<String> getItemsAsString() {
        ArrayList<String> list = new ArrayList<>();
        for (Item item : items)
            list.add(item.toString());
        return list;
    }

}
