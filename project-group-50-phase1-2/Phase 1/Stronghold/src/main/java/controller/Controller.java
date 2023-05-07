package controller;

import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import controller.control.State;
import view.*;

import java.io.IOException;
import java.util.Scanner;

public class Controller {
    private final Scanner scanner = new Scanner(System.in);
    private final Users users;
    private final SignupMenu signupMenu;
    private final LoginMenu loginMenu;
    private final ProfileMenu profileMenu;
    private final MapMenu mapMenu;
    private final GameMenu gameMenu;
    private User currentUser;

    public Controller() {
        this.signupMenu = new SignupMenu();
        this.loginMenu = new LoginMenu();
        this.users = new Users();
        this.profileMenu = new ProfileMenu();
        this.mapMenu = new MapMenu();
        this.gameMenu=new GameMenu ();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void run() throws InterruptedException, IOException {

        while (true) {
            State nextMenu = this.signupMenu.run(scanner);
            if (nextMenu.equals(State.EXIT)) {
                return;
            }
            this.loginMenu.setNextMatcher(this.signupMenu.getNextMatcher());
            nextMenu = this.loginMenu.run(scanner);
            if (nextMenu.equals(State.SIGN)) {
                this.signupMenu.setNextMatcher(this.loginMenu.getNextMatcher());
                continue;
            } else if (nextMenu.equals(State.EXIT)) {
                continue;
            }
            this.gameMenu.getGameMenuController ().setCurrentUser (this.loginMenu.getLoginController().getLoggedIn());
            while (true) {
                nextMenu = this.gameMenu.run (scanner);
                if (nextMenu.equals(State.SIGN)) {
                    this.signupMenu.setNextMatcher(null);
                    break;
                } else if (nextMenu.equals(State.MAP)) {
                    this.mapMenu.getMapMenuController().setCurrentUser(this.loginMenu.getLoginController().getLoggedIn());
                    this.mapMenu.setNextMatcher(this.gameMenu.getNextMatcher());
                    this.mapMenu.run(scanner);
                }
                else if (nextMenu.equals (State.PROFILE)) {
                    this.profileMenu.getProfileController().setCurrentUser(this.loginMenu.getLoginController().getLoggedIn());
                    this.profileMenu.run(scanner);
                }


            }
        }
    }
}
