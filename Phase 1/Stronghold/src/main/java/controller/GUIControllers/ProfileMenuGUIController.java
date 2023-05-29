package controller.GUIControllers;

import controller.UserDatabase.User;
import controller.control.SecurityQuestion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import model.Map.Map;
import model.UserColor.UserColor;
import view.show.ProfileMenu.ShowProfileMenu;

import java.net.URL;

public class ProfileMenuGUIController {
    private static Background getBackground(double width, double height) {
        String path = "/phase2-assets/background/backgrounds/Backgrounds For Menus - yac/5559468.jpg";
        URL url = ShowProfileMenu.class.getResource(path);

        Image image = new Image(url.toExternalForm());

        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              new BackgroundSize(width,
                                                                                 height,
                                                                                 false,
                                                                                 false,
                                                                                 false,
                                                                                 false)
        );

        return new Background(backgroundImage);
    }

    private static StackPane getUserData(User user, double height, double width) {
        StackPane avatar = getAvatar(user);
        HBox username = getHbox("Username:", user.getUserName(), ProfileMenuGUIController::editUsername);
        HBox nickname = getHbox("Nickname:", user.getNickName(), ProfileMenuGUIController::editNickname);
        HBox password = getHbox("Password:", user.getPassword(), ProfileMenuGUIController::editPassword);
        HBox slogan = getHbox("Slogan:",
                              user.getSlogan().isBlank() ? "Slogan is empty" : user.getSlogan(),
                              ProfileMenuGUIController::editSlogan);
        HBox email = getHbox("Email:", user.getEmail(), ProfileMenuGUIController::editEmail);
        HBox securityQuestion = getHbox("Security Question:",
                                        user.getSecurityQuestion().getQuestion() + " " +
                                                user.getSecurityQuestionAnswer(),
                                        ProfileMenuGUIController::editSecurityQuestion);
        VBox data = new VBox(avatar, nickname, username, password, slogan, email, securityQuestion);
        data.setAlignment(Pos.CENTER);
        data.setSpacing(15);

        ImageView backgroundImage = getDataBackgroundImage(height * 0.95, width * 3 / 5);

        StackPane stackPane = new StackPane(backgroundImage, data);
        stackPane.setPrefSize(width, height);
        stackPane.setMaxSize(width, height);

        return stackPane;
    }

    private static ImageView getDataBackgroundImage(double height, double width) {
        URL url = User.class.getResource("/images/background/profileData.png");
        Image image = new Image(url.toExternalForm());
        ImageView backgroundImage = new ImageView(image);

        backgroundImage.setFitHeight(height);
        backgroundImage.setFitWidth(width);
        return backgroundImage;
    }


    private static HBox getHbox(String nameString, String dataString, EventHandler<ActionEvent> eventHandler) {
        Text name = new Text(nameString);
        name.setStyle("-fx-font: 20 System; -fx-fill: Black; -fx-font-weight: bold");
        Text data = new Text(dataString);
        data.setStyle("-fx-font: 20 System; -fx-fill: Black;");
        Button button = getButton(eventHandler);

        HBox hBox = new HBox(name, data, button);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        return hBox;
    }

    private static Button getButton(EventHandler<ActionEvent> eventHandler) {
        Button button = new Button();

        BackgroundImage backgroundImage = getButtonBackgroundImage();
        button.setBackground(new Background(backgroundImage));
        button.setTextFill(Color.BLACK);
        button.setOnAction(eventHandler);

        button.setMaxSize(50, 50);
        button.setPrefSize(50, 50);
        return button;
    }

    private static BackgroundImage getButtonBackgroundImage() {
        URL url = User.class.getResource("/images/Icons/edit.png");
        Image image = new Image(url.toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              new BackgroundSize(
                                                                      25, 25,
                                                                      false, false, false, false
                                                              ));
        return backgroundImage;
    }


    private static StackPane getAvatar(User user) {
        URL avatarURL = user.getAvatar();
        Image image = new Image(avatarURL.toExternalForm());
        Button button = getButton(ProfileMenuGUIController::editAvatar);
        Circle circle = new Circle();
        circle.setRadius(100.0);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1.0);
        circle.setFill(new ImagePattern(image));
        StackPane stackPane = new StackPane(circle, button);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        return stackPane;
    }

    public static Pane getPane() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        Pane pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setPrefSize(width, height);
        pane.setBackground(getBackground(width, height));

        User user = new User("Sahand",
                             "Ap",
                             "shndap",
                             "bjbdkw",
                             "wewd",
                             5,
                             5,
                             UserColor.PURPLE, new Map(200, 200));
        user.setSecurityQuestion(SecurityQuestion.NUMBER1);
        user.setSecurityQuestionAnswer("Hahah");

        pane.getChildren().add(getUserData(user, height, width));

        return pane;
    }

    private static void editUsername(ActionEvent actionEvent) {

    }

    private static void editPassword(ActionEvent actionEvent) {
    }

    private static void editNickname(ActionEvent actionEvent) {
    }

    private static void editSlogan(ActionEvent actionEvent) {
    }

    private static void editEmail(ActionEvent actionEvent) {
    }

    private static void editSecurityQuestion(ActionEvent actionEvent) {
    }

    private static void editAvatar(ActionEvent actionEvent) {
    }
}
