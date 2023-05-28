package controller.Menus;

import controller.UserDatabase.Shop;
import controller.UserDatabase.User;
import model.Item.Item;

public class ShopMenuController {
    private final User currentUser;

    public ShopMenuController(User currentUser) {
        this.currentUser = currentUser;
    }

    public String showAllItems() {
        return Shop.show();
    }

    public String showMyItems() {
        return String.join(",\n", currentUser.getItemsAsString());
    }

    public String buy(String name) {
        Item item = getShopItemByName(name);
        if (item == null || !Shop.getItems().contains(item)) return "Item does not exist!";
        if (!Shop.userCanAfford(currentUser, item)) return "Not enough coins!";
        Shop.buy(currentUser, item);
        return "Item bought successfully!";
    }

    public String sell(String name) {
        Item item = getUserItemByName(name);
        if (item == null || !currentUser.getItems().contains(item)) return "Item does not exist!";
        Shop.sell(currentUser, item);
        return "Item sold successfully!";
    }

    public Item getUserItemByName(String name) {
        for (Item item : currentUser.getItems())
            if (item.getName().equals(name))
                return item;

        return null;
    }

    public Item getShopItemByName(String name) {
        for (Item item : Shop.getItems())
            if (item.getName().equals(name))
                return item;

        return null;
    }

    public boolean itemExists(String name) {
        return getShopItemByName(name) != null || getUserItemByName(name) != null;
    }

    public void addItem(String name, int price, int count) {
        currentUser.addItem(new Item(price, name, count));
    }

    public boolean hasMarket() {
        return currentUser.getGovernment().hasMarket();
    }
}