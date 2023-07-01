package model.Trade;

import controller.UserDatabase.User;
import model.ObjectsPackage.Resource;

import java.io.Serializable;

public class Trade implements Serializable {
    private final User from;
    private final int id;
    private final int price;
    private final Resource resourceType;
    private final int resourceAmount;
    private final String type;
    private User to;
    private String message;
    private boolean isAccepted;
    private boolean answered;
    private boolean seen;

    private String secondMessage;

    public Trade(User from, User to, int id, int price, Resource resourceType, int resourceAmount, String message, String type) {
        this.from = from;
        this.to = to;
        this.id = id;
        this.price = price;
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.message = message;
        this.type = type;
        this.isAccepted = false;
        this.answered = false;
        this.seen = false;
        this.secondMessage = "";
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

    public String getType() {
        return type;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getSecondMessage() {
        return secondMessage;
    }

    public void setSecondMessage(String secondMessage) {
        this.secondMessage = secondMessage;
    }
}
