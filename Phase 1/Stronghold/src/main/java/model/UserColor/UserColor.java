package model.UserColor;

import javafx.scene.paint.Color;
import model.ObjectsPackage.People.Soldier.SoldierName;

public enum UserColor {
    BLUE,
    BLACK,
    RED,
    YELLOW,
    WHITE,
    PURPLE,
    GREEN,
    BROWN;

    private final String type;

    UserColor() {
        type = SoldierName.getName(this.name());
    }

    public static UserColor getColorByName(String line) {
        for (UserColor userColor : values())
            if (userColor.type.equals(line))
                return userColor;

        return null;
    }

    public String getName() {
        return type;
    }

    public Color toColor() {
        switch (this) {
            case BLUE -> {
                return Color.BLUE;
            }
            case BLACK -> {
                return Color.BLACK;
            }
            case RED -> {
                return Color.RED;
            }
            case YELLOW -> {
                return Color.YELLOW;
            }
            case WHITE -> {
                return Color.WHITE;
            }
            case PURPLE -> {
                return Color.PURPLE;
            }
            case GREEN -> {
                return Color.GREEN;
            }
            case BROWN -> {
                return Color.BROWN;
            }
            default -> throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
