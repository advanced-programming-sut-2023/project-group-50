package model.ObjectsPackage.Buildings;

import model.User;

public class Mine extends Building {
    private final int rate;

    protected Mine(BuildingType type, User owner, int x, int y, int maxHp, int rate) {
        super(type, owner, x, y, maxHp);
        this.rate = rate;
    }

    public String produce(String resource) {
        //TODO: fill here
        return null;
    }
}
