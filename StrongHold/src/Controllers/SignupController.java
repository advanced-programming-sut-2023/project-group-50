package Controllers;

import Controllers.control.*;
import Controllers.control.Error;
import Moudel.User;
import View.SignupMenu;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupController {
    private SignupMenu signupMenu;


    public SignupController (SignupMenu signupMenu){
        this.signupMenu=signupMenu;

    }



    public String createUser(Matcher matcher,Scanner scanner) {
        matcher.find();
        String input = matcher.group();

        if(!userNameIsValid(input,scanner).truth){
            return userNameIsValid(input,scanner).errorMassage;
        }

        String username=userNameIsValid(input,scanner).errorMassage;

        if(!passwordIsValid(input,scanner).truth){
            return passwordIsValid(input,scanner).errorMassage;
        }
        String password=passwordIsValid(input,scanner).errorMassage;

        if(!emailIsValid(input).truth){
            return emailIsValid(input).errorMassage;
        }

        String email=emailIsValid(input).errorMassage;

        if(!checkHasField(input,Commands.NICKNAME).truth){
            return checkHasField(input,Commands.NICKNAME).errorMassage;
        }
        String nickname=checkHasField(input,Commands.NICKNAME).errorMassage;

        if(!checkHasField(input,Commands.SLOGAN).truth){
            return checkHasField(input,Commands.SLOGAN).errorMassage;
        }
        String slogan=checkHasField(input,Commands.SLOGAN).errorMassage;
        if (slogan.equals("random")){
            slogan=randomSlogan();
        }

        User user=new User(username,password,nickname,email,slogan);
        pickSecurityQuestion(scanner,user);
        Users.addUser(user);
        return "Your signup successful!";

    }

    private Error checkHasField(String input,Commands command){
        Matcher matcher = Commands.getMatcher(command,input);

        if(!matcher.find()){
            if (command.name().equals("SLOGAN")){
                return new Error("",true);
            }
            return new Error("You should enter all field!\nEnter a "+command.name(),false);
        }

        String string = matcher.group();
        string=string.substring(3,string.length());

        string = removeDoubleCoutString(string);
        if(matcher.find())
            return new Error("Invalid command!\nYou should enter all field!\nEnter " + command.name() + " once", false);
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

    private Error userNameIsValid(String input,Scanner scanner){

        if(!checkHasField(input,Commands.USERNAME).truth){
            return checkHasField(input,Commands.USERNAME);
        }

        String username=checkHasField(input,Commands.USERNAME).errorMassage;

        Matcher matcher=Pattern.compile("[^\\w ]").matcher(username);

        if(matcher.find()){
            return new Error("Invalid Username format!\nyou can't use this character :"+matcher.group(),false);
        }

        if(Users.getUser(username)!=null) {
            System.out.print("The username entered is duplicate!\nyou want to use suggested username? (yes or no) : ");

            while (true){
                String string=scanner.nextLine();
                if(string.equals("no")){
                    return new Error("Please signup again",false);
                }
                else if(string.equals("yse")){
                    break;
                }
                else {
                    System.out.print("I'm sorry we don't understand please just enter yes or no : ");
                }

            }

            while (true) {
                int digit = (new Random().nextInt(1000));
                if (Users.getUser(username+digit) == null) {
                    System.out.print("you wanna use this username : "+username+digit+"\nPleas just enter (yes or no or quit) : ");
                    while (true){
                        String string=scanner.nextLine();
                        if(string.equals("quit")){
                            return new Error("Please signup again",false);
                        }
                        else if(string.equals("yse")){
                            return new Error(username+digit,true);

                        } else if (string.equals("no")) {
                            break;

                        } else {
                            System.out.print("I'm sorry we don't understand please just enter yes or no or quit : ");
                        }

                    }
                }
            }
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
        if(allPart.equals("random")){
            String pass=randomPassword();
            System.out.print("Your random password is: "+pass+"\nPlease re-enter your password here:");
            while (true){
                String string=scanner.nextLine();
                if(string.equals(pass)){
                    System.out.print("your random password is not this "+string+"\nPlease try again : ");
                }
                else {
                    System.out.print("your password confirm is successful");
                    break;
                }
            }
            return new Error(pass,true);
        }
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

    private String randomPassword(){
        int number=new  Random().nextInt(30-6+1)+6;
        char[] chars=new char[number];
        ArrayList<Character> characterArrayList=new ArrayList<>(4);

        char capital= (char) (new Random().nextInt('Z'-'A'+1)+'A');
        char small= (char) (new Random().nextInt('z'-'a'+1)+'a');
        char digit=(char) (new Random().nextInt('9'-'0'+1)+'0');
        char random;
        while (true){
            random=(char) (new Random().nextInt(256)+32);
            if((Character.isLowerCase(random)) || (Character.isUpperCase(random)) || (Character.isDigit(random)) || (Character.isWhitespace(random))){
                continue;
            }
            break;
        }
        characterArrayList.add(capital);
        characterArrayList.add(small);
        characterArrayList.add(digit);
        characterArrayList.add(random);

        int j=0;
        while (j<4) {
            int x = new Random().nextInt(number);
            if(chars[x]!='\0'){
                continue;
            }
            chars[x]=characterArrayList.get(j);
            j++;
        }
        for (int i=0;i<number;i++){
            if(chars[i]=='\0'){
               while (true) {
                   random = (char) (new Random().nextInt(256) + 32);
                   if ((Character.isWhitespace(random))) {
                       continue;
                   }
                   chars[i]=random;
                   i++;
                   break;
               }
            }
        }

        String pass=new String(chars);
        return pass;

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

    private String randomSlogan(){
        int number=new Random().nextInt(Slogans.getNumberSlogans());
        String slogan=Slogans.getSlogansByNumber(number);

        System.out.println("Your slogan is \""+slogan+"\"");
        return slogan;

    }

    private void pickSecurityQuestion(Scanner scanner,User user){
        showSecurityQuestions();

        while (true) {
            String input = scanner.nextLine();
            if(Commands.getMatcher(Commands.PICKQUESTION,input).find()){
                if(!checkHasField(input,Commands.QUESTION).truth){
                    System.out.println(checkHasField(input,Commands.QUESTION).errorMassage);
                }
                String question=checkHasField(input,Commands.QUESTION).errorMassage;

                if(Pattern.compile("\\D").matcher(question).find()){
                    System.out.println("You should enter a number in question field\nTry again");
                    continue;
                }
                int number=Integer.parseInt(question);
                
                if(number>SecurityQuestion.getNumber()){
                    System.out.println("You should enter a number lower than "+SecurityQuestion.getNumber()+" in question field\nTry again");
                    continue;
                }
                
                if(!checkHasField(input,Commands.ANSWERQUESTION).truth){
                    System.out.println(checkHasField(input,Commands.ANSWERQUESTION).errorMassage);
                }
                String answer=checkHasField(input, Commands.ANSWERQUESTION).errorMassage;
                
                if(!checkHasField(input,Commands.ANSWERCONFIRM).truth){
                    System.out.println(checkHasField(input,Commands.ANSWERCONFIRM).errorMassage);
                }
                String confirmAnswer=checkHasField(input,Commands.ANSWERCONFIRM).errorMassage;

                if(!answer.equals(confirmAnswer)){
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

    private void showSecurityQuestions(){
        for (SecurityQuestion securityQuestion:SecurityQuestion.values()){
            System.out.println((securityQuestion.ordinal()+1)+". "+securityQuestion.getQuestion());
        }

    }










}
