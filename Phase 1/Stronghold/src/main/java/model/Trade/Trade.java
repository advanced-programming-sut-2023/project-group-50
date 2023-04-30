package model.Trade;

import controller.UserDatabase.User;
import model.ObjectsPackage.Resource;

public class Trade {
    private final User from;
    private final int id;
    private final int price;
    private final Resource resourceType;
    private final int resourceAmount;
    private User to;
    private String message;

    public Trade(User from, User to, int id, int price, Resource resourceType, int resourceAmount, String message) {
        this.from = from;
        this.to = to;
        this.id = id;
        this.price = price;
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.message = message;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public Resource getResourceType() {
        return resourceType;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "from=" + from.getUserName() +
                ", to=" + (to == null ? "-" : to.getUserName()) +
                ", id=" + id +
                ", price=" + price +
                ", resourceType=" + resourceType +
                ", resourceAmount=" + resourceAmount +
                ", message='" + message + '\'' +
                '}';
    }
}
