package view.show.Menus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ServerOfflineMenu extends Application {

    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartMenu.stage = primaryStage;

        primaryStage.setTitle("StrongHold-sa");
        primaryStage.getIcons().add(new Image(String.valueOf(StartMenu.class.getResource(
                "/images/140-1402842_logo-crusaders-logo-blue-hd-png-download.png"))));
        Scene scene = new Scene(getServerOffline());
        StartMenu.stage.setScene(scene);
        StartMenu.stage.show();
    }

    private StackPane getServerOffline() {
        StackPane pane = new StackPane();
        pane.setPrefSize(500, 500);
        Text text = new Text("Server is offline!");
        text.setFont(new Font("Old English Text MT", 50));
        pane.getChildren().add(text);
        return pane;
    }
}
