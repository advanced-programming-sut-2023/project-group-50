package controller.GUIControllers.SoldierMenuController;

import controller.GUIControllers.UnitMenuController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import model.Map.GroundType;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.People.Soldier.Engineer;
import view.show.MainMenu.MainMenu;
import view.show.SoldierMenu.BuildMenu;

public class EngineerMenuController extends SoldierMenuController {
    private final Engineer engineer;

    public EngineerMenuController(Engineer soldier) {
        super(soldier);
        this.engineer = soldier;

        addButtons();
    }

    private static boolean hasOil() {
        return UnitMenuController.getTexture().equals(GroundType.OIL);
    }

    private void addButtons() {
        String buildString = hasBuilding() ? "Remove building" : "Build";
        String oilString = hasOil() ? "Remove oil" : "Pour oil";
        String protectionString = hasProtection() ? "Remove protection" : "Make protection";
        String pitchDitchString = hasPitchDitch() ? "Remove pitch ditch" : "Place pitch ditch";

        addButton(oilString, super.buttonHeight, new oil());
        addButton(protectionString, super.buttonHeight, new makeProtection());
        if (canBuildHere())
            addButton(buildString, super.buttonHeight, new build());
        if (canPlacePitchDitch())
            addButton(pitchDitchString, super.buttonHeight, new placePitchDitch());
    }

    private boolean canPlacePitchDitch() {
        return UnitMenuController.canPlacePitchDitch(engineer.getOwner());
    }

    private boolean hasPitchDitch() {
        return UnitMenuController.hasPitchDitch(engineer.getOwner());
    }

    private boolean canBuildHere() {
        return UnitMenuController.canBuild(engineer.getOwner());
    }

    private boolean hasBuilding() {
        return UnitMenuController.hasBuilding(engineer.getOwner());
    }

    private boolean hasProtection() {
        return UnitMenuController.isProtected();
    }

    @Override
    public EngineerMenuController getThis() {
        return this;
    }

    public boolean canAfford(BuildingType buildingType) {
        return engineer.getOwner().getGovernment().canAfford(buildingType);
    }

    public boolean canPlace(BuildingType buildingType) {
        return Building.canPlace(
                buildingType,
                engineer.getOwner().getGovernment().getMap().getXY(engineer.getX(), engineer.getY()).getTexture()
        );
    }

    public boolean hasPalace() {
        return engineer.getOwner().getGovernment().hasPalace();
    }

    public void place(BuildingType buildingType) {
        engineer.build(buildingType);
    }

    private void removeBuilding() {
        engineer.getOwner().getGovernment().getMap().getXY(engineer.getX(), engineer.getY()).removeObject(
                engineer.getOwner().getGovernment().getMap().getXY(engineer.getX(), engineer.getY()).getBuilding()
        );
    }

    private class build implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (hasBuilding())
                removeBuilding();
            else try {
                BuildMenu buildMenu = new BuildMenu();
                buildMenu.init(getThis());
                buildMenu.start(MainMenu.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class oil implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (hasOil()) {
                engineer.getOwner().getGovernment().getMap().getXY(engineer.getX(), engineer.getY())
                        .setTexture(GroundType.PLAIN);
            } else {
                if (!engineer.hasOil()) {
                    new Alert(Alert.AlertType.ERROR, "This engineer doesn't have oil").show();
                    return;
                }
                engineer.getOwner().getGovernment().getMap().getXY(engineer.getX(), engineer.getY())
                        .setTexture(GroundType.OIL);
            }

            getThis().showSoldierMenu(null);
        }
    }

    private class makeProtection implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (hasProtection())
                engineer.getOwner().getGovernment().getMap().getXY(engineer.getX(), engineer.getY()).setProtected(false);
            else engineer.makeProtection(engineer.getX(), engineer.getY());

            getThis().showSoldierMenu(null);
        }
    }

    private class placePitchDitch implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (!hasPitchDitch())
                engineer.placePitchDitch(engineer.getX(), engineer.getY());
            else
                removeBuilding();

            getThis().showSoldierMenu(null);
        }
    }
}
