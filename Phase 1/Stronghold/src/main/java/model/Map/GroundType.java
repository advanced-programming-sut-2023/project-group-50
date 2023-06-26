package model.Map;

import javafx.scene.image.Image;
import model.ObjectsPackage.People.Soldier.SoldierName;
import model.RandomGenerator.RandomGroundType;

import java.io.Serializable;
import java.net.URL;

public enum GroundType implements Serializable {
    GROUND(ConsoleColors.YELLOW_BACKGROUND_BRIGHT), // zamin
    RIGGED_GROUND(ConsoleColors.YELLOW_BACKGROUND), // zamin ba sangrize
    CLIFF(ConsoleColors.BLACK_BACKGROUND), // takhte sang
    STONE(ConsoleColors.BLACK_BACKGROUND_BRIGHT), // sang
    IRON(ConsoleColors.WHITE_BACKGROUND), // ahan
    GRASS(ConsoleColors.CYAN_BACKGROUND), // chaman
    LAWN(ConsoleColors.GREEN_BACKGROUND), // alafzaar
    MEADOW(ConsoleColors.BLACK_BRIGHT), // alafzaare portarakom

    OIL(ConsoleColors.BLACK_BOLD_BRIGHT),//naft
    PLAIN(ConsoleColors.GREEN_BOLD_BRIGHT), //jolge

    SHALLOW_WATER(ConsoleColors.BLUE), // ab kam omgh

    RIVER(ConsoleColors.BLUE_BRIGHT), //rood
    SMALL_POND(ConsoleColors.BLUE_BACKGROUND), //berke koochak
    BIG_POND(ConsoleColors.BLACK_UNDERLINED), // berke bozorg

    BEACH(ConsoleColors.YELLOW_BRIGHT), // sahel
    SEA(ConsoleColors.BLUE_BOLD); // darya

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

    private String getURLPath() {
        switch (this) {
            case GROUND -> {
                return RandomGroundType.getRandomPlain();
            }
            case RIGGED_GROUND -> {
                return RandomGroundType.getRandomRigged();
            }
            case CLIFF -> {
                return RandomGroundType.getRandomCliff();
            }
            case STONE -> {
                return RandomGroundType.getRandomStone();
            }
            case IRON -> {
                return RandomGroundType.getRandomIron();
            }
            case GRASS, MEADOW, LAWN -> {
                return RandomGroundType.getRandomGrassLawnMeadow();
            }
            case OIL -> {
                return RandomGroundType.getRandomOil();
            }
            case PLAIN -> {
                return RandomGroundType.getRandomPlain();
            }
            case BIG_POND, SEA, RIVER, SHALLOW_WATER, SMALL_POND -> {
                return RandomGroundType.getRandomWater();
            }
            case BEACH -> {
                return RandomGroundType.getRandomBeach();
            }
            default -> throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    private URL getURL() {
        return GroundType.class.getResource("/phase2-assets/" + getURLPath());
    }

    public Image getImage() {
        return new Image(getURL().toExternalForm());
    }

    public boolean isWater() {
        return this == GroundType.SHALLOW_WATER || this == GroundType.SEA || this == GroundType.BIG_POND ||
                this == GroundType.SMALL_POND || this == GroundType.RIVER;
    }

    public boolean isGreen() {
        return this == GroundType.GRASS || this == GroundType.LAWN || this == GroundType.MEADOW;
    }
}
