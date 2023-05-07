package model.ObjectsPackage.People.Soldier;

import controller.UserDatabase.User;

public class Tunneler extends Soldier {
    public Tunneler(User owner) {
        super(SoldierName.TUNNELER, owner);
    }

    public void digTunnel(int x, int y) {
        //TODO: fill strategy
    }
}
