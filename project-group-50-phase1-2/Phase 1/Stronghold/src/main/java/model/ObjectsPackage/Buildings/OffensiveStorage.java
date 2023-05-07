package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;
import model.ObjectsPackage.Weapons.WeaponName;

import java.util.HashMap;

public class OffensiveStorage extends Building {
    private final HashMap<WeaponName, Integer> weaponCount;
    private final int defendRange;
    private final int capacity;
    private final int cost;
    private final int costOfLadderman;
    private final int costOfEngineer;
    private final int damage;
    private final int rate;

    protected OffensiveStorage(BuildingType type,
                               User owner,
                               int x,
                               int y,
                               int maxHp,
                               int defendRange,
                               int capacity,
                               int cost,
                               int costOfLadderman,
                               int costOfEngineer,
                               int damage,
                               int rate) {
        super(type, owner, x, y, maxHp);
        this.defendRange = defendRange;
        this.capacity = capacity;
        this.cost = cost;
        this.costOfLadderman = costOfLadderman;
        this.costOfEngineer = costOfEngineer;
        this.damage = damage;
        this.rate = rate;
        weaponCount = new HashMap<>();
    }

    public String trainTroops(int count, OffensiveStorage armoury) {
        // TODO: fill here
        return null;
    }

    public String buyTroop(int count, int gold) {
        //TODO: fill here
        return null;
    }

    public String hireEngineer() {
        //TODO: fill here
        return null;
    }

    public String hireLadderman() {
        //TODO: fill here
        return null;
    }

    public String kill(User opponent) {
        //TODO: fill here
        return null;
    }


}
