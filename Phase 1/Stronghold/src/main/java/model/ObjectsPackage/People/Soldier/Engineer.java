package model.ObjectsPackage.People.Soldier;

import controller.UserDatabase.User;
import model.ObjectsPackage.Buildings.BuildingType;

public class Engineer extends Soldier {
    private int range;

    public Engineer(User owner) {
        super(SoldierName.ENGINEER, owner); //TODO: fill range based on type
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
