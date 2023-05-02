package model.ObjectsPackage.People.Soldier;

public class Infantry extends Soldier {
    private final ArmourType armourType;
    private final boolean canScaleWalls;
    private final boolean isRider;

    public Infantry(SoldierName type) {
        super(type);

        switch (type) {
            case MACEMAN -> armourType = ArmourType.LEATHER;
            case PIKEMAN, SWORDSMAN, KNIGHT, THE_LORD -> armourType = ArmourType.METAL;
            default -> armourType = ArmourType.NONE;
        }

        isRider = type == SoldierName.KNIGHT;
        canScaleWalls = (type == SoldierName.LADDERMAN) | (type == SoldierName.ASSASIN);
    }

    public void captureGate(int x, int y) {
        //TODO: fill strategy
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
