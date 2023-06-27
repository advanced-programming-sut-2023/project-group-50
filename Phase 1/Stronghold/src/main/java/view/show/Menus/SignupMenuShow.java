package view.show.Menus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.show.controller.SignupMenuShowController;

public class SignupMenuShow extends Application {


    public static SignupMenuShowController signupMenuShowController = new SignupMenuShowController();
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        SignupMenuShow.stage = primaryStage;

        Scene scene = new Scene(SignupMenuShow.signupMenuShowController.createContent());
        SignupMenuShow.stage.setScene(scene);
        SignupMenuShow.stage.show();
    }
}
