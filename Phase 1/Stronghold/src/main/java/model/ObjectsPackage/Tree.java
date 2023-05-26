package model.ObjectsPackage;

import controller.UserDatabase.User;
import javafx.scene.image.Image;
import model.Map.Map;
import model.RandomGenerator.RandomTree;

import java.net.URL;

public class Tree extends Objects {
    private TreeType type;

    public Tree(TreeType type, User owner) {
        super(ObjectType.TREE, owner);
        this.type = type;
    }

    public static boolean isValidType(String type) {
        return TreeType.get(type) != null;
    }


    public static boolean canPlace(int x, int y, User owner) {
        Map map = owner.getGovernment().getMap();

        switch (map.getXY(x, y).getTexture()) {
            case GROUND, MEADOW, LAWN, GRASS, RIGGED_GROUND -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public TreeType getType() {
        return type;
    }

    public void setType(TreeType type) {
        this.type = type;
    }

    @Override
    public Image getImage() {
        URL url = Tree.class.getResource("/phase2-assets/" + RandomTree.getRandomTree(type));
        return new Image(url.toExternalForm());
    }
}
