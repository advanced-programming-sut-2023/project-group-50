package view.show.OnlineMenu;

import Server.Client;
import controller.GUIControllers.MainMenuGUIController;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Request.Request;
import view.show.MainMenu.MainMenu;
import view.show.ProfileMenu.EditUsernameMenu;

import java.util.HashSet;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;
import static model.Map.GUI.Unit.UnitPane.getBackground;
import static model.Map.GUI.Unit.UnitPane.initButton;

public class NewRequestMenu extends Application {
    private Pane pane;
    private User user;
    private TextField name;
    private TextField numberOfPlayers;

    public static Button getButton(String string, EventHandler<ActionEvent> eventHandler) {
        Image image = new Image(MainMenuGUIController.class.getResource("/images/Buttons/bg.jpg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              new BackgroundSize(
                                                                      200,
                                                                      50,
                                                                      false, false, false, false
                                                              ));

        Button button = new Button(string);
        button.setBackground(new Background(backgroundImage));
        button.setPrefSize(200, 50);
        button.setFont(new Font("Bell MT", 18));
        button.setStyle("-fx-text-fill: yellow");
        button.setAlignment(Pos.CENTER);
        button.setOnAction(eventHandler);

        initButton(button);

        return button;
    }

    public void init(User user) {
        Client.getData();
        this.user = Users.getUser(user.getUserName());
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

    private Button initConfirmButton(EventHandler<ActionEvent> eventHandler) {
        return getButton("Confirm", eventHandler);
    }

    private VBox getVBox(double width, double height) {
        Text text = new Text("Create a new request");
        text.setFont(Font.font("Bell MT", FontWeight.BOLD, 25));
        Button backButton = initBackButton();
        Button confirm = initConfirmButton(new confirm());
        HBox hBox = new HBox(backButton, confirm);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        VBox vBox = new VBox(
                text,
                (name = getTextField("Name", width * 0.85, height)),
                (numberOfPlayers = getTextField("Number of players (2 to 8)", width * 0.85, height)),
                hBox
        );
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private TextField getTextField(String text, double width, double height) {
        TextField field = new TextField();
        field.setPromptText(text);
        field.setMaxSize(width * 0.75, height);
        field.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);" +
                               " -fx-prompt-text-fill: black; -fx-font: 20 System");
        return field;
    }

    private void back(ActionEvent actionEvent) {
        LobbyMenu lobbyMenu = new LobbyMenu();
        lobbyMenu.init(user);
        try {
            lobbyMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private class confirm implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String name;
            int numberOfPlayers;
            try {
                name = NewRequestMenu.this.name.getText();
                numberOfPlayers = Integer.parseInt(NewRequestMenu.this.numberOfPlayers.getText());
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Invalid parameters").show();
                return;
            }

            if (name.isBlank() || numberOfPlayers < 2 || numberOfPlayers > 8) {
                new Alert(Alert.AlertType.ERROR, "Invalid name or number of players").show();
                return;
            }

            if (Users.requestNameExists(name)) {
                new Alert(Alert.AlertType.ERROR, "Name is duplicate").show();
                return;
            }

            Client.getData();

            Request request = new Request(new HashSet<>(), user.getUserName(), numberOfPlayers, name);

            request.add(user.getUserName());
            Users.addRequest(request);
            Users.getUser(user.getUserName()).setRequest(request);

            Client.sendData();
            back(null);
        }
    }
}
