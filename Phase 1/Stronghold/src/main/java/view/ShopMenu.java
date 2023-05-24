package view;

import controller.Menus.ShopMenuController;
import controller.control.Commands;
import controller.control.Error;
import controller.control.State;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {

    private final ShopMenuController shopMenuController;

    public ShopMenu(ShopMenuController shopMenuController) {
        this.shopMenuController = shopMenuController;
    }

    public State run(Scanner scanner) {
        String line;
        Matcher matcher;
        System.out.println("in shop menu");

        if (!shopMenuController.hasMarket()) {
            System.out.println("You don't have a market!");
            return State.PROFILE;
        }

        while (true) {
            line = scanner.nextLine();
            if ((matcher = Commands.getMatcher(Commands.BUY_ITEM, line)).matches())
                System.out.println(buy(matcher));
            else if ((matcher = Commands.getMatcher(Commands.SELL_ITEM, line)).matches())
                System.out.println(sell(matcher));
            else if ((matcher = Commands.getMatcher(Commands.ADD_NEW_ITEM, line)).matches())
                System.out.println(addNewItem(matcher));
            else if (Commands.getMatcher(Commands.SHOW_MY_ITEMS, line).matches())
                System.out.println(showMyItems());
            else if (Commands.getMatcher(Commands.SHOW_ALL_ITEMS, line).matches())
                System.out.println(show());
            else if (Commands.getMatcher(Commands.EXIT, line).matches())
                return State.PROFILE;
            else
                System.out.println("Invalid command!");
        }
    }

    private String show() {
        return shopMenuController.showAllItems();
    }

    private String showMyItems() {
        return shopMenuController.showMyItems();
    }

    private String buy(Matcher matcher) {
        String name = matcher.group("name");
        return shopMenuController.buy(name);
    }

    private String sell(Matcher matcher) {
        String name = matcher.group("name");
        return shopMenuController.sell(name);
    }

    private String addNewItem(Matcher matcher) {
        String name = matcher.group("name");
        int price = Integer.parseInt(matcher.group("price"));
        int count = Integer.parseInt(matcher.group("count"));

        Error itemExistsError = itemExistsError(name);
        Error priceError = priceError(price);
        Error countError = countError(count);

        if (!itemExistsError.truth) return itemExistsError.errorMassage;
        if (!priceError.truth) return priceError.errorMassage;
        if (!countError.truth) return countError.errorMassage;

        shopMenuController.addItem(name, price, count);
        return "Item added successfully!";
    }

    private Error priceError(int price) {
        return new Error("Price is invalid!", price >= 0);
    }

    private Error countError(int count) {
        return new Error("Count is invalid!", count > 0);
    }

    private Error itemExistsError(String name) {
        return new Error("Item already exists!", !shopMenuController.itemExists(name));
    }


}
