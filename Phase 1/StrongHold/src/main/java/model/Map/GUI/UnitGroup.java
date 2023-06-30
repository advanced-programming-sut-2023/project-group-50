package model.Map.GUI;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.Person;

public class UnitGroup {
    private final Unit unit;
    private Group peopleGroup;
    private Group buildingGroup;
    private Group wallpaperGroup;
    private final boolean hasBuilding;
    private final boolean hasObjects;

    public UnitGroup(Unit unit, double tileHeight, double tileWidth) {
        this.unit = unit;
        hasBuilding = unit.hasBuilding();
        hasObjects = unit.hasObjects();
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
        else if (object instanceof Person) addToPeople(tileWidth, imageView);
        else addObject(tileHeight, tileWidth, imageView);
    }

    private void addObject(double tileHeight, double tileWidth, ImageView imageView) {
        imageView.setFitHeight(tileHeight);
        imageView.setFitWidth(tileWidth);
        if (peopleGroup == null) peopleGroup = new Group();
        peopleGroup.getChildren().add(imageView);
    }

    private void addToPeople(double tileWidth, ImageView imageView) {
        imageView.setFitWidth(tileWidth / 5);
        imageView.setPreserveRatio(true);
        if (peopleGroup == null) peopleGroup = new Group();
        peopleGroup.getChildren().add(imageView);
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
        return hasBuilding;
    }

    public boolean hasObjects() {
        return hasObjects;
    }

    public Group getBuilding() {
        return buildingGroup;
    }

    public Group getPeople() {
        return peopleGroup;
    }
}
