package controller.control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    DOUBLE_QUOTE("\".*\""),
    WORD("\\S+"),
    CAPITAL_LETTER("[A-Z]"),
    SMALL_LETTER("[a-z]"),
    DIGIT("\\d"),
    USERNAME("(?<username>-u ((\".*\")|(\\S*)))"),
    PASSWORD("(?<password>-p(( random)|(( \".*\"){2})|(( \\S*){2})))"),
    NICKNAME("(?<nickname>-n ((\".*\")|(\\S*)))"),
    SLOGAN("(?<slogan>-s ((\".*\")|(\\S*)))"),
    EMAIL("(?<email>-e ((\".*\")|(\\S*)))"),
    EMAIL_FORMAT("[\\w\\.]+@[\\w\\.]+\\.[\\w\\.]+"),
    PASS("(?<username>-p ((\".*\")|(\\S*)))"),
    STAY("--stay-logged-in"),
    OLD_PASS("-o ((\".*\")|(\\S*))"),
    NEW_PASS("-n ((\".*\")|(\\S*))"),
    X("-x \\d*"),
    Y("-y \\d*"),
    DIRECTION("(up|left|down|right)( \\d+)?"),
    TYPE("-type ((\".*\")|(\\S*))"),
    COUNT("-c (\\d*)"),


    CREATE_USER("^user create(( -p(( random)|(( \".*\"){2})|(( \\S*){2})))|( -[sune] ((\".*\")|(\\S*)))){4,5}$"),
    PICK_QUESTION("^question pick( -[a-z]+ ((\".*\")|(\\S*))){3}$"),
    QUESTION("(?<username>-q ((\".*\")|(\\S*)))"),
    ANSWER_QUESTION("(?<username>-a ((\".*\")|(\\S*)))"),
    ANSWER_CONFIRM("(?<username>-c ((\".*\")|(\\S*)))"),
    EXIT("^exit$"),
    LOGIN("^user login(( --stay-logged-in)|(( -[up] ((\\\".*\\\")|(\\S*))))){2,3}$"),
    FORGOT("^forgot my password$"),
    LOGOUT("^user logout$"),
    PROFILE_CHANGE("^profile change -[une] ((\".*\")|(\\S*))$"),
    REMOVE_SLOGAN("^Profile remove slogan$"),
    CHANGE_SLOGAN("^profile change slogan -s ((\".*\")|(\\S*))$"),
    CHANGE_PASS("^profile change password( -[on] ((\".*\")|(\\S*))){2}$"),
    PROFILE_DISPlAY("^profile display(( highscore)|( rank)|( slogan)|())$"),

    NEW_TRADE("^trade (?=.*(-t (?<resourceType>\\S+)))(?=.*(-a (?<resourceAmount>-?\\d+)))(?=.*(-p (?<price>-?\\d+)))(?=.*(-m (?<message>.*)))"),
    TRADE_LIST("^trade list"),
    TRADE_ACCEPT("^trade accept (?=.*(-i (?<id>-?\\d+)))(?=.*(-m (?<message>.*)))"),
    TRADE_HISTORY("^trade history"),

    SHOW_MAP("^show map( -(x|y) \\d*){2}"),
    MOVE_MAP("^map( (up|left|down|right)( \\d+)?)+"),
    DROP_BUILDING("^dropbuilding(( -(x|y) (\\d*))|( -type ((\".*\")|(\\S*)))){3}"),
    SELECT_BUILDING("^select building( -(x|y) \\d*){2}"),
    REPAIR("^repair"),
    CREATE_UNIT("^createunit(( -(c) (\\d*))|( -type ((\".*\")|(\\S*)))){2}"),
    BUY_ITEM("^buy item (?<name>.*)"),
    SELL_ITEM("^sell item (?<name>.*)"),
    ADD_NEW_ITEM("^add new item (?=.*(-c (?<count>-?\\d+)))(?=.*(-p (?<price>-?\\d+)))(?=.*(-n (?<name>.*)))"),
    SHOW_ALL_ITEMS("^show all items"),
    SHOW_MY_ITEMS("^show my items"),

    SELECT_UNIT("^select unit (?=.*(-x (?<x>-?\\d+)))(?=.*(-y (?<y>-?\\d+)))"),
    MOVE_UNIT("^move unit to (?=.*(-x (?<x>-?\\d+)))(?=.*(-y (?<y>-?\\d+)))"),
    PATROL_UNIT("^patrol unit (?=.*(-x1 (?<x1>-?\\d+)))(?=.*(-y1 (?<y1>-?\\d+)))(?=.*(-y2 (?<y2>-?\\d+)))(?=.*(-x2 (?<x2>-?\\d+)))"),
    SET_UNIT("^set (?=.*(-x (?<x>-?\\d+)))(?=.*(-y (?<y>-?\\d+)))(?=.*(-s (?<s>)(standing|defensive|offensive)))"),
    ATTACK_ENEMY("^attack -e (?<enemy>.+)"),
    ARCHER_ATTACK("^attack (?=.*(-x (?<x>-?\\d+)))(?=.*(-y (?<y>-?\\d+)))"),
    POUR_OIL("^pour oil -d (?<direction>.+)$"),
    DIG_TUNNEL("^dig tunnel (?=.*(-x (?<x>-?\\d+)))(?=.*(-y (?<y>-?\\d+)))"),
    BUILD("^build -q (?<equipment>.+)$"),
    DISBAND_UNIT("^disband unit$"),

    SET_TEXTURE("^set texture (?=.*(-x (?<x>-?\\d+)))(?=.*(-y (?<y>-?\\d+)))(?=.*(-t (?<type>.+)))"),
    SET_TEXTURE_RECT("^set texture (?=.*(-x1 (?<x1>-?\\d+)))(?=.*(-y1 (?<y1>-?\\d+)))(?=.*(-y2 (?<y2>-?\\d+)))(?=.*(-x2 (?<x2>-?\\d+)))(?=.*(-t (?<type>.+)))"),
    CLEAR("^clear (?=.*(-x (?<x>-?\\d+)))(?=.*(-y (?<y>-?\\d+)))"),
    DROP_ROCK("^drop rock (?=.*(-x (?<x>-?\\d+)))(?=.*(-y (?<y>-?\\d+)))(?=.*(-d (?<direction>.+)))"),
    DROP_TREE("^drop tree (?=.*(-x (?<x>-?\\d+)))(?=.*(-y (?<y>-?\\d+)))(?=.*(-t (?<type>.+)))"),
    DROP_UNIT("^drop unit (?=.*(-x (?<x>-?\\d+)))(?=.*(-y (?<y>-?\\d+)))(?=.*(-t (?<type>.+)))(?=.*(-c (?<count>-?\\d+)))"),
    DROP_BUILDING_MAP("^drop building (?=.*(-x (?<x>-?\\d+)))(?=.*(-y (?<y>-?\\d+)))(?=.*(-t (?<type>.+))"),
    END_TURN("^end turn$"),
    SHOW_MY_DATA("^show my data$");
    private final String regex;

    Commands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(Commands command, String input) {
        return Pattern.compile(command.regex).matcher(input);
    }
}
