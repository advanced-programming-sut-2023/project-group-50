package view.show.OnlineMenu;

import Server.Packet;
import Server.ServerCommands;
import controller.GUIControllers.ProfileMenuGUIController;
import controller.GUIControllers.SoldierMenuController.SoldierMenuController;
import controller.UserDatabase.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Government.GUI.GovernmentPane;
import model.Map.GUI.Unit.UnitPane;
import view.show.ProfileMenu.EditUsernameMenu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;

public class StartConnectionMenu extends Application {
    private TextField host;
    private TextField port;

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
        pane.getChildren().add(getEditPane(width * 0.5, height * 0.5));

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

        Text text = new Text("Connect to server...");
        text.setFont(new Font("System", 25));
        text.setStyle("-fx-font-weight: bold");

        vBox.getChildren().addAll(text, getData(width * 0.75), getButtons(width));

        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);

        return EditUsernameMenu.getEditPane(vBox, width, height);
    }

    private HBox getButtons(double width) {
        Button backButton = getBackButton(ProfileMenuGUIController::showMainMenu);
        Button confirm = UnitPane.getButtonUtil("Connect", 50, new confirmHandler());
        Button disconnect = UnitPane.getButtonUtil("Disconnect", 50, new disconnect());
        Button current = UnitPane.getButtonUtil("Current Server", 50, new current());

        HBox hBox = new HBox(confirm, disconnect, current, backButton);
        hBox.setPrefSize(width, 50);
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private HBox getData(double width) {
        HBox hBox = new HBox(getHostPort(width * 0.5));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(width);
        hBox.setSpacing(20);
        return hBox;
    }

    private VBox getHostPort(double width) {
        Text text = new Text("Server data");
        text.setStyle("-fx-font: 20 System");

        setTextField(true, width);
        setTextField(false, width);
        setTextField(false, width);

        VBox vBox = new VBox(text, host, port);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        return vBox;
    }

    private void setTextField(boolean isHost, double width) {
        TextField field;

        field = new TextField();
        field.setPrefSize(width, 50);
        field.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2); -fx-prompt-text-fill: black; -fx-font: 20 System");

        if (isHost) {
            field.setPromptText("Host");
            host = field;
        } else {
            field.setPromptText("Port");
            port = field;
        }
    }

    private class confirmHandler implements EventHandler<ActionEvent> {
        @Override
        public synchronized void handle(ActionEvent actionEvent) {
            String Host = host.getText();
            String Port = port.getText();

            try {
                if (!InetAddress.getByName(Host).isReachable(5000)) {
                    new Alert(Alert.AlertType.ERROR, "Connection timeout").show();
                    return;
                }

                try {
                    Socket socket = new Socket(Host, Integer.parseInt(Port));

                    Packet packet = new Packet(ServerCommands.INIT.getString(), user);
                    new ObjectOutputStream(socket.getOutputStream()).writeObject(packet);

                    Packet res = (Packet) new ObjectInputStream(socket.getInputStream()).readObject();
                    if (Objects.equals(res.command, ServerCommands.INIT_DONE.getString())) {
                        user.setSocket(socket);
                    } else
                        throw new RuntimeException();

                    ProfileMenuGUIController.showMainMenu(null);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Connection failed").show();
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class disconnect implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (user.getSocket() != null) {
                try {
                    user.getSocket().close();
                    user.setSocket(null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                user.setSocket(null);
                ProfileMenuGUIController.showMainMenu(null);
            }
        }
    }

    private class current implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (user.getSocket() != null)
                new Alert(Alert.AlertType.INFORMATION, "Connected to: " + user.getSocket().toString()).show();
            else
                new Alert(Alert.AlertType.INFORMATION, "Not connected!").show();
        }
    }
}
