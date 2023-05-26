package model.Map.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane mapPane = MapPane.getMapPane(null);

        Scene scene = new Scene(mapPane);
        stage.setScene(scene);
        stage.show();
    }
}
