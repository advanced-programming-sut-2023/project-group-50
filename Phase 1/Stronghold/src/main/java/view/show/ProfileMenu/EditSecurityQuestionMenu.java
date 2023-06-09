package view.show.ProfileMenu;

import controller.GUIControllers.MainMenuGUIController;
import controller.GUIControllers.ProfileMenuGUIController;
import controller.UserDatabase.Users;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;

public class EditSecurityQuestionMenu extends Application {
    private Stage stage;
    private boolean done = true;
    private String username;
    private TextField answer;
    private Button button;
    private VBox checkBoxes;
    private int choice;

    public void newMenu(String username, int choice) {
        this.username = username;
        this.choice = choice;
    }

    private Pane getMainPane() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        Pane pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setPrefSize(width, height);
        pane.setBackground(EditUsernameMenu.getBackground(width, height));

        VBox vBox = new VBox(getPane(width / 3, height / 1.75));
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
        Text text = new Text("Edit Security Question");
        text.setStyle("-fx-font: 25 System; -fx-fill: Black; -fx-font-weight: bold");

        button = getButton();

        checkBoxes = new VBox();
        checkBoxes.getChildren().addAll(ProfileMenuGUIController.getCheckBoxes(choice));
        checkBoxes.setAlignment(Pos.CENTER_LEFT);
        checkBoxes.setMaxWidth(width * 0.8);
        checkBoxes.setSpacing(15);

        initTextFields(width);

        vBox.getChildren().addAll(text, checkBoxes, answer, button);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        vBox.setPrefSize(width, height);
        return EditUsernameMenu.getEditPane(vBox, width, height);
    }

    private void initTextFields(double width) {
        answer = getField(width);
    }

    private TextField getField(double width) {
        TextField field;
        field = new TextField();
        field.setPromptText("New Answer");
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
        if (!done) new Alert(Alert.AlertType.ERROR, "Answer is invalid!").show();
        else setNewSlogan();
    }

    private void setNewSlogan() {
        try {
            choice = getChoice();
            Users.setSecurityQuestion(username, choice, answer.getText());
            new ShowProfileMenu().start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getChoice() {
        if (((RadioButton) checkBoxes.getChildren().get(0)).selectedProperty().get()) return 0;
        else if (((RadioButton) checkBoxes.getChildren().get(1)).selectedProperty().get()) return 1;
        else if (((RadioButton) checkBoxes.getChildren().get(2)).selectedProperty().get()) return 2;
        else return -1;
    }

    private void invalidated(Observable observable) {
        if (answer.getText().isBlank()) {
            button.setText("Answer is blank");
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
