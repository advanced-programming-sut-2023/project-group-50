package model.ObjectsPackage.Buildings;

import model.ObjectsPackage.People.Soldier.SoldierName;
import model.User;

import java.util.HashMap;

public class Barracks extends Building {
    private HashMap<SoldierName, Integer> soldiersCount;
    protected Barracks(User owner, int x, int y, int maxHp) {
        super(BuildingType.BARRACKS, owner, x, y, maxHp);
        soldiersCount = new HashMap<>();
    }

    public HashMap<SoldierName, Integer> soldiersCount() {
        return soldiersCount;
    }

    public void setSoldiersCount(HashMap<SoldierName, Integer> soldiersCount) {
        this.soldiersCount = soldiersCount;
    }
}
