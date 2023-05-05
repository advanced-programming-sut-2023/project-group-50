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

    NEW_TRADE("^trade (?=.*(-t (?<resourceType>\\S+)))(?=.*(-a (?<resourceAmount>-?\\d+)))(?=.*(-p (?<price>-?\\d+)))(?=.*(-m (?<message>.*)))$"),
    TRADE_LIST("^trade list$"),
    TRADE_ACCEPT("^trade accept (?=.*(-i (?<id>-?\\d+)))(?=.*(-m (?<message>.*)))$"),
    TRADE_HISTORY("^trade history$"),

    SHOW_MAP("^show map( -(x|y) \\d*){2}$"),
    MOVE_MAP("^map( (up|left|down|right)( \\d+)?)+$"),
    DROP_BUILDING("^dropbuilding(( -(x|y) (\\d*))|( -type ((\".*\")|(\\S*)))){3}$")




;
    private final String regex;

    Commands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(Commands command, String input) {
        return Pattern.compile(command.regex).matcher(input);
    }
}
