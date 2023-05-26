package model.Map.GUI;

import javafx.scene.layout.Pane;
import model.Map.Map;

public class MapPane {
    public static Pane getMapPane(Map map) {
        Pane pane = new Pane();
        pane.setMaxSize(1920, 1080);
        pane.setPrefSize(1920, 1080);

        return pane;
    }
}
