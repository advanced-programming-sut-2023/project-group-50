package model.Map;

import model.ObjectsPackage.People.Soldier.SoldierName;

public enum GroundType {
    GROUND(ConsoleColors.YELLOW_BACKGROUND_BRIGHT), // zamin
    RIGGED_GROUND(ConsoleColors.YELLOW_BACKGROUND), // zamin ba sangrize
    CLIFF(ConsoleColors.BLACK_BACKGROUND), // takhte sang
    STONE(ConsoleColors.BLACK_BACKGROUND_BRIGHT), // sang
    IRON(ConsoleColors.WHITE_BACKGROUND), // ahan
    GRASS(ConsoleColors.CYAN_BACKGROUND), // chaman
    LAWN(ConsoleColors.GREEN_BACKGROUND), // alafzaar
    MEADOW(ConsoleColors.BLACK_BACKGROUND_BRIGHT), // alafzaare portarakom

    OIL(ConsoleColors.BLACK_BOLD),//naft
    PLAIN(ConsoleColors.BLACK_BOLD), //jolge

    SHALLOW_WATER(ConsoleColors.BLUE), // ab kam omgh

    RIVER(ConsoleColors.BLACK_BOLD), //rood
    SMALL_POND(ConsoleColors.BLUE), //berke koochak
    BIG_POND(ConsoleColors.BLACK_BRIGHT), // berke bozorg

    BEACH(ConsoleColors.BLUE), // sahel
    SEA(ConsoleColors.BLUE); // darya

    private final String color;
    private final String type;

    GroundType(String color) {
        this.color = color;
        type = SoldierName.getName(this.name());
    }

    public static GroundType get(String type) {
        for (GroundType groundType : GroundType.values()) {
            if (groundType.type.equals(type)) {
                return groundType;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public String getString(String content) {
        return color + content + ConsoleColors.RESET;
    }
}
