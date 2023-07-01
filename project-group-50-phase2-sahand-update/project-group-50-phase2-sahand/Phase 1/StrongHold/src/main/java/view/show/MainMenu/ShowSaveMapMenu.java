package view.show.MainMenu;

import controller.GUIControllers.SaveMapMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Government.GUI.GovernmentPane;

public class ShowSaveMapMenu extends Application {
    private SaveMapMenuController saveMapMenuController;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane(saveMapMenuController.getPane());
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(GovernmentPane.class.getResource("/css.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Stronghold");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    public void init(SaveMapMenuController saveMapMenuController) {
        this.saveMapMenuController = saveMapMenuController;
    }
}
