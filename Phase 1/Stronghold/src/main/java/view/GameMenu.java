package view;

import controller.Menus.GameMenuController;
import controller.control.Commands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private final GameMenuController gameMenuController;

    public GameMenu() {
        this.gameMenuController = new GameMenuController(this);
    }

    public GameMenuController getGameMenuController() {
        return gameMenuController;
    }

    public void run(Scanner scanner) {
        Matcher matcher;

        while (true) {
            boolean flag = false;
            String input = scanner.nextLine();
            if ((matcher = Commands.getMatcher(Commands.MOVE_UNIT, input)).matches())
                System.out.println(this.gameMenuController.moveUnit(matcher));
            else if ((matcher = Commands.getMatcher(Commands.PATROL_UNIT, input)).matches())
                System.out.println(this.gameMenuController.patrolUnit(matcher));
            else if ((matcher = Commands.getMatcher(Commands.SET_UNIT, input)).matches())
                System.out.println(this.gameMenuController.setUnit(matcher));
            else if ((matcher = Commands.getMatcher(Commands.ATTACK_ENEMY, input)).matches())
                System.out.println(this.gameMenuController.attackEnemy(matcher));
            else if ((matcher = Commands.getMatcher(Commands.ARCHER_ATTACK, input)).matches())
                System.out.println(this.gameMenuController.archerAttack(matcher));
            else if ((matcher = Commands.getMatcher(Commands.POUR_OIL, input)).matches())
                System.out.println(this.gameMenuController.pourOil(matcher));
            else if ((matcher = Commands.getMatcher(Commands.DIG_TUNNEL, input)).matches())
                System.out.println(this.gameMenuController.digTunnel(matcher));
            else if ((matcher = Commands.getMatcher(Commands.BUILD, input)).matches())
                System.out.println(this.gameMenuController.build(matcher));
            else if (Commands.getMatcher(Commands.DISBAND_UNIT, input).matches())
                System.out.println(this.gameMenuController.disband());
            else if ((matcher = Commands.getMatcher(Commands.DROP_BUILDING, input)).matches())
                System.out.println(this.gameMenuController.dropBuilding(matcher));
            else if (Commands.getMatcher(Commands.SELECT_BUILDING, input).matches()) {
                System.out.println(this.gameMenuController.selectBuilding(matcher));
                flag = true;
            } else if (Commands.getMatcher(Commands.REPAIR, input).matches())
                System.out.println(this.gameMenuController.repair());
            else if (Commands.getMatcher(Commands.CREATE_UNIT, input).matches())
                System.out.println(this.gameMenuController.createUnit(matcher));
            else System.out.println("invalid command");
            if (!flag) {
                this.gameMenuController.setSelectedBuilding(null);
            }
        }
    }
}
