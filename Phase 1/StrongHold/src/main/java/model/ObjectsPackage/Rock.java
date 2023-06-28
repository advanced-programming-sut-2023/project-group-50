package model.ObjectsPackage;

import controller.UserDatabase.User;
import javafx.scene.image.Image;
import model.Direction.Direction;
import model.Map.GroundType;
import model.RandomGenerator.RandomRock;

import java.net.URL;

public class Rock extends Objects {
    private Direction direction;

    public Rock(Direction direction, User owner) {
        super(ObjectType.ROCK, owner);
        this.direction = direction;
    }

    public static boolean canPlace(int x, int y, User user) {
        GroundType groundType = user.getGovernment().getMap().getXY(x, y).getTexture();

        return validGround(groundType);
    }

    private static boolean validGround(GroundType groundType) {
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

    public boolean canPlace(int x, int y) {
        GroundType groundType = this.getOwner().getGovernment().getMap().getXY(x, y).getTexture();

        return validGround(groundType);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Image getImage() {
        URL url = GroundType.class.getResource("/phase2-assets/" + RandomRock.getRandomRock());
        return new Image(url.toExternalForm());
    }

    public boolean canPlace(GroundType texture) {
        return validGround(texture);
    }
}
