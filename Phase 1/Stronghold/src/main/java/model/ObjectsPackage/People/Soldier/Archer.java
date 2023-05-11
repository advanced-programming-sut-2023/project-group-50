package model.ObjectsPackage.People.Soldier;

import controller.UserDatabase.User;
import model.Map.Map;
import model.ObjectsPackage.Objects;

public class Archer extends Soldier {
    private final boolean isRider;
    private final ArmourType armourType;
    private boolean isOnFire;
    private int range;

    public Archer(SoldierName type, User owner) {
        super(type, owner);
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

    public void attack(int x, int y) {
        assert Map.distance(getX(), getY(), x, y) <= range;

        for (Objects object : getOwner().getGovernment().getMap().getXY(x, y).getObjects())
            object.applyDamage(getType().getAttackPower());
    }

    public void defend(int x, int y) {
        assert Map.distance(getX(), getY(), x, y) <= range;

        for (Objects object : getOwner().getGovernment().getMap().getXY(x, y).getObjects())
            object.applyDamage(getType().getDefensePower());
    }

    public boolean isInRange(int x, int y) {
        return Map.distance(getX(), getY(), x, y) <= range;
    }
}
