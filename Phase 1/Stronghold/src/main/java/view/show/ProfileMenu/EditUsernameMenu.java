package view.show.ProfileMenu;

import controller.Menus.ProfileController;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import controller.control.Commands;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.ProfileMenu;

import java.util.regex.Matcher;

import static controller.GUIControllers.ProfileMenuGUIController.getEditBackgroundImage;

public class EditUsernameMenu extends Application {
    private Stage stage;
    private boolean done = true;
    private String username;
    private TextField newUsername;
    private Button button;

    static Background getBackground(double width, double height) {
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

    public static Pane getEditPane(VBox vBox, double width, double height) {
        StackPane pane = new StackPane();

        BackgroundImage backgroundImage =
                getEditBackgroundImage(User.class.getResource("/phase2-assets/Tiles/tynk.jpg"),
                                       new BackgroundSize(
                                               width, height,
                                               false, false, false, false
                                       ));

        pane.setBackground(new Background(backgroundImage));
        pane.getChildren().add(vBox);

        return pane;
    }

    public void newMenu(String username) {
        this.username = username;
    }

    private Pane getMainPane() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        Pane pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setPrefSize(width, height);
        pane.setBackground(getBackground(width, height));

        VBox vBox = new VBox(getPane(width / 3, height / 3));
        vBox.setPrefSize(width, height);
        vBox.setAlignment(Pos.CENTER);

        pane.getChildren().add(vBox);

        return pane;
    }

    private Pane getPane(double width, double height) {
        VBox vBox = new VBox();
        Text text = new Text("Edit Username");
        text.setStyle("-fx-font: 25 System; -fx-fill: Black; -fx-font-weight: bold");

        button = getButton();

        initNewUserName(width);

        vBox.getChildren().addAll(text, newUsername, button);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        vBox.setPrefSize(width, height);
        return getEditPane(vBox, width, height);
    }

    private void initNewUserName(double width) {
        newUsername = new TextField(username);
        newUsername.setPromptText("New Username");
        newUsername.setMaxWidth(width * 2 / 3);
        newUsername.textProperty().addListener(observable -> {
            if (newUsername.getText().isBlank()) {
                button.setText("Username is blank");
                button.setStyle("-fx-background-color: red; -fx-background-radius: 20;" +
                                        " -fx-text-fill: black; -fx-font: 20 System");
                done = false;
            } else if (Users.usernameExists(newUsername.getText()) &&
                    !username.equals(newUsername.getText())) {
                button.setText("Username is taken");
                button.setStyle("-fx-background-color: red; -fx-background-radius: 20;" +
                                        " -fx-text-fill: black; -fx-font: 20 System");
                done = false;
            } else {
                button.setText("Confirm");
                button.setStyle("-fx-background-color: green; -fx-background-radius: 20;" +
                                        " -fx-text-fill: black; -fx-font: 20 System");
                done = true;
            }
        });

        newUsername.setStyle("""
                                     -fx-font: 20 System;
                                     -fx-text-fill: -fx-text-inner-color;
                                         -fx-highlight-fill: derive(-fx-control-inner-background,-20%);
                                         -fx-highlight-text-fill: -fx-text-inner-color;
                                         -fx-prompt-text-fill: rgba(0, 0, 0, 0.2);
                                         -fx-background-color: transparent;
                                         -fx-background-insets: 0;
                                         -fx-background-radius: 0;
                                         -fx-border-color: black;
                                         -fx-border-width: 1.5;
                                         -fx-border-insets: 0 0 1 0;
                                         -fx-border-style: hidden hidden solid hidden;
                                         -fx-cursor: text;
                                         -fx-padding: 0.166667em 0em 0.333333em 0em;""");
    }

    private Button getButton() {
        Button button = new Button("Confirm");
        button.setStyle("-fx-background-color: green; -fx-background-radius: 20;" +
                                " -fx-text-fill: black; -fx-font: 20 System");
        button.setPrefWidth(250);
        button.setPrefHeight(50);
        button.setOnAction(this::handleButton);
        return button;
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        Pane pane = getMainPane();

        stage.getScene().setRoot(pane);
        stage.show();
    }

    private void handleButton(ActionEvent actionEvent1) {
        if (!done) new Alert(Alert.AlertType.ERROR, "Username is invalid!").show();
        else setUsername();
    }

    private void setUsername() {
        try {
            ProfileController profileController = new ProfileController(new ProfileMenu());
            profileController.setCurrentUser(Users.getUser(username));
            Matcher matcher = Commands.getMatcher(Commands.PROFILE_CHANGE,
                                                  "profile change -u " + newUsername.getText());
            matcher.matches();
            System.out.println(profileController.profileChange(matcher));
            new ShowProfileMenu().start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
