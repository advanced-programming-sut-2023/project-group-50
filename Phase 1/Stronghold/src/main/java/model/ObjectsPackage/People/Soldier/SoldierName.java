package model.ObjectsPackage.People.Soldier;

public enum SoldierName {
    //TODO: fill speed and range and...
    ARCHER(0,0,0,0,0),
    CROSSBOWMAN(0,0,0,0,0),
    SPEARMAN(0,0,0,0,0),
    PIKEMAN(0,0,0,0,0),
    MACEMAN(0,0,0,0,0),
    SWORDSMAN(0,0,0,0,0),
    KNIGHT(0,0,0,0,0),
    TUNNELER(0,0,0,0,0),
    LADDERMAN(0,0,0,0,0),
    ENGINEER(0,0,0,0,0),
    BLACK_MONK(0,0,0,0,0),
    ARABIAN_BOWMAN(0,0,0,0,0),
    SLAVE(0,0,0,0,0),
    SLINGER(0,0,0,0,0),
    ASSASIN(0,0,0,0,0),
    HORSE_ARCHER(0,0,0,0,0),
    ARABIAN_SWORDSMAN(0,0,0,0,0),
    FIRE_THROWER(0,0,0,0,0)
    ;
    private final int attackPower;
    private final int defensePower;
    private final int cost;
    private final int life;
    private final int speed;

    SoldierName(int attackPower, int defensePower, int cost, int life, int speed) {
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.cost = cost;
        this.life = life;
        this.speed = speed;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefensePower() {
        return defensePower;
    }

    public int getCost() {
        return cost;
    }

    public int getLife() {
        return life;
    }

    public int getSpeed() {
        return speed;
    }
}
