package model.ObjectsPackage.People.Soldier;

public enum SoldierName {

    ARCHER(0, 0, 0, 0, 0,"Archer"),
    CROSSBOWMAN(0, 0, 0, 0, 0,"Crossbowmen"),
    SPEARMAN(0, 0, 0, 0, 0,"Spearmen"),
    PIKEMAN(0, 0, 0, 0, 0,"Pikemen"),
    MACEMAN(0, 0, 0, 0, 0,"Macemen"),
    SWORDSMAN(0, 0, 0, 0, 0,"Swordsmen"),
    KNIGHT(0, 0, 0, 0, 0,"Knight"),
    TUNNELER(0, 0, 0, 0, 0,"Tunneler"),
    LADDERMAN(0, 0, 0, 0, 0,"Laddermen"),
    ENGINEER(0, 0, 0, 0, 0,"Engineer"),
    BLACK_MONK(0, 0, 0, 0, 0,"Black Monk"),


    ARABIAN_BOWMAN(0, 0, 0, 0, 0,"Archer Bow"),
    SLAVE(0, 0, 0, 0, 0,"Slaves"),
    SLINGER(0, 0, 0, 0, 0,"Slingers"),
    ASSASIN(0, 0, 0, 0, 0,"Assassins"),
    HORSE_ARCHER(0, 0, 0, 0, 0,"Horse Archers"),
    ARABIAN_SWORDSMAN(0, 0, 0, 0, 0,"Arabian Swordsmen"),
    FIRE_THROWER(0, 0, 0, 0, 0,"Fire Throwers");
    private final int attackPower;
    private final int defensePower;
    private final int cost;
    private final int life;
    private final int speed;
    private final  String type;

    SoldierName(int attackPower, int defensePower, int cost, int life, int speed,String type) {
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.cost = cost;
        this.life = life;
        this.speed = speed;
        this.type=type;
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

    public String getType() {
        return type;
    }

    public static SoldierName getSoldierNameByType(String type){
        for (SoldierName soldierName:SoldierName.values ()){
            if(soldierName.type.equals (type)){
                return soldierName;
            }
        }

        return null;
    }

    public static boolean isArab(SoldierName soldierName){
       if( soldierName.ordinal ()>10){
           return true;
       }
       return false;
    }
}
