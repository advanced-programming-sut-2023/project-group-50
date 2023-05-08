package model.ObjectsPackage.People.Soldier;

import controller.UserDatabase.User;
import model.ObjectsPackage.Buildings.Gate;

public class Infantry extends Soldier {
    private final ArmourType armourType;
    private final boolean canScaleWalls;
    private final boolean isRider;

    public Infantry(SoldierName type, User owner) {
        super(type, owner);

        switch (type) {
            case MACEMAN -> armourType = ArmourType.LEATHER;
            case PIKEMAN, SWORDSMAN, KNIGHT, THE_LORD -> armourType = ArmourType.METAL;
            default -> armourType = ArmourType.NONE;
        }

        isRider = type == SoldierName.KNIGHT;
        canScaleWalls = (type == SoldierName.LADDERMAN) | (type == SoldierName.ASSASIN);
    }

    public void captureGate(Gate gate) {
        int x = gate.getX(), y = gate.getY();
        move(x, y);
        if (getX() == x || getY() == y)
            attack(gate);
    }

    public ArmourType getArmourType() {
        return armourType;
    }

    public boolean canScaleWalls() {
        return canScaleWalls;
    }

    public boolean isRider() {
        return isRider;
    }
}
