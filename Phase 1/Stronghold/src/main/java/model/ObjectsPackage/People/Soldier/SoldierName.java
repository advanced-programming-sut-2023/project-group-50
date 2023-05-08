package model.ObjectsPackage.People.Soldier;

import java.util.ArrayList;

public enum SoldierName {
    THE_LORD(100, 100, 0, 100, 55),
    ARCHER(40, 45, 12, 30, 90),
    CROSSBOWMAN(70, 75, 20, 50, 55),
    SPEARMAN(50, 50, 8, 40, 60),
    PIKEMAN(76, 77, 20, 80, 60),
    MACEMAN(75, 70, 20, 50, 85),
    SWORDSMAN(94, 94, 40, 80, 40),
    KNIGHT(94, 90, 40, 80, 95),
    ARABIAN_BOWMAN(45, 45, 75, 40, 90),
    SLAVE(10, 20, 5, 10, 90),
    TUNNELER(0, 0, 0, 0, 0),
    LADDERMAN(0, 0, 4, 10, 90),
    ENGINEER(0, 0, 0, 0, 0),
    BLACK_MONK(75, 75, 10, 30, 30),
    SLINGER(36, 20, 12, 30, 90),
    ASSASIN(76, 73, 60, 40, 67),
    HORSE_ARCHER(50, 55, 80, 30, 95),
    ARABIAN_SWORDSMAN(88, 88, 80, 80, 40),
    FIRE_THROWER(84, 60, 100, 30, 60),
    LOOSE_WAR_DOG(70, 70, 0, 50, 70);
    private final int attackPower;
    private final int defensePower;
    private final int goldCost;
    private final int life;
    private final int speed;
    private final String type;

    SoldierName(int attackPower, int defensePower, int goldCost, int life, int speed) {
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.goldCost = goldCost;
        this.life = life;
        this.speed = speed;
        this.type = getName(this.name());
    }

    public static SoldierName getSoldierNameByType(String type) {
        for (SoldierName soldierName : SoldierName.values()) {
            if (soldierName.type.equals(type)) {
                return soldierName;
            }
        }

        return null;
    }

    public static boolean isArab(SoldierName soldierName) {
        return soldierName.ordinal() > 10;
    }

    private String getName(String name) {
        ArrayList<String> Words = new ArrayList<>();
        for (String word : name.replaceAll("_", " ").toLowerCase().split(" "))
            Words.add(Character.toUpperCase(word.charAt(0)) + word.substring(1));
        return String.join(" ", Words);
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefensePower() {
        return defensePower;
    }

    public int getGoldCost() {
        return goldCost;
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
}
