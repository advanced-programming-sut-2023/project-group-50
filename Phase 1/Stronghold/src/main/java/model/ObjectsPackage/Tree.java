package model.ObjectsPackage;

import controller.UserDatabase.User;

public class Tree extends Objects {
    private TreeType type;

    public Tree(TreeType type, User owner) {
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

    public TreeType getType() {
        return type;
    }

    public void setType(TreeType type) {
        this.type = type;
    }
}
