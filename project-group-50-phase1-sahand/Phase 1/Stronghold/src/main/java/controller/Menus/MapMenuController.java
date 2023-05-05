package controller.Menus;

import controller.UserDatabase.User;
import controller.control.Commands;
import controller.control.Error;
import view.MapMenu;

import java.util.regex.Matcher;
import static controller.Menus.LoginController.*;

public class MapMenuController {

    private final MapMenu mapMenu;

    private  int x;
    private  int y;

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public MapMenuController(MapMenu mapMenu) {
       this.mapMenu=mapMenu;
    }



    public MapMenu getMapMenu() {
        return mapMenu;
    }

    public String showMap(Matcher matcher) {
        matcher.find();
        String input=matcher.group();

        Error error=checkHasField(input,Commands.X);
        if (!error.truth) {
            return error.errorMassage;
        }
        int x=Integer.parseInt(error.errorMassage);
        error=checkHasField(input,Commands.Y);
        if (!error.truth) {
            return error.errorMassage;
        }
        int y=Integer.parseInt(error.errorMassage);

       String s=this.currentUser.getGovernment().getMap().getMap(x,y,15,15);
        if(s.equals("")){
            return "invalid bounds for show";
        }
        this.x=x;
        this.y=y;
        return s;
    }

    public String moveMap(Matcher matcher) {
        matcher.find();
        String input = matcher.group();

        input = input.substring(4);
        matcher = Commands.getMatcher(Commands.DIRECTION, input);
        int x = 0;
        int y = 0;
        while (! matcher.find()) {
            String in = matcher.group();
            Matcher match = Commands.getMatcher(Commands.WORD, in);
            match.find();
            String direct = match.group();
            int value = 1;
            if (match.find()) {
                value = Integer.parseInt(match.group());
            }
            if (direct.equals("up")) {
                y = y + value;
            } else if (direct.equals("down")) {
                y = y - value;
            } else if (direct.equals("left")) {
                x = x - value;
            } else if (direct.equals("right")) {
                x = x + value;
            }


        }

        String s=this.currentUser.getGovernment().getMap().getMap(this.x+x,this.y+y,15,15);
        if(s.equals("")){
            return "invalid bounds for move";
        }
        this.x=this.x+x;
        this.y=this.y+y;
        return s;



    }

    public String showDetails(Matcher matcher) {

        return null;
    }
}
