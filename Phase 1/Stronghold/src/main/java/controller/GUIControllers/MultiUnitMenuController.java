package controller.GUIControllers;

import controller.UserDatabase.User;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import model.Map.GUI.Unit.MultiUnitPane;
import model.Map.GroundType;
import model.Map.Map;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.Rock;
import model.ObjectsPackage.Tree;
import view.show.MainMenu.MainMenu;
import view.show.UnitMenu.ChangeTextureMenu;
import view.show.UnitMenu.SelectionMenu;

import java.util.ArrayList;
import java.util.HashSet;

public class MultiUnitMenuController {
    private static Map map;
    private static int xFrom;
    private static int xTo;
    private static int yFrom;
    private static int yTo;

    public static void init(int xFrom, int xTo, int yFrom, int yTo, Map map) {
        MultiUnitMenuController.xFrom = xFrom;
        MultiUnitMenuController.xTo = xTo;
        MultiUnitMenuController.yFrom = yFrom;
        MultiUnitMenuController.yTo = yTo;
        MultiUnitMenuController.map = map;
    }

    public static void clear(ActionEvent ignoredActionEvent) {
        for (int x = xFrom; x <= xTo; x++)
            for (int y = yFrom; y <= yTo; y++)
                map.getXY(x, y).clear(MainMenuGUIController.getUser());


        showMultiUnitMenu(null);
    }

    public static boolean hasBuilding() {
        for (int x = xFrom; x <= xTo; x++)
            for (int y = yFrom; y <= yTo; y++)
                if (map.getXY(x, y).hasBuilding())
                    return true;

        return false;
    }

    public static ArrayList<Building> getBuildings() {
        ArrayList<Building> arrayList = new ArrayList<>();

        for (int x = xFrom; x <= xTo; x++)
            for (int y = yFrom; y <= yTo; y++)
                if (map.getXY(x, y).hasBuilding())
                    arrayList.add(map.getXY(x, y).getBuilding());

        return arrayList;
    }

    public static boolean hasOwner() {
        for (int x = xFrom; x <= xTo; x++)
            for (int y = yFrom; y <= yTo; y++)
                if (map.getXY(x, y).getOwner() != null)
                    return true;

        return false;
    }

    public static HashSet<User> getOwners() {
        HashSet<User> arrayList = new HashSet<>();

        for (int x = xFrom; x <= xTo; x++)
            for (int y = yFrom; y <= yTo; y++)
                if (map.getXY(x, y).getOwner() != null)
                    arrayList.add(map.getXY(x, y).getOwner());

        return arrayList;
    }

    public static HashSet<GroundType> getTextures() {
        HashSet<GroundType> arrayList = new HashSet<>();

        for (int x = xFrom; x <= xTo; x++)
            for (int y = yFrom; y <= yTo; y++)
                arrayList.add(map.getXY(x, y).getTexture());

        return arrayList;
    }

    public static ArrayList<Person> getPeople() {
        ArrayList<Person> arrayList = new ArrayList<>();

        for (int x = xFrom; x <= xTo; x++)
            for (int y = yFrom; y <= yTo; y++)
                for (Objects object : map.getXY(x, y).getObjects())
                    if (object instanceof Person person)
                        arrayList.add(person);

        return arrayList;
    }

    public static void changeTexture(ActionEvent ignoredActionEvent) {
        ChangeTextureMenu changeTextureMenu = new ChangeTextureMenu();
        changeTextureMenu.init(true);
        try {
            changeTextureMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void showMultiUnitMenu(ActionEvent ignoredActionEvent) {
        try {
            new SelectionMenu().start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Pane getPane() {
        return new MultiUnitPane(xFrom, xTo, yFrom, yTo, map).getPane();
    }

    public static GroundType getTexture() {
        return map.getXY(xFrom, yFrom).getTexture();
    }

    public static void setTexture(GroundType texture) {
        for (int x = xFrom; x <= xTo; x++)
            loop:
                    for (int y = yFrom; y <= yTo; y++) {
                        for (Objects object : map.getXY(x, y).getObjects()) {
                            if (object instanceof Person)
                                if (!Person.canPlace(texture))
                                    continue loop;
                            if (object instanceof Building building)
                                if (!Building.canPlace(building.getType(), texture))
                                    continue loop;
                            if (object instanceof Tree tree)
                                if (!tree.canPlace(texture))
                                    continue loop;
                            if (object instanceof Rock rock)
                                if (!rock.canPlace(texture))
                                    continue loop;
                        }
                        map.getXY(x, y).setTexture(texture);
                    }
    }
}
