package model.ObjectsPackage.People.Soldier;

import controller.UserDatabase.User;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.Weapons.Weapon;
import model.ObjectsPackage.Weapons.WeaponName;

public abstract class Soldier extends Person {
    private final Weapon weapon;
    private final SoldierName type;
    private SoldierState soldierState;
    private boolean isPatrolling;
    private int[][] patrolPath; //[x,y][0,1]

    public Soldier(SoldierName type, User owner) {
        super(true, type.getLife(), type.getSpeed(), owner);
        weapon = new Weapon(getWeaponName(type), this, owner);
        this.type = type;
        soldierState = SoldierState.STANDING;
        isPatrolling = false;
        patrolPath = new int[2][2];
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
            case SWORDSMAN, KNIGHT, ARABIAN_SWORDSMAN, THE_LORD -> {
                return WeaponName.SWORDS;
            }
            case SLAVE, TUNNELER, LADDERMAN, ENGINEER, BLACK_MONK, ASSASIN -> {
                return null;
            }
            default -> throw new IllegalStateException("Unexpected value: " + soldierName);
        }
    }

    public SoldierState getSoldierState() {
        return soldierState;
    }

    public void setSoldierState(SoldierState soldierState) {
        this.soldierState = soldierState;
    }

    public void startPatrolling(int x1, int y1, int x2, int y2) {
        patrolPath = new int[][]{{x1, x2}, {y1, y2}};
        isPatrolling = true;
    }

    public void endPatrolling() {
        patrolPath = null;
        isPatrolling = false;
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

    public boolean isPatrolling() {
        return isPatrolling;
    }

    public void setPatrolling(boolean patrolling) {
        isPatrolling = patrolling;
    }

    public static Soldier getSoldierByType(SoldierName soldierName, User owner) {
        switch (soldierName) {
            case THE_LORD, SPEARMAN, PIKEMAN, MACEMAN, SWORDSMAN, KNIGHT, SLAVE, ARABIAN_SWORDSMAN,
                    LADDERMAN, ASSASIN, BLACK_MONK -> {
                return new Infantry(soldierName, owner);
            }
            case ARCHER, CROSSBOWMAN, ARABIAN_BOWMAN, FIRE_THROWER, HORSE_ARCHER, SLINGER -> {
                return new Archer(soldierName, owner);
            }
            case TUNNELER -> {
                return new Tunneler(owner);
            }
            case ENGINEER -> {
                return new Engineer(owner);
            }
            default -> throw new IllegalStateException("Unexpected value: " + soldierName);
        }
    }
}
