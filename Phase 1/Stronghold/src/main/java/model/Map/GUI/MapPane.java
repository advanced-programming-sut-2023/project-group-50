package model.Map.GUI;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import model.Map.Map;
import model.RandomGenerator.RandomRuin;

import java.util.HashMap;
import java.util.Map.Entry;

public class MapPane {
    private static Pair getXY(double tileHeight, double tileWidth, int X0, int Y0, int x, int y) {
        int DX = x - X0, DY = y - Y0;
        return new Pair(DX * tileHeight / 2 + DY * tileHeight / 2,
                        DX * tileWidth / 2 - DY * tileWidth / 2);
    }

    private static Pair getXYReal(double tileHeight,
                                  double tileWidth,
                                  int X0,
                                  int Y0,
                                  double xScreen,
                                  double yScreen) {
        return new Pair(Math.ceil(xScreen / tileHeight + yScreen / tileWidth) + X0,
                        Math.ceil(xScreen / tileWidth - yScreen / tileHeight) + Y0);
    }

    private static boolean XYisValid(double tileHeight,
                                     double tileWidth,
                                     double xScreen,
                                     double yScreen) {
        if ((xScreen / tileHeight + yScreen / tileWidth) % 1.0 != 0) return false;
        return (xScreen / tileWidth - yScreen / tileHeight) % 1.0 == 0;
    }

    public static Pane getMapPane(Map map, double tileHeight, double tileWidth, int topLeftX, int topLeftY) {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        Pane pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setPrefSize(width, height);

        HashMap<Pair, Group> buildings = new HashMap<>();
        HashMap<Pair, Group> people = new HashMap<>();

        for (double dx = -tileWidth / 2; dx <= width; dx += tileWidth / 2) {
            for (double dy = -tileHeight / 2; dy <= height; dy += tileHeight / 2) {
                if (!XYisValid(tileHeight, tileWidth, dx, dy)) continue;
                Pair pair = getXYReal(tileHeight, tileWidth, topLeftX, topLeftY, dx, dy);
                int x = (int) pair.x;
                int y = (int) pair.y;

                Group group;
                if (map.isValid(x, y)) {
                    UnitGroup unitGroup = new UnitGroup(map.getUnitByXY(x, y), tileHeight, tileWidth);
                    group = unitGroup.getWallpaperGroup();
                    if (unitGroup.hasBuilding())
                        buildings.put(new Pair(dx, dy), unitGroup.getBuilding());
                    if (unitGroup.hasObjects())
                        people.put(new Pair(dx, dy), unitGroup.getPeople());
                } else {
                    group = getRandomRuinGroup(tileHeight, tileWidth);
                }
                pane.getChildren().add(group);
                group.setLayoutX(dx);
                group.setLayoutY(dy);
            }
        }

        addHashMap(pane, buildings);
        addHashMap(pane, people);

        return pane;
    }

    private static void addHashMap(Pane pane, HashMap<Pair, Group> people) {
        for (Entry<Pair, Group> entry : people.entrySet()) {
            double x = entry.getKey().x, y = entry.getKey().y;
            Group group = entry.getValue();
            pane.getChildren().add(group);
            group.setLayoutX(x);
            group.setLayoutY(y);
        }
    }

    private static Group getRandomRuinGroup(double tileHeight, double tileWidth) {
        Image image = new Image(MapPane.class.getResource(
                "/phase2-assets/" + RandomRuin.getRandomRuin()).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(tileHeight);
        imageView.setFitWidth(tileWidth);

        return new Group(imageView);
    }

    private static class Pair {
        double x;
        double y;

        public Pair(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
