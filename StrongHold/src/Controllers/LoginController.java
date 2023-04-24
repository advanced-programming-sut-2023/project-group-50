package Controllers;

import Controllers.control.Commands;
import Controllers.control.Error;
import Controllers.control.Users;
import Moudel.Captcha;
import Moudel.User;
import View.LoginMenu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController {

    private LoginMenu loginMenu;
    private User tryToLogin;


    public LoginController(LoginMenu loginMenu){
        this.loginMenu=loginMenu;

    }

    public User getTryToLogin() {
        return tryToLogin;
    }

    public void setTryToLogin(User tryToLogin) {
        this.tryToLogin = tryToLogin;
    }

    public String login(Matcher matcher, Scanner scanner) throws InterruptedException, IOException {
        matcher.find();
        String input=matcher.group();

        if(!checkHasField(input,Commands.USERNAME).truth){
            return checkHasField(input,Commands.USERNAME).errorMassage;
        }

        String username=checkHasField(input,Commands.USERNAME).errorMassage;

        if(!checkHasField(input,Commands.PASS).truth){
            return checkHasField(input,Commands.PASS).errorMassage;
        }
        String password=checkHasField(input,Commands.PASS).errorMassage;

        if(Users.getUser(username)==null){
            return "Username didn't match!";
        }

        User user=Users.getUser(username);

        if(!user.getPassword().equals(password)){
            this.tryToLogin=user;
            user.setAttemptToLogin(user.getAttemptToLogin()+1);
            if(user.getAttemptToLogin()>5){
               return timerForLogin(user.getAttemptToLogin(),scanner);
            }

            return "Password didn't match!";
        }
        if((matcher=Commands.getMatcher(Commands.STAY,input)).find()){
            if (matcher.find()){
                return "Invalid command!\nEnter a  once";
            }

            FileWriter file=new FileWriter("D:\\aa\\CE FILE\\start again\\AP\\project\\User logged in.txt");
            file.append(username+"\n");
        }

        showCaptcha(scanner);

        user.setAttemptToLogin(0);
        this.loginMenu.setUserLoggedIn(user);
        this.tryToLogin=null;
        return "user logged in successfully!";

    }

    private Error checkHasField(String input, Commands command){
        Matcher matcher = Commands.getMatcher(command,input);

        if(!matcher.find()){
            return new Error("You should enter all field!\nEnter a "+command.name(),false);
        }

        String string = matcher.group();
        string=string.substring(3,string.length());

        string = removeDoubleCoutString(string);
        if(matcher.find()){
            return new Error("Invalid command!\nYou should enter all field!\nEnter "+command.name()+" once",false);

        }
        string=string.trim();
        if(string.equals("")){
            return new Error("You should fill "+command.name()+" field!",false);
        }

        return new Error(string,true);

    }
    private static String removeDoubleCoutString(String string){

        if(Commands.getMatcher(Commands.DOUBLEQOUT,string).find()){
            string=string.substring(1,string.length()-1);
        }
        return string;
    }


    private static String timerForLogin(int attempt, Scanner scanner) throws InterruptedException {

        int time=5*(attempt-5);

        System.out.println("You should wait "+time+" second an try again");

        TimeUnit.SECONDS.sleep(time);
        return "try again";
    }

    public String forgotPassword(Scanner scanner){
        if(tryToLogin==null){
            return "First you should enter your username";
        }
        System.out.print("Your security question is :\n"+tryToLogin.getSecurityQuestion().getQuestion()+"\nPlease answer this question :");
        String input=scanner.nextLine();
        if(!tryToLogin.getSecurityQuestionAnswer().equals(input)){
            return "Your answer is wrong";
        }

        System.out.println("Your answer is correct");
        while (true) {
            System.out.print("Now set your new password :");
            input = scanner.nextLine();
            input = input.trim();

            if (input.equals("")) {
                System.out.println("You should enter something!");
                continue;
            }
            else if (input.length() < 6) {
                System.out.println("Your password should have 6 character");
                continue;
            }
            else if (!Commands.getMatcher(Commands.CAPITALLATTER, input).find()) {
                System.out.println("Your password should have a capital letter");
                continue;
            }
            else if (!Commands.getMatcher(Commands.SMALLLETTER, input).find()) {
                System.out.println("Your password should have a small letter");
                continue;
            }
            else if (!Commands.getMatcher(Commands.DIGIT, input).find()) {
                System.out.println("Your password should have a digit ");
                    continue;
            }
            else if (!Pattern.compile("[^a-zA-Z0-9 ]").matcher(input).find()) {
                System.out.println("Your password should have a non-word character");
                continue;
            }

            System.out.print("Now confirm your password :");
            while (true){
                String confirm=scanner.nextLine();
                if(!input.equals(confirm)){
                    System.out.print("Your confirm password doesn't match\nPlease try again :");
                    continue;
                }
                break;


            }

            break;
        }

        return "Your password successfully changed!";
    }


    private static void showCaptcha(Scanner scanner) {
        System.out.println("Now fill this captcha :\n");


        while (true){
            Captcha captcha=new Captcha();
            System.out.println(captcha.getCaptcha());
            String str= scanner.nextLine();
            if (str.equals("change the current captcha")) {
                continue;
            }
            if(captcha.answerIsCorrect(str)){
                System.out.println("Your answer is correct");
                break;
            }
            System.out.println("Your answer is wrong\nPlease try new captcha :\n");

        }
    }




}
