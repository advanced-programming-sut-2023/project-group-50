package model.Map.GUI.MapPane;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.Person;
import model.RandomGenerator.RandomGenerator;

public class UnitGroup {
    private final Unit unit;
    private final boolean hasBuilding;
    private final boolean hasObjects;
    private final boolean hasPeople;
    private Group peopleGroup;
    private Group buildingGroup;
    private Group wallpaperGroup;
    private Group objectsGroup;

    public UnitGroup(Unit unit, double tileHeight, double tileWidth) {
        this.unit = unit;
        hasBuilding = unit.hasBuilding();
        hasObjects = unit.hasObjects();
        hasPeople = unit.hasObjectByType(ObjectType.PERSON);
        initGroup(tileHeight, tileWidth);
    }

    private ImageView getTileBackground(double tileHeight, double tileWidth) {
        ImageView imageView = new ImageView(unit.getTexture().getImage());
        imageView.setFitHeight(tileHeight);
        imageView.setFitWidth(tileWidth);

        return imageView;
    }

    private void getObjects(double tileHeight, double tileWidth) {
        for (Objects object : unit.getObjects()) {
            ImageView imageView = new ImageView(object.getImage());
            addToGroups(tileHeight, tileWidth, object, imageView);
        }
    }

    private void addToGroups(double tileHeight, double tileWidth, Objects object, ImageView imageView) {
        if (object instanceof Building) addToBuildings(tileWidth, imageView);
        else if (object instanceof Person) addToPeople(tileWidth, tileHeight, imageView);
        else addObject(tileWidth, imageView);
    }

    private void addObject(double tileWidth, ImageView imageView) {
        imageView.setFitWidth(tileWidth);
        imageView.setPreserveRatio(true);
        if (objectsGroup == null) objectsGroup = new Group();
        objectsGroup.getChildren().add(imageView);
    }

    private void addToPeople(double tileWidth, double tileHeight, ImageView imageView) {
        imageView.setFitHeight(tileHeight / 5);
        imageView.setPreserveRatio(true);
        if (peopleGroup == null) peopleGroup = new Group();
        peopleGroup.getChildren().add(imageView);
        double x0 = RandomGenerator.getRandomIntersection(0, tileWidth);
        double y0 = RandomGenerator.getRandomIntersection(-(x0 % (tileWidth / 2)) / (2 * tileWidth / tileHeight),
                                                          (x0 % (tileWidth / 2)) / (2 * tileWidth / tileHeight));


        imageView.setLayoutX(imageView.getLayoutX() + x0);
        imageView.setLayoutY(imageView.getLayoutY() + y0);
    }

    private void addToBuildings(double tileWidth, ImageView imageView) {
        imageView.setFitWidth(tileWidth);
        imageView.setPreserveRatio(true);
        if (buildingGroup == null) buildingGroup = new Group();
        buildingGroup.getChildren().add(imageView);
    }

    private void initGroup(double tileHeight, double tileWidth) {
        wallpaperGroup = new Group();
        wallpaperGroup.getChildren().add(getTileBackground(tileHeight, tileWidth));
        getObjects(tileHeight, tileWidth);
    }

    public Group getWallpaperGroup() {
        return wallpaperGroup;
    }

    public boolean hasBuilding() {
        return hasBuilding && buildingGroup != null;
    }

    public boolean hasObjects() {
        return hasObjects && objectsGroup != null;
    }

    public boolean hasPeople() {
        return hasPeople && peopleGroup != null;
    }

    public Group getBuilding() {
        return buildingGroup;
    }

    public Group getPeople() {
        return peopleGroup;
    }

    public Group getObjectsGroup() {
        return objectsGroup;
    }

    public void setSize(double tileWidth, double tileHeight) {
        if (peopleGroup != null && !peopleGroup.getChildren().isEmpty())
            for (Node node : peopleGroup.getChildren()) {
                if (node instanceof ImageView imageView) {
                    imageView.setFitHeight(tileHeight / 5);
                    imageView.setPreserveRatio(true);
                }
            }

        if (buildingGroup != null && !buildingGroup.getChildren().isEmpty())
            for (Node node : buildingGroup.getChildren()) {
                if (node instanceof ImageView imageView) {
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(tileWidth);
                }
            }

        if (!wallpaperGroup.getChildren().isEmpty())
            for (Node node : wallpaperGroup.getChildren()) {
                if (node instanceof ImageView imageView) {
                    imageView.setFitHeight(tileHeight);
                    imageView.setFitWidth(tileWidth);
                }
            }
    }
}
