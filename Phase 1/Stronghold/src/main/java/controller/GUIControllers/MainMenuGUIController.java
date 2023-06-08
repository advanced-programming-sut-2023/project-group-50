package controller.GUIControllers;

import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import model.Government.GUI.GovernmentPane;
import model.Map.GUI.MapPane;

public class MainMenuGUIController {
    private static Pane pane;
    private static Pane governmentPane;

    public static Pane getPane(String username) {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        User user = Users.getUser(username);

        pane = new Pane();
        pane.setPrefSize(width, height);
        pane.getChildren().add(MapPane.getMapPane(user.getGovernment().getMap(),
                                                  50,
                                                  50,
                                                  0,
                                                  0));

        governmentPane = GovernmentPane.getPane(user.getGovernment(), pane);

        pane.getChildren().add(governmentPane);

        governmentPane.setLayoutX(width / 2);
        governmentPane.setLayoutY(height - 100);

        return pane;
    }

    public static Pane getPane(User user) {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        pane = new Pane();
        pane.setPrefSize(width, height);
        pane.getChildren().add(MapPane.getMapPane(user.getGovernment().getMap(),
                                                  50,
                                                  50,
                                                  0,
                                                  0));

        governmentPane = GovernmentPane.getPane(user.getGovernment(), pane);

        pane.getChildren().add(governmentPane);

        governmentPane.setLayoutX(0);
        governmentPane.setLayoutY(height - 200);

        return pane;
    }

    public static void updateGovernmentPane(String username) {
        pane.getChildren().remove(governmentPane);
        governmentPane = GovernmentPane.getPane(Users.getUser(username).getGovernment(), pane);
        pane.getChildren().add(governmentPane);

        governmentPane.setLayoutX(0);
        governmentPane.setLayoutY(pane.getHeight() - 200);
    }
}
