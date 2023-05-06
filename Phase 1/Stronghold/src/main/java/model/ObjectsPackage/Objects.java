package model.ObjectsPackage;

import controller.UserDatabase.User;

import java.io.Serializable;

public abstract class Objects implements Serializable {
    private final ObjectType objectType;
    protected User owner;
    private int X;
    private int Y;

    protected Objects(ObjectType objectType, User owner) {
        this.objectType = objectType;
        this.owner = owner;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public User getOwner() {
        return owner;
    }
}
