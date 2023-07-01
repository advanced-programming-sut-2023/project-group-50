package view.show.UnitMenu;

import controller.GUIControllers.UnitMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Government.GUI.GovernmentPane;

public class UnitMenu extends Application {
    private String username;
    private int X;
    private int Y;
    private Pane pane;

    public void initialize(String username, int X, int Y) {
        this.username = username;
        this.X = X;
        this.Y = Y;
    }

    @Override
    public void start(Stage stage) throws Exception {
        pane = UnitMenuController.getPane(username, X, Y);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(GovernmentPane.class.getResource("/css.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Stronghold");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }
}
