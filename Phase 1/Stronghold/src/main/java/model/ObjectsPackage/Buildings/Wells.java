package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;

public class Wells extends Building {
    protected Wells(BuildingType type, User owner, int x, int y, int maxHp) {
        super(type, owner, x, y, maxHp);
    }

    public void putOutFire(int x, int y) {
        getOwner().getGovernment().getMap().getXY(x, y).setOnFire(false);
    }
}
