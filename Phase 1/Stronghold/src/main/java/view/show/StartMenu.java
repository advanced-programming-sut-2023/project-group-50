package view.show;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartMenu extends Application {

    public static StartMenuController startMenuController=new StartMenuController ();
    public static Stage stage;
    @Override
    public void start (Stage primaryStage) throws Exception {
        StartMenu.stage=primaryStage;
        Scene scene=new Scene ( StartMenu.startMenuController.createContent () );
        StartMenu.stage.setScene ( scene );
        StartMenu.stage.show ();
    }
    public static void main (String[] args) {
        launch ( args );
    }
}
