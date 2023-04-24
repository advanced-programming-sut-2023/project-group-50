package model.ObjectsPackage.Buildings;

import model.User;

public class ReligiousBuilding extends Building {
    private int popularity;
    protected ReligiousBuilding(BuildingType type, User owner, int x, int y, int maxHp, int popularity) {
        super(type, owner, x, y, maxHp);
        this.popularity = popularity;
    }

    public boolean isCathedral() {
        //TODO: fill here
        return true;
    }

    public String trainMonks(int count) {
        //TODO: fill here
        return null;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
