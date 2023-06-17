package view.show.Loading;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class Loading {
    static ImageView imageView;

    static {
        Image image = new Image(Loading.class.getResource("/images/loading.jpg").toExternalForm());
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(Screen.getPrimary().getBounds().getWidth());
    }

    public static Pane getLoadingPane() {
        return new Pane(imageView);
    }

    public static double getWidth() {
        return imageView.getFitWidth();
    }

    public static double getHeight() {
        return imageView.getFitHeight();
    }
}
