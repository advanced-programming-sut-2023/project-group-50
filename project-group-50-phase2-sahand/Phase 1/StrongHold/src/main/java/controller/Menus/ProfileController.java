package controller.Menus;

import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import controller.control.Commands;
import controller.control.Error;
import view.ProfileMenu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileController {
    private final ProfileMenu profileMenu;
    private User currentUser;

    public ProfileController(ProfileMenu profileMenu) {
        this.profileMenu = profileMenu;
    }

    private static String removeDoubleCoutString(String string) {

        if (Commands.getMatcher(Commands.DOUBLE_QUOTE, string).find()) {
            string = string.substring(1, string.length() - 1);
        }
        return string;
    }

    private static void showCaptcha(Scanner scanner) {
        LoginController.staticShowCaptcha(scanner);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String profileChange(Matcher matcher) {
//        matcher.find();
        String input = matcher.group();

        Error error = userNameIsValid(input);
        if (error.truth) {
            String username = error.errorMassage;
            this.currentUser.setUserName(username);
            return "change username was successfully";
        } else {
            if (!error.errorMassage.equals("this command isn't match")) {
                return error.errorMassage;
            }

        }
        error = emailIsValid(input);
        if (error.truth) {
            String email = error.errorMassage;
            this.currentUser.setEmail(email);
            return "change email was successfully";
        } else {
            if (!error.errorMassage.equals("this command isn't match")) {
                return error.errorMassage;
            }

        }


        error = checkHasField(input, Commands.NICKNAME);
        if (error.truth) {
            String nickName = error.errorMassage;
            this.currentUser.setNickName(nickName);
            return "change nick name was successfully";
        } else {
            if (!error.errorMassage.equals("this command isn't match")) {
                return error.errorMassage;
            }

        }

        error = checkHasField(input, Commands.SLOGAN);
        if (error.truth) {
            String slogan = error.errorMassage;
            this.currentUser.setSlogan(slogan);
            return "change slogan was successfully";
        } else {
            if (!error.errorMassage.equals("this command isn't match")) {
                return error.errorMassage;
            }

        }

        return "";


    }

    private Error checkHasField(String input, Commands command) {
        Matcher matcher = Commands.getMatcher(command, input);

        if (!matcher.find()) {
            return new Error("this command isn't match", false);
        }

        String string = matcher.group();
        string = string.substring(3);

        string = removeDoubleCoutString(string);
        string = string.trim();
        if (string.equals("")) {
            return new Error("You should fill " + command.name() + " field!", false);
        }

        return new Error(string, true);

    }

    private Error userNameIsValid(String input) {

        if (!checkHasField(input, Commands.USERNAME).truth) {
            return checkHasField(input, Commands.USERNAME);
        }

        String username = checkHasField(input, Commands.USERNAME).errorMassage;

        Matcher matcher = Pattern.compile("[^\\w ]").matcher(username);

        if (matcher.find()) {
            return new Error("Invalid Username format!\nyou can't use this character :" + matcher.group(), false);
        }

        if (Users.getUser(username) != null) {
            System.out.print("The username entered is duplicate!");

        }

        return new Error(username, true);

    }

    public String changePassword(Matcher matcher, Scanner scanner) {
//        matcher.find();
        String input = matcher.group();

        Error error = checkHasField(input, Commands.OLD_PASS);
        if (!error.truth) {
            return error.errorMassage;
        }

        String oldPass = error.errorMassage;

        if (!this.currentUser.getPassword().equals(oldPass)) {
            return "Current password is incorrect!";
        }
        error = checkHasField(input, Commands.NEW_PASS);
        if (!error.truth) {
            return error.errorMassage;
        }

        String newPass = error.errorMassage;

        if (oldPass.equals(newPass)) {
            return "Please enter a new password!";
        }

        if (newPass.length() < 6) {
            return "Your new password should have 6 character";
        }
        if (!Commands.getMatcher(Commands.CAPITAL_LETTER, newPass).find()) {
            return "Your new password should have a capital letter";
        }
        if (!Commands.getMatcher(Commands.SMALL_LETTER, newPass).find()) {
            return "Your password should have a small letter";
        }
        if (!Commands.getMatcher(Commands.DIGIT, newPass).find()) {
            return "Your new password should have a digit ";
        }
        if (!Pattern.compile("[^a-zA-Z0-9 ]").matcher(newPass).find()) {
            return "Your new password should have a non-word character";
        }

        System.out.print("Please enter your new password again :");

        while (true) {
            String str = scanner.nextLine();
            if (str.equals(newPass)) {
                break;
            } else if (str.equals("quit")) {
                return "Your change password failed";
            }
            System.out.print("Your confirm new password failed\nPlease try again :");
        }


//        showCaptcha(scanner);
        this.currentUser.setPassword(newPass);
        return "Your change password was successfully";

    }

    private Error emailIsValid(String input) {
        if (!checkHasField(input, Commands.EMAIL).truth) {
            return checkHasField(input, Commands.EMAIL);
        }
        String email = checkHasField(input, Commands.EMAIL).errorMassage;

        if (!Commands.getMatcher(Commands.EMAIL_FORMAT, email).find()) {
            return new Error("Your email format is invalid", false);
        }
        if (Users.checkRepetitiousEmail(email)) {
            return new Error("The entered email address is already in use", false);
        }
        return new Error(email, true);
    }

    public String removeSlogan() {
        this.currentUser.setSlogan("");
        return "Slogan remove successfully";

    }

    public String profileDisplay(Matcher matcher) {
//        matcher.find();
        String input = matcher.group();

        if (Pattern.compile("highscore").matcher(input).find()) {
            return "Your highscore is : " + this.currentUser.getHighScore();
        }

        if (Pattern.compile("rank").matcher(input).find()) {
            return "Your rank is : " + this.currentUser.getRank();
        }

        if (Pattern.compile("slogan").matcher(input).find()) {
            if (this.currentUser.getSlogan().equals("")) {
                return "Slogan is empty!";
            }
            return "Your slogan is : " + this.currentUser.getSlogan();
        }

        return this.currentUser.toString();
    }


}
