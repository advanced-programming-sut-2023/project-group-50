package controller.GUIControllers;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Government.Government;

import java.net.URL;

public class GovernmentMenuGUIController {

    public static ImageView getPopularityImage(int popularity, double size) {
        if (popularity <= -100) popularity = -100;
        if (popularity >= 100) popularity = 100;

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(popularity / 100.0);

        String path = "/images/indicators/" + popularity / 50 + ".png";
        URL url = Government.class.getResource(path);
        Image image = new Image(url.toExternalForm());

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(size);
        return imageView;
    }
}
