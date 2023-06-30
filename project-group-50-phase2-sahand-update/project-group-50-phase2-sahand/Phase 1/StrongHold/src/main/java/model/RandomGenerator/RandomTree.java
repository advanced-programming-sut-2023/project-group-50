package model.RandomGenerator;

import model.ObjectsPackage.TreeType;

public class RandomTree {
    private static String getRandomShrub() {
        return RandomGenerator.randomFrom("Plants/tree_shrub2.gm1/0_0img0.png",
                                          "Plants/tree_shrub1.gm1/0_0img0.png");
    }

    private static String getRandomBirch() {
        int randomNumber = RandomGenerator.getRandomNumber(1, 56);
        return "Plants/tree_birch.gm1/birch (" + randomNumber + ").png";
    }

    private static String getRandomOak() {
        int randomNumber = RandomGenerator.getRandomNumber(1, 53);
        return "Plants/tree_oak.gm1/oak (" + randomNumber + ").png";
    }

    private static String getRandomApple() {
        int randomNumber = RandomGenerator.getRandomNumber(1, 13);
        return "Plants/tree_apple.gm1/apple (" + randomNumber + ").png";
    }

    public static String getRandomTree(TreeType type) {
        switch (type) {
            case DESERT_SHRUB -> {
                return getRandomShrub();
            }
            case CHERRY_PALM -> {
                return getRandomApple();
            }
            case OLIVE_TREE -> {
                return getRandomOak();
            }
            case COCONUT_PALM, DATE_PALM -> {
                return getRandomBirch();
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
