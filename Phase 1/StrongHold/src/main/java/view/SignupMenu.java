package view;

import controller.Menus.SignupController;
import controller.control.Commands;
import controller.control.State;
import model.Game;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu {

    private Matcher nextMatcher;




    public Matcher getNextMatcher() {
        return nextMatcher;
    }

    public void setNextMatcher(Matcher nextMatcher) {
        this.nextMatcher = nextMatcher;
    }

    public State run(Scanner scanner, Game game) {
        Matcher matcher;

        while (true) {
            String input = getInput(scanner);
             if (Commands.getMatcher(Commands.EXIT, input).find()) {
                return State.EXIT;
            } else if ((matcher = Commands.getMatcher(Commands.LOGIN, input)).matches()) {
                this.setNextMatcher(matcher);
                return State.LOGIN;
            } else if ((matcher = Commands.getMatcher(Commands.FORGOT, input)).matches()) {
                this.setNextMatcher(matcher);
                return State.LOGIN;

            } else {
                System.out.println("Invalid Command!");
            }
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
