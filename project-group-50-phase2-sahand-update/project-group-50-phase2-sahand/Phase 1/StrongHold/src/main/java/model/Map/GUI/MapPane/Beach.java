package model.Map.GUI.MapPane;

import javafx.scene.image.Image;
import model.RandomGenerator.RandomGenerator;

public enum Beach {
    TOP_LEFT_CORNER,
    TOP_RIGHT_CORNER,
    BOTTOM_LEFT_CORNER,
    BOTTOM_RIGHT_CORNER,
    LEFT,
    RIGHT,
    TOP,
    BOTTOM;

    public Image getImage() {
        String path = getPath();
        return new Image(Beach.class.getResource(path).toExternalForm());
    }

    private String getPath() {
        switch (this) {
            case TOP_LEFT_CORNER -> {
                return getRandomTopLeft();
            }
            case TOP_RIGHT_CORNER -> {
                return getRandomTopRight();
            }
            case BOTTOM_LEFT_CORNER -> {
                return getRandomBottomLeft();
            }
            case BOTTOM_RIGHT_CORNER -> {
                return getRandomBottomRight();
            }
            case LEFT -> {
                return getRandomLeft();
            }
            case RIGHT -> {
                return getRandomRight();
            }
            case TOP -> {
                return getRandomTop();
            }
            case BOTTOM -> {
                return getRandomBottom();
            }
            default -> throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    private String getRandomTopLeft() {
        int rand = RandomGenerator.getRandomNumber(1, 4);
        return "/images/Beach/top_left/beach (" + rand + ").png";
    }

    private String getRandomTop() {
        int rand = RandomGenerator.getRandomNumber(1, 12);
        return "/images/Beach/top/beach (" + rand + ").png";
    }

    private String getRandomBottom() {
        int rand = RandomGenerator.getRandomNumber(1, 7);
        return "/images/Beach/bottom/beach (" + rand + ").png";
    }

    private String getRandomLeft() {
        int rand = RandomGenerator.getRandomNumber(1, 10);
        return "/images/Beach/left/beach (" + rand + ").png";
    }

    private String getRandomRight() {
        int rand = RandomGenerator.getRandomNumber(1, 8);
        return "/images/Beach/right/beach (" + rand + ").png";
    }

    private String getRandomBottomRight() {
        int rand = RandomGenerator.getRandomNumber(1, 6);
        return "/images/Beach/bottom_right/beach (" + rand + ").png";
    }

    private String getRandomTopRight() {
        int rand = RandomGenerator.getRandomNumber(1, 6);
        return "/images/Beach/top_right/beach (" + rand + ").png";
    }

    private String getRandomBottomLeft() {
        int rand = RandomGenerator.getRandomNumber(1, 5);
        return "/images/Beach/bottom_left/beach (" + rand + ").png";
    }
}
