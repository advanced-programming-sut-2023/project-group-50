package view;

import controller.Menus.ProfileController;
import controller.control.Commands;
import controller.control.State;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {

    private final ProfileController profileController;



    public ProfileMenu() {
        this.profileController = new ProfileController(this);
    }

    public ProfileController getProfileController() {
        return profileController;
    }



    public State run(Scanner scanner) {

        while (true) {
            String input = scanner.nextLine();


            if (Commands.getMatcher(Commands.PROFILE_CHANGE, input).find()) {
                System.out.println(this.profileController.profileChange(Commands.getMatcher(Commands.PROFILE_CHANGE, input)));
            } else if (Commands.getMatcher(Commands.REMOVE_SLOGAN, input).find()) {
                System.out.println(this.profileController.removeSlogan());
            } else if (Commands.getMatcher(Commands.CHANGE_SLOGAN, input).find()) {
                System.out.println(this.profileController.profileChange(Commands.getMatcher(Commands.CHANGE_SLOGAN, input)));
            } else if (Commands.getMatcher(Commands.CHANGE_PASS, input).find()) {
                System.out.println(this.profileController.changePassword(Commands.getMatcher(Commands.CHANGE_PASS, input), scanner));
            } else if (Commands.getMatcher(Commands.PROFILE_DISPlAY, input).find()) {
                System.out.println(this.profileController.profileDisplay(Commands.getMatcher(Commands.PROFILE_DISPlAY, input)));
            }
            else if (Commands.getMatcher (Commands.BACK,input).find ()) {
                return State.GAME;
            }
            else {
                System.out.println("Invalid Command!");
            }


        }


    }

}
