package model.ObjectsPackage.People.Soldier;

import controller.UserDatabase.User;
import model.ObjectsPackage.Weapons.Weapon;

public class Tunneler extends Soldier {
    public Tunneler(SoldierName type, Weapon weapon, User owner) {
        super(weapon, type,owner); //TODO: fill weapon, isRider and range based on type
    }

    public void digTunnel(int x, int y) {
        //TODO: fill strategy
    }
}
