package model.ObjectsPackage.People.Soldier;

import model.ObjectsPackage.Weapons.Weapon;

public class Tunneler extends Soldier {
    public Tunneler(SoldierName type, Weapon weapon) {
        super(weapon, type); //TODO: fill weapon, isRider and range based on type
    }

    public void digTunnel(int x, int y) {
        //TODO: fill strategy
    }
}
