package model.ObjectsPackage.Buildings;

import model.ObjectsPackage.Weapons.WeaponName;
import model.User;

import java.util.HashMap;

public class OffensiveStorage extends Building {
    private HashMap<WeaponName, Integer> weaponCount;
    private int defendRange;
    private int capacity;
    private int cost;
    private int costOfLadderman;
    private int costOfEngineer;
    private int damage;
    private int rate;
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
