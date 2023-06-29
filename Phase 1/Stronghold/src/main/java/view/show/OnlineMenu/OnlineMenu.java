package view.show.OnlineMenu;

import controller.GUIControllers.ProfileMenuGUIController;
import controller.GUIControllers.SoldierMenuController.SoldierMenuController;
import controller.UserDatabase.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Government.GUI.GovernmentPane;
import model.Map.GUI.Unit.UnitPane;
import view.show.MainMenu.MainMenu;
import view.show.ProfileMenu.EditUsernameMenu;

import java.util.Objects;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;

public class OnlineMenu extends Application {
    private User user;

    public void init(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage stage) throws Exception {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        StackPane pane = new StackPane();
        pane.setPrefSize(width, height);

        pane.setBackground(SoldierMenuController.getBackground());
        pane.getChildren().add(getEditPane(width * 0.4, height * 0.8));

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Objects.requireNonNull(
                GovernmentPane.class.getResource("/css.css")).toExternalForm()
        );
        stage.setScene(scene);
        stage.setTitle("Stronghold");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    private Pane getEditPane(double width, double height) {
        VBox vBox = new VBox();

        Text text = LobbyMenu.getText("Online Menu");

        Text connection = new Text(getConnection());
        connection.setFont(Font.font("System", FontWeight.NORMAL, 15));

        vBox.getChildren().addAll(text, connection, getButtons(width));

        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);

        return EditUsernameMenu.getEditPane(vBox, width, height);
    }

    private String getConnection() {
        return user.getSocket() != null ? "Connected to: " + user.getSocket().toString() : "Not connected!";
    }

    private VBox getButtons(double width) {
        Button backButton = getBackButton(ProfileMenuGUIController::showMainMenu);
        Button connect = UnitPane.getButtonUtil("Server Setting", 50, this::connect);
        Button lobby = UnitPane.getButtonUtil("Lobby", 50, this::lobby);
        Button chat = UnitPane.getButtonUtil("Chat", 50, this::chat);
        Button scoreBoard = UnitPane.getButtonUtil("Score Board", 50, this::scoreBoard);
        Button friends = UnitPane.getButtonUtil("Friends", 50, this::friends);

        VBox vBox = new VBox(connect, lobby, chat, scoreBoard, friends, backButton);
        vBox.setPrefWidth(width);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private void friends(ActionEvent actionEvent) {

    }

    private void scoreBoard(ActionEvent actionEvent) {

    }

    private void chat(ActionEvent actionEvent) {
    }

    private void lobby(ActionEvent actionEvent) {
        if (user.getSocket() == null) {
            new Alert(Alert.AlertType.ERROR, "You're not connected to a server!").show();
            return;
        }

        LobbyMenu lobbyMenu = new LobbyMenu();
        lobbyMenu.init(user);
        try {
            lobbyMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void connect(ActionEvent actionEvent) {
        StartConnectionMenu startConnectionMenu = new StartConnectionMenu();
        startConnectionMenu.init(user);
        try {
            startConnectionMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
