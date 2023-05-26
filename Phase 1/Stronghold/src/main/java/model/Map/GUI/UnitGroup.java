package model.Map.GUI;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import model.Map.Unit;
import model.ObjectsPackage.Objects;

import java.util.ArrayList;

public class UnitGroup {
    private final Unit unit;
    private Group group;

    public UnitGroup(Unit unit, double tileHeight, double tileWidth) {
        this.unit = unit;
        initGroup(tileHeight, tileWidth);
    }

    public Group getGroup() {
        return group;
    }

    private ImageView getTileBackground(double tileHeight, double tileWidth) {
        ImageView imageView = new ImageView(unit.getTexture().getImage());
        imageView.setFitHeight(tileHeight);
        imageView.setFitWidth(tileWidth);

        return imageView;
    }

    private ArrayList<ImageView> getObjects(double tileHeight, double tileWidth) {
        ArrayList<ImageView> imageViews = new ArrayList<>();

        for (Objects object : unit.getObjects()) {
            ImageView imageView = new ImageView(object.getImage());
            imageView.setFitHeight(tileHeight);
            imageView.setFitWidth(tileWidth);
            imageViews.add(imageView);
        }

        return imageViews;
    }

    private void initGroup(double tileHeight, double tileWidth) {
        group = new Group();
        group.getChildren().add(getTileBackground(tileHeight, tileWidth));
        for (ImageView obj : getObjects(tileHeight, tileWidth))
            group.getChildren().add(obj);
    }
}
