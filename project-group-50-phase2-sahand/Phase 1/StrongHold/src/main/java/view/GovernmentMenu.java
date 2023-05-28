package view;

import controller.Menus.GovernmentMenuController;
import controller.control.Commands;
import controller.control.State;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GovernmentMenu {
    private final GovernmentMenuController governmentMenuController;


    public GovernmentMenu() {
        this.governmentMenuController = new GovernmentMenuController(this);
    }

    public GovernmentMenuController getGovernmentMenuController() {
        return governmentMenuController;
    }

    public State run(Scanner scanner) {
        Matcher matcher;
        while (true) {
            String input = scanner.nextLine();
            if (Commands.getMatcher(Commands.SHOW_POPULARITY_FACTOR, input).matches())
                System.out.println(this.governmentMenuController.showPopularityFactor());
            else if (Commands.getMatcher(Commands.SHOW_POPULARITY, input).matches())
                System.out.println(this.governmentMenuController.showPopularity());
            else if (Commands.getMatcher(Commands.SHOW_FOOD_LIST, input).matches())
                System.out.println(this.governmentMenuController.showFoodList());
            else if ((matcher = Commands.getMatcher(Commands.ADD_FOOD, input)).matches())
                System.out.println(this.governmentMenuController.addFood(matcher));
            else if ((matcher = Commands.getMatcher(Commands.SET_FOOD_RATE, input)).matches())
                System.out.println(this.governmentMenuController.setRateFood(matcher));
            else if (Commands.getMatcher(Commands.SHOW_FOOD_RATE, input).matches())
                System.out.println(this.governmentMenuController.showRateFoodNumber());
            else if ((matcher = Commands.getMatcher(Commands.SET_TAX_RATE, input)).matches())
                System.out.println(this.governmentMenuController.setTaxRate(matcher));
            else if (Commands.getMatcher(Commands.SHOW_TAX_RATE, input).matches())
                System.out.println(this.governmentMenuController.showRateTaxNumber());
            else if (Commands.getMatcher(Commands.SET_FEAR_RATE, input).matches())
                System.out.println(this.governmentMenuController.setFearRate(matcher));
            else if (Commands.getMatcher(Commands.BACK, input).matches())
                return State.GAME;
            else if (Commands.getMatcher(Commands.START_GAME, input).matches()) {
                return State.GAME;
            } else System.out.println("invalid command");
        }
    }
}
