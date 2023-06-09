package view.show.ProfileMenu;

import controller.GUIControllers.MainMenuGUIController;
import controller.Menus.ProfileController;
import controller.UserDatabase.Users;
import controller.control.Commands;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.ProfileMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;

public class EditPasswordMenu extends Application {
    private Stage stage;
    private boolean done = true;
    private String username;
    private String oldPassword;
    private PasswordField newPassword;
    private PasswordField confirmPassword;
    private Text state;

    public void newMenu(String username, String oldPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        state = new Text("Enter a password");
        state.setStyle("-fx-font: 20 System; -fx-fill: Black");
    }

    private Pane getMainPane() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        Pane pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setPrefSize(width, height);
        pane.setBackground(EditUsernameMenu.getBackground(width, height));

        VBox vBox = new VBox(getPane(width / 3, height / 2));
        vBox.setPrefSize(width, height);
        vBox.setAlignment(Pos.CENTER);

        pane.getChildren().add(vBox);
        Button backButton = getBackButton(MainMenuGUIController::profile);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(width - 125);

        return pane;
    }

    private Pane getPane(double width, double height) {
        VBox vBox = new VBox();
        Text text = new Text("Edit Password");
        text.setStyle("-fx-font: 25 System; -fx-fill: Black; -fx-font-weight: bold");

        Button button = getButton();

        initTextFields(width);

        vBox.getChildren().addAll(text, newPassword, confirmPassword, state, button);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        vBox.setPrefSize(width, height);
        return EditUsernameMenu.getEditPane(vBox, width, height);
    }

    private void initTextFields(double width) {
        confirmPassword = getField("Confirm Password", width);
        newPassword = getField("New Password", width);
    }

    private PasswordField getField(String string, double width) {
        PasswordField field;
        field = new PasswordField();
        field.setPromptText(string);
        field.setMaxWidth(width * 2 / 3);

        field.textProperty().addListener(this::invalidated);

        field.setStyle("""
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
        return field;
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
        if (!done) new Alert(Alert.AlertType.ERROR, "Password is invalid!").show();
        else setNewPassword();
    }

    private void setNewPassword() {
        try {
            ProfileController profileController = new ProfileController(new ProfileMenu());
            profileController.setCurrentUser(Users.getUser(username));
            Matcher matcher = Commands.getMatcher(Commands.CHANGE_PASS,
                                                  "profile change password -o " + oldPassword + " -n "
                                                          + newPassword.getText());
            matcher.matches();
            String confirm = confirmPassword.getText();
            System.out.println(profileController.changePassword(matcher, new Scanner(confirm)));
            new ShowProfileMenu().start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void invalidated(Observable observable) {
        String n = newPassword.getText();
        String c = confirmPassword.getText();

        if (!n.equals(c)) {
            state.setText("Passwords must match");
            done = false;
        } else {
            String s = ProfileController.checkPassword(n);
            if (s != null) {
                state.setText(s);
                done = false;
            } else {
                state.setText("All set");
                done = true;
            }
        }

    }
}
