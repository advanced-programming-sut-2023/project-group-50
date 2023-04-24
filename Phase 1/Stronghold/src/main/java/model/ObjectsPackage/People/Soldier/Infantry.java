package model.ObjectsPackage.People.Soldier;

import model.ObjectsPackage.Weapons.Weapon;

public class Infantry extends Soldier {
    private boolean isArmoured;
    private boolean isScaleWalls;
    private boolean isRider;

    public Infantry(Weapon weapon, SoldierName type) {
        super(weapon, type); //TODO: fill weapon, isRider, isArmoured, isScaleWalls based on type
    }

    public void captureGate(int x, int y) {
        //TODO: fill strategy
    }

    public boolean isArmoured() {
        return isArmoured;
    }

    public boolean isScaleWalls() {
        return isScaleWalls;
    }

    public boolean isRider() {
        return isRider;
    }
}
