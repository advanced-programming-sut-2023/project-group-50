package model.ObjectsPackage.People;

import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;

public abstract class Person extends Objects {
    private boolean isSoldier;
    private int life;
    private int maxLife;
    private int speed;
    private int starving;
    private int income;
    protected Person(boolean isSoldier, int life, int speed) {
        super(ObjectType.PERSON);
        this.isSoldier = isSoldier;
        this.life = life;
        this.maxLife = life;
        this.speed = speed;
    }

    public void heal() {
        life = maxLife;
    }

    public boolean isAlive() {
        return life >= 0;
    }

    public boolean isSoldier() {
        return isSoldier;
    }

    public void setSoldier(boolean soldier) {
        isSoldier = soldier;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStarving() {
        return starving;
    }

    public void setStarving(int starving) {
        this.starving = starving;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}
