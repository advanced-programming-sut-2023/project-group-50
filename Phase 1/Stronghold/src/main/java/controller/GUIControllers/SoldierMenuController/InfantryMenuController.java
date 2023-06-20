package controller.GUIControllers.SoldierMenuController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.ObjectsPackage.People.Soldier.Infantry;
import view.show.MainMenu.MainMenu;
import view.show.SoldierMenu.CaptureMenu;

public class InfantryMenuController extends SoldierMenuController {
    private final Infantry infantry;

    public InfantryMenuController(Infantry soldier) {
        super(soldier);
        this.infantry = soldier;

        initButtons();
    }

    private void initButtons() {
        addButton("Capture gate", super.buttonHeight, new capture());
    }

    @Override
    public InfantryMenuController getThis() {
        return this;
    }

    public String getPromptCaptureX() {
        return infantry.getNextTurnData().getGate() == null
                ? "Enter X"
                : infantry.getNextTurnData().getGate().getX() + "(" +
                infantry.getNextTurnData().getGate().getOwner().getUserName() + ")";
    }

    public String getPromptCaptureY() {
        return infantry.getNextTurnData().getGate() == null
                ? "Enter Y"
                : infantry.getNextTurnData().getGate().getY() + "(" +
                infantry.getNextTurnData().getGate().getOwner().getUserName() + ")";
    }

    public boolean doesntHaveGate(int xTo, int yTo) {
        return infantry.getOwner().getGovernment().getMap().getXY(xTo, yTo).hasGate();
    }

    public void setCapture(int xTo, int yTo) {
        infantry.getNextTurnData().setGate(infantry.getOwner().getGovernment().getMap().getXY(xTo, yTo).getGate());
    }

    public void stopCapturing() {
        infantry.getNextTurnData().setGate(null);
    }

    private class capture implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                CaptureMenu captureMenu = new CaptureMenu();
                captureMenu.init(getThis());
                captureMenu.start(MainMenu.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
