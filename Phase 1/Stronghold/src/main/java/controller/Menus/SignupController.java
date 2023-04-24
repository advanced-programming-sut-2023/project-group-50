package controller.Menus;

import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import controller.control.Commands;
import controller.control.Error;
import controller.control.SecurityQuestion;
import controller.control.Slogans;
import view.SignupMenu;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static controller.Menus.LoginController.getError;

public class SignupController {
    private final SignupMenu signupMenu;


    public SignupController(SignupMenu signupMenu) {
        this.signupMenu = signupMenu;
    }

    private static void showCaptcha(Scanner scanner) {
        LoginController.staticShowCaptcha(scanner);
    }

    private static String removeDoubleCoutString(String string) {

        if (Commands.getMatcher(Commands.DOUBLEQOUT, string).find()) {
            string = string.substring(1, string.length() - 1);
        }
        return string;
    }

    public String createUser(Matcher matcher, Scanner scanner) {
        matcher.find();
        String input = matcher.group();

        Error error = userNameIsValid(input, scanner);
        if (!error.truth) {
            return error.errorMassage;
        }

        String username = error.errorMassage;

        error = passwordIsValid(input, scanner);
        if (!error.truth) {
            return error.errorMassage;
        }
        String password = error.errorMassage;

        error = emailIsValid(input);
        if (!error.truth) {
            return error.errorMassage;
        }

        String email = error.errorMassage;

        error = checkHasField(input, Commands.NICKNAME);
        if (!error.truth) {
            return error.errorMassage;
        }
        String nickname = checkHasField(input, Commands.NICKNAME).errorMassage;

        error = checkHasField(input, Commands.SLOGAN);
        if (!error.truth) {
            return error.errorMassage;
        }
        String slogan = error.errorMassage;
        if (slogan.equals("random")) {
            slogan = randomSlogan();
        }


        User user = new User(username, password, nickname, email, slogan);
        pickSecurityQuestion(scanner, user);
        showCaptcha(scanner);
        Users.addUser(user);
        return "Your signup successful!";

    }

    private Error checkHasField(String input, Commands command) {
        Matcher matcher = Commands.getMatcher(command, input);

        if (!matcher.find()) {
            if (command.name().equals("SLOGAN")) {
                return new Error("", true);
            }
            return new Error("You should enter all field!\nEnter a " + command.name(), false);
        }

        String string = matcher.group();
        string = string.substring(3);

        string = removeDoubleCoutString(string);
        return getError(command, matcher, string);
    }

    private Error userNameIsValid(String input, Scanner scanner) {

        if (!checkHasField(input, Commands.USERNAME).truth) {
            return checkHasField(input, Commands.USERNAME);
        }

        String username = checkHasField(input, Commands.USERNAME).errorMassage;

        Matcher matcher = Pattern.compile("[^\\w ]").matcher(username);

        if (matcher.find()) {
            return new Error("Invalid Username format!\nyou can't use this character : \"" + matcher.group() + "\"", false);
        }

        if (Users.getUser(username) != null) {
            System.out.print("The username entered is duplicate!\nyou want to use suggested username? (yes or no) : ");

            while (true) {
                String string = scanner.nextLine();
                if (string.equals("no")) {
                    return new Error("Please signup again", false);
                } else if (string.equals("yes")) {
                    break;
                } else {
                    System.out.print("I'm sorry we don't understand please just enter yes or no : ");
                }

            }

            while (true) {
                int digit = (new Random().nextInt(1000));
                if (Users.getUser(username + digit) == null) {
                    System.out.print("you wanna use this username : " + username + digit + "\nPleas just enter (yes or no or quit) : ");
                    while (true) {
                        String string = scanner.nextLine();
                        if (string.equals("quit")) {
                            return new Error("Please signup again", false);
                        } else if (string.equals("yse")) {
                            return new Error(username + digit, true);

                        } else if (string.equals("no")) {
                            break;

                        } else {
                            System.out.print("I'm sorry we don't understand please just enter yes or no or quit : ");
                        }

                    }
                }
            }
        }

        return new Error(username, true);


    }

    private Error passwordIsValid(String input, Scanner scanner) {
        Matcher matcher = Commands.getMatcher(Commands.PASSWORD, input);
        if (!matcher.find()) {
            return new Error("You should enter all field!\nEnter a PASSWORD", false);
        }
        String allPart = matcher.group();
        allPart = allPart.substring(3);
        if (allPart.equals("random")) {
            String pass = randomPassword();
            System.out.print("Your random password is: " + pass + "\nPlease re-enter your password here:");
            while (true) {
                String string = scanner.nextLine();
                if (string.equals(pass)) {
                    System.out.print("your random password is not this " + string + "\nPlease try again : ");
                } else {
                    System.out.print("your password confirm is successful");
                    break;
                }
            }
            return new Error(pass, true);
        }
        String pass = "";
        String confirmPass = "";

        if ((matcher = Commands.getMatcher(Commands.DOUBLEQOUT, allPart)).find()) {
            pass = matcher.group();
            pass = removeDoubleCoutString(pass);
            confirmPass = allPart.substring(matcher.end() + 2, allPart.length() - 1);
        } else {
            Matcher matcher1 = Pattern.compile(" ").matcher(allPart);
            matcher1.find();
            pass = allPart.substring(0, matcher1.start());
            confirmPass = allPart.substring(matcher1.end());
        }
        pass = pass.trim();
        confirmPass = confirmPass.trim();
        if (pass.equals("")) {
            return new Error("You should fill password  field!", false);
        }
        if (pass.length() < 6) {
            return new Error("Your password should have 6 character", false);
        }
        if (!Commands.getMatcher(Commands.CAPITALLATTER, pass).find()) {
            return new Error("Your password should have a capital letter", false);
        }
        if (!Commands.getMatcher(Commands.SMALLLETTER, pass).find()) {
            return new Error("Your password should have a small letter", false);
        }
        if (!Commands.getMatcher(Commands.DIGIT, pass).find()) {
            return new Error("Your password should have a digit ", false);
        }
        if (!Pattern.compile("[^a-zA-Z0-9 ]").matcher(pass).find()) {
            return new Error("Your password should have a non-word character", false);
        }
        if (!pass.equals(confirmPass)) {
            return new Error("Your confirm password isn't match with password", false);
        }
        return new Error(pass, true);
    }

    private String randomPassword() {
        int number = new Random().nextInt(30 - 6 + 1) + 6;
        char[] chars = new char[number];
        ArrayList<Character> characterArrayList = new ArrayList<>(4);

        char capital = (char) (new Random().nextInt('Z' - 'A' + 1) + 'A');
        char small = (char) (new Random().nextInt('z' - 'a' + 1) + 'a');
        char digit = (char) (new Random().nextInt('9' - '0' + 1) + '0');
        char random;
        while (true) {
            random = (char) (new Random().nextInt(256) + 32);
            if ((Character.isLowerCase(random)) || (Character.isUpperCase(random)) || (Character.isDigit(random)) || (Character.isWhitespace(random))) {
                continue;
            }
            break;
        }
        characterArrayList.add(capital);
        characterArrayList.add(small);
        characterArrayList.add(digit);
        characterArrayList.add(random);

        int j = 0;
        while (j < 4) {
            int x = new Random().nextInt(number);
            if (chars[x] != '\0') {
                continue;
            }
            chars[x] = characterArrayList.get(j);
            j++;
        }
        for (int i = 0; i < number; i++) {
            if (chars[i] == '\0') {
                while (true) {
                    random = (char) (new Random().nextInt(256) + 32);
                    if ((Character.isWhitespace(random))) {
                        continue;
                    }
                    chars[i] = random;
                    i++;
                    break;
                }
            }
        }

        String pass = new String(chars);
        return pass;

    }

    private Error emailIsValid(String input) {
        if (!checkHasField(input, Commands.EMAIL).truth) {
            return checkHasField(input, Commands.EMAIL);
        }
        String email = checkHasField(input, Commands.EMAIL).errorMassage;

        if (!Commands.getMatcher(Commands.EMAILFORMAT, email).find()) {
            return new Error("Your email format is invalid", false);
        }
        if (Users.checkRepetitiousEmail(email)) {
            return new Error("The entered email address is already in use", false);
        }
        return new Error(email, true);
    }

    private String randomSlogan() {
        int number = new Random().nextInt(Slogans.getNumberSlogans());
        String slogan = Slogans.getSlogansByNumber(number);

        System.out.println("Your slogan is \"" + slogan + "\"");
        return slogan;

    }

    private void pickSecurityQuestion(Scanner scanner, User user) {
        showSecurityQuestions();

        while (true) {
            String input = scanner.nextLine();
            if (Commands.getMatcher(Commands.PICKQUESTION, input).find()) {
                if (!checkHasField(input, Commands.QUESTION).truth) {
                    System.out.println(checkHasField(input, Commands.QUESTION).errorMassage);
                }
                String question = checkHasField(input, Commands.QUESTION).errorMassage;

                if (Pattern.compile("\\D").matcher(question).find()) {
                    System.out.println("You should enter a number in question field\nTry again");
                    continue;
                }
                int number = Integer.parseInt(question);

                if (number > SecurityQuestion.getNumber()) {
                    System.out.println("You should enter a number lower than " + SecurityQuestion.getNumber() + " in question field\nTry again");
                    continue;
                }

                if (!checkHasField(input, Commands.ANSWERQUESTION).truth) {
                    System.out.println(checkHasField(input, Commands.ANSWERQUESTION).errorMassage);
                }
                String answer = checkHasField(input, Commands.ANSWERQUESTION).errorMassage;

                if (!checkHasField(input, Commands.ANSWERCONFIRM).truth) {
                    System.out.println(checkHasField(input, Commands.ANSWERCONFIRM).errorMassage);
                }
                String confirmAnswer = checkHasField(input, Commands.ANSWERCONFIRM).errorMassage;

                if (!answer.equals(confirmAnswer)) {
                    System.out.println("Your confirm answer isn't match with answer\nTry again");
                    continue;
                }

                user.setSecurityQuestion(SecurityQuestion.getByNumber(number));
                user.setSecurityQuestionAnswer(answer);
                System.out.println("Your question pick was successful");
                return;
            } else {
                System.out.println("Invalid command!\nYou should pick a SecurityQuestion");
            }
        }
    }

    private void showSecurityQuestions() {
        for (SecurityQuestion securityQuestion : SecurityQuestion.values()) {
            System.out.println((securityQuestion.ordinal() + 1) + ". " + securityQuestion.getQuestion());
        }

    }

}
