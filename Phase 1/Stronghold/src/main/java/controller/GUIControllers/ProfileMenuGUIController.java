package controller.GUIControllers;

import controller.UserDatabase.User;
import controller.control.SecurityQuestion;
import controller.control.Slogans;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import model.RandomGenerator.RandomGenerator;
import view.show.MainMenu.MainMenu;
import view.show.ProfileMenu.*;

import java.net.URL;

public class ProfileMenuGUIController {
    private static boolean done;
    private static User user;
    private static Pane pane;

    public static void init(User user) {
        ProfileMenuGUIController.user = user;
        done = false;
    }

    private static Background getBackground(double width, double height) {
        String path = "/phase2-assets/background/backgrounds/Backgrounds For Menus - yac/5559468.jpg";
        BackgroundImage backgroundImage =
                getEditBackgroundImage(ShowProfileMenu.class.getResource(path), new BackgroundSize(width,
                                                                                                   height,
                                                                                                   false,
                                                                                                   false,
                                                                                                   false,
                                                                                                   false)
                );


        return new Background(backgroundImage);
    }

    private static StackPane getUserData(double height, double width) {
        StackPane avatar = getAvatar();
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
        assert url != null;
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
        Button button = getButton(eventHandler, false);

        HBox hBox = new HBox(name, data, button);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        return hBox;
    }

    private static Button getButton(EventHandler<ActionEvent> eventHandler, boolean white) {
        Button button = new Button();

        BackgroundImage backgroundImage = getButtonBackgroundImage(white);
        button.setBackground(new Background(backgroundImage));
        button.setTextFill(Color.BLACK);
        button.setOnAction(eventHandler);

        button.setMaxSize(50, 50);
        button.setPrefSize(50, 50);
        return button;
    }

    private static BackgroundImage getButtonBackgroundImage(boolean white) {
        URL url = User.class.getResource("/images/Icons/edit" + (white ? "White" : "") + ".png");
        assert url != null;
        Image image = new Image(url.toExternalForm());
        return new BackgroundImage(image,
                                   BackgroundRepeat.NO_REPEAT,
                                   BackgroundRepeat.NO_REPEAT,
                                   BackgroundPosition.CENTER,
                                   new BackgroundSize(
                                           25, 25,
                                           false, false, false, false
                                   ));
    }


    private static StackPane getAvatar() {
        URL avatarURL = user.getAvatar();
        Image image = new Image(avatarURL.toExternalForm());
        Button button = getButton(ProfileMenuGUIController::editAvatar, true);
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(200.0);
        rectangle.setWidth(200.0);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(1.0);
        rectangle.setFill(new ImagePattern(image));
        StackPane stackPane = new StackPane(rectangle, button);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        return stackPane;
    }

    public static Pane getPane() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setPrefSize(width, height);
        pane.setBackground(getBackground(width, height));

        pane.getChildren().add(getUserData(height, width));

        Button backButton = getBackButton(ProfileMenuGUIController::showMainMenu);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(width - 125);

        return pane;
    }

    public static Button getBackButton(EventHandler<ActionEvent> eventHandler) {
        Image image;
        image = new Image(MainMenuGUIController.class.getResource("/images/Buttons/bg.jpg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              new BackgroundSize(
                                                                      100,
                                                                      50,
                                                                      false, false, false, false
                                                              ));

        Button button = new Button("Back");
        button.setBackground(new Background(backgroundImage));
        button.setPrefSize(100, 50);
        button.setFont(new Font("Bell MT", 18));
        button.setStyle("-fx-text-fill: yellow");
        button.setAlignment(Pos.CENTER);
        button.setOnAction(eventHandler);
        return button;
    }

    public static BackgroundImage getEditBackgroundImage(URL Resource, BackgroundSize backgroundSize) {
        URL url = Resource;
        assert url != null;
        Image image = new Image(url.toExternalForm());

        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              backgroundSize);

        return backgroundImage;
    }

    private static void editUsername(ActionEvent actionEvent) {
        EditUsernameMenu editUsernameMenu = new EditUsernameMenu();
        editUsernameMenu.newMenu(user.getUserName());
        try {
            editUsernameMenu.start(ShowProfileMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void editPassword(ActionEvent actionEvent) {
        EditPasswordMenu editPasswordMenu = new EditPasswordMenu();
        editPasswordMenu.newMenu(user.getUserName(), user.getPassword());
        try {
            editPasswordMenu.start(ShowProfileMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void editNickname(ActionEvent actionEvent) {
        EditNicknameMenu editNicknameMenu = new EditNicknameMenu();
        editNicknameMenu.newMenu(user.getUserName());
        try {
            editNicknameMenu.start(ShowProfileMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void editSlogan(ActionEvent actionEvent) {
        EditSloganMenu editSloganMenu = new EditSloganMenu();
        editSloganMenu.newMenu(user.getUserName());
        try {
            editSloganMenu.start(ShowProfileMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void editEmail(ActionEvent actionEvent) {
        EditEmailMenu editEmailMenu = new EditEmailMenu();
        editEmailMenu.newMenu(user.getUserName());
        try {
            editEmailMenu.start(ShowProfileMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void editSecurityQuestion(ActionEvent actionEvent) {
        EditSecurityQuestionMenu editSecurityQuestionMenu = new EditSecurityQuestionMenu();
        editSecurityQuestionMenu.newMenu(user.getUserName(), user.getSecurityQuestion().ordinal());
        try {
            editSecurityQuestionMenu.start(ShowProfileMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void editAvatar(ActionEvent actionEvent) {
        EditAvatarMenu editAvatarMenu = new EditAvatarMenu();
        editAvatarMenu.newMenu(user.getUserName());
        try {
            editAvatarMenu.start(ShowProfileMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRandomSlogan() {
        return RandomGenerator.randomFrom(Slogans.values()).getSlogan();
    }

    public static RadioButton[] getCheckBoxes(int choice) {
        RadioButton[] checkBoxes = new RadioButton[3];
        ToggleGroup toggleGroup = new ToggleGroup();

        SecurityQuestion[] values = SecurityQuestion.values();
        for (int i = 0; i < values.length; i++) {
            SecurityQuestion question = values[i];
            RadioButton box = new RadioButton();
            box.setText(question.getQuestion());
            box.selectedProperty().set(choice == i);
            box.setStyle("-fx-font: 20 System; -fx-fill: Black;");
            box.setToggleGroup(toggleGroup);
            checkBoxes[i] = box;
        }

        return checkBoxes;
    }

    public static void showMainMenu(ActionEvent actionEvent) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.init(user);
        try {
            mainMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
