package view;

import controller.Menus.ProfileController;
import controller.control.Commands;
import controller.control.State;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {

    private final ProfileController profileController;

    private Matcher nextMatcher;

    public ProfileMenu() {
        this.profileController = new ProfileController(this);
    }

    public ProfileController getProfileController() {
        return profileController;
    }

    public Matcher getNextMatcher() {
        return nextMatcher;
    }

    public void setNextMatcher(Matcher nextMatcher) {
        this.nextMatcher = nextMatcher;
    }


    public State run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();

            if (Commands.getMatcher(Commands.LOGOUT, input).find()) {
                System.out.println("user logged out successfully!");
                return State.SIGN;
            } else if (Commands.getMatcher(Commands.SHOW_MAP, input).find()) {
                this.setNextMatcher(Commands.getMatcher(Commands.SHOW_MAP, input));
                return State.MAP;
            } else if (Commands.getMatcher(Commands.PROFILE_CHANGE, input).find())
                System.out.println(this.profileController.profileChange(Commands.getMatcher(Commands.PROFILE_CHANGE, input)));
            else if (Commands.getMatcher(Commands.REMOVE_SLOGAN, input).find())
                System.out.println(this.profileController.removeSlogan());
            else if (Commands.getMatcher(Commands.CHANGE_SLOGAN, input).find())
                System.out.println(this.profileController.profileChange(Commands.getMatcher(Commands.CHANGE_SLOGAN, input)));
            else if (Commands.getMatcher(Commands.CHANGE_PASS, input).find())
                System.out.println(this.profileController.changePassword(Commands.getMatcher(Commands.CHANGE_PASS, input), scanner));
            else if (Commands.getMatcher(Commands.PROFILE_DISPlAY, input).find())
                System.out.println(this.profileController.profileDisplay(Commands.getMatcher(Commands.PROFILE_DISPlAY, input)));
            else System.out.println("Invalid Command!");
        }
    }
}
