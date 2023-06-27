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
            String input=scanner.nextLine();
            if (true){

            }
            else {
                System.out.println("invalid command");
            }


        }







    }
}
