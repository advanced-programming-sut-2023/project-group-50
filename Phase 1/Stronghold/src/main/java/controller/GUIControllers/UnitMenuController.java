package controller.GUIControllers;

import Server.Client;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
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
    private static String username;
    private static int X;
    private static int Y;

    public static void init(User user, int X, int Y) {
        UnitMenuController.username = user.getUserName();
        UnitMenuController.X = X;
        UnitMenuController.Y = Y;
    }

    public static Unit getUnit() {
        return Users.getUser(username).getGovernment().getMap().getUnitByXY(X, Y);
    }

    public static Pane getPane(String username, int X, int Y) {
        UnitPane unitPane = new UnitPane(username, X, Y);
        return unitPane.getPane();
    }

    public static void showMainMenu(ActionEvent ignoredActionEvent) {
        Client.sendData();
        MainMenu mainMenu = new MainMenu();
        mainMenu.init(Users.getUser(username));
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
        Client.sendData();
        showUnitMenu();
    }

    public static GroundType getTexture() {
        return getUnit().getTexture();
    }

    public static void setTexture(GroundType texture) {
        getUnit().setTexture(texture);
    }

    public static void update() {
        Users.getUser(username).getGovernment().getMap().setXY(getUnit().getX(), getUnit().getY(), getUnit());
        Client.sendData();
    }

    public static void repairBuilding(ActionEvent ignoredActionEvent) {
        Building building = getUnit().getBuilding();
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

        getUnit().getBuilding().repair();

        showUnitMenu();

        new Alert(Alert.AlertType.INFORMATION, "Repaired successfully").show();
    }

    public static void dropTree(ActionEvent ignoredActionEvent) {
        if (!Tree.canPlace(getUnit().getX(), getUnit().getY(), MainMenuGUIController.getUser())) {
            new Alert(Alert.AlertType.ERROR, "Ground type is not valid for tree").show();
            return;
        }
        if (getUnit().hasObjectByType(ObjectType.BUILDING)) {
            new Alert(Alert.AlertType.ERROR, "There is a building here").show();
            return;
        }
        if (getUnit().hasObjectByType(ObjectType.ROCK)) {
            new Alert(Alert.AlertType.ERROR, "There is a rock here").show();
            return;
        }
        if (getUnit().getTree() != null &&
                !Objects.equals(getUnit().getTree().getOwner(), MainMenuGUIController.getUser())) {
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
        return getUnit().getTree();
    }

    public static void addTree(TreeType type) {
        if (getUnit().getTree() != null)
            getUnit().removeObject(getUnit().getTree());
        getUnit().addObject(new Tree(type, MainMenuGUIController.getUser()));
    }

    public static void dropRock(ActionEvent ignoredActionEvent) {
        if (!Rock.canPlace(getUnit().getX(), getUnit().getY(), MainMenuGUIController.getUser())) {
            new Alert(Alert.AlertType.ERROR, "Ground type is not valid for tree").show();
            return;
        }
        if (getUnit().hasObjectByType(ObjectType.BUILDING)) {
            new Alert(Alert.AlertType.ERROR, "There is a building here").show();
            return;
        }
        if (getUnit().hasObjectByType(ObjectType.TREE)) {
            new Alert(Alert.AlertType.ERROR, "There is a tree here").show();
            return;
        }
        if (getUnit().getRock() != null &&
                !Objects.equals(getUnit().getRock().getOwner(), MainMenuGUIController.getUser())) {
            new Alert(Alert.AlertType.ERROR, "This rock is not yours").show();
            return;
        }

        if (getUnit().getRock() != null) {
            getUnit().removeObject(getUnit().getRock());
        }

        getUnit().addObject(new Rock(Direction.getRandomDirection(), MainMenuGUIController.getUser()));

        showUnitMenu();
    }

    private static void showUnitMenu() {
        update();
        UnitMenu unitMenu = new UnitMenu();
        unitMenu.initialize(username, X, Y);
        try {
            unitMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void clear(ActionEvent ignoredActionEvent) {
        getUnit().clear(MainMenuGUIController.getUser());

        showUnitMenu();
    }

    public static void shop(ActionEvent actionEvent) {
        ShopMenuShow shopMenuShow = new ShopMenuShow();
        shopMenuShow.init(UnitMenuController.getUnit().getOwner());
        try {
            shopMenuShow.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void dropUnit(ActionEvent ignoredActionEvent) {
        if (!Person.canPlace(getUnit().getTexture()) ||
                getUnit().hasObjectByType(ObjectType.ROCK) ||
                getUnit().hasObjectByType(ObjectType.TREE)) {
            new Alert(Alert.AlertType.ERROR, "Can't place a soldier here").show();
            return;
        }

        if (getUnit().hasObjectByType(ObjectType.BUILDING) &&
                !Objects.equals(getUnit().getBuilding().getOwner(), Users.getUser(username))) {
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
        return Users.getUser(username).getGovernment().canAffordSoldiers(selected, number);
    }

    public static boolean hasEnoughWeapons(SoldierName selected, int number) {
        return Users.getUser(username).getGovernment().hasEnoughWeapons(selected, number);
    }

    public static boolean hasEnoughArmour(SoldierName selected, int number) {
        return Users.getUser(username).getGovernment().hasEnoughArmour(selected, number);
    }

    public static void dropUnits(SoldierName selected, int number) {
        Government government = Users.getUser(username).getGovernment();
        WeaponName weaponName = Soldier.getWeaponName(selected);
        government.setCoins(government.getCoins() - (double) number * selected.getCoinCost());
        government.setWeaponAmount(weaponName, government.getWeaponAmount(weaponName) - number);
        government.decrementArmourAmount(selected.getArmourType(), number);

        for (int i = 0; i < number; i++)
            getUnit().addObject(Soldier.getSoldierByType(selected, Users.getUser(username)));
    }

    public static boolean isProtected() {
        return getUnit().isProtected();
    }

    public static boolean hasBuilding(User owner) {
        return getUnit().getBuilding() != null && Objects.equals(getUnit().getBuilding().getOwner(), owner);
    }

    public static boolean canBuild(User owner) {
        return getUnit().getBuilding() == null || Objects.equals(getUnit().getBuilding().getOwner(), owner);
    }

    public static boolean hasPitchDitch(User owner) {
        return getUnit().getBuilding() != null && getUnit().getBuilding().getType().equals(BuildingType.PITCH_DITCH) &&
                getUnit().getBuilding().getOwner() == owner;
    }

    public static boolean canPlacePitchDitch(User owner) {
        return getUnit().getBuilding() == null || (getUnit().getBuilding().getType().equals(BuildingType.PITCH_DITCH) &&
                getUnit().getBuilding().getOwner() == owner);
    }
}
