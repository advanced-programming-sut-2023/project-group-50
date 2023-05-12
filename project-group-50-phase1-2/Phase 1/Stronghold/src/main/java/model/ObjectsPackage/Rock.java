package model.ObjectsPackage;

import controller.UserDatabase.User;
import controller.control.Error;
import model.Map.GroundType;
import model.Map.Unit;

public class Rock extends Objects {
    private String direction;

    public Rock(String direction, User owner) {
        super(ObjectType.ROCK, owner);
        this.direction = direction;
    }



    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
