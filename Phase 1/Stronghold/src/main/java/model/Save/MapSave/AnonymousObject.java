package model.Save.MapSave;

import controller.UserDatabase.User;
import model.Direction.Direction;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.*;

import java.io.Serializable;

public record AnonymousObject(ObjectType objectType, Object type) implements Serializable {
    public Objects getObject(User user, int x, int y) {
        switch (objectType) {
            case TREE -> {
                return new Tree((TreeType) type, user);
            }
            case ROCK -> {
                return new Rock((Direction) type, user);
            }
            case BUILDING -> {
                return Building.getBuildingByType((BuildingType) type, user, x, y);
            }
        }

        return null;
    }
}