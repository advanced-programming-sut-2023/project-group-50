package view;

import controller.Menus.GameMenuController;
import controller.control.Commands;

import java.util.Scanner;

public class GameMenu {
    private final GameMenuController gameMenuController;

    public GameMenuController getGameMenuController() {
        return gameMenuController;
    }

    public GameMenu() {
        this.gameMenuController = new GameMenuController(this);
    }

    public void run(Scanner scanner) {

        while (true){
            int flag=0;
            String input=scanner.nextLine();
            if (Commands.getMatcher (Commands.DROP_BUILDING,input).find ()){
                System.out.println (this.gameMenuController.dropBuilding (Commands.getMatcher (Commands.DROP_BUILDING,input)));
            }
            else if (Commands.getMatcher (Commands.SELECT_BUILDING,input).find ()) {
                System.out.println (this.gameMenuController.selectBuilding (Commands.getMatcher (Commands.SELECT_BUILDING,input)));
                flag=1;
            }
            else if (Commands.getMatcher (Commands.REPAIR,input).find ()) {
                System.out.println (this.gameMenuController.repair ());
            }
            else if (Commands.getMatcher (Commands.CREATE_UNIT,input).find ()) {
                System.out.println ();
            }
            else {
                System.out.println("invalid command");
            }
            if(flag==0){
                this.gameMenuController.setSelectedBuilding (null);
            }


        }







    }
}
