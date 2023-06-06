package model.Map.GUI;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import model.Map.GroundType;
import model.Map.Map;

import java.util.HashMap;
import java.util.Map.Entry;

public class MapPane {
    private static double startX;
    private static double startY;
    private static int topLeftY;
    private static int topLeftX;
    private static double tileWidth;
    private static double tileHeight;
    private static Map map;
    private static Pane pane;
    private static HashMap<Integer, HashMap<Integer, UnitGroup>> unitGroups;
    private static HashMap<Integer, HashMap<Integer, Group>> ruinGroups;

    private static Pair diffXY(Pair xy) {
        return new Pair(xy.x / 2 + xy.y / 2, xy.x / 2 - xy.y / 2).by(0.75);
    }

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
        MapPane.tileHeight = tileHeight;
        MapPane.tileWidth = tileWidth;
        MapPane.topLeftX = topLeftX;
        MapPane.topLeftY = topLeftY;
        MapPane.map = map;

        initTiles();
        initPane();
        fillTiles();

        return pane;
    }

    private static void initPane() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setPrefSize(width, height);
        pane.setOnMousePressed(MapPane::handleStartDrag);
        pane.setOnMouseDragged(MapPane::handleEndDrag);
    }

    private static void fillTiles() {
        pane.getChildren().clear();
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

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
                    UnitGroup unitGroup = unitGroups.get(x).get(y);
                    group = unitGroup.getWallpaperGroup();
                    if (unitGroup.hasBuilding())
                        buildings.put(new Pair(dx, dy), unitGroup.getBuilding());
                    if (unitGroup.hasObjects()) {
                        //TODO
//                        people.put(new Pair(dx, dy), unitGroup.getPeople());
                    }
                } else {
                    ruinGroups.putIfAbsent(x, new HashMap<>());
                    ruinGroups.get(x).putIfAbsent(y, getRandomRuinGroup(x, y, tileHeight, tileWidth));
                    group = ruinGroups.get(x).get(y);
                }
                pane.getChildren().add(group);
                group.setLayoutX(dx);
                group.setLayoutY(dy);
            }
        }
        addHashMap(pane, buildings);
        addHashMap(pane, people);
    }

    private static void initTiles() {
        ruinGroups = new HashMap<>();
        unitGroups = new HashMap<>();
        for (int x = 0; x < map.getXSize(); x++) {
            unitGroups.putIfAbsent(x, new HashMap<>());
            for (int y = 0; y < map.getYSize(); y++)
                unitGroups.get(x).putIfAbsent(y, new UnitGroup(map.getUnitByXY(x, y), tileHeight, tileWidth));
        }
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

    private static Group getRandomRuinGroup(int x, int y, double tileHeight, double tileWidth) {
        Beach dir = getDir(x, y);
        Image image;

        if (dir != null)
            image = dir.getImage();
        else
            image = GroundType.SEA.getImage();

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(tileHeight);
        imageView.setFitWidth(tileWidth);

        return new Group(imageView);
    }

    private static Beach getDir(int x, int y) {
        if (x == -1) {
            if (y == -1) return Beach.TOP_LEFT_CORNER;
            if (y == map.getYSize()) return Beach.TOP_RIGHT_CORNER;
            if (y > map.getYSize() || y < 0) return null;
            else return Beach.TOP;
        }

        if (x == map.getXSize()) {
            if (y == -1) return Beach.BOTTOM_LEFT_CORNER;
            if (y == map.getYSize()) return Beach.BOTTOM_RIGHT_CORNER;
            if (y > map.getYSize() || y < 0) return null;
            else return Beach.BOTTOM;
        }

        if (y == -1) return x < 0 || x > map.getXSize() ? null : Beach.LEFT;
        if (y == map.getYSize()) return x < 0 || x > map.getXSize() ? null : Beach.RIGHT;

        return null;
    }

    private static void handleStartDrag(MouseEvent mouseEvent) {
        startX = mouseEvent.getX();
        startY = mouseEvent.getY();
    }

    private static void handleEndDrag(MouseEvent mouseEvent) {
        double dScreenX = mouseEvent.getX() - startX;
        double dScreenY = mouseEvent.getY() - startY;

        Pair xy = diffXY(getXYReal(tileHeight,
                                   tileWidth,
                                   (int) Math.round(dScreenX),
                                   (int) Math.round(dScreenY),
                                   0,
                                   0));

        int dTileX = (int) Math.round(xy.x / tileWidth);
        int dTileY = (int) Math.round(xy.y / tileHeight);

        topLeftX -= dTileX;
        topLeftY -= dTileY;

        fillTiles();
    }

    private static class Pair {

        double x;
        double y;

        public Pair(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Pair by(double mul) {
            return new Pair(x * mul, y * mul);
        }
    }
}
