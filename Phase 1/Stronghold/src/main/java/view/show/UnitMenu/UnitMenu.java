package view.show.UnitMenu;

import controller.GUIControllers.UnitMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Government.GUI.GovernmentPane;
import model.Map.Unit;

public class UnitMenu extends Application {
    private Unit unit;
    private Pane pane;

    public void initialize(Unit unit) {
        this.unit = unit;
    }

    @Override
    public void start(Stage stage) throws Exception {
        pane = UnitMenuController.getPane(unit);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(GovernmentPane.class.getResource("/css.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Stronghold");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }
}
