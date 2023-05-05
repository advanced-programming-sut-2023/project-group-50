package model.ObjectsPackage.People.Soldier;

import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Weapons.Weapon;

public class Engineer extends Soldier {
    private int range;

    public Engineer(SoldierName type, Weapon weapon) {
        super(weapon, type); //TODO: fill weapon and range based on type
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
