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
        Matcher matcher;

        while (true) {
            String input = scanner.nextLine();

            if ((matcher = Commands.getMatcher(Commands.PROFILE_CHANGE, input)).matches())
                System.out.println(this.profileController.profileChange(matcher));
            else if (Commands.getMatcher(Commands.REMOVE_SLOGAN, input).matches())
                System.out.println(this.profileController.removeSlogan());
            else if ((matcher = Commands.getMatcher(Commands.CHANGE_SLOGAN, input)).matches())
                System.out.println(this.profileController.profileChange(matcher));
            else if ((matcher = Commands.getMatcher(Commands.CHANGE_PASS, input)).matches())
                System.out.println(this.profileController.changePassword(matcher, scanner));
            else if ((matcher = Commands.getMatcher(Commands.PROFILE_DISPlAY, input)).matches())
                System.out.println(this.profileController.profileDisplay(matcher));
            else if (Commands.getMatcher(Commands.BACK, input).matches())
                return State.GAME;
            else if (Commands.getMatcher(Commands.TRADE_MENU, input).matches())
                return State.TRADE;
            else
                System.out.println("Invalid Command!");
        }
    }
}
