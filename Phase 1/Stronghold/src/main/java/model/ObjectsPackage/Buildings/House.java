package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;
import model.ObjectsPackage.People.Soldier.SoldierName;

import java.util.HashMap;

public class House extends Building {
    private final int capacity;
    private final HashMap<SoldierName, Integer> soldierCount;

    protected House(BuildingType type, User owner, int x, int y, int maxHp, int capacity) {
        super(type, owner, x, y, maxHp);
        this.capacity = capacity;
        soldierCount = new HashMap<>();
    }

    public boolean canDefendItself() {
        //TODO: fill here
        return false;
    }

    public boolean canIncreasePopularity() {
        //TODO: fill here
        return false;
    }

    public int getSoldierCount(SoldierName soldierName) {
        return soldierCount.getOrDefault(soldierName, 0);
    }


    /**
     * be aware that this function ADDS to the current capacity
     */
    public void addSoldierCount(SoldierName soldierName, int count) {
        int initCount = soldierCount.getOrDefault(soldierName, 0);
        soldierCount.put(soldierName, initCount);
    }

}
