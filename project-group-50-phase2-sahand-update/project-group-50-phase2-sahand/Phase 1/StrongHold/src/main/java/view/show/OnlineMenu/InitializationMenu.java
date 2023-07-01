package view.show.OnlineMenu;

import Server.ClientStart;
import Server.Server;
import Server.ServerStart;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.InetAddress;

public class InitializationMenu extends Application {
    public static Stage stage;
    private static RadioButton[] checkBoxes;
    private TextField ip;
    private Text current;

    private static TextField getTextField() {
        TextField ip = new TextField(Server.ip);
        ip.setPromptText("Enter IP");
        ip.setMaxSize(300, 50);
        return ip;
    }

    public static HBox getCheckBoxes(int choice) {
        checkBoxes = new RadioButton[2];
        ToggleGroup toggleGroup = new ToggleGroup();

        for (int i = 0; i < 2; i++) {
            RadioButton box = new RadioButton();
            box.setText(i == 1 ? "Server" : "Client");
            box.selectedProperty().set(choice == i);
            box.setToggleGroup(toggleGroup);
            checkBoxes[i] = box;
        }
        HBox hBox = new HBox(checkBoxes);
        hBox.setSpacing(15);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    @Override
    public void start(Stage stage) throws Exception {
        InitializationMenu.stage = stage;
        StackPane stackPane = new StackPane();
        stackPane.setMinSize(500, 300);

        current = new Text("Disconnected");

        ip = getTextField();

        Button button = getButton();

        VBox vBox = new VBox(current, ip, button, getCheckBoxes(-1));

        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);

        stackPane.getChildren().add(vBox);
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.setTitle("Server");
        stage.show();
    }

    private Button getButton() {
        Button button = new Button("Confirm");
        button.setPrefSize(100, 30);
        button.setOnAction(this::checkConnected);
        return button;
    }

    private void checkConnected(ActionEvent actionEvent) {
        Server.ip = ip.getText();
        try {
            if (InetAddress.getByName(ip.getText()).isReachable(50)) {
                current.setText("Connected");

                if (isClient()) {
                    stage.close();
                    ClientStart.main(null);
                } else if (isServer()) {
                    stage.close();
                    ServerStart.main(null);
                } else current.setText("Select server or client");
            } else current.setText("Not Available");
        } catch (Exception e) {
            current.setText("Disconnected");
            throw new RuntimeException (e);
        }
    }

    public boolean isClient() {
        return checkBoxes[0].selectedProperty().get();
    }

    public boolean isServer() {
        return checkBoxes[1].selectedProperty().get();
    }
}
