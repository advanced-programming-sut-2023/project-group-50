package view.show;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.Random;

public class StartMenuController {


    public Parent createContent() {
        Pane pane = new Pane();
        pane.setPrefSize(1920, 1080);
        Background background = new Background(setBackGround());
        pane.setBackground(background);


        return pane;
    }

    public Node createMenu() {

        MenuItem login = new MenuItem("LOGIN");
        MenuItem signup = new MenuItem("SING UP");

        login.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                StartMenu.startMenuController.login();
            }
        });
        signup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                StartMenu.startMenuController.signup();
            }
        });
        VBox vBox = new VBox(login, signup);
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);
        return vBox;

    }

    private void signup() {
    }

    private void login() {

    }

    private BackgroundImage setBackGround() {
        int n = (new Random().nextInt(1000) % 2) + 1;
        Image image = new Image(StartMenuController.class.getResource(
                "/images/background/startmenu" + n + ".png").toExternalForm(), 1080, 1920, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.DEFAULT,
                                                              BackgroundSize.DEFAULT);
        return backgroundImage;
    }


}
