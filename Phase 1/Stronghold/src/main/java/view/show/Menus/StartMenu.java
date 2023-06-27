package view.show.Menus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.show.controller.StartMenuController;

public class StartMenu extends Application {

    public static StartMenuController startMenuController = new StartMenuController();
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartMenu.stage = primaryStage;

        primaryStage.setTitle("StrongHold-sa");
        primaryStage.getIcons().add(new Image(String.valueOf(StartMenu.class.getResource("/images/140-1402842_logo-crusaders-logo-blue-hd-png-download.png"))));
        Scene scene = new Scene(StartMenu.startMenuController.createContent());
        StartMenu.stage.setScene(scene);
        StartMenu.stage.show();
    }
}
