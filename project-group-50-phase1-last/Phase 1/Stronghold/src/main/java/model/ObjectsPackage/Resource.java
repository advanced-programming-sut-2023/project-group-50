package model.ObjectsPackage;

import java.io.Serializable;

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

        for (Resource enums : Resource.values())
            if (name.equals(enums.name()))
                return true;

        return false;
    }

    public static Resource getResourceByString(String name) {
        assert nameIsValid(name);

        name = name.toUpperCase();

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
}
