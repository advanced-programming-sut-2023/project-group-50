package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;
import model.Government.Government;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.People.Soldier.SoldierName;
import model.ObjectsPackage.Weapons.WeaponName;

import java.util.ArrayList;
import java.util.HashMap;

public class OffensiveStorage extends Building {
    private final HashMap<WeaponName, Integer> weaponCount;
    private final int defendRange;
    private final int capacity;
    private final int currentCapacity;
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
        currentCapacity = 0;
        weaponCount = new HashMap<>();
    }

    public void trainTroops(SoldierName soldierName, int count) {
        assert count + currentCapacity <= capacity;
        assert hasMoneyForTroops(soldierName, count);
        assert hasEnoughArmourForTroops(soldierName, count);

        int cost = soldierName.getCoinCost() * count;
        Government government = getOwner().getGovernment();

        government.setCoins(government.getCoins() - cost);
        ArrayList<Soldier> soldiers = getNewSoldiers(soldierName, count);
        government.addUnDeployedSoldier(soldiers);
    }

    private ArrayList<Soldier> getNewSoldiers(SoldierName soldierName, int count) {
        ArrayList<Soldier> soldiers = new ArrayList<>();
        for (int i = 0; i < count; i++)
            soldiers.add(Soldier.getSoldierByType(soldierName, getOwner()));
        return soldiers;
    }

    private boolean hasMoneyForTroops(SoldierName soldierName, int count) {
        return getOwner().getGovernment().getCoins() >= soldierName.getCoinCost() * count;
    }

    private boolean hasMoneyForEngineer(int count) {
        return getOwner().getGovernment().getCoins() >= costOfEngineer * count;
    }

    private boolean hasMoneyForLadderman(int count) {
        return getOwner().getGovernment().getCoins() >= costOfLadderman * count;
    }

    private boolean hasEnoughArmourForTroops(SoldierName soldierName, int count) {
        return getOwner().getGovernment().getArmourAmount(soldierName.getArmourType()) >= count;
    }

    public void hireEngineer(int count) {
        assert count + currentCapacity <= capacity;
        assert hasMoneyForEngineer(count);

        int cost = costOfEngineer * count;
        Government government = getOwner().getGovernment();

        government.setCoins(government.getCoins() - cost);
        ArrayList<Soldier> soldiers = getNewSoldiers(SoldierName.ENGINEER, count);
        government.addUnDeployedSoldier(soldiers);
    }

    public void hireLadderman(int count) {
        assert count + currentCapacity <= capacity;
        assert hasMoneyForLadderman(count);

        int cost = costOfLadderman * count;
        Government government = getOwner().getGovernment();

        government.setCoins(government.getCoins() - cost);
        ArrayList<Soldier> soldiers = getNewSoldiers(SoldierName.LADDERMAN, count);
        government.addUnDeployedSoldier(soldiers);
    }
}
