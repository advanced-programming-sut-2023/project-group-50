package model.ObjectsPackage.People.Soldier;

import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Weapons.Weapon;

public class Engineer extends Soldier {
    private int range;

    public Engineer() {
        super(SoldierName.ENGINEER); //TODO: fill range based on type
    }

    //TODO: fill strategies

    public void build(BuildingType building) {
    }

    public void pourOil(String Direction) {

    }

    public void inChargeOil(int x, int y) {

    }

    public void makeProtection(int x, int y) {

    }

    public void makeHammer(int x, int y) {

    }
}
