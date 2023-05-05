package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;

import java.util.HashMap;

public abstract class Building extends Objects {
    private final BuildingType type;
    private final User owner;
    private final int X;
    private final int Y;
    private final int maxHp;
    private final HashMap<String, Integer> residents;
    private int hp;

    protected Building(BuildingType type, User owner, int x, int y, int maxHp) {
        super(ObjectType.BUILDING);
        this.type = type;
        this.owner = owner;
        X = x;
        Y = y;
        this.maxHp = maxHp;
        hp = maxHp;
        residents = new HashMap<>();
    }

    private void repair() {
        hp = maxHp;
    }

    private boolean isDestroyed() {
        return hp <= 0;
    }

    private int getNumberOfResidents() {
        return residents.size();
    }

    public BuildingType getType() {
        return type;
    }

    public User getOwner() {
        return owner;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public HashMap<String, Integer> getResidents() {
        return residents;
    }

    public static Building getBuildingByType(BuildingType buildingType,int x, int y){


        return null;
    }




}
