package model.ObjectsPackage;

import controller.UserDatabase.User;
import model.Map.GroundType;

public class Tree extends Objects {
    private String type;

    public Tree(String type, User owner) {
        super(ObjectType.TREE, owner);
        this.type = type;
    }

    public boolean canPlace(GroundType groundType) {
        switch (groundType) {
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


    public static boolean isValidType(String type){
        switch (type){
            case "desert shrub","cherry tree","olive tree","coconut palm","palm tree"->{
                return true;
            }

            default -> {
                return false;
            }
        }

    }
}
