package model.ObjectsPackage.People.Soldier;

import controller.UserDatabase.User;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Buildings.Tunnel;

public class Tunneler extends Soldier {
    public Tunneler(User owner) {
        super(SoldierName.TUNNELER, owner);
    }

    public void digTunnel(int x, int y) {
        getOwner().getGovernment().getMap().addObject(
                new Tunnel(BuildingType.TUNNEL, owner, getX(), getY(), 0, getX(), getY(), x, y),
                getX(), getY()
        );
    }
}
