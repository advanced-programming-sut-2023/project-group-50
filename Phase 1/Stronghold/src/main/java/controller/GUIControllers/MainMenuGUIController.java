package controller.GUIControllers;

import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import model.Government.GUI.GovernmentPane;
import model.Map.GUI.MapPane;

public class MainMenuGUIController {
    public static Pane getPane(String username) {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        User user = Users.getUser(username);

        Pane pane = new Pane();
        pane.setPrefSize(width, height);
        pane.getChildren().add(MapPane.getMapPane(user.getGovernment().getMap(),
                                                  50,
                                                  50,
                                                  0,
                                                  0));

        Pane governmentPane = GovernmentPane.getPane(user.getGovernment());

        pane.getChildren().add(governmentPane);

        governmentPane.setLayoutX(width / 2);
        governmentPane.setLayoutY(height - 100);

        return pane;
    }

    public static Pane getPane(User user) {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        Pane pane = new Pane();
        pane.setPrefSize(width, height);
        pane.getChildren().add(MapPane.getMapPane(user.getGovernment().getMap(),
                                                  50,
                                                  50,
                                                  0,
                                                  0));

        Pane governmentPane = GovernmentPane.getPane(user.getGovernment());

        pane.getChildren().add(governmentPane);

        governmentPane.setLayoutX(0);
        governmentPane.setLayoutY(height - 200);

        return pane;
    }
}
