package Controllers;

import Controllers.control.Commands;
import Controllers.control.Error;
import Controllers.control.Users;
import Moudel.Captcha;
import View.ProfileMenu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileController {
    private ProfileMenu profileMenu;

   public ProfileController(ProfileMenu profileMenu){
       this.profileMenu=profileMenu;
   }


   public String profileChange(Matcher matcher){
       matcher.find();
       String input = matcher.group();

       Error error=userNameIsValid(input);
       if(error.truth ){
           String username=error.errorMassage;
           this.profileMenu.getCurrentUser().setUserName(username);
           return "change username was successfully";
       }else {
           if(!error.errorMassage.equals("this command isn't match")){
               return error.errorMassage;
           }

       }
        error=emailIsValid(input);
       if(error.truth ){
           String email=error.errorMassage;
           this.profileMenu.getCurrentUser().setEmail(email);
           return "change email was successfully";
       }else {
           if(!error.errorMassage.equals("this command isn't match")){
               return error.errorMassage;
           }

       }


       error= checkHasField(input, Commands.NICKNAME);
       if(error.truth ){
           String nickName=error.errorMassage;
           this.profileMenu.getCurrentUser().setNickName(nickName);
           return "change nick name was successfully";
       }else {
           if(!error.errorMassage.equals("this command isn't match")){
               return error.errorMassage;
           }

       }

       error= checkHasField(input,Commands.SLOGAN);
       if(error.truth ){
           String slogan=error.errorMassage;
           this.profileMenu.getCurrentUser().setSlogan(slogan);
           return "change nick name was successfully";
       }else {
           if(!error.errorMassage.equals("this command isn't match")){
               return error.errorMassage;
           }

       }

       return "";


   }


    private Error checkHasField(String input, Commands command){
        Matcher matcher = Commands.getMatcher(command,input);

        if(!matcher.find()){
            return new Error("this command isn't match",false);
        }

        String string = matcher.group();
        string=string.substring(3,string.length());

        string = removeDoubleCoutString(string);
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

    private Error userNameIsValid(String input){

        if(!checkHasField(input,Commands.USERNAME).truth){
            return checkHasField(input,Commands.USERNAME);
        }

        String username=checkHasField(input,Commands.USERNAME).errorMassage;

        Matcher matcher= Pattern.compile("[^\\w ]").matcher(username);

        if(matcher.find()){
            return new Error("Invalid Username format!\nyou can't use this character :"+matcher.group(),false);
        }

        if(Users.getUser(username)!=null) {
            System.out.print("The username entered is duplicate!");

        }

        return new Error(username,true);

    }

    public String changePassword(Matcher matcher, Scanner scanner){
        matcher.find();
        String input = matcher.group();


        if(!checkHasField(input,Commands.OLDPASS).truth){
            return checkHasField(input,Commands.OLDPASS).errorMassage;
        }

        String oldPass=checkHasField(input,Commands.USERNAME).errorMassage;

        if(!this.profileMenu.getCurrentUser().getPassword().equals(oldPass)){
            return "Current password is incorrect!";
        }
        if(!checkHasField(input,Commands.NEWPASS).truth){
            return checkHasField(input,Commands.NEWPASS).errorMassage;
        }

        String newPass=checkHasField(input,Commands.NEWPASS).errorMassage;

        if(oldPass.equals(newPass)){
            return "Please enter a new password!";
        }

        if (newPass.length()<6){
            return "Your new password should have 6 character";
        }
        if (!Commands.getMatcher(Commands.CAPITALLATTER, newPass).find()){
            return "Your new password should have a capital letter";
        }
        if (!Commands.getMatcher(Commands.SMALLLETTER, newPass).find()){
            return "Your password should have a small letter";
        }
        if (!Commands.getMatcher(Commands.DIGIT, newPass).find()){
            return "Your new password should have a digit ";
        }
        if (!Pattern.compile("[^a-zA-Z0-9 ]").matcher(newPass).find()){
            return "Your new password should have a non-word character";
        }

        System.out.print("Please enter your new password again :");

        while (true){
            String str=scanner.nextLine();
            if(str.equals(newPass)){
               break;
            } else if (str.equals("quit")) {
                return "Your change password failed";
            }
            System.out.print("Your confirm new password failed\nPlease try again :");
        }


        showCaptcha(scanner);
        this.profileMenu.getCurrentUser().setPassword(newPass);
        return "Your change password was successfully";

    }


    private Error emailIsValid(String input){
        if(!checkHasField(input,Commands.EMAIL).truth){
            return checkHasField(input,Commands.EMAIL);
        }
        String email=checkHasField(input,Commands.EMAIL).errorMassage;

        if(!Commands.getMatcher(Commands.EMAILFORMAT,email).find()){
            return new Error("Your email format is invalid",false);
        }
        if (Users.checkRepetitiousEmail(email)){
            return new Error("The entered email address is already in use",false);
        }
        return new Error(email,true);
    }

    public String removeSlogan(){
       this.profileMenu.getCurrentUser().setSlogan("");
       return "Slogan remove successfully";

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


    public String profileDisplay(Matcher matcher){
        matcher.find();
        String input = matcher.group();

        if(Pattern.compile("highscore").matcher(input).find()){
            return "Your highscore is :"+this.profileMenu.getCurrentUser().getHighScore();
        }

        if(Pattern.compile("rank").matcher(input).find()){
            return "Your rank is :"+this.profileMenu.getCurrentUser().getRank();
        }

        if(Pattern.compile("slogan").matcher(input).find()){
            if(this.profileMenu.getCurrentUser().getSlogan().equals("")){
                return "Slogan is empty!";
            }
            return "Your slogan is :"+this.profileMenu.getCurrentUser().getSlogan();
        }

        return this.profileMenu.getCurrentUser().showAllInformation();











    }


}
