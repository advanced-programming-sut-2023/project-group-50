package model.UserColor;

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

}
