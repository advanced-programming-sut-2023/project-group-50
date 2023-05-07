package model.ObjectsPackage;

import controller.UserDatabase.User;

public class Tree extends Objects {
    private String type;

    protected Tree(String type, User owner) {
        super(ObjectType.TREE, owner);
        this.type = type;
    }

    public boolean canPlace(int x, int y) {
        switch (owner.getGovernment().getMap().getXY(x, y).getTexture()) {
            case GROUND, MEADOW, LAWN, GRASS, RIGGED_GROUND -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
