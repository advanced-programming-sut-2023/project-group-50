package controller.Menus;

import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import controller.control.Commands;
import controller.control.Error;
import controller.control.SecurityQuestion;
import controller.control.Slogans;
import javafx.scene.control.Label;
import model.Government.Government;
import model.UserColor.UserColor;
import view.SignupMenu;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static controller.Menus.LoginController.getError;

public class SignupController {

    private static void showCaptcha (Scanner scanner) {
        LoginController.staticShowCaptcha ( scanner );
    }


    public static User createUser (String username ,String password, String email,String nickName,String slogan) {




        /*Government.Pair xy = findAPlaceForLord(scanner);
        UserColor color = pickAColor(scanner, players);
*/
        User user = new User(username, password, nickName, email,slogan,0,0,null);
        return user;
    }

    private UserColor pickAColor(Scanner scanner, ArrayList<UserColor> remainingColors) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (UserColor userColor : remainingColors)
            stringArrayList.add(userColor.getName());

        System.out.println("Pick one color: " + String.join(", ", stringArrayList) + ":");

        while (true) {
            String line = scanner.nextLine();
            Matcher matcher = Commands.getMatcher(Commands.WORD, line);
            if (!matcher.matches()) {
                System.out.println("Invalid command, try again:");
                continue;
            }
            UserColor userColor = UserColor.getColorByName(line);

            if (userColor == null) {
                System.out.println("Invalid color, try again:");
            } else if (!remainingColors.contains(userColor) && !remainingColors.isEmpty()) {
                System.out.println("This color is not available, try again:");
            } else {
                System.out.println("Chosen successfully!");
                return userColor;
            }
        }
    }

    private Government.Pair findAPlaceForLord(Scanner scanner) {
        Government.Pair xy = new Government.Pair(0, 0);
//        System.out.println("Enter a coordinate (x y) for your palace:");

//        while (true) {
//            String line = scanner.nextLine();
//            if (!line.matches("^(?<x>\\d+) (?<y>\\d+)$")) {
//                System.out.println("Invalid command, try again:");
//                continue;
//            }
//            Matcher matcher = Commands.getMatcher(Commands.COORDINATE, line);
//            matcher.find();
//            xy = new Government.Pair(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
//            if (map.getXY(xy.x, xy.y).getObjects().isEmpty()) {
//                System.out.println("Picked successfully!");
//                return xy;
//            } else
//                System.out.println("This place is not empty, choose another one:");
//        }
        return xy;
    }


    public static Error userNameIsValid(String input) {

        String username = input.trim ();

        Matcher matcher = Pattern.compile("[^\\w ]").matcher(username);

        if (matcher.find()) {
            return new Error(
                    "you can't use this character :  " + matcher.group() , false);
        }

        if (Users.getUser(username) != null) {
            return new Error ("The username entered is duplicate!",true);
        }

        return new Error("", true);
    }

    private static String randomUsername (String string, String username) {

        while (true){
            int digit = (new Random().nextInt(1000));
            if (Users.getUser( username + digit) == null) {
               return username + digit;
            }
        }
    }

    public static Error passwordIsValid(String input) {

        String pass = input;

        if (pass.length() < 6) {
            return new Error("Your password should have 6 character", false);
        }
        if (!Commands.getMatcher(Commands.CAPITAL_LETTER, pass).find()) {
            return new Error("Your password should have a capital letter", false);
        }
        if (!Commands.getMatcher(Commands.SMALL_LETTER, pass).find()) {
            return new Error("Your password should have a small letter", false);
        }
        if (!Commands.getMatcher(Commands.DIGIT, pass).find()) {
            return new Error("Your password should have a digit ", false);
        }
        if (!Pattern.compile("[^a-zA-Z0-9 ]").matcher(pass).find()) {
            return new Error("Your password should have a non-word character", false);
        }
        return new Error("", true);
    }

    public static String randomPassword() {
        int number = new Random().nextInt(30 - 6 + 1) + 6;
        char[] chars = new char[number];
        ArrayList<Character> characterArrayList = new ArrayList<>(4);

        char capital = (char) (new Random().nextInt('Z' - 'A' + 1) + 'A');
        char small = (char) (new Random().nextInt('z' - 'a' + 1) + 'a');
        char digit = (char) (new Random().nextInt('9' - '0' + 1) + '0');
        char random;
        while (true) {
            random = (char) (new Random().nextInt(128-32) + 32);
            if ((Character.isLowerCase(random)) || (Character.isUpperCase(random)) || (Character.isDigit(random)) ||
                    (Character.isWhitespace(random))) {
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
                    random = (char) (new Random().nextInt(128-32) + 32);
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

    public static Error emailIsValid(String input) {
        String email = input.trim ();

        if (!Commands.getMatcher(Commands.EMAIL_FORMAT, email).find()) {
            return new Error("Your email format is invalid", false);
        }
        if (Users.checkRepetitiousEmail(email)) {
            return new Error("The entered email address is already in use", false);
        }
        return new Error("", true);
    }

    public static String randomSlogan() {
        int number = new Random().nextInt(Slogans.getNumberSlogans());
        String slogan = Slogans.getSlogansByNumber(number);

        return "Your slogan is : " + slogan;

    }

/*    private void pickSecurityQuestion(Scanner scanner, User user) {
        showSecurityQuestions();

        while (true) {
            String input = scanner.nextLine();
            if (Commands.getMatcher(Commands.PICK_QUESTION, input).find()) {
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
                    System.out.println("You should enter a number lower than " + SecurityQuestion.getNumber() +
                                               " in question field\nTry again");
                    continue;
                }

                if (!checkHasField(input, Commands.ANSWER_QUESTION).truth) {
                    System.out.println(checkHasField(input, Commands.ANSWER_QUESTION).errorMassage);
                }
                String answer = checkHasField(input, Commands.ANSWER_QUESTION).errorMassage;

                if (!checkHasField(input, Commands.ANSWER_CONFIRM).truth) {
                    System.out.println(checkHasField(input, Commands.ANSWER_CONFIRM).errorMassage);
                }
                String confirmAnswer = checkHasField(input, Commands.ANSWER_CONFIRM).errorMassage;

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
*/
        private void showSecurityQuestions () {
            for ( SecurityQuestion securityQuestion : SecurityQuestion.values () ) {
                System.out.println ( (securityQuestion.ordinal () + 1) + ". " + securityQuestion.getQuestion () );
            }

        }






}

