package controller.GUIControllers;

import controller.UserDatabase.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import model.Direction.Direction;
import model.Government.Government;
import model.Map.GUI.Unit.UnitPane;
import model.Map.GroundType;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.People.Soldier.SoldierName;
import model.ObjectsPackage.Rock;
import model.ObjectsPackage.Tree;
import model.ObjectsPackage.TreeType;
import model.ObjectsPackage.Weapons.WeaponName;
import view.show.MainMenu.MainMenu;
import view.show.Menus.ShopMenuShow;
import view.show.UnitMenu.ChangeTextureMenu;
import view.show.UnitMenu.DropUnitMenu;
import view.show.UnitMenu.TreeMenu;
import view.show.UnitMenu.UnitMenu;

import java.util.Objects;

public class UnitMenuController {
    private static User user;
    private static Unit unit;

    public static void init(User user, Unit unit) {
        UnitMenuController.user = user;
        UnitMenuController.unit = unit;
    }

    public static Pane getPane(Unit unit) {
        UnitPane unitPane = new UnitPane(unit);
        return unitPane.getPane();
    }

    public static void showMainMenu(ActionEvent ignoredActionEvent) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.init(user);
        try {
            mainMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeTexture(ActionEvent ignoredActionEvent) {
        try {
            new ChangeTextureMenu().start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void showUnitMenu(ActionEvent ignoredActionEvent) {
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

    public static void repairBuilding(ActionEvent ignoredActionEvent) {
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

        unit.getBuilding().repair();

        UnitMenu unitMenu = new UnitMenu();
        unitMenu.initialize(unit);
        try {
            unitMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        new Alert(Alert.AlertType.INFORMATION, "Repaired successfully").show();
    }

    public static void dropTree(ActionEvent ignoredActionEvent) {
        if (!Tree.canPlace(unit.getX(), unit.getY(), MainMenuGUIController.getUser())) {
            new Alert(Alert.AlertType.ERROR, "Ground type is not valid for tree").show();
            return;
        }
        if (unit.hasObjectByType(ObjectType.BUILDING)) {
            new Alert(Alert.AlertType.ERROR, "There is a building here").show();
            return;
        }
        if (unit.hasObjectByType(ObjectType.ROCK)) {
            new Alert(Alert.AlertType.ERROR, "There is a rock here").show();
            return;
        }
        if (unit.getTree() != null && !Objects.equals(unit.getTree().getOwner(), MainMenuGUIController.getUser())) {
            new Alert(Alert.AlertType.ERROR, "This tree is not yours").show();
            return;
        }

        try {
            new TreeMenu().start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Tree getTree() {
        return unit.getTree();
    }

    public static void addTree(TreeType type) {
        if (unit.getTree() != null)
            unit.removeObject(unit.getTree());
        unit.addObject(new Tree(type, MainMenuGUIController.getUser()));
    }

    public static void dropRock(ActionEvent ignoredActionEvent) {
        if (!Rock.canPlace(unit.getX(), unit.getY(), MainMenuGUIController.getUser())) {
            new Alert(Alert.AlertType.ERROR, "Ground type is not valid for tree").show();
            return;
        }
        if (unit.hasObjectByType(ObjectType.BUILDING)) {
            new Alert(Alert.AlertType.ERROR, "There is a building here").show();
            return;
        }
        if (unit.hasObjectByType(ObjectType.TREE)) {
            new Alert(Alert.AlertType.ERROR, "There is a tree here").show();
            return;
        }
        if (unit.getRock() != null && !Objects.equals(unit.getRock().getOwner(), MainMenuGUIController.getUser())) {
            new Alert(Alert.AlertType.ERROR, "This rock is not yours").show();
            return;
        }

        if (unit.getRock() != null) {
            unit.removeObject(unit.getRock());
        }

        unit.addObject(new Rock(Direction.getRandomDirection(), MainMenuGUIController.getUser()));

        UnitMenu unitMenu = new UnitMenu();
        unitMenu.initialize(unit);
        try {
            unitMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void clear(ActionEvent ignoredActionEvent) {
        unit.clear(MainMenuGUIController.getUser());

        UnitMenu unitMenu = new UnitMenu();
        unitMenu.initialize(unit);
        try {
            unitMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void shop(ActionEvent actionEvent) {
        ShopMenuShow shopMenuShow = new ShopMenuShow ();
        shopMenuShow.init ( UnitMenuController.user );
        try {
            shopMenuShow.start ( MainMenu.getStage () );
        } catch (Exception e) {
            throw new RuntimeException ( e );
        }
    }
    public static void dropUnit(ActionEvent ignoredActionEvent) {
        if (!Person.canPlace(unit.getTexture()) ||
                unit.hasObjectByType(ObjectType.ROCK) ||
                unit.hasObjectByType(ObjectType.TREE)) {
            new Alert(Alert.AlertType.ERROR, "Can't place a soldier here").show();
            return;
        }

        if (unit.hasObjectByType(ObjectType.BUILDING) && !Objects.equals(unit.getBuilding().getOwner(), user)) {
            new Alert(Alert.AlertType.ERROR, "Can't place a soldier in other player's buildings").show();
            return;
        }

        DropUnitMenu dropUnitMenu = new DropUnitMenu();
        try {
            dropUnitMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean canAfford(SoldierName selected, int number) {
        return user.getGovernment().canAffordSoldiers(selected, number);
    }

    public static boolean hasEnoughWeapons(SoldierName selected, int number) {
        return user.getGovernment().hasEnoughWeapons(selected, number);
    }

    public static boolean hasEnoughArmour(SoldierName selected, int number) {
        return user.getGovernment().hasEnoughArmour(selected, number);
    }

    public static void dropUnits(SoldierName selected, int number) {
        Government government = user.getGovernment();
        WeaponName weaponName = Soldier.getWeaponName(selected);
        government.setCoins(government.getCoins() - (double) number * selected.getCoinCost());
        government.setWeaponAmount(weaponName, government.getWeaponAmount(weaponName) - number);
        government.decrementArmourAmount(selected.getArmourType(), number);

        for (int i = 0; i < number; i++)
            unit.addObject(Soldier.getSoldierByType(selected, user));
    }

    public static boolean isProtected() {
        return unit.isProtected();
    }

    public static boolean hasBuilding(User owner) {
        return unit.getBuilding() != null && Objects.equals(unit.getBuilding().getOwner(), owner);
    }

    public static boolean canBuild(User owner) {
        return unit.getBuilding() == null || Objects.equals(unit.getBuilding().getOwner(), owner);
    }

    public static boolean hasPitchDitch(User owner) {
        return unit.getBuilding() != null && unit.getBuilding().getType().equals(BuildingType.PITCH_DITCH) &&
                unit.getBuilding().getOwner() == owner;
    }

    public static boolean canPlacePitchDitch(User owner) {
        return unit.getBuilding() == null || (unit.getBuilding().getType().equals(BuildingType.PITCH_DITCH) &&
                unit.getBuilding().getOwner() == owner);
    }
}
