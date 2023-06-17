package view.show.MainMenu;

import controller.GUIControllers.MainMenuGUIController;
import controller.UserDatabase.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Government.GUI.GovernmentPane;
import view.show.Loading.Loading;

import static model.Map.GUI.Test.initMap;

public class MainMenu extends Application {
    private static Stage stage;
    private Pane pane;
    private User user;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(Loading.getLoadingPane()));
        stage.setTitle("Loading...");
        stage.setFullScreen(true);
        stage.show();

        MainMenu.stage = stage;
        if (user == null) user = initMap();
        pane = MainMenuGUIController.getPane(user);

        stage.close();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(GovernmentPane.class.getResource("/css.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Stronghold");
        stage.setFullScreen(true);
        stage.show();
    }

    public void init(User user) {
        this.user = user;
    }
}
