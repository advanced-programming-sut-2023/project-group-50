package view.show.Menus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.show.controller.LoginMenuShowController;

public class LoginMenuShow extends Application {
    public static LoginMenuShowController loginMenuShowController = new LoginMenuShowController();
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginMenuShow.stage = primaryStage;

        Scene scene = new Scene(LoginMenuShow.loginMenuShowController.createContent());
        LoginMenuShow.stage.setScene(scene);
        LoginMenuShow.stage.show();
    }
}
