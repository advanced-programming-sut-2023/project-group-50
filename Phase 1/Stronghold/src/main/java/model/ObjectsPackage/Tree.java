package model.ObjectsPackage;

import controller.UserDatabase.User;
import model.Map.Map;

public class Tree extends Objects {
    private TreeType type;

    public Tree(TreeType type, User owner) {
        super(ObjectType.TREE, owner);
        this.type = type;
    }

    public static boolean isValidType(String type) {
        return TreeType.get(type) != null;

    }


    public static boolean canPlace(int x, int y, User owner) {
        Map map = owner.getGovernment().getMap();

        switch (map.getXY(x, y).getTexture()) {
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
