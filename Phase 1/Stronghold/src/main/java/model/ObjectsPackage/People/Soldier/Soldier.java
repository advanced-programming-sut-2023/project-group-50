package model.ObjectsPackage.People.Soldier;

import controller.UserDatabase.User;
import model.Map.GroundType;
import model.Map.Map;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.Weapons.Weapon;
import model.ObjectsPackage.Weapons.WeaponName;

public abstract class Soldier extends Person {
    private final Weapon weapon;
    private final SoldierName type;
    private SoldierState soldierState;
    private boolean isPatrolling;
    private int[][] patrolPath; //[x,y][0,1]
    private boolean going;

    public Soldier(SoldierName type, User owner) {
        super(true, type.getLife(), type.getSpeed(), owner);
        weapon = new Weapon(getWeaponName(type), this, owner);
        this.type = type;
        soldierState = SoldierState.STANDING;
        isPatrolling = false;
        patrolPath = new int[2][2];
        going = false;
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
                return WeaponName.HAND;
            }
            case LOOSE_WAR_DOG -> {
                return WeaponName.TEETH;
            }
            default -> throw new IllegalStateException("Unexpected value: " + soldierName);
        }
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
            case LOOSE_WAR_DOG -> {
                return new Infantry(SoldierName.LOOSE_WAR_DOG, owner);
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

    public void patrol() {
        int x = getX(), y = getY();

        if (x == patrolPath[0][0] && y == patrolPath[1][0]) {
            going = true;
            moveClosest(patrolPath[0][1], patrolPath[1][1]);
        } else if (x == patrolPath[0][1] && y == patrolPath[1][1]) {
            going = false;
            moveClosest(patrolPath[0][0], patrolPath[1][0]);
        } else {
            if (going) moveClosest(patrolPath[0][1], patrolPath[1][1]);
            else moveClosest(patrolPath[0][0], patrolPath[1][0]);
        }
    }

    private void moveClosest(int xFinal, int yFinal) {
        if (Map.distance(getX(), getY(), xFinal, yFinal) <= type.getSpeed()) {
            move(xFinal, yFinal);
            return;
        }

        int dx = type.getSpeed() / 2;
        int dy = type.getSpeed() - dx;

        if (xFinal < getX()) setX(getX() - dx);
        else setX(getX() + dx);

        if (yFinal < getY()) setY(getY() - dy);
        else setY(getY() + dy);
    }

    public void move(int xFinal, int yFinal) {
        GroundType texture = getOwner().getGovernment().getMap().getXY(xFinal, yFinal).getTexture();
        if (texture == GroundType.RIVER || texture == GroundType.SEA || texture == GroundType.BIG_POND) return;
        if (Map.distance(getX(), getY(), xFinal, yFinal) > type.getSpeed()) return;
        setX(xFinal);
        setY(yFinal);
    }

    public void endPatrolling() {
        patrolPath = null;
        isPatrolling = false;
    }

    public boolean canPlace(int x, int y) {
        GroundType groundType = owner.getGovernment().getMap().getXY(x, y).getTexture();
        switch (groundType) {
            case GROUND, BEACH, RIVER, SHALLOW_WATER, PLAIN, MEADOW, LAWN, GRASS, RIGGED_GROUND -> {
                return true;
            }
            case CLIFF, SEA, BIG_POND, SMALL_POND, OIL, IRON, STONE -> {
                return false;
            }
            default -> throw new IllegalStateException("Unexpected value: " + groundType);
        }
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

    public void attack(Objects enemy) {
        enemy.applyDamage(type.getAttackPower());
    }

    public void defend(Objects enemy) {
        enemy.applyDamage(type.getDefensePower());
    }
}
