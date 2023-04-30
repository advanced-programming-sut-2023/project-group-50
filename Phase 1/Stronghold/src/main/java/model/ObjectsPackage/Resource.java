package model.ObjectsPackage;

public enum Resource {
    WHEAT,
    FLOUR,
    HOPS,
    ALE,
    STONE,
    IRON,
    WOOD,
    PITCH;

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


}
