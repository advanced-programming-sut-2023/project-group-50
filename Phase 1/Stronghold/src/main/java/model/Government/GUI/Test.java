package model.Government.GUI;

import controller.UserDatabase.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Government.Government;
import model.Map.Map;
import model.UserColor.UserColor;

public class Test extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Map map = new Map(100, 100);

        User user = new User("Sahand",
                             "Ap",
                             "shndap",
                             "bjbdkw",
                             "wewd",
                             5,
                             5,
                             UserColor.PURPLE, map);

        Pane pane = GovernmentPane.getPane(new Government(user, 10, 10), null);

        Scene scene = new Scene(pane);

        scene.getStylesheets().add(GovernmentPane.class.getResource("/css.css").toExternalForm());

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }
}
