package model.ObjectsPackage;

import javafx.scene.image.Image;
import model.ObjectsPackage.People.Soldier.SoldierName;

public enum TreeType {
    DESERT_SHRUB,
    CHERRY_PALM,
    OLIVE_TREE,
    COCONUT_PALM,
    DATE_PALM;

    private final String type;

    TreeType() {
        type = SoldierName.getName(this.name());
    }

    public static TreeType get(String s) {
        for (TreeType treeType : TreeType.values())
            if (treeType.type.equals(s))
                return treeType;

        return null;
    }

    public String getType() {
        return type;
    }

    public Image getImage() {
        return new Tree(this, null).getImage();
    }
}
