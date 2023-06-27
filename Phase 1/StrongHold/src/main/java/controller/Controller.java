package controller;

import controller.Menus.ShopMenuController;
import controller.Menus.TradeMenuController;
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
    private final GovernmentMenu governmentMenu;
    private User currentUser;

    public Controller() {
        this.signupMenu = new SignupMenu();
        this.loginMenu = new LoginMenu();
        this.users = new Users();
        this.profileMenu = new ProfileMenu();
        this.mapMenu = new MapMenu();
        this.gameMenu = new GameMenu();
        this.governmentMenu = new GovernmentMenu();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void run() throws InterruptedException, IOException {

        while (true) {
            State nextMenu = this.signupMenu.run(scanner, gameMenu.getGameMenuController().getGame());
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
            this.currentUser = this.loginMenu.getLoginController().getLoggedIn();
            this.gameMenu.getGameMenuController().setCurrentUser(this.currentUser);

            gameMenu.addCurrentUserToGame();

            while (true) {
                nextMenu = this.gameMenu.run(scanner);
                if (nextMenu.equals(State.SIGN)) {
                    this.signupMenu.setNextMatcher(null);
                    this.currentUser = null;
                    break;
                } else if (nextMenu.equals(State.MAP)) {
                    this.mapMenu.getMapMenuController().setCurrentUser(this.currentUser);
                    this.mapMenu.setNextMatcher(this.gameMenu.getNextMatcher());
                    this.mapMenu.run(this.scanner);
                } else if (nextMenu.equals(State.PROFILE)) {
                    this.profileMenu.getProfileController().setCurrentUser(this.currentUser);
                    this.profileMenu.run(this.scanner);
                } else if (nextMenu.equals(State.GOVERNMENT)) {
                    this.governmentMenu.getGovernmentMenuController().setCurrentUser(this.loginMenu.getLoginController().getLoggedIn());
                    this.governmentMenu.run(this.scanner);
                } else if (nextMenu.equals(State.TRADE)) {
                    TradeMenuController tradeMenuController = new TradeMenuController(currentUser);
                    new TradeMenu(tradeMenuController).run(scanner);
                } else if (nextMenu.equals(State.SHOP)) {
                    ShopMenuController shopMenuController = new ShopMenuController(currentUser);
                    new ShopMenu(shopMenuController).run(scanner);
                } else if (nextMenu.equals(State.EXIT)) {
                    return;
                }
            }
        }
    }
}

