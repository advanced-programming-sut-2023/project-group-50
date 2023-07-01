package controller.UserDatabase;

import model.Item.Item;
import model.ObjectsPackage.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Shop implements Serializable {
    private static HashSet<Item> items;

    public Shop() {
        items = new HashSet<>();
    }

    public static void buy(User buyer, Item item) {
        if (userCanAfford(buyer, item)) {
            buyer.getGovernment().setCoins(buyer.getGovernment().getCoins() - item.getPrice() * item.getCount());
            buyer.getGovernment().setResourceAmount(Resource.getResourceByString(item.getName()), item.getCount() +
                    buyer.getGovernment().getResourceAmount(Resource.getResourceByString(item.getName())));
            buyer.addItem(item);
            items.remove(item);
        }
    }

    public static void sell(User seller, Item item) {
        seller.getGovernment().setCoins(seller.getGovernment().getCoins() + item.getPrice() * item.getCount());
        seller.getGovernment().setResourceAmount(Resource.getResourceByString(item.getName()),
                                                 seller.getGovernment().getResourceAmount(Resource.getResourceByString(item.getName())) -
                                                         item.getCount());
        items.add(item);
        seller.removeItem(item);
    }

    public static String show() {
        return String.join(",\n", getItemsAsString());
    }

    public static HashSet<Item> getItems() {
        if (items == null) items = new HashSet<>();
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
