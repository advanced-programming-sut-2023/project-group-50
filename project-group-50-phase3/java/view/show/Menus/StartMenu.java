package view.show.Menus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
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

        primaryStage.setTitle("Stronghold");
        primaryStage.getIcons().add(new Image(String.valueOf(StartMenu.class.getResource(
                "/images/140-1402842_logo-crusaders-logo-blue-hd-png-download.png"))));
        Scene scene;
        scene = new Scene(StartMenu.startMenuController.createContent());
        StartMenu.stage.setScene(scene);
        StartMenu.stage.setFullScreenExitHint("");
        StartMenu.stage.setFullScreen(true);
        StartMenu.stage.show();
    }

    private StackPane getServerOffline() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        StackPane pane = new StackPane();
        pane.setPrefSize(width, height);
        Text text = new Text("Server is offline!");
        text.setFont(new Font("Old English Text MT", 50));
        pane.getChildren().add(text);
        return pane;
    }
}
