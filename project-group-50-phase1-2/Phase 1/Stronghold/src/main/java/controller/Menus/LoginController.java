package controller.Menus;

import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import controller.control.Commands;
import controller.control.Error;
import model.Captcha.Captcha;
import model.Map.Map;
import view.LoginMenu;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController {

    private static String userLoggedInPath = ".\\Phase 1\\User logged in.txt";
    private final LoginMenu loginMenu;
    private User tryToLogin;
    private User loggedIn;

    public LoginController(LoginMenu loginMenu) {
        this.loginMenu = loginMenu;

    }

    static Error getError(Commands command, Matcher matcher, String string) {
        if (matcher.find()) {
            return new Error("Invalid command!\nEnter " + command.name() + " once", false);

        }
        string = string.trim();
        if (string.equals("")) {
            return new Error("You should fill " + command.name() + " field!", false);
        }

        return new Error(string, true);
    }

    static String removeDoubleCoutString(String string) {

        if (Commands.getMatcher(Commands.DOUBLE_QUOTE, string).find()) {
            string = string.substring(1, string.length() - 1);
        }
        return string;
    }

    private static String timerForLogin(int attempt, Scanner scanner) throws InterruptedException {

        int time = 5 * (attempt - 5);

        System.out.println("You should wait " + time + " second an try again");
        TimeUnit.SECONDS.sleep(time);
        return "try again";
    }

    private static void showCaptcha(Scanner scanner) {
        staticShowCaptcha(scanner);
    }

    static void staticShowCaptcha(Scanner scanner) {
        System.out.println("Now fill this captcha :\n");


        while (true) {
            Captcha captcha = new Captcha();
            System.out.println(captcha.getCaptcha());
            String str = scanner.nextLine();
            if (str.equals("change the current captcha")) {
                continue;
            }
            if (captcha.answerIsCorrect(str)) {
                System.out.println("Your answer is correct");
                break;
            }
            System.out.println("Your answer is wrong\nPlease try new captcha :\n");

        }
    }

    private static void writInFile(String username) throws IOException {
        FileWriter file = new FileWriter(userLoggedInPath, true);
        File obj = new File(userLoggedInPath);
        BufferedWriter bw = null;
        PrintWriter pw = null;
        bw = new BufferedWriter(file);
        pw = new PrintWriter(bw);
        Scanner read = new Scanner(obj);
        int f = 0;
        while (read.hasNextLine()) {
            if (username.equals(read.nextLine())) {
                f = 1;
                break;
            }
        }
        if (f == 0) {
            pw.println(username);
        }
        pw.close();
        bw.close();
        file.close();
        read.close();
    }

    static Error checkHasField(String input, Commands command) {
        Matcher matcher = Commands.getMatcher(command, input);

        if (!matcher.find()) {
            return new Error("You should enter all field!\nEnter a " + command.name(), false);
        }
        String string = matcher.group();
        string = string.substring(3);
        string = removeDoubleCoutString(string);
        return getError(command, matcher, string);

    }

    public User getLoggedIn() {
        return loggedIn;
    }

    public User getTryToLogin() {
        return tryToLogin;
    }

    public void setTryToLogin(User tryToLogin) {
        this.tryToLogin = tryToLogin;
    }

    public String login(Matcher matcher, Scanner scanner) throws InterruptedException, IOException {
        matcher.find();
        String input = matcher.group();

        Error error = checkHasField(input, Commands.USERNAME);
        if (!error.truth) {
            return error.errorMassage;
        }

        String username = checkHasField(input, Commands.USERNAME).errorMassage;

        error = checkHasField(input, Commands.PASS);
        if (!error.truth) {
            return error.errorMassage;
        }
        String password = checkHasField(input, Commands.PASS).errorMassage;

        if (Users.getUser(username) == null) {
            return "Username didn't match!";
        }

        User user = Users.getUser(username);

        if (!user.getPassword().equals(password)) {
            this.tryToLogin = user;
            user.setAttemptToLogin(user.getAttemptToLogin() + 1);
            if (user.getAttemptToLogin() > 5) {
                return timerForLogin(user.getAttemptToLogin(), scanner);
            }

            return "Password didn't match!";
        }
        if ((matcher = Commands.getMatcher(Commands.STAY, input)).find()) {
            if (matcher.find()) {
                return "Invalid command!\nEnter a  once";
            }
            writInFile(username);
        }

        showCaptcha(scanner);
        setMapSize(user, scanner);
        user.setAttemptToLogin(0);
        this.loggedIn = (user);
        this.tryToLogin = null;
        return "user logged in successfully!";

    }

    public String forgotPassword(Scanner scanner) {
        if (tryToLogin == null) {
            return "First you should enter your username";
        }
        System.out.print("Your security question is :\n" + tryToLogin.getSecurityQuestion().getQuestion() + "\nPlease answer this question : ");
        String input = scanner.nextLine();
        if (!tryToLogin.getSecurityQuestionAnswer().equals(input)) {
            return "Your answer is wrong";
        }

        System.out.println("Your answer is correct");
        while (true) {
            System.out.print("Now set your new password : ");
            input = scanner.nextLine();
            input = input.trim();

            if (input.equals("")) {
                System.out.println("You should enter something!");
                continue;
            } else if (input.length() < 6) {
                System.out.println("Your password should have 6 character");
                continue;
            } else if (!Commands.getMatcher(Commands.CAPITAL_LETTER, input).find()) {
                System.out.println("Your password should have a capital letter");
                continue;
            } else if (!Commands.getMatcher(Commands.SMALL_LETTER, input).find()) {
                System.out.println("Your password should have a small letter");
                continue;
            } else if (!Commands.getMatcher(Commands.DIGIT, input).find()) {
                System.out.println("Your password should have a digit");
                continue;
            } else if (!Pattern.compile("[^a-zA-Z0-9 ]").matcher(input).find()) {
                System.out.println("Your password should have a non-word character");
                continue;
            }

            System.out.print("Now confirm your password : ");
            while (true) {
                String confirm = scanner.nextLine();
                if (!input.equals(confirm)) {
                    System.out.print("Your confirm password doesn't match\nPlease try again : ");
                    continue;
                }
                break;


            }

            break;
        }
        tryToLogin.setPassword(input);
        return "Your password successfully changed!";
    }

    private void setMapSize(User user, Scanner scanner) {
        if (user.getGovernment().getMap() != null) {
            return;
        }
        System.out.println("Please enter the map size from the sizes below :");
        System.out.print("Please enter (200*200) or (400*400) : ");
        Map map = null;
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("200*200")) {
                map = new Map(200, 200);
                break;
            } else if (input.equals("400*400")) {
                map = new Map(400, 400);
                break;
            } else {
                System.out.print("Please enter a valid size : ");
            }


        }
        user.getGovernment().setMap(map);

    }
}
