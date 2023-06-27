package controller.Menus;

import controller.UserDatabase.Shop;
import controller.UserDatabase.User;
import controller.control.Error;
import model.Item.Item;

public class ShopMenuController {
    private final User currentUser;

    public ShopMenuController(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser () {
        return currentUser;
    }

    public String showAllItems() {
        return Shop.show();
    }

    public String showMyItems() {
        return String.join(",\n", currentUser.getItemsAsString());
    }

    public Error buy(String name) {
        Item item = getShopItemByName(name);
        if (item == null || !Shop.getItems().contains(item)) return new Error ( "Item does not exist!",false);
        if (!Shop.userCanAfford(currentUser, item)) return new Error ( "Not enough coins!",false);
        Shop.buy(currentUser, item);
        return new Error ( "Item bought successfully!",true);
    }

    public Error sell(String name) {
        Item item = getUserItemByName(name);
        if (item == null || !currentUser.getItems().contains(item)) return new Error ("Item does not exist!",false);
        Shop.sell(currentUser, item);
        return new Error ( "Item sold successfully!",true);
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