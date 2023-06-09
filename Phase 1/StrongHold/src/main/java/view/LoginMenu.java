package view;

import controller.Menus.LoginController;
import controller.control.Commands;
import controller.control.State;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {

    private final LoginController loginController;
    private Matcher nextMatcher;

    public LoginMenu() {
        this.loginController = new LoginController(this);
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public Matcher getNextMatcher() {
        return nextMatcher;
    }

    public void setNextMatcher(Matcher nextMatcher) {
        this.nextMatcher = nextMatcher;
    }


    public State run(Scanner scanner) throws InterruptedException, IOException {


        while (true) {
            String input = getInput(scanner);

            Matcher matcher;
            if (Commands.getMatcher(Commands.CREATE_USER, input).find()) {
                this.setNextMatcher(Commands.getMatcher(Commands.CREATE_USER, input));

                return State.SIGN;
            } else if (Commands.getMatcher(Commands.EXIT, input).find()) {

                return State.SIGN;

            } else if (Commands.getMatcher(Commands.LOGIN, input).matches()) {
                String str = this.loginController.login(Commands.getMatcher(Commands.LOGIN, input), scanner);
                System.out.println(str);
                if (str.equals("user logged in successfully!")) {
                    return State.GAME;
                }
            } else if ((matcher = Commands.getMatcher(Commands.FORGOT, input)).matches()) {
                System.out.println(this.loginController.forgotPassword(matcher, scanner));
            } else {
                System.out.println("Invalid Command!");
            }
            nextMatcher = null;
        }
    }

    private String getInput(Scanner scanner) {
        String input;
        if (this.nextMatcher != null) {
            // this.nextMatcher.find();
            input = this.nextMatcher.group();
            this.nextMatcher = null;
        } else {
            input = scanner.nextLine();
        }
        return input;
    }
}
