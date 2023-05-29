package view.show.ProfileMenu;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.ProfileMenu;

import java.util.regex.Matcher;

public class EditEmailMenu extends Application {
    private Stage stage;
    private boolean done = true;
    private String username;
    private TextField newEmail;
    private Button button;

    public void newMenu(String username) {
        this.username = username;
    }

    private Pane getMainPane() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        Pane pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setPrefSize(width, height);
        pane.setBackground(EditUsernameMenu.getBackground(width, height));

        VBox vBox = new VBox(getPane(width / 3, height / 3));
        vBox.setPrefSize(width, height);
        vBox.setAlignment(Pos.CENTER);

        pane.getChildren().add(vBox);

        return pane;
    }

    private Pane getPane(double width, double height) {
        VBox vBox = new VBox();
        Text text = new Text("Edit Email");
        button = getButton();

        text.setStyle("-fx-font: 25 System; -fx-fill: Black; -fx-font-weight: bold");

        initTextFields(width);

        vBox.getChildren().addAll(text, newEmail, button);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        vBox.setPrefSize(width, height);
        return EditUsernameMenu.getEditPane(vBox, width, height);
    }

    private void initTextFields(double width) {
        newEmail = getField(width);
    }

    private TextField getField(double width) {
        TextField field;
        field = new PasswordField();
        field.setPromptText("New Email");
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
        if (!done) new Alert(Alert.AlertType.ERROR, "Nickname is invalid!").show();
        else setNewEmail();
    }

    private void setNewEmail() {
        try {
            ProfileController profileController = new ProfileController(new ProfileMenu());
            profileController.setCurrentUser(Users.getUser(username));
            Matcher matcher = Commands.getMatcher(Commands.PROFILE_CHANGE,
                                                  "profile change -e " + newEmail.getText());
            matcher.matches();
            System.out.println(profileController.profileChange(matcher));
            new ShowProfileMenu().start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void invalidated(Observable observable) {
        if (newEmail.getText().isBlank()) {
            button.setText("Email is blank");
            button.setStyle("-fx-background-color: red; -fx-background-radius: 20;" +
                                    " -fx-text-fill: black; -fx-font: 20 System");
            done = false;
        } else if (!Commands.getMatcher(Commands.EMAIL_FORMAT, newEmail.getText()).matches()) {
            button.setText("Invalid Email");
            button.setStyle("-fx-background-color: red; -fx-background-radius: 20;" +
                                    " -fx-text-fill: black; -fx-font: 20 System");
            done = false;
        } else {
            button.setText("Confirm");
            button.setStyle("-fx-background-color: green; -fx-background-radius: 20;" +
                                    " -fx-text-fill: black; -fx-font: 20 System");
            done = true;
        }
    }
}
