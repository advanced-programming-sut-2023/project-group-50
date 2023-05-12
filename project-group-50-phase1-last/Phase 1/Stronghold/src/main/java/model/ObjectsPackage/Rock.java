package model.ObjectsPackage;

import controller.UserDatabase.User;
import model.Direction.Direction;
import model.Map.GroundType;

public class Rock extends Objects {
    private Direction direction;

    public Rock(Direction direction, User owner) {
        super(ObjectType.ROCK, owner);
        this.direction = direction;
    }

    public boolean canPlace(int x, int y) {
        GroundType groundType = this.getOwner().getGovernment().getMap().getXY(x, y).getTexture();

        switch (groundType) {
            case GROUND, BEACH, PLAIN, MEADOW, LAWN, GRASS, RIGGED_GROUND -> {
                return true;
            }

            case CLIFF, SEA, BIG_POND, SMALL_POND, RIVER, SHALLOW_WATER, OIL, IRON, STONE -> {
                return false;
            }
            default -> throw new IllegalStateException("Unexpected value: " + groundType);
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
