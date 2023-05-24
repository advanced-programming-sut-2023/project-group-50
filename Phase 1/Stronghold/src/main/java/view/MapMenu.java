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
        Matcher matcher;
        String input;

        while (true) {
            input = getInput(scanner);

            System.out.println(input);

            if ((matcher = Commands.getMatcher(Commands.SET_TEXTURE, input)).matches())
                System.out.println(mapMenuController.setTexture(matcher));
            else if ((matcher = Commands.getMatcher(Commands.SET_TEXTURE_RECT, input)).matches())
                System.out.println(mapMenuController.setTextureRect(matcher));
            else if ((matcher = Commands.getMatcher(Commands.CLEAR, input)).matches())
                System.out.println(mapMenuController.clear(matcher));
            else if ((matcher = Commands.getMatcher(Commands.DROP_ROCK, input)).matches())
                System.out.println(mapMenuController.dropRock(matcher));
            else if ((matcher = Commands.getMatcher(Commands.DROP_TREE, input)).matches())
                System.out.println(mapMenuController.dropTree(matcher));
            else if ((matcher = Commands.getMatcher(Commands.DROP_BUILDING_MAP, input)).matches())
                System.out.println(mapMenuController.dropBuilding(matcher));
            else if ((matcher = Commands.getMatcher(Commands.DROP_UNIT, input)).matches())
                System.out.println(mapMenuController.dropUnit(matcher));
            else if ((matcher = Commands.getMatcher(Commands.SHOW_MAP, input)).matches())
                System.out.println(this.mapMenuController.showMap(matcher));
            else if ((matcher = Commands.getMatcher(Commands.MOVE_MAP, input)).matches())
                System.out.println(this.mapMenuController.moveMap(matcher));
            else if (Commands.getMatcher(Commands.EXIT, input).matches())
                return State.PROFILE;
            else if ((matcher = Commands.getMatcher(Commands.SHOW_DETAIL, input)).matches())
                System.out.println(this.mapMenuController.showDetails(matcher));
            else
                System.out.println("invalid command");


        }
    }

    private String getInput(Scanner scanner) {
        String input;
        if (this.nextMatcher != null) {
            this.nextMatcher.find();
            input = this.nextMatcher.group();
            this.nextMatcher = null;
        } else {
            input = scanner.nextLine();
        }
        return input;
    }

}
