package model.ObjectsPackage;

import javafx.scene.image.Image;
import model.ObjectsPackage.People.Soldier.SoldierName;

import java.io.Serializable;
import java.net.URL;

public enum Resource implements Serializable {
    WHEAT,
    FLOUR,
    HOPS,
    ALE,
    STONE,
    IRON,
    WOOD,
    PITCH,
    BREAD,
    COW,
    MEAT, CHEESE, APPLE, OIL;

    public static boolean nameIsValid(String name) {
        name = name.toUpperCase();

        for (Resource enums : Resource.values()) {
            if (name.equals(enums.name())) {
                return true;
            }
        }

        return false;
    }

    public static Resource getResourceByString(String name) {
        assert nameIsValid(name);

        name = name.toUpperCase();
        System.out.println(name);

        for (Resource enums : Resource.values())
            if (name.equals(enums.name()))
                return enums;

        return null;
    }


    public Resource getFoodByName(String name) {
        if (name.equals("apple")) return APPLE;
        if (name.equals("meat")) return MEAT;
        if (name.equals("cheese")) return CHEESE;
        if (name.equals("bread")) return BREAD;
        return null;
    }

    public boolean isFood() {
        return getFoodByName(this.name().toLowerCase()) != null;
    }

    public String getName() {
        return SoldierName.getName(this.name());
    }

    public Image getImage() {
        URL url = Resource.class.getResource("/images/Resources/" + getName() + ".png");
        return new Image(url.toExternalForm());
    }
}
