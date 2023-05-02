package model.ObjectsPackage;

public class Rock extends Objects {
    private String direction;

    protected Rock(String direction) {
        super(ObjectType.ROCK, owner);
        this.direction = direction;
    }

    public boolean canPlace(int x, int y) {
        //TODO: complete this when map is done
        return true;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
