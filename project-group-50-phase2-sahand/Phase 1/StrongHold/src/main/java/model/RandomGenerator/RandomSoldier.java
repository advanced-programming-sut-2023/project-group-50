package model.RandomGenerator;

import model.ObjectsPackage.People.Soldier.SoldierName;

public class RandomSoldier {
    //todo: fill images
    public static String getSoldier(SoldierName soldierName) {
        switch (soldierName) {
            case THE_LORD -> {
            }
            case ARCHER -> {
            }
            case CROSSBOWMAN -> {
            }
            case SPEARMAN -> {
            }
            case PIKEMAN -> {
            }
            case MACEMAN -> {
            }
            case SWORDSMAN -> {
            }
            case KNIGHT -> {
            }
            case ARABIAN_BOWMAN -> {
            }
            case SLAVE -> {
            }
            case TUNNELER -> {
            }
            case LADDERMAN -> {
            }
            case ENGINEER -> {
            }
            case BLACK_MONK -> {
            }
            case SLINGER -> {
            }
            case ASSASIN -> {
            }
            case HORSE_ARCHER -> {
            }
            case ARABIAN_SWORDSMAN -> {
            }
            case FIRE_THROWER -> {
            }
            case LOOSE_WAR_DOG -> {
            }
            default -> throw new IllegalStateException("Unexpected value: " + soldierName);
        }
        return null;
    }
}
