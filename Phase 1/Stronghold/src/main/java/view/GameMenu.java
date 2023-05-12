package view;

import controller.Menus.GameMenuController;
import controller.control.Commands;
import controller.control.State;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private final GameMenuController gameMenuController;
    private Matcher nextMatcher;

    public GameMenu() {
        this.gameMenuController = new GameMenuController(this);
    }

    public GameMenuController getGameMenuController() {
        return gameMenuController;
    }

    public Matcher getNextMatcher() {
        return nextMatcher;
    }

    public void setNextMatcher(Matcher nextMatcher) {
        this.nextMatcher = nextMatcher;
    }


    public State run(Scanner scanner) {
        Matcher matcher;

        while (gameMenuController.gameIsFinished()) {
            boolean flag = false;
            String input = scanner.nextLine();

            if ((matcher = Commands.getMatcher(Commands.MOVE_UNIT, input)).matches())
                System.out.println(this.gameMenuController.moveUnit(matcher));
            if (Commands.getMatcher(Commands.LOGOUT, input).find()) {
                System.out.println("user logged out successfully!");
                return State.SIGN;
            } else if (Commands.getMatcher(Commands.SHOW_MAP, input).find()) {
                this.setNextMatcher(Commands.getMatcher(Commands.SHOW_MAP, input));
                return State.MAP;
            } else if ((matcher = Commands.getMatcher(Commands.PATROL_UNIT, input)).matches())
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
            else if (Commands.getMatcher(Commands.END_TURN, input).matches())
                System.out.println(this.gameMenuController.nextTurn());
            else if (Commands.getMatcher(Commands.SHOW_MY_DATA, input).matches())
                System.out.println(this.gameMenuController.getUserData());
            else if (Commands.getMatcher(Commands.EXIT, input).matches())
                return State.PROFILE;
            else if (Commands.getMatcher(Commands.GOVERNMENT_MENU, input).find()) {
                return State.GOVERNMENT;
            } else if ((matcher = Commands.getMatcher(Commands.CAPTURE_GATE, input)).matches())
                System.out.println(this.gameMenuController.captureGate(matcher));
            else if (Commands.getMatcher(Commands.MAKE_BATTERING_RAM, input).find())
                System.out.println(this.gameMenuController.makeBatteringRam());
            else if (Commands.getMatcher(Commands.MAKE_CATAPULT, input).find())
                System.out.println(this.gameMenuController.makeCatapult());
            else if (Commands.getMatcher(Commands.MAKE_PROTECTION, input).find())
                System.out.println(this.gameMenuController.makeProtection());
            else if (Commands.getMatcher(Commands.MAKE_FIRE_THROWER, input).find())
                System.out.println(this.gameMenuController.makeFireThrower());
            else
                System.out.println("invalid command");


            if (!flag) this.gameMenuController.setSelectedBuilding(null);
        }

        return State.PROFILE;
    }
}
