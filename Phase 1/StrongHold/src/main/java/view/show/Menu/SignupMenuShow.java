package view.show.Menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.show.controller.SignupMenuShowController;
import view.show.controller.StartMenuController;

public class SignupMenuShow extends Application {


    public static SignupMenuShowController signupMenuShowController=new SignupMenuShowController ();
    public static Stage stage;
    @Override
    public void start (Stage primaryStage) throws Exception {
        SignupMenuShow.stage=primaryStage;

        Scene scene=new Scene ( SignupMenuShow.signupMenuShowController.createContent () );
        StartMenu.stage.setScene ( scene );
        StartMenu.stage.show ();
    }
}
