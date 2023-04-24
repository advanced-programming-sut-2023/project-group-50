package Controllers;

import Controllers.control.State;
import Controllers.control.Users;
import View.LoginMenu;
import View.ProfileMenu;
import View.SignupMenu;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Controller {
   private Users users;
    private SignupMenu signupMenu;
    private LoginMenu loginMenu;
    private ProfileMenu profileMenu;

    private final Scanner scanner = new Scanner(System.in);

    public Controller() {
        this.signupMenu = new SignupMenu();
        this.loginMenu = new LoginMenu();
        this.users=new Users();
        this.profileMenu=new ProfileMenu();
    }

    public void run() throws InterruptedException, IOException {

        while (true) {
            State nextMenu = this.signupMenu.run(scanner);
            if (nextMenu.equals(State.EXIT)) {
                return;
            }
            this.loginMenu.setNextMatcher(this.signupMenu.getNextMatcher());
            nextMenu = this.loginMenu.run(scanner);
            if(nextMenu.equals(State.SIGN)){
                this.signupMenu.setNextMatcher(this.loginMenu.getNextMatcher());
                continue;
            } else if (nextMenu.equals(State.EXIT)) {
                continue;
            }
            this.profileMenu.setCurrentUser(this.loginMenu.getUserLoggedIn());
            nextMenu=this.profileMenu.run(scanner);


        }


    }

}
