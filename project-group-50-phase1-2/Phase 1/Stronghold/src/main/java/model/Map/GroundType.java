package model.Map;

public enum GroundType {
    GROUND(ConsoleColors.YELLOW_BACKGROUND_BRIGHT,"ground"), // zamin
    RIGGED_GROUND(ConsoleColors.YELLOW_BACKGROUND,"rigged ground"), // zamin ba sangrize
    CLIFF(ConsoleColors.BLACK_BACKGROUND,"cliff"), // takhte sang
    STONE(ConsoleColors.BLACK_BACKGROUND_BRIGHT,"stone"), // sang
    IRON(ConsoleColors.WHITE_BACKGROUND,"iron"), // ahan
    GRASS(ConsoleColors.CYAN_BACKGROUND,"grass"), // chaman
    LAWN(ConsoleColors.GREEN_BACKGROUND,"lawn"), // alafzaar
    MEADOW(ConsoleColors.BLACK_BACKGROUND_BRIGHT,"meadow"), // alafzaare portarakom

    OIL(ConsoleColors.BLACK_BOLD,"oil"),//naft
    PLAIN(ConsoleColors.BLACK_BOLD,"plain"), //jolge

    SHALLOW_WATER(ConsoleColors.BLUE,"shallow water"), // ab kam omgh

    RIVER(ConsoleColors.BLACK_BOLD,"river"), //rood
    SMALL_POND(ConsoleColors.BLUE,"small pond"), //berke koochak
    BIG_POND(ConsoleColors.BLACK_BRIGHT,"big pond"), // berke bozorg

    BEACH(ConsoleColors.BLUE,"beach"), // sahel
    SEA(ConsoleColors.BLUE,"sea"); // darya

    private final String color;
    private final String type;
    GroundType(String color,String type) {
        this.color = color;
        this.type=type;
    }

    public String getString(String content) {
        return color + content + ConsoleColors.RESET;
    }
    public static GroundType isType(String type){
        for ( GroundType groundType:GroundType.values () ){
            if(groundType.type.equals ( type )){
                return groundType;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }
}
