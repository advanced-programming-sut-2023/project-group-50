package model.ObjectsPackage.People.Soldier;

import javafx.scene.image.Image;

public enum SoldierState {
    OFFENSIVE,
    DEFENSIVE,
    STANDING;

    public static SoldierState get(String s) {
        for (SoldierState state : SoldierState.values())
            if (state.name().toLowerCase().equals(s))
                return state;

        return null;
    }

    public Image getImage() {
        String path = "/images/SoldierState/" + SoldierName.getName(name()) + ".png";
        return new Image(Soldier.class.getResource(path).toExternalForm());
    }
}
