package model.ObjectsPackage.Buildings;

import model.User;

public class Wells extends Building {
    protected Wells(BuildingType type, User owner, int x, int y, int maxHp) {
        super(type, owner, x, y, maxHp);
    }

    public String putOutFire(int x, int y) {
        //TODO: fill here
        return null;
    }
}
