package model.ObjectsPackage.People.Soldier;

import model.ObjectsPackage.Weapons.Weapon;

public class Archer extends Soldier {
    private boolean isRider;
    private int range;

    public Archer(SoldierName type, Weapon weapon) {
        super(weapon, type); //TODO: fill weapon, isRider and range based on type
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public boolean isRider() {
        return isRider;
    }
}
