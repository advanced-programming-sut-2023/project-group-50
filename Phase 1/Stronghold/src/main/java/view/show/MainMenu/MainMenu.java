package view.show.MainMenu;

import controller.GUIControllers.MainMenuGUIController;
import controller.UserDatabase.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Government.GUI.GovernmentPane;

import static model.Map.GUI.Test.initMap;

public class MainMenu extends Application {
    private Pane pane;

    @Override
    public void start(Stage stage) throws Exception {
        User user = initMap();
        pane = MainMenuGUIController.getPane(user);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(GovernmentPane.class.getResource("/css.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Stronghold");
        stage.setFullScreen(true);
        stage.show();
    }
}
