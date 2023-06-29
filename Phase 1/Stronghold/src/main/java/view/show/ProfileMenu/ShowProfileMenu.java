package view.show.ProfileMenu;

import Server.Client;
import controller.GUIControllers.ProfileMenuGUIController;
import controller.UserDatabase.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ShowProfileMenu extends Application {

    private static User user;
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public void init(User user) {
        ShowProfileMenu.user = user;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Client.getData();
        ShowProfileMenu.stage = stage;

        ProfileMenuGUIController.init(user);
        Pane profilePane = ProfileMenuGUIController.getPane();

        Scene scene = new Scene(profilePane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setTitle("Stronghold");

        stage.show();
    }
}
