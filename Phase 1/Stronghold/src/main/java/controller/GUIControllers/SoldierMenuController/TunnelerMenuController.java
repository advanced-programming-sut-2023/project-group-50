package controller.GUIControllers.SoldierMenuController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.ObjectsPackage.People.Soldier.Tunneler;
import view.show.MainMenu.MainMenu;
import view.show.SoldierMenu.TunnelMenu;

public class TunnelerMenuController extends SoldierMenuController {
    private final Tunneler tunneler;

    public TunnelerMenuController(Tunneler soldier) {
        super(soldier);
        tunneler = soldier;

        addButtons();
    }

    private void addButtons() {
        String tunnelString = hasTunnel() ? "Remove tunnel" : "Dig tunnel";

        addButton(tunnelString, super.buttonHeight, new dig());
        if (hasTunnel())
            addButton("Use tunnel", super.buttonHeight, new use());
    }

    private boolean hasTunnel() {
        return tunneler.hasTunnel();
    }

    public String getPromptTunnelX() {
        return !tunneler.getNextTurnData().isMoving() || !tunneler.getNextTurnData().usesTunnel(tunneler)
                ? "Enter X"
                : String.valueOf(tunneler.getNextTurnData().getToX());
    }

    public String getPromptTunnelY() {
        return !tunneler.getNextTurnData().isMoving() || !tunneler.getNextTurnData().usesTunnel(tunneler)
                ? "Enter Y"
                : String.valueOf(tunneler.getNextTurnData().getToY());
    }

    @Override
    public boolean cannotMoveTo(int xTo, int yTo) {
        return !hasTunnel() && super.cannotMoveTo(xTo, yTo) ||
                hasTunnel() && !tunneler.getOwner().getGovernment().getMap().getXY(xTo, yTo).hasTunnel()
                        && super.cannotMoveTo(xTo, yTo);
    }

    @Override
    public TunnelerMenuController getThis() {
        return this;
    }

    private class dig implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (hasTunnel())
                tunneler.getOwner().getGovernment().getMap().getXY(tunneler.getX(), tunneler.getY()).removeObject(
                        tunneler.getOwner().getGovernment().getMap().getXY(tunneler.getX(), tunneler.getY()).getTunnel()
                );
            else
                tunneler.digTunnel(tunneler.getX(), tunneler.getY());

            showSoldierMenu(null);
        }
    }

    private class use implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                TunnelMenu tunnelMenu = new TunnelMenu();
                tunnelMenu.init(getThis());
                tunnelMenu.start(MainMenu.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
