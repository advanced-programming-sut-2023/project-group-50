package model.Direction;

import java.util.Random;

public enum Direction {
    UP,
    LEFT,
    RIGHT,
    DOWN;

    public static Direction get(String d) {
        switch (d) {
            case "n" -> {
                return UP;
            }
            case "e" -> {
                return RIGHT;
            }
            case "w" -> {
                return LEFT;
            }
            case "s" -> {
                return DOWN;
            }
            case "r" -> {
                return getRandomDirection();
            }
        }

        return null;
    }

    public static Direction getRandomDirection() {
        Direction[] values = Direction.values();
        Random rand = new Random();
        return values[rand.nextInt(values.length)];
    }
}
