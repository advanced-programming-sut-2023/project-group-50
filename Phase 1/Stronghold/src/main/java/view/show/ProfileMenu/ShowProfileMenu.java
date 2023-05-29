package view.show.ProfileMenu;

import controller.GUIControllers.ProfileMenuGUIController;
import controller.UserDatabase.User;
import controller.control.SecurityQuestion;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Map.Map;
import model.UserColor.UserColor;

public class ShowProfileMenu extends Application {

    private static Stage stage;
    private static final User user;

    static {
        user = new User("Sahand",
                        "Ap",
                        "shndap",
                        "bjbdkw",
                        "wewd",
                        5,
                        5,
                        UserColor.PURPLE, new Map(200, 200));
        user.setSecurityQuestion(SecurityQuestion.NUMBER1);
        user.setSecurityQuestionAnswer("Hahah");
    }

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ShowProfileMenu.stage = stage;

        ProfileMenuGUIController.init(user);
        Pane profilePane = ProfileMenuGUIController.getPane();

        if (stage.getScene() == null) {
            Scene scene = new Scene(profilePane);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setTitle("Stronghold");
        } else stage.getScene().setRoot(profilePane);

        stage.show();
    }
}
