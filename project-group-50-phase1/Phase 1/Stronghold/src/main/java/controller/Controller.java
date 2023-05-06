package controller;

import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import controller.control.State;
import view.LoginMenu;
import view.MapMenu;
import view.ProfileMenu;
import view.SignupMenu;

import java.io.IOException;
import java.util.Scanner;

public class Controller {
    private final Scanner scanner = new Scanner(System.in);
    private final Users users;
    private final SignupMenu signupMenu;
    private final LoginMenu loginMenu;
    private final ProfileMenu profileMenu;
    private User currentUser;
    private final MapMenu mapMenu;
    public Controller() {
        this.signupMenu = new SignupMenu();
        this.loginMenu = new LoginMenu();
        this.users = new Users();
        this.profileMenu = new ProfileMenu();
        this.mapMenu=new MapMenu();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void run() throws InterruptedException, IOException {

        while (true) {
            State nextMenu = this.signupMenu.run (scanner);
            if (nextMenu.equals (State.EXIT)) {
                return;
            }
            this.loginMenu.setNextMatcher (this.signupMenu.getNextMatcher ());
            nextMenu = this.loginMenu.run (scanner);
            if (nextMenu.equals (State.SIGN)) {
                this.signupMenu.setNextMatcher (this.loginMenu.getNextMatcher ());
                continue;
            } else if (nextMenu.equals (State.EXIT)) {
                continue;
            }
            this.profileMenu.getProfileController ().setCurrentUser (this.loginMenu.getLoginController ().getLoggedIn ());
            while (true) {
                nextMenu = this.profileMenu.run (scanner);
                if (nextMenu.equals (State.SIGN)) {
                    this.signupMenu.setNextMatcher (null);
                    break;
                } else if (nextMenu.equals (State.MAP)) {
                    this.mapMenu.getMapMenuController ().setCurrentUser (this.loginMenu.getLoginController ().getLoggedIn ());
                    this.mapMenu.setNextMatcher (this.profileMenu.getNextMatcher ());
                    this.mapMenu.run (scanner);

                }

            }
        }
    }
}
