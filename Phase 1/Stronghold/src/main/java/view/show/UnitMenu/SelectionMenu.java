package view.show.UnitMenu;

import Server.Client;
import controller.GUIControllers.MultiUnitMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Government.GUI.GovernmentPane;

public class SelectionMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Client.getData();
        Pane pane = MultiUnitMenuController.getPane();

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(GovernmentPane.class.getResource("/css.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Stronghold");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }
}
