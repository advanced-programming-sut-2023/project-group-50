package model.ObjectsPackage.People.Soldier;

import javafx.scene.image.Image;
import model.ObjectsPackage.Weapons.WeaponName;

public enum ArmourType {
    LEATHER,
    METAL,
    NONE;

    public Image getImage() {
        switch (this) {
            case LEATHER -> {
                return WeaponName.LEATHER_ARMOUR.getImage();
            }
            case METAL -> {
                return WeaponName.METAL_ARMOUR.getImage();
            }
            case NONE -> {
                return null;
            }
            default -> throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
