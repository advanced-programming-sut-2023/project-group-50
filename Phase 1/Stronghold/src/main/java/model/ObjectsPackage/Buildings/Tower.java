package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;

public class Tower extends Building {
    private int fireRange;
    private int defendRange;

    protected Tower(BuildingType type, User owner, int x, int y, int maxHp, int fireRange, int defendRange) {
        super(type, owner, x, y, maxHp);
        this.fireRange = fireRange;
        this.defendRange = defendRange;
    }

    public int getFireRange() {
        return fireRange;
    }

    public void setFireRange(int fireRange) {
        this.fireRange = fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }

    public void setDefendRange(int defendRange) {
        this.defendRange = defendRange;
    }
}
