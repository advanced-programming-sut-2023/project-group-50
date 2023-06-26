package model.Map.GUI.MapPane;

import controller.GUIControllers.MainMenuGUIController;
import javafx.event.ActionEvent;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import model.Government.GUI.GovernmentPane;
import model.Map.GroundType;
import model.Map.Map;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.Building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapPane {
    private static double startX;
    private static double startY;
    private static int topLeftY;
    private static int topLeftX;
    private static int BottomRightY;
    private static int BottomRightX;
    private static double tileWidth;
    private static double tileHeight;
    private static Map map;
    private static Pane pane;
    private static HashMap<Integer, HashMap<Integer, UnitGroup>> unitGroups;
    private static HashMap<Integer, HashMap<Integer, Group>> ruinGroups;
    private static HBox navigation;
    private static int TopLeftX;
    private static int TopLeftY;

    private static Pair diffXY(Pair xy) {
        return new Pair(xy.x / 2 + xy.y / 2, xy.x / 2 - xy.y / 2).by(0.5);
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

    public static Pane getSmallPane(Map map, int xFrom, int yFrom, int xTo, int yTo, double width, double height) {
        MapPane.tileWidth = width / ((xTo - xFrom) + (yTo - yFrom) + 1);
        MapPane.tileHeight = tileWidth;
        MapPane.BottomRightX = xTo;
        MapPane.BottomRightY = yTo;
        MapPane.TopLeftX = xFrom - (xTo - xFrom) / 2;
        MapPane.TopLeftY = yFrom - (yTo - yFrom) / 2;
        MapPane.topLeftX = xFrom;
        MapPane.topLeftY = yTo;
        MapPane.map = map;

        initSmallTiles();
        initSmallPane(width, height);
        fillSmallTiles(width);

        return pane;
    }

    private static void fillTiles() {
        pane.getChildren().clear();
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        HashMap<Pair, Group> buildings = new HashMap<>();
        HashMap<Pair, Group> people = new HashMap<>();
        HashMap<Pair, Group> objects = new HashMap<>();
        HashMap<Pair, String> buildingsID = new HashMap<>();
        HashMap<Pair, String> peopleID = new HashMap<>();
        HashMap<Pair, String> objectID = new HashMap<>();

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
                    if (unitGroup.hasBuilding()) {
                        Pair pair1 = new Pair(dx, dy);
                        buildings.put(pair1, unitGroup.getBuilding());
                        buildingsID.put(pair1, "{%d, %d}".formatted(x, y));
                    }
                    if (unitGroup.hasPeople()) {
                        Pair pair1 = new Pair(dx, dy);
                        people.put(pair1, unitGroup.getPeople());
                        peopleID.put(pair1, "{%d, %d}".formatted(x, y));
                    }
                    if (unitGroup.hasObjects()) {
                        Pair pair1 = new Pair(dx, dy);
                        objects.put(pair1, unitGroup.getObjectsGroup());
                        objectID.put(pair1, "{%d, %d}".formatted(x, y));
                    }
                } else {
                    ruinGroups.putIfAbsent(x, new HashMap<>());
                    ruinGroups.get(x).putIfAbsent(y, getRandomRuinGroup(x, y, tileHeight, tileWidth));
                    group = ruinGroups.get(x).get(y);
                }
                pane.getChildren().add(group);
                group.setLayoutX(dx);
                group.setLayoutY(dy);
                setUpGroup(group, "{%d, %d}".formatted(x, y));

                Tooltip.install(group, new Tooltip(getToolTip(x, y)));

                group.setOnMouseEntered(MapPane::mouseEnteredTile);

            }
        }
        addHashMap(pane,
                   people,
                   peopleID,
                   0);
        addHashMap(pane, buildings, buildingsID, tileHeight / 4);
        addHashMap(pane, objects, objectID, tileHeight / 4);


        pane.getChildren().addAll(navigation);
        navigation.setLayoutX(10);
        navigation.setLayoutY(5);
    }

    private static void initSmallTiles() {
        ruinGroups = new HashMap<>();
        unitGroups = new HashMap<>();
        for (int x = TopLeftX; x <= BottomRightX; x++) {
            unitGroups.putIfAbsent(x, new HashMap<>());
            for (int y = TopLeftY; y <= BottomRightY; y++)
                if (map.isValid(x, y))
                    unitGroups.get(x).putIfAbsent(y, new UnitGroup(map.getUnitByXY(x, y), tileHeight, tileWidth));
        }
    }

    private static void initSmallPane(double width, double height) {
        pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setPrefSize(width, height);
    }

    private static void initPane() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setPrefSize(width, height);
        pane.setOnMousePressed(MapPane::handleStartDrag);
        pane.setOnMouseDragged(MapPane::handleEndDrag);

        navigation = initNavigation();
    }

    private static HBox initNavigation() {
        StackPane zoom = initZoom();
        HBox hBox = new HBox(zoom, initNavigation(true), initNavigation(false));
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        return hBox;
    }

    private static StackPane initNavigation(boolean upLeft) {
        VBox vBox = getNavigationVBox(upLeft);
        Region region = getBorder();
        return new StackPane(region, vBox);
    }

    private static VBox getNavigationVBox(boolean leftOrRight) {
        Button up = new Button();
        Button down = new Button();


        down.setPrefSize(50, 50);
        down.setMaxSize(50, 50);
        up.setPrefSize(50, 50);
        up.setMaxSize(50, 50);

        up.setStyle("-fx-font: 40 Impact; -fx-font-weight: bolder");
        down.setStyle("-fx-font: 40 Impact; -fx-font-weight: bolder");

        if (!leftOrRight) {
            up.setBackground(getNavigationBackground("up"));
            up.setOnAction(MapPane::up);
            down.setBackground(getNavigationBackground("down"));
            down.setOnAction(MapPane::down);
        } else {
            up.setBackground(getNavigationBackground("left"));
            up.setOnAction(MapPane::left);
            down.setBackground(getNavigationBackground("right"));
            down.setOnAction(MapPane::right);
        }

        VBox vBox = new VBox(up, down);
        vBox.setSpacing(-27);
        vBox.setMaxSize(50, 100);
        return vBox;
    }

    private static StackPane initZoom() {
        VBox vBox = getZoomVBox();
        Region region = getBorder();
        return new StackPane(region, vBox);
    }

    private static Region getBorder() {
        Region region = new Region();
        region.setMaxHeight(110);
        region.setMaxWidth(60);
        region.setPrefWidth(60);
        region.setStyle("-fx-border-color: black; -fx-border-width: 5; -fx-border-radius: 25");
        return region;
    }

    private static VBox getZoomVBox() {
        Button zoomIn = new Button();
        Button zoomOut = new Button();

        zoomIn.setBackground(getZoomBackground(true));
        zoomOut.setBackground(getZoomBackground(false));

        zoomOut.setPrefSize(50, 50);
        zoomOut.setMaxSize(50, 50);
        zoomIn.setPrefSize(50, 50);
        zoomIn.setMaxSize(50, 50);

        zoomIn.setStyle("-fx-font: 40 Impact; -fx-font-weight: bolder");
        zoomOut.setStyle("-fx-font: 40 Impact; -fx-font-weight: bolder");

        zoomIn.setOnAction(MapPane::zoomIn);
        zoomOut.setOnAction(MapPane::zoomOut);

        VBox vBox = new VBox(zoomIn, zoomOut);
        vBox.setSpacing(-27);
        vBox.setMaxSize(50, 100);
        return vBox;
    }

    private static Background getZoomBackground(boolean in) {
        Image image = new Image(MapPane.class.getResource(
                "/images/Buttons/Zoom/" + (in ? "in" : "out") + ".png").toExternalForm());

        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              BackgroundSize.DEFAULT
        );

        return new Background(backgroundImage);
    }

    private static Background getNavigationBackground(String dir) {
        Image image = new Image(MapPane.class.getResource(
                "/images/Buttons/Zoom/" + dir + ".png").toExternalForm());

        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              BackgroundSize.DEFAULT
        );

        return new Background(backgroundImage);
    }

    private static void zoomIn(ActionEvent actionEvent) {
        if (tileHeight >= 100) return;

        tileHeight += 10;
        tileWidth += 10;

        updateSize();
        fillTiles();
    }

    private static void zoomOut(ActionEvent actionEvent) {
        if (tileHeight <= 30) return;

        tileHeight -= 10;
        tileWidth -= 10;

        updateSize();
        fillTiles();
    }

    private static void fillSmallTiles(double size) {
        pane.getChildren().clear();

        HashMap<Pair, Group> buildings = new HashMap<>();
        HashMap<Pair, Group> people = new HashMap<>();
        HashMap<Pair, Group> objects = new HashMap<>();
        HashMap<Pair, String> buildingsID = new HashMap<>();
        HashMap<Pair, String> peopleID = new HashMap<>();
        HashMap<Pair, String> objectID = new HashMap<>();

        for (double dx = -tileWidth / 2; dx <= size; dx += tileWidth / 2) {
            for (double dy = -tileHeight / 2; dy <= size; dy += tileHeight / 2) {
                if (!XYisValid(tileHeight, tileWidth, dx, dy)) continue;
                Pair pair = getXYReal(tileHeight, tileWidth, topLeftX, topLeftY, dx, dy);
                int x = (int) pair.x;
                int y = (int) pair.y;

                Group group;
                if (map.isValid(x, y) && !(x < TopLeftX || x > BottomRightX || y < TopLeftY || y > BottomRightY)) {
                    UnitGroup unitGroup = unitGroups.get(x).get(y);
                    group = unitGroup.getWallpaperGroup();
                    if (unitGroup.hasBuilding()) {
                        Pair pair1 = new Pair(dx, dy);
                        buildings.put(pair1, unitGroup.getBuilding());
                        buildingsID.put(pair1, "{%d, %d}".formatted(x, y));
                    }
                    if (unitGroup.hasPeople()) {
                        Pair pair1 = new Pair(dx, dy);
                        people.put(pair1, unitGroup.getPeople());
                        peopleID.put(pair1, "{%d, %d}".formatted(x, y));
                    }
                    if (unitGroup.hasObjects()) {
                        Pair pair1 = new Pair(dx, dy);
                        objects.put(pair1, unitGroup.getObjectsGroup());
                        objectID.put(pair1, "{%d, %d}".formatted(x, y));
                    }

                    pane.getChildren().add(group);
                    group.setLayoutX(dx);
                    group.setLayoutY(dy);
                    group.setId("{%d, %d}".formatted(x, y));

                    Tooltip.install(group, new Tooltip(getToolTip(x, y)));
                }

            }
        }
        addHashMap(pane,
                   people,
                   peopleID,
                   0);
        addHashMap(pane, buildings, buildingsID, tileHeight / 4);
        addHashMap(pane, objects, objectID, tileHeight / 4);
    }

    private static String getToolTip(int x, int y) {
        return map.isValid(x, y) ? "%d, %d".formatted(x, y) : "Nowhere!";
    }

    private static void mouseEnteredTile(MouseEvent mouseEvent) {
        if (GovernmentPane.selectedBuilding == null) return;

        Node group = mouseEvent.getPickResult().getIntersectedNode();

        Pair xy = new Pair(group.getId());

        Unit unit = map.getXY((int) xy.x, (int) xy.y);

        Image image;
        if (xy.x < 0 || xy.x >= map.getXSize() || xy.y < 0 || xy.y >= map.getYSize() ||
                !Building.canPlace(GovernmentPane.selectedBuilding, unit.getTexture()) ||
                unit.isOnFire() || unit.hasBuilding()
        )
            image = GovernmentPane.getUnavailableTileCursor();
        else if (!GovernmentPane.government.canAfford(GovernmentPane.selectedBuilding)) {
            pane.setCursor(Cursor.DEFAULT);
            GovernmentPane.selectedBuilding = null;
            return;
        } else
            image = GovernmentPane.getChosenBuildingCursor();
        Dimension2D dim = ImageCursor.getBestSize(Screen.getPrimary().getBounds().getWidth(),
                                                  Screen.getPrimary().getBounds().getHeight());

        ImageCursor imageCursor = new ImageCursor(image, dim.getWidth(), dim.getHeight());
        pane.setCursor(imageCursor);
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

    private static void updateSize() {
        for (int x = 0; x < unitGroups.size(); x++) {
            unitGroups.putIfAbsent(x, new HashMap<>());
            for (int y = 0; y < unitGroups.get(x).size(); y++)
                unitGroups.get(x).get(y).setSize(tileWidth, tileHeight);
        }

        for (HashMap<Integer, Group> X : ruinGroups.values())
            for (Group Y : X.values())
                for (Node Z : Y.getChildren())
                    if (Z instanceof ImageView imageView) {
                        imageView.setFitHeight(tileHeight);
                        imageView.setFitWidth(tileWidth);
                    }
    }

    private static void addHashMap(Pane pane,
                                   HashMap<Pair, Group> people,
                                   HashMap<Pair, String> peopleID,
                                   double intersectY) {
        ArrayList<Entry<Pair, Group>> entries = new ArrayList<>(people.entrySet());

        entries.sort(MapPane::front);

        for (Entry<Pair, Group> entry : entries) {
            double x = entry.getKey().x, y = entry.getKey().y;
            Group group = entry.getValue();
            pane.getChildren().add(group);
            group.setLayoutX(x);
            group.setLayoutY(y - intersectY);
            String s = peopleID.get(entry.getKey());

            setUpGroup(group, s);
        }
    }

    private static <T> int front(T t, T t1) {
        try {
            Entry<Pair, Group> a = (Entry<Pair, Group>) t;
            Entry<Pair, Group> b = (Entry<Pair, Group>) t1;

            return a.getKey().isInFrontOf(b.getKey()) ? -1 : 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private static void setUpGroup(Group group, String s) {
        group.setOnMouseEntered(MapPane::mouseEnteredTile);
        group.setOnMouseExited(MapPane::mouseExitedTile);

        group.setId(s);
        for (Node child : group.getChildren()) {
            if (child instanceof Group group1) {
                setUpGroup(group1, s);
            } else
                child.setId(s);
        }
    }

    private static void mouseExitedTile(MouseEvent mouseEvent) {
        pane.setCursor(Cursor.DEFAULT);
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

        if (GovernmentPane.selectedBuilding != null) {
            placeBuilding(mouseEvent);
        } else {
            if (mouseEvent.isSecondaryButtonDown()) {
                try {
                    openUnitPane(mouseEvent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void openUnitPane(MouseEvent mouseEvent) throws Exception {
        Node group = mouseEvent.getPickResult().getIntersectedNode();
        Pair xy = new Pair(group.getId());
        int x = (int) xy.x;
        int y = (int) xy.y;

        if (xy.x >= 0 && xy.x < map.getXSize() && xy.y >= 0 && xy.y < map.getYSize()) {
            Unit unit = map.getXY(x, y);
            MainMenuGUIController.showUnit(unit);
        }
    }

    private static void placeBuilding(MouseEvent mouseEvent) {
        if (mouseEvent.isSecondaryButtonDown()) {
            GovernmentPane.selectedBuilding = null;
            pane.setCursor(Cursor.DEFAULT);
            return;
        }

        Node group = mouseEvent.getPickResult().getIntersectedNode();
        Pair xy = new Pair(group.getId());
        int x = (int) xy.x;
        int y = (int) xy.y;

        Unit unit = map.getXY(x, y);

        if (xy.x < 0 || xy.x >= map.getXSize() || xy.y < 0 || xy.y >= map.getYSize() ||
                !Building.canPlace(GovernmentPane.selectedBuilding, unit.getTexture()) ||
                unit.isOnFire() || unit.hasBuilding()
        ) return;
        else {
            Building building = Building.getBuildingByType(GovernmentPane.selectedBuilding,
                                                           GovernmentPane.government.getLord().getOwner(),
                                                           x,
                                                           y);
            GovernmentPane.government.getMap().addObject(building, x, y);
            GovernmentPane.government.buyBuilding(GovernmentPane.selectedBuilding, 1);
            GovernmentPane.government.addBuildings(building);
            UnitGroup unitGroup = new UnitGroup(map.getXY(x, y), tileHeight, tileWidth);
            unitGroups.get(x).replace(y, unitGroup);
            MainMenuGUIController.updateGovernmentPane(GovernmentPane.government.getLord().getOwner().getUserName());
            fillTiles();
        }


        pane.setCursor(Cursor.DEFAULT);
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

    private static void up(ActionEvent actionEvent) {
        topLeftY++;
        topLeftX--;
        updateSize();
        fillTiles();
    }

    private static void left(ActionEvent actionEvent) {
        topLeftX--;
        topLeftY--;
        updateSize();
        fillTiles();
    }

    private static void down(ActionEvent actionEvent) {
        topLeftY--;
        topLeftX++;
        updateSize();
        fillTiles();
    }

    private static void right(ActionEvent actionEvent) {
        topLeftX++;
        topLeftY++;
        updateSize();
        fillTiles();
    }

    private static class Pair {

        double x;
        double y;

        public Pair(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Pair(String id) {
            Pattern pattern = Pattern.compile("\\{(?<x>-?\\d+), (?<y>-?\\d+)}");
            Matcher matcher = pattern.matcher(id);

            try {
                matcher.find();
                x = Double.parseDouble(matcher.group("x"));
                y = Double.parseDouble(matcher.group("y"));
            } catch (Exception e) {
                System.out.println(id);
                throw new RuntimeException(e);
            }
        }

        public Pair by(double mul) {
            return new Pair(x * mul, y * mul);
        }

        public boolean isInFrontOf(Pair that) {
            Pair This = getXY(tileHeight, tileWidth, topLeftX, topLeftY, (int) this.x, (int) this.y);
            Pair That = getXY(tileHeight, tileWidth, topLeftX, topLeftY, (int) that.x, (int) that.y);

            if (This.y > That.y) return true;
            if (This.y < That.y) return false;
            return This.x < That.x;
        }
    }
}
