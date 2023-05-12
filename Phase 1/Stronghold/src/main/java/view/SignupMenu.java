package view;

import controller.Menus.SignupController;
import controller.control.Commands;
import controller.control.State;
import model.Game;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu {
    private final SignupController signupController;
    private Matcher nextMatcher;


    public SignupMenu() {
        this.signupController = new SignupController(this);

    }

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

            if ((matcher = Commands.getMatcher(Commands.CREATE_USER, input)).matches()) {
                System.out.println(this.signupController.createUser(matcher, scanner, game.getMap(), game.getRemainingColors()));
            } else if (Commands.getMatcher(Commands.EXIT, input).find()) {
                return State.EXIT;
            } else if ((matcher = Commands.getMatcher(Commands.LOGIN, input)).matches()) {
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
