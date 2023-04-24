package Controllers;

import Controllers.control.Commands;
import Controllers.control.Error;
import Controllers.control.Users;
import Moudel.User;
import View.ProfileMenu;

import java.util.ArrayList;
import java.util.Random;
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

    private Error passwordIsValid(String input,Scanner scanner){
        Matcher matcher=Commands.getMatcher(Commands.PASSWORD,input);
        if (!matcher.find()){
            return new Error("You should enter all field!\nEnter a PASSWORD",false);
        }
        String allPart=matcher.group();
        allPart=allPart.substring(3,allPart.length());

        String pass="";
        String confirmPass="";

        if((matcher=Commands.getMatcher(Commands.DOUBLEQOUT,allPart)).find()) {
            pass = matcher.group();
            pass = removeDoubleCoutString(pass);
            confirmPass=allPart.substring(matcher.end()+2,allPart.length()-1);
        }else {
            Matcher matcher1=Pattern.compile(" ").matcher(allPart);
            matcher1.find();
            pass=allPart.substring(0,matcher1.start());
            confirmPass=allPart.substring(matcher1.end(),allPart.length());
        }
        pass= pass.trim();
        confirmPass=confirmPass.trim();
        if(pass.equals("")){
            return new Error("You should fill password  field!",false);
        }
        if (pass.length()<6){
            return new Error("Your password should have 6 character",false);
        }
        if (!Commands.getMatcher(Commands.CAPITALLATTER,pass).find()){
            return new Error("Your password should have a capital letter",false);
        }
        if (!Commands.getMatcher(Commands.SMALLLETTER,pass).find()){
            return new Error("Your password should have a small letter",false);
        }
        if (!Commands.getMatcher(Commands.DIGIT,pass).find()){
            return new Error("Your password should have a digit ",false);
        }
        if (!Pattern.compile("[^a-zA-Z0-9 ]").matcher(pass).find()){
            return new Error("Your password should have a non-word character",false);
        }
        if (!pass.equals(confirmPass)){
            return new Error("Your confirm password isn't match with password",false);
        }
        return new Error(pass,true);
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

}
