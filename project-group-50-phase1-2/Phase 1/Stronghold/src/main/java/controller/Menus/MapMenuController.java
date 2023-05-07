package controller.Menus;

import controller.UserDatabase.User;
import controller.control.Commands;
import controller.control.Error;
import model.Map.Map;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.NonSoldier.NonSoldier;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.People.Soldier.Soldier;
import view.MapMenu;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;

import static controller.Menus.GameMenuController.checkValue;

public class MapMenuController {

    private final MapMenu mapMenu;

    private int x;
    private int y;

    private User currentUser;

    public MapMenuController(MapMenu mapMenu) {
        this.mapMenu = mapMenu;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public MapMenu getMapMenu() {
        return mapMenu;
    }

    public String showMap(Matcher matcher) {
        matcher.find();
        String input = matcher.group();


        Error error = checkValue(input, Commands.X, currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int x = Integer.parseInt(error.errorMassage);

        error = checkValue(input, Commands.Y, currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int y = Integer.parseInt(error.errorMassage);

        String s = this.currentUser.getGovernment().getMap().getMap(x, y, 15, 15);
        if (s.equals("")) {
            return "invalid bounds for show";
        }
        this.x = x;
        this.y = y;
        return s;
    }

    public String moveMap(Matcher matcher) {
        matcher.find();
        String input = matcher.group();

        input = input.substring(4);
        matcher = Commands.getMatcher(Commands.DIRECTION, input);
        int x = 0;
        int y = 0;
        while (!matcher.find()) {
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

        String s = this.currentUser.getGovernment().getMap().getMap(this.x + x, this.y + y, 15, 15);
        if (s.equals("")) {
            return "invalid bounds for move";
        }
        this.x = this.x + x;
        this.y = this.y + y;
        return s;

    }

    public String showDetails(Matcher matcher) {
        matcher.find();
        String input = matcher.group();


        Error error = checkValue(input, Commands.X, currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int x = Integer.parseInt(error.errorMassage);

        error = checkValue(input, Commands.Y, currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int y = Integer.parseInt(error.errorMassage);

        Unit unit= this.currentUser.getGovernment ().getMap ().getUnitByXY (x,y);
        String string="Ground type : "+unit.getTexture ()+"\n";
        if(unit.getObjects ().isEmpty ()){
            return string;
        }
        ArrayList<Objects> objects =new ArrayList<> (unit.getObjects ());
        for (int i=0;i<objects.size ();i++){
            Objects object=objects.get (i);
            int number=1;
            if(object instanceof Soldier){
                for (int j=i+1;j<objects.size ();j++){
                    Objects objectsSample= objects.get (j);
                    if(objectsSample instanceof  Soldier){
                        if(((Soldier) object).getType ().equals (((Soldier) objectsSample).getType ())){
                            number++;
                            objects.remove (objectsSample);
                        }
                    }
                }
                string=string+"Soldier : "+((Soldier) object).getType ().getType ()+"*"+number+"\n";
            }else if (object instanceof Building){
                string=string+"Building : "+((Building) object).getType ().getType ()+"*"+number+"\n";
            }

        }

        return string;

    }
}
