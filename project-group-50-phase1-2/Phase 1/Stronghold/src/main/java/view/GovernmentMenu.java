package view;

import controller.Menus.GameMenuController;
import controller.Menus.GovernmentMenuController;
import controller.control.Commands;
import controller.control.State;

import java.util.Scanner;

public class GovernmentMenu {
    private  GovernmentMenuController governmentMenuController;


    public GovernmentMenu (){
        this.governmentMenuController=new GovernmentMenuController (this);
    }

    public GovernmentMenuController getGovernmentMenuController() {
        return governmentMenuController;
    }

    public State run(Scanner scanner){

        while (true){
            String input=scanner.nextLine ();
            if (Commands.getMatcher (Commands.SHOW_POPULARITY_FACTOR,input).find ()){
                System.out.println (this.governmentMenuController.showPopularityFactor ());
            }
            else if (Commands.getMatcher (Commands.SHOW_POPULARITY,input).find ()) {
                System.out.println (this.governmentMenuController.showPopularity());
            }
            else if (Commands.getMatcher (Commands.SHOW_FOOD_LIST,input).find ()) {
                System.out.println (this.governmentMenuController.showFoodList ());
            }
            else if (Commands.getMatcher (Commands.SET_FOOD_RATE,input).find ()) {
                System.out.println (this.governmentMenuController.setRateFood (Commands.getMatcher (Commands.SET_FOOD_RATE,input)));
            }
            else if (Commands.getMatcher (Commands.SHOW_FOOD_RATE,input).find ()) {
                System.out.println (this.governmentMenuController.showRateFoodNumber ());
            }
            else if (Commands.getMatcher (Commands.SET_TAX_RATE,input).find ()) {
                System.out.println (this.governmentMenuController.setTaxRate (Commands.getMatcher (Commands.SET_TAX_RATE,input)));
            }else if (Commands.getMatcher (Commands.SHOW_TAX_RATE,input).find ()) {
                System.out.println (this.governmentMenuController.showRateTaxNumber ());
            }
            else if ( Commands.getMatcher ( Commands.SET_FEAR_RATE, input ).find () ){
                System.out.println ( );
            }
            else if (Commands.getMatcher (Commands.BACK,input).find ()) {
                return State.GAME;
            }
            else {
                System.out.println("invalid command");
            }



        }

    }
}
