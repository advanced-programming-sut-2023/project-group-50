package view.show.ProfileMenu;

import controller.GUIControllers.MainMenuGUIController;
import controller.GUIControllers.ProfileMenuGUIController;
import controller.Menus.ProfileController;
import controller.UserDatabase.Users;
import controller.control.Commands;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.ProfileMenu;

import java.util.regex.Matcher;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;

public class EditSloganMenu extends Application {
    private Stage stage;
    private String username;
    private TextField newSlogan;

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

        VBox vBox = new VBox(getPane(width / 2, height / 3));
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
        Text text = new Text("Edit Nickname");
        text.setStyle("-fx-font: 25 System; -fx-fill: Black; -fx-font-weight: bold");

        HBox hBox = gethBox();

        initTextFields(width);

        vBox.getChildren().addAll(text, newSlogan, hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        vBox.setPrefSize(width, height);
        return EditUsernameMenu.getEditPane(vBox, width, height);
    }

    private HBox gethBox() {
        Button button = getButton("Confirm", "green", this::handleConfirm);
        Button randomButton = getButton("Random", "orange", this::handleRandom);

        HBox hBox = new HBox(button, randomButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        return hBox;
    }

    private void handleRandom(ActionEvent actionEvent) {
        newSlogan.textProperty().set(ProfileMenuGUIController.getRandomSlogan());
    }

    private void initTextFields(double width) {
        newSlogan = getField(width);
    }

    private TextField getField(double width) {
        TextField field;
        field = new TextField();
        field.setPromptText("New Slogan");
        field.setMaxWidth(width * 2 / 3);

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

    private Button getButton(String text, String color, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 20;" +
                                " -fx-text-fill: black; -fx-font: 20 System");
        button.setPrefWidth(125);
        button.setPrefHeight(50);
        button.setOnAction(eventHandler);
        return button;
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        Pane pane = getMainPane();

        stage.getScene().setRoot(pane);
        stage.show();
    }

    private void handleConfirm(ActionEvent actionEvent1) {
        setSlogan();
    }

    private void setSlogan() {
        try {
            ProfileController profileController = new ProfileController(new ProfileMenu());
            profileController.setCurrentUser(Users.getUser(username));

            String slogan = newSlogan.getText();

            if (slogan.isBlank())
                profileController.removeSlogan();
            else {
                Matcher matcher = Commands.getMatcher(Commands.PROFILE_CHANGE,
                                                      "profile change -s \"" + slogan + "\"");
                matcher.matches();
                System.out.println(profileController.profileChange(matcher));
            }

            new ShowProfileMenu().start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
