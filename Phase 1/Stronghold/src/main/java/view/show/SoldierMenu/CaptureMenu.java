package view.show.SoldierMenu;

import controller.GUIControllers.SoldierMenuController.InfantryMenuController;
import controller.GUIControllers.SoldierMenuController.SoldierMenuController;
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

import java.util.Objects;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;

public class CaptureMenu extends Application {
    private TextField toX;
    private TextField toY;

    private InfantryMenuController infantryMenuController;

    public void init(InfantryMenuController infantryMenuController) {
        this.infantryMenuController = infantryMenuController;
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

        Text text = new Text("Capture gate");
        text.setFont(new Font("System", 25));
        text.setStyle("-fx-font-weight: bold");

        vBox.getChildren().addAll(text, getToFrom(width * 0.75), getButtons(width));

        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);

        return EditUsernameMenu.getEditPane(vBox, width, height);
    }

    private HBox getButtons(double width) {
        Button backButton = getBackButton(infantryMenuController::showSoldierMenu);
        Button confirm = UnitPane.getButtonUtil("Confirm", 50, new confirmHandler());
        Button stop = UnitPane.getButtonUtil("Stop capturing", 50, new stopHandler());

        HBox hBox = new HBox(confirm, stop, backButton);
        hBox.setPrefSize(width, 50);
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private HBox getToFrom(double width) {
        HBox hBox = new HBox(getTo(width * 0.5));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(width);
        hBox.setSpacing(20);
        return hBox;
    }

    private VBox getTo(double width) {
        Text text = new Text("At");
        text.setStyle("-fx-font: 20 System");

        setTextField(true, width);
        setTextField(false, width);

        VBox vBox = new VBox(text, toX, toY);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        return vBox;
    }

    private void setTextField(boolean x, double width) {
        TextField field;

        field = new TextField();
        field.setPrefSize(width, 50);
        field.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2); -fx-prompt-text-fill: black; -fx-font: 20 System");

        if (x) {
            field.setPromptText(infantryMenuController.getPromptCaptureX());
            toX = field;
        } else {
            field.setPromptText(infantryMenuController.getPromptCaptureY());
            toY = field;
        }
    }

    private class confirmHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            int xTo, yTo;
            try {
                xTo = Integer.parseInt(toX.getText());
                yTo = Integer.parseInt(toY.getText());
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Invalid coordinates").show();
                return;
            }

            if (SoldierMenuController.isInvalid(0, xTo, 0, yTo)) {
                new Alert(Alert.AlertType.ERROR, "Coordinates out of bounds").show();
                return;
            }

            if (infantryMenuController.cannotMoveTo(xTo, yTo)) {
                new Alert(Alert.AlertType.ERROR, "Destination out of range or invalid").show();
                return;
            }

            if (infantryMenuController.doesntHaveGate(xTo, yTo)) {
                new Alert(Alert.AlertType.ERROR, "No gate to capture").show();
                return;
            }

            infantryMenuController.setCapture(xTo, yTo);
            infantryMenuController.showSoldierMenu(null);
        }
    }

    private class stopHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            infantryMenuController.stopCapturing();
            infantryMenuController.showSoldierMenu(null);
        }
    }
}
