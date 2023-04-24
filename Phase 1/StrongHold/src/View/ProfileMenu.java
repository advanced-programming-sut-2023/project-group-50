package View;

import Controllers.ProfileController;
import Controllers.control.Commands;
import Controllers.control.State;
import Moudel.User;
import java.util.Scanner;

public class ProfileMenu {

    private ProfileController profileController;
    private User currentUser;

    public ProfileMenu(){
        this.profileController=new ProfileController(this);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public State run(Scanner scanner){

        while (true){
            String input=scanner.nextLine();



            if (Commands.getMatcher(Commands.LOGOUT,input).find()) {
                return State.SIGN;
            } else if (Commands.getMatcher(Commands.PROFILECHANGE,input).find()) {
                System.out.println(this.profileController.profileChange(Commands.getMatcher(Commands.PROFILECHANGE,input)));
            } else if (Commands.getMatcher(Commands.REMOVESLOGAN,input).find()) {
                System.out.println(this.profileController.removeSlogan());
            } else if (Commands.getMatcher(Commands.CHANGESLOGAN,input).find()) {
                System.out.println(this.profileController.profileChange(Commands.getMatcher(Commands.CHANGESLOGAN,input)));
            } else if (Commands.getMatcher(Commands.CHANGEPASS,input).find()) {
                System.out.println(this.profileController.changePassword(Commands.getMatcher(Commands.CHANGEPASS,input),scanner));
            }else if (Commands.getMatcher(Commands.PROFILEDISPlAY,input).find()) {
                System.out.println(this.profileController.profileDisplay(Commands.getMatcher(Commands.PROFILEDISPlAY,input)));
            } else {
                System.out.println("Invalid Command!");
            }



        }



    }

}
