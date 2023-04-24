package Controllers.control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {

        DOUBLEQOUT("\".*\""),
        CAPITALLATTER("[A-Z]"),
        SMALLLETTER("[a-z]"),
        DIGIT("\\d"),


        USERNAME("(?<username>-u ((\".*\")|(\\S*)))"),
        PASSWORD("(?<password>-p(( random)|(( \".*\"){2})|(( \\S*){2})))"),
        NICKNAME("(?<nickname>-n ((\".*\")|(\\S*)))"),
        SLOGAN("(?<slogan>-s ((\".*\")|(\\S*)))"),
        EMAIL("(?<email>-e ((\".*\")|(\\S*)))"),
        EMAILFORMAT("[\\w\\.]+@[\\w\\.]+\\.[\\w\\.]+"),
        PASS("(?<username>-p ((\".*\")|(\\S*)))"),
        STAY("--stay-logged-in"),





        CREATUSER("^user create(( -p(( random)|(( \".*\"){2})|(( \\S*){2})))|( -[sune] ((\".*\")|(\\S*)))){4,5}$"),
        PICKQUESTION("^question pick( -[a-z]+ ((\".*\")|(\\S*))){3}$"),
        QUESTION("(?<username>-q ((\".*\")|(\\S*)))"),
        ANSWERQUESTION("(?<username>-a ((\".*\")|(\\S*)))"),
        ANSWERCONFIRM("(?<username>-c ((\".*\")|(\\S*)))"),
        EXIT("^exit$"),
        LOGIN("^user login(( --stay-logged-in)|(( -[up] ((\\\".*\\\")|(\\S*))))){2,3}$"),
        FORGOT("^forgot my password$"),
        LOGOUT("^user logout$"),
        PROFILECHANG("^profile change -[une] ((\".*\")|(\\S*))$"),
        REMOVESLOGAN("^Profile remove slogan$"),
        CHANGSLOGAN("^profile change slogan -s ((\".*\")|(\\S*))$")


    ;

























    private String regex;
    private Commands(String regex){
        this.regex=regex;
    }

    public static Matcher getMatcher(Commands command,String input){
        return Pattern.compile(command.regex).matcher(input);
    }
}
