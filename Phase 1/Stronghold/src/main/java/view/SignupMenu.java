package View;

import Controllers.control.Commands;
import Controllers.SignupController;
import Controllers.control.State;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu {
    private SignupController signupController;
    private Matcher nextMatcher;


     public SignupMenu(){
         this.signupController=new SignupController(this);

     }

    public Matcher getNextMatcher() {
        return nextMatcher;
    }

    public void setNextMatcher(Matcher nextMatcher) {
        this.nextMatcher = nextMatcher;
    }

    public State run(Scanner scanner){


        while (true){
            String input;
            if(this.nextMatcher!=null){
                this.nextMatcher.find();
                input=this.nextMatcher.group();
                this.nextMatcher=null;
            }
            else {
                input=scanner.nextLine();
            }


                if(Commands.getMatcher(Commands.CREATUSER,input).find()){
                    System.out.println( this.signupController.createUser(Commands.getMatcher(Commands.CREATUSER,input),scanner));
                } else if (Commands.getMatcher(Commands.EXIT,input).find()) {
                    return State.EXIT;
                    
                } else if (Commands.getMatcher(Commands.LOGIN,input).find()) {
                    this.setNextMatcher(Commands.getMatcher(Commands.LOGIN,input));
                    return State.LOGIN;
                } else {
                    System.out.println("Invalid Command!");
                }



        }


     }



}
