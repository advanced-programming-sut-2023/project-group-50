package view.show.ProfileMenu;

import Server.Client;
import controller.GUIControllers.SaveMapMenuController;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.show.MainMenu.MainMenu;
import view.show.MainMenu.ShowSaveMapMenu;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;
import static model.Map.GUI.Unit.UnitPane.getBackground;
import static view.show.OnlineMenu.NewRequestMenu.getButton;

public class GetSaveNameMenu extends Application {
    private Pane pane;
    private User user;
    private TextField textField;

    public void init(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage stage) throws Exception {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        pane = new Pane();
        Pane editPane = EditUsernameMenu.getEditPane(getVBox(width * 0.5, 50),
                                                     width * 0.5, height * 0.5);

        editPane.setPrefSize(width * 0.5, height * 0.5);

        pane.setPrefSize(width, height);

        pane.setBackground(getBackground(width, height));
        pane.getChildren().add(editPane);
        editPane.setLayoutX(width * 0.25);
        editPane.setLayoutY(height * 0.25);

        Scene scene = new Scene(pane);
        stage.setFullScreenExitHint("");
        stage.setTitle("Stronghold");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    private Button initBackButton() {
        Button backButton = getBackButton(this::back);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
        return backButton;
    }

    private Button initConfirmButton(String string, EventHandler<ActionEvent> eventHandler) {
        return getButton(string, eventHandler);
    }

    private VBox getVBox(double width, double height) {
        Text text = new Text("Enter a name");
        text.setStyle("-fx-font: 20 System; -fx-font-weight: bold");
        Button backButton = initBackButton();
        Button saveAsPrivate = initConfirmButton("Save As Private", new saveAsPrivate());
        Button saveAsPublic = initConfirmButton("Save As Public", new saveAsPublic());
        HBox hBox = new HBox(backButton, saveAsPrivate, saveAsPublic);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        VBox vBox = new VBox(text, getTextField(width * 0.85, height), hBox);
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private TextField getTextField(double width, double height) {
        textField = new TextField();
        textField.setPromptText("Enter a name...");
        textField.setMaxSize(width * 0.75, height);
        textField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);" +
                                   " -fx-prompt-text-fill: black; -fx-font: 20 System");
        return textField;
    }

    private void back(ActionEvent actionEvent) {
        SaveMapMenuController saveMapMenuController = new SaveMapMenuController(user);
        ShowSaveMapMenu showSaveMapMenu = new ShowSaveMapMenu();
        showSaveMapMenu.init(saveMapMenuController);
        try {
            showSaveMapMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void overrideMap(String name, boolean isPublic) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Map already exists, wanna replace it?");
        alert.initOwner(pane.getScene().getWindow());
        alert.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
            Users.saveMap(user, name, isPublic);
            Client.sendData();
            back(null);
        });
    }

    private class saveAsPrivate implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String name = textField.getText();
            if (name.isBlank()) {
                new Alert(Alert.AlertType.ERROR, "Name is invalid").show();
                return;
            }

            Client.getData();

            if (Users.mapNameExists(user, name)) {
                overrideMap(name, false);
                return;
            }

            Users.saveMap(user, name, false);
            Client.sendData();
            back(null);
        }
    }

    private class saveAsPublic implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String name = textField.getText();
            if (name.isBlank()) {
                new Alert(Alert.AlertType.ERROR, "Name is invalid").show();
                return;
            }

            Client.getData();

            if (Users.mapNameExists(name)) {
                if (Users.getMapOwner(name).equals(user.getUserName())) {
                    overrideMap(name, true);
                } else new Alert(Alert.AlertType.ERROR, "Map already exists!").show();

                return;
            }

            Users.saveMap(user, name, true);
            Client.sendData();
            back(null);
        }
    }
}
