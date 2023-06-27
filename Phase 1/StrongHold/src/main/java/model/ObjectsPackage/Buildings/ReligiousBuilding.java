package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.People.Soldier.SoldierName;

import java.util.ArrayList;

public class ReligiousBuilding extends Building {
    private int popularity;

    protected ReligiousBuilding(BuildingType type, User owner, int x, int y, int maxHp, int popularity) {
        super(type, owner, x, y, maxHp);
        this.popularity = popularity;
    }

    public boolean isCathedral() {
        return getType().equals(BuildingType.CATHEDRAL);
    }

    public void trainMonks(int count) {
        ArrayList<Soldier> newSoldiers = new ArrayList<>();
        for (int i = 0; i < count; i++) newSoldiers.add(Soldier.getSoldierByType(SoldierName.BLACK_MONK, getOwner()));
        getOwner().getGovernment().addUnDeployedSoldier(newSoldiers);
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
