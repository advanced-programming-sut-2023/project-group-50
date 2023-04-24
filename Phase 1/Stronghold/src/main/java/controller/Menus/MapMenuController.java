package controller.Menus;

import controller.UserDatabase.User;
import model.Map.Map;

import java.util.regex.Matcher;

public class MapMenuController {
    private final Map map;
    private final int x;
    private final int y;
    private final User currentUser;

    public MapMenuController(Map map, int x, int y, User currentUser) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.currentUser = currentUser;
    }

    public String showMap(Matcher matcher) {

        return null;
    }

    public String moveMap(Matcher matcher) {

        return null;
    }

    public String showDetails(Matcher matcher) {

        return null;
    }
}
