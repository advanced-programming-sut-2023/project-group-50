package controller.control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    DOUBLE_QUOTE("\"[^\"]*\""),
    WORD("\\S+"),
    CAPITAL_LETTER("[A-Z]"),
    SMALL_LETTER("[a-z]"),
    DIGIT("\\d"),
    USERNAME("(?<username>-u ((\"[^\"]*\")|(\\S*)))"),
    PASSWORD("(?<password>-p(( random)|(( \"[^\"]*\"){2})|(( \\S*){2})))"),
    NICKNAME("(?<nickname>-n ((\"[^\"]*\")|(\\S*)))"),
    SLOGAN("(?<slogan>-s ((\"[^\"]*\")|(\\S*)))"),
    EMAIL("(?<email>-e ((\"[^\"]*\")|(\\S*)))"),
    EMAIL_FORMAT("[\\w\\.]+@[\\w\\.]+\\.[\\w\\.]+"),
    PASS("(?<username>-p ((\"[^\"]*\")|(\\S*)))"),
    STAY("--stay-logged-in"),
    OLD_PASS("-o ((\"[^\"]*\")|(\\S*))"),
    NEW_PASS("-n ((\"[^\"]*\")|(\\S*))"),
    QUESTION("(?<question>-q ((\"[^\"]*\")|(\\S*)))"),
    ANSWER_QUESTION("(?<answer>-a ((\"[^\"]*\")|(\\S*)))"),
    ANSWER_CONFIRM("(?<confirm>-c ((\"[^\"]*\")|(\\S*)))"),
    RATE("(?<rate>-r (-)?\\d*)"),
    X("-x \\d*"),
    Y("-y \\d*"),
    DIRECTION("(up|left|down|right)( \\d+)?"),
    TYPE("-type ((\"[^\"]*\")|(\\S*))"),
    COUNT("-c (\\d*)"),
    X1("-x1 \\d*"),
    Y1("-y1 \\d*"),
    X2("-x2 \\d*"),
    Y2("-y2 \\d*"),
    DIRECT("-d \\S*"),


    PROFILE_MENU("^profile menu$"),
    GOVERNMENT_MENU("^government menu$"),
    BACK("^back$"),

    CREATE_USER("^user create(( -p(( random)|(( \".*\"){2})|(( \\S*){2})))|( -[sune] ((\".*\")|(\\S*)))){4,5}$"),
    PICK_QUESTION("^question pick( -[a-z]+ ((\".*\")|(\\S*))){3}$"),
    EXIT("^exit$"),
    LOGIN("^user login(( --stay-logged-in)|(( -[up] ((\\\".*\\\")|(\\S*))))){2,3}$"),
    FORGOT("^forgot my password -u ((\".*\")|(\\S*))$"),

    LOGOUT("^user logout$"),
    PROFILE_CHANGE("^profile change -[unes] ((\".*\")|(\\S*))$"),
    REMOVE_SLOGAN("^Profile remove slogan$"),
    CHANGE_SLOGAN("^profile change slogan -s ((\".*\")|(\\S*))$"),
    CHANGE_PASS("^profile change password( -[on] ((\".*\")|(\\S*))){2}$"),
    PROFILE_DISPlAY("^profile display(( highscore)|( rank)|( slogan)|())$"),

    NEW_TRADE("^trade ((-t (?<resourceType>\\S+))) ((-a (?<resourceAmount>-?\\d+))) ((-p (?<price>-?\\d+))) ((-m (?<message>.*)))"),
    TRADE_LIST("^trade list"),
    TRADE_ACCEPT("^trade accept ((-i (?<id>-?\\d+))) ((-m (?<message>.*)))"),
    TRADE_HISTORY("^trade history"),

    SHOW_MAP("^show map( -(x|y) \\d*){2}"),
    MOVE_MAP("^map( (up|left|down|right)( \\d+)?)+"),
    REPAIR("^repair"),
    CREATE_UNIT("^createunit(( -(c) (\\d*))|( -type ((\".*\")|(\\S*)))){2}"),
    BUY_ITEM("^buy item (?<name>.*)"),
    SELL_ITEM("^sell item (?<name>.*)"),
    ADD_NEW_ITEM("^add new item ((-c (?<count>-?\\d+))) ((-p (?<price>-?\\d+))) ((-n (?<name>.*)))"),
    SHOW_ALL_ITEMS("^show all items"),
    SHOW_MY_ITEMS("^show my items"),
    SELECT_UNIT("^select unit ((-x (?<x>-?\\d+))) ((-y (?<y>-?\\d+)))"),
    MOVE_UNIT("^move unit to ((-x (?<x>-?\\d+))) ((-y (?<y>-?\\d+)))"),
    PATROL_UNIT("^patrol unit ((-x1 (?<x1>-?\\d+))) ((-y1 (?<y1>-?\\d+))) ((-y2 (?<y2>-?\\d+))) ((-x2 (?<x2>-?\\d+)))"),
    SET_UNIT("^set ((-x (?<x>-?\\d+))) ((-y (?<y>-?\\d+))) ((-s (?<s>)(standing|defensive|offensive)))"),
    ATTACK_ENEMY("^attack -e (?<enemy>.+)"),
    ARCHER_ATTACK("^attack ((-x (?<x>-?\\d+))) ((-y (?<y>-?\\d+)))"),
    POUR_OIL("^pour oil -d (?<direction>.+)$"),
    DIG_TUNNEL("^dig tunnel ((-x (?<x>-?\\d+))) ((-y (?<y>-?\\d+)))"),
    BUILD("^build -q (?<q>.+)$"),
    DISBAND_UNIT("^disband unit$"),

    DROP_BUILDING("^drop building(( -(x|y) (\\d*))|( -type ((\".*\")|(\\S*)))){3}$"),
    SELECT_BUILDING("^select building( -(x|y) \\d*){2}$"),
    SHOW_POPULARITY_FACTOR("^show popularity factors$"),
    SHOW_POPULARITY("^show popularity$"),
    SHOW_FOOD_LIST("^show food list$"),
    SHOW_FOOD_RATE("^food rate show$"),
    SET_FOOD_RATE("^food rate -r (-)?(\\d*)$"),
    ADD_FOOD("^add food -f (?<f>\\S+)$"),
    SET_TAX_RATE("^tax rate -r (-)?(\\d*)$"),
    SHOW_TAX_RATE("^tax rate show$"),
    SET_FEAR_RATE("^fear rate -r (-)?(\\d*)$"),
    SET_TEXTURE_PLACE("^set texture(( -(x1|x2|y1|y2) \\d*)|( -type ((\".*\")|(\\S*)))){5}$"),

    SET_TEXTURE("^set texture ((-x (?<x>-?\\d+))) ((-y (?<y>-?\\d+))) ((-t (?<type>.+)))"),
    SET_TEXTURE_RECT("^set texture -x1 (?<x1>-?\\d+) -y1 (?<y1>-?\\d+) -x2 (?<x2>-?\\d+) -y2 (?<y2>-?\\d+) -t (?<type>.+)"),
    CLEAR("^clear ((-x (?<x>-?\\d+))) ((-y (?<y>-?\\d+)))$"),
    DROP_ROCK("^drop rock ((-x (?<x>-?\\d+))) ((-y (?<y>-?\\d+))) ((-d (?<direction>.+)))"),
    DROP_TREE("^drop tree ((-x (?<x>-?\\d+))) ((-y (?<y>-?\\d+))) ((-t (?<type>.+)))"),
    DROP_UNIT("^drop unit ((-x (?<x>-?\\d+))) ((-y (?<y>-?\\d+))) ((-t (?<type>.+))) ((-c (?<count>-?\\d+)))"),
    DROP_BUILDING_MAP("^drop building ((-x (?<x>-?\\d+))) ((-y (?<y>-?\\d+))) ((-t (?<type>.+)))"),
    END_TURN("^end turn$"),
    SHOW_MY_DATA("^show my data$"),
    CAPTURE_GATE("^capture gate ((-x (?<x>-?\\d+))) ((-y (?<y>-?\\d+)))"),
    MAKE_PROTECTION("^make protection"),
    MAKE_BATTERING_RAM("^make battering ram"),
    MAKE_CATAPULT("^make catapult"),
    MAKE_FIRE_THROWER("^make fire thrower"),
    START_GAME("^start game$"),
    SHOW_DETAIL("^show details( -(x|y) \\d*){2}$"),

    COORDINATE("^(?<x>\\d+) (?<y>\\d+)$"),
    TRADE_MENU("^trade menu$"),
    SHOP_MENU("^shop menu$");

    private final String regex;

    Commands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(Commands command, String input) {
        return Pattern.compile(command.regex).matcher(input);
    }
}
