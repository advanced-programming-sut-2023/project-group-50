package controller.GUIControllers;

import controller.UserDatabase.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import model.Map.GUI.Unit.UnitPane;
import model.Map.GroundType;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.Building;
import view.show.MainMenu.MainMenu;
import view.show.UnitMenu.ChangeTextureMenu;
import view.show.UnitMenu.UnitMenu;

public class UnitMenuController {
    private static User user;
    private static Unit unit;

    public static void init(User user, Unit unit) {
        UnitMenuController.user = user;
        UnitMenuController.unit = unit;
    }

    public static Pane getPane(Unit unit) {
        return new UnitPane(unit).getPane();
    }

    public static void showMainMenu(ActionEvent actionEvent) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.init(user);
        try {
            mainMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeTexture(ActionEvent actionEvent) {
        try {
            new ChangeTextureMenu().start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void showUnitMenu(ActionEvent actionEvent) {
        UnitMenu unitMenu = new UnitMenu();
        unitMenu.initialize(unit);
        try {
            unitMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static GroundType getTexture() {
        return unit.getTexture();
    }

    public static void setTexture(GroundType texture) {
        unit.setTexture(texture);
    }

    public static void repairBuilding(ActionEvent actionEvent) {
        Building building = unit.getBuilding();
        if (building == null) {
            new Alert(Alert.AlertType.ERROR, "No building in here").show();
            return;
        }

        if (!building.getOwner().equals(MainMenuGUIController.getUser())) {
            new Alert(Alert.AlertType.ERROR, "This building is not yours").show();
            return;
        }

        String s = MainMenuGUIController.getUser().getGovernment().canRepair(building);
        if (s != null) {
            new Alert(Alert.AlertType.ERROR, s).show();
            return;
        }

        new Alert(Alert.AlertType.INFORMATION, "Repaired successfully").show();
    }
}
