package model.Map;

public enum GroundType {
    GROUND(ConsoleColors.YELLOW_BACKGROUND_BRIGHT), // zamin
    RIGGED_GROUND(ConsoleColors.YELLOW_BACKGROUND), // zamin ba sangrize
    CLIFF(ConsoleColors.BLACK_BACKGROUND), // takhte sang
    STONE(ConsoleColors.BLACK_BACKGROUND_BRIGHT), // sang
    IRON(ConsoleColors.WHITE_BACKGROUND), // ahan
    GRASS(ConsoleColors.CYAN_BACKGROUND), // chaman
    LAWN(ConsoleColors.GREEN_BACKGROUND), // alafzaar
    MEADOW(ConsoleColors.BLACK_BACKGROUND_BRIGHT); // alafzaare portarakom

    private String color;

    GroundType(String color) {
        this.color = color;
    }

    public String getString(String content) {
        return color + content + ConsoleColors.RESET;
    }
}
