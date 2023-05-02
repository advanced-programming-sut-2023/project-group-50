package model.ObjectsPackage.People.Soldier;

import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.Weapons.Weapon;
import model.ObjectsPackage.Weapons.WeaponName;

public abstract class Soldier extends Person {
    private Weapon weapon;
    private SoldierName type;

    public Soldier(SoldierName type) {
        super(true, type.getLife(), type.getSpeed());
        weapon = new Weapon(getWeaponName(type), this);
        this.type = type;
    }

    public static WeaponName getWeaponName(SoldierName soldierName) {
        switch (soldierName) {
            case ARCHER, ARABIAN_BOWMAN, SLINGER, HORSE_ARCHER, FIRE_THROWER -> {
                return WeaponName.BOW;
            }
            case CROSSBOWMAN -> {
                return WeaponName.CROSSBOW;
            }
            case SPEARMAN -> {
                return WeaponName.SPEAR;
            }
            case PIKEMAN -> {
                return WeaponName.PIKE;
            }
            case MACEMAN -> {
                return WeaponName.MACE;
            }
            case SWORDSMAN, KNIGHT, ARABIAN_SWORDSMAN -> {
                return WeaponName.SWORDS;
            }
            case SLAVE, TUNNELER, LADDERMAN, ENGINEER, BLACK_MONK, ASSASIN -> {
                return null;
            }
            default -> throw new IllegalStateException("Unexpected value: " + soldierName);
        }
    }

    public void patrol(int x1, int y1, int x2, int y2) {
        //TODO: fill here
    }

    public boolean canPlace(int x, int y) {
        //TODO: complete this when map is done
        return true;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public SoldierName getType() {
        return type;
    }
}
