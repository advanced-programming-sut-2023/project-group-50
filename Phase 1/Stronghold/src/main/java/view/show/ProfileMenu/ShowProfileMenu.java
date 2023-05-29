package view.show.ProfileMenu;

import controller.GUIControllers.ProfileMenuGUIController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ShowProfileMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane profilePane = ProfileMenuGUIController.getPane();
        Scene scene = new Scene(profilePane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Stronghold");
        stage.show();
    }
}
