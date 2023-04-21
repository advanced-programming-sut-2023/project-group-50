package model.ObjectsPackage.People.Soldier;

public enum SoldierName {
    ;
    private int attackPower;
    private int defensePower;
    private int cost;
    private int life;
    private int speed;

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
