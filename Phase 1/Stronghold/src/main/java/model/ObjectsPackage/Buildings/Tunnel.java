package model.ObjectsPackage.Buildings;

import model.User;

public class Tunnel extends Building {
    private int xFrom;
    private int yFrom;
    private int xTo;
    private int yTo;
    protected Tunnel(BuildingType type, User owner, int x, int y, int maxHp, int xFrom, int yFrom, int xTo, int yTo) {
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
