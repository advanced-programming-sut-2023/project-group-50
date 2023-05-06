package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;

public class Tunnel extends Building {
    private final int xFrom;
    private final int yFrom;
    private final int xTo;
    private final int yTo;

    public Tunnel(BuildingType type, User owner, int x, int y, int maxHp, int xFrom, int yFrom, int xTo, int yTo) {
        super(type, owner, x, y, maxHp);
        this.xFrom = xFrom;
        this.yFrom = yFrom;
        this.xTo = xTo;
        this.yTo = yTo;
    }

    public String attack() {
        //TODO: fill here
        return null;
    }

    public String setOnFire() {
        //TODO: fill here
        return null;
    }
}
