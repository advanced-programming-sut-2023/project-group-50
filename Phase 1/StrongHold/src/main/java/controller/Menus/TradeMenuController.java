package controller.Menus;

import controller.UserDatabase.User;
import controller.control.Error;
import model.Government.Government;
import model.ObjectsPackage.Resource;
import model.Trade.Trade;
import model.Trade.TradeMarket;

public class TradeMenuController {
    private final User currentUser;

    public TradeMenuController(User currentUser) {
        this.currentUser = currentUser;
    }

    public static boolean resourceNameIsValid(String name) {
        return Resource.nameIsValid(name);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void newTrade(String resourceType, int resourceAmount, int price, String message) {
        Resource resource = Resource.getResourceByString(resourceType);
        TradeMarket.addTrade(new Trade(currentUser, null, TradeMarket.getNextId(), price, resource, resourceAmount, message, "Request"));
    }

    public String showTrades() {
        return String.join("\n", TradeMarket.getTradesAsString());
    }

    public Error acceptTrade(int id, String message) {
        Trade trade = TradeMarket.getTrade(id);

        Government seller;
        Government buyer;
        Resource resource = trade.getResourceType();

        if (trade.getType().equals("Donate")) {
            seller = trade.getFrom().getGovernment();
            buyer = currentUser.getGovernment();
        } else {
            buyer = trade.getFrom().getGovernment();
            seller = currentUser.getGovernment();
        }

        if (!canAcceptTrade(String.valueOf(id)).truth) return canAcceptTrade(String.valueOf(id));


        seller.setResourceAmount(resource, seller.getResourceAmount(resource) - trade.getResourceAmount());
        buyer.setResourceAmount(resource, buyer.getResourceAmount(resource) + trade.getResourceAmount());

        seller.setCoins(seller.getCoins() + trade.getPrice());
        buyer.setCoins(buyer.getCoins() - trade.getPrice());

        trade.setSecondMessage(message);
        return new Error("Trading was successful", true);
    }

    public String showTradeHistory() {
        return String.join("\n", currentUser.getTradesAsString());
    }

    public boolean idIsValid(int id) {
        return TradeMarket.idIsValid(id);
    }

    public Error canAcceptTrade(String id) {
        Trade trade = TradeMarket.getTrade(Integer.parseInt(id));
        Government seller;
        Government buyer;
        Resource resource = trade.getResourceType();

        if (trade.getType().equals("Donate")) {
            seller = trade.getFrom().getGovernment();
            buyer = currentUser.getGovernment();
            if (buyer.getCoins() < trade.getPrice())
                return new Error("You haven't enough coins", false);
            if (seller.getResourceAmount(resource) < trade.getResourceAmount())
                return new Error("Your seller haven't enough resource", false);
        } else {
            buyer = trade.getFrom().getGovernment();
            seller = currentUser.getGovernment();
            if (buyer.getCoins() < trade.getPrice())
                return new Error("Your buyer haven't enough coins", false);
            if (seller.getResourceAmount(resource) < trade.getResourceAmount())
                return new Error("You haven't enough resource", false);
        }

        return new Error("", true);

    }
}
