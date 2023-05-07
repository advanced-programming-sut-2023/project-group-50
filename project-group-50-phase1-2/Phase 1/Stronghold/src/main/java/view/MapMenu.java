package view;

import controller.Menus.MapMenuController;
import controller.control.Commands;
import controller.control.State;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {
    private final MapMenuController mapMenuController;
    private Matcher nextMatcher;

    public MapMenu() {
        this.mapMenuController = new MapMenuController(this);
    }

    public MapMenuController getMapMenuController() {
        return mapMenuController;
    }

    public Matcher getNextMatcher() {
        return nextMatcher;
    }

    public void setNextMatcher(Matcher nextMatcher) {
        this.nextMatcher = nextMatcher;
    }


    public State run(Scanner scanner) {
        while (true) {
            String input;
            if (this.nextMatcher != null) {
                this.nextMatcher.find();
                input = this.nextMatcher.group();
                this.nextMatcher = null;
            } else {
                input = scanner.nextLine();
            }

            if (Commands.getMatcher(Commands.SHOW_MAP, input).find()) {
                System.out.println(this.mapMenuController.showMap(Commands.getMatcher(Commands.SHOW_MAP, input)));
            } else if (Commands.getMatcher(Commands.MOVE_MAP, input).find()) {
                System.out.println(this.mapMenuController.moveMap(Commands.getMatcher(Commands.MOVE_MAP, input)));
            } else if (Commands.getMatcher(Commands.EXIT, input).find()) {
                return State.PROFILE;
            }
            else if (Commands.getMatcher (Commands.SHOW_DETAIL,input).find ()) {
                System.out.println (this.mapMenuController.showDetails (Commands.getMatcher (Commands.SHOW_DETAIL,input)));
            }
            else {
                System.out.println("invalid command");
            }


        }
    }

}
