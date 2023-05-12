package model.ObjectsPackage.People;

import controller.UserDatabase.User;
import model.Map.GroundType;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;

public abstract class Person extends Objects {
    private final int maxLife;
    private boolean isSoldier;
    private int life;
    private int speed;
    private int starving;
    private double income;

    protected Person(boolean isSoldier, int life, int speed, User owner) {
        super(ObjectType.PERSON, owner);
        this.isSoldier = isSoldier;
        this.life = life;
        this.maxLife = life;
        this.speed = speed;
    }

    public static boolean canPlace(GroundType texture) {
        switch (texture) {
            case GROUND, BEACH, SHALLOW_WATER, PLAIN, MEADOW, LAWN, GRASS, IRON, RIGGED_GROUND -> {
                return true;
            }
            case CLIFF, SEA, BIG_POND, SMALL_POND, RIVER, OIL, STONE -> {
                return false;
            }
            default -> throw new IllegalStateException("Unexpected value: " + texture);
        }
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

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
