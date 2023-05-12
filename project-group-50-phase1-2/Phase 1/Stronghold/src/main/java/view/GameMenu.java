package view;

import controller.Menus.GameMenuController;
import controller.control.Commands;
import controller.control.State;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private final GameMenuController gameMenuController;

    public GameMenu() {
        this.gameMenuController = new GameMenuController(this);
    }

    public GameMenuController getGameMenuController() {
        return gameMenuController;
    }
    private Matcher nextMatcher;

    public Matcher getNextMatcher() {
        return nextMatcher;
    }

    public void setNextMatcher(Matcher nextMatcher) {
        this.nextMatcher = nextMatcher;
    }


    public State run(Scanner scanner) {

        while (true) {
            int flag = 0;
            String input = scanner.nextLine();
            if (Commands.getMatcher(Commands.LOGOUT, input).find()) {
                System.out.println("user logged out successfully!");
                return State.SIGN;
            } else if (Commands.getMatcher(Commands.SHOW_MAP, input).find()) {
                this.setNextMatcher(Commands.getMatcher(Commands.SHOW_MAP, input));
                return State.MAP;
            } else
            if (Commands.getMatcher(Commands.DROP_BUILDING, input).find()) {
                System.out.println(this.gameMenuController.dropBuilding(Commands.getMatcher(Commands.DROP_BUILDING, input)));
            } else if (Commands.getMatcher(Commands.SELECT_BUILDING, input).find()) {
                System.out.println(this.gameMenuController.selectBuilding(Commands.getMatcher(Commands.SELECT_BUILDING, input)));
                flag = 1;
            } else if (Commands.getMatcher(Commands.REPAIR, input).find()) {
                System.out.println(this.gameMenuController.repair());
            } else if (Commands.getMatcher(Commands.CREATE_UNIT, input).find()) {
                System.out.println(this.gameMenuController.createUnit (Commands.getMatcher(Commands.CREATE_UNIT, input)));
            }
            else if (Commands.getMatcher (Commands.PROFILE_MENU,input).find ()) {
                return State.PROFILE;
            }
            else if (Commands.getMatcher (Commands.GOVERNMENT_MUNE,input).find ()) {
                return State.GOVERNMENT;
            }
            else if ( Commands.getMatcher ( Commands.SET_TEXTURE,input ).find () ) {
                System.out.println (this.gameMenuController.setTexture ( Commands.getMatcher ( Commands.SET_TEXTURE,input ) ));
            }
            else if ( Commands.getMatcher ( Commands.CLEAR ,input).find () ) {
                System.out.println (this.gameMenuController.clearUnit ( Commands.getMatcher ( Commands.CLEAR ,input) ));
            }
            else if ( Commands.getMatcher ( Commands.SET_TEXTURE_PLACE ,input).find () ) {
                System.out.println (this.gameMenuController.setTexturePlace ( Commands.getMatcher ( Commands.SET_TEXTURE_PLACE ,input) ));
            }
            else if ( Commands.getMatcher ( Commands.DROP_ROCK , input).find () ) {
                System.out.println (this.gameMenuController.dropRock ( Commands.getMatcher ( Commands.DROP_ROCK , input) ));
            }
            else {
                System.out.println("invalid command");
            }
            if (flag == 0) {
                this.gameMenuController.setSelectedBuilding(null);
            }


        }


    }
}
