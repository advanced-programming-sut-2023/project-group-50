package model.Direction;

public enum Direction {
    UP,
    LEFT,
    RIGHT,
    DOWN;

    public static Direction get(String d) {
        for (Direction direction : Direction.values())
            if (direction.name().toLowerCase().equals(d))
                return direction;

        return null;
    }
}
