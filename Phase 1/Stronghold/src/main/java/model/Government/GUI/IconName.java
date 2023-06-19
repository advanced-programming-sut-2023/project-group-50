package model.Government.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ObjectsPackage.People.Soldier.SoldierName;

import java.net.URL;

public enum IconName {
    COIN("/phase2-assets/Others/images/units/nugget.png"),
    FOOD("/phase2-assets/Others/images/units/nswheat.png"),
    RELIGION("/phase2-assets/Others/images/units/church.png"),
    RESOURCES("/phase2-assets/Others/images/units/mine.png"),
    FEAR("/phase2-assets/Others/images/units/nizam.png"),
    TAX("/phase2-assets/Others/images/techs/greekageupstable02card.png");
    private final String string;

    IconName(String string) {
        this.string = string;
    }

    public ImageView getImage(double size) {
        URL url = IconName.class.getResource(this.string);
        Image image = new Image(url.toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        return imageView;
    }

    public String getType() {
        return SoldierName.getName(name());
    }
}
