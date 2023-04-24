package model.ObjectsPackage;

public abstract class Objects {
    private int X;
    private int Y;
    private ObjectType objectType;

    protected Objects(ObjectType objectType) {
        this.objectType = objectType;
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
}
