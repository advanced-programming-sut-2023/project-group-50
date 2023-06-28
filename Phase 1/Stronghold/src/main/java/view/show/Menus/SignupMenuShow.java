package view.show.Menus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.show.controller.SignupMenuShowController;

public class SignupMenuShow extends Application {


    public static SignupMenuShowController signupMenuShowController = new SignupMenuShowController();
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        SignupMenuShow.stage = primaryStage;
        primaryStage.setTitle("Stronghold");
        primaryStage.getIcons().add(new Image(String.valueOf(StartMenu.class.getResource(
                "/images/140-1402842_logo-crusaders-logo-blue-hd-png-download.png"))));

        Scene scene = new Scene(SignupMenuShow.signupMenuShowController.createContent());
        SignupMenuShow.stage.setScene(scene);
        SignupMenuShow.stage.setFullScreen(true);
        SignupMenuShow.stage.setFullScreen(true);
        SignupMenuShow.stage.show();
    }
}
