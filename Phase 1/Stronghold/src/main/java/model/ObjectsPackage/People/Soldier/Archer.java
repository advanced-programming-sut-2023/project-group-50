package model.ObjectsPackage.People.Soldier;

public class Archer extends Soldier {
    private boolean isRider;
    private boolean isOnFire;
    private int range;
    private ArmourType armourType;

    public Archer(SoldierName type) {
        super(type);
        isRider = type.equals(SoldierName.HORSE_ARCHER);
        isOnFire = type.equals(SoldierName.FIRE_THROWER);
        armourType = (type == SoldierName.CROSSBOWMAN) ? ArmourType.LEATHER : ArmourType.NONE;
    }

    public ArmourType getArmourType() {
        return armourType;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public boolean isRider() {
        return isRider;
    }

    public boolean isOnFire() {
        return isOnFire;
    }

    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }
}
