package model.ObjectsPackage.People.Soldier;

import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.People.Soldier.SoldierName;

public class Tunneler extends Soldier {
    public Tunneler(SoldierName type) {
        super(weapon, type); //TODO: fill weapon, isRider and range based on type
    }

    public void digTunnel(int x, int y) {
        //TODO: fill strategy
    }
}
