package controller.Menus;

import controller.UserDatabase.User;
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

    public void newTrade(String resourceType, int resourceAmount, int price, String message) {
        Resource resource = Resource.getResourceByString(resourceType);
        TradeMarket.addTrade(new Trade(currentUser, null, TradeMarket.getNextId(), price, resource, resourceAmount, message));
    }

    public String showTrades() {
        return String.join("\n", TradeMarket.getTradesAsString());
    }

    public void acceptTrade(int id, String message) {
        assert TradeMarket.idIsValid(id);

        Trade trade = TradeMarket.getTrade(id);

        Government seller = trade.getFrom().getGovernment();
        Government buyer = currentUser.getGovernment();
        Resource resource = trade.getResourceType();

        seller.setResourceAmount(resource, seller.getResourceAmount(resource) - trade.getResourceAmount());
        buyer.setResourceAmount(resource, buyer.getResourceAmount(resource) + trade.getResourceAmount());

        seller.setCoin(seller.getCoin() + trade.getPrice());
        buyer.setCoin(buyer.getCoin() - trade.getPrice());

        trade.setTo(currentUser);
        trade.setMessage(message);
        TradeMarket.removeTrade(trade.getId());
    }

    public String showTradeHistory() {
        return String.join("\n", currentUser.getTradesAsString());
    }

    public boolean idIsValid(int id) {
        return TradeMarket.idIsValid(id);
    }
}
