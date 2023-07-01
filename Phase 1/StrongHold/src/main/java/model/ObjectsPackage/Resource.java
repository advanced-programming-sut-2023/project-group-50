package model.ObjectsPackage;

import javafx.scene.image.Image;
import model.ObjectsPackage.People.Soldier.SoldierName;

import java.io.Serializable;
import java.net.URL;

public enum Resource implements Serializable {
    WHEAT(1, 2),
    FLOUR(2, 3),
    HOPS(2, 3),
    ALE(4, 5),
    STONE(5, 7),
    IRON(8, 10),
    WOOD(6, 8),
    PITCH(5, 6),
    BREAD(3, 4),
    COW(10, 16),
    MEAT(7, 9),
    CHEESE(3, 5),
    APPLE(3, 4),
    OIL(10, 15);


    private final int sellingPrice;
    private final int buyingPrice;

    Resource(int sellingPrice, int buyingPrice) {
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
    }


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
        for (Resource enums : Resource.values())
            if (name.equals(enums.name()))
                return enums;

        return null;
    }

    public static Resource getResourceByNumber(int number) {
        return Resource.values()[number];
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

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }
}
