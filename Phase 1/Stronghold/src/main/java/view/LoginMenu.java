package view;

import controller.Menus.LoginController;
import controller.UserDatabase.User;
import controller.control.Commands;
import controller.control.State;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {

    private final LoginController loginController;
    private Matcher nextMatcher;

    private User userLoggedIn;

    public LoginMenu() {
        this.loginController = new LoginController(this);
    }

    public Matcher getNextMatcher() {
        return nextMatcher;
    }

    public void setNextMatcher(Matcher nextMatcher) {
        this.nextMatcher = nextMatcher;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(User userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    public State run(Scanner scanner) throws InterruptedException, IOException {


        while (true) {
            String input;
            if (this.nextMatcher != null) {
                this.nextMatcher.find();
                input = this.nextMatcher.group();
                this.nextMatcher = null;
            } else {
                input = scanner.nextLine();
            }

            if (Commands.getMatcher(Commands.CREATUSER, input).find()) {
                this.setNextMatcher(Commands.getMatcher(Commands.CREATUSER, input));
                this.loginController.setTryToLogin(null);
                return State.SIGN;
            } else if (Commands.getMatcher(Commands.EXIT, input).find()) {
                this.loginController.setTryToLogin(null);
                return State.SIGN;

            } else if (Commands.getMatcher(Commands.LOGIN, input).find()) {
                String str = this.loginController.login(Commands.getMatcher(Commands.LOGIN, input), scanner);
                System.out.println(str);
                if (str.equals("user logged in successfully!")) {
                    this.loginController.setTryToLogin(null);
                    return State.PROFILE;
                }
            } else if (Commands.getMatcher(Commands.FORGOT, input).find()) {
                System.out.println(this.loginController.forgotPassword(scanner));
            } else {
                System.out.println("Invalid Command!");
            }
        }
    }
}
