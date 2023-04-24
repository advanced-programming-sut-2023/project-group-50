package model.ObjectsPackage.Buildings;

import model.User;

public class Workshops extends Building {
    private String resource;
    private String output;
    private int rate;
    protected Workshops(BuildingType type, User owner, int x, int y, int maxHp, int rate) {
        super(type, owner, x, y, maxHp);
        this.rate = rate;
        //TODO: fill resource and output
    }

    public String makeWeapon() {
        //TODO: fill here
        return null;
    }

    public String getResource() {
        return resource;
    }

    public String getOutput() {
        return output;
    }

    public int getRate() {
        return rate;
    }
}
