package model.RandomGenerator;


import model.ObjectsPackage.People.NonSoldier.Job;

public class RandomNonSoldier {
    //todo: fill images
    public static String getNonSoldier(Job job) {
        switch (job) {
            case LADY -> {
            }
            case JESTER -> {
            }
            case WOODCUTTER -> {
            }
            case HUNTER -> {
            }
            case FARMER -> {
            }
            case PEASANT -> {
            }
            case CHILD -> {
            }
            case MOTHER_AND_BABY -> {
            }
            case STONE_MASON -> {
            }
            case IRON_MINER -> {
            }
            case PITCH_DIGGER -> {
            }
            case MILL_BOY -> {
            }
            case BAKER -> {
            }
            case BREWER -> {
            }
            case INNKEEPER -> {
            }
            case DRUNKARD -> {
            }
            case FLETCHER -> {
            }
            case ARMORER -> {
            }
            case BLACKSMITH -> {
            }
            case POLETURNER -> {
            }
            case TANNER -> {
            }
            case PRIEST -> {
            }
            case HEALER -> {
            }
            case MARKER_TRADER -> {
            }
            case JUGGLER -> {
            }
            case FIRE_EATER -> {
            }
            default -> throw new IllegalStateException("Unexpected value: " + job);
        }
        return null;
    }
}
