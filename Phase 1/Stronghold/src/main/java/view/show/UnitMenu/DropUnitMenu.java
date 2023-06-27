package view.show.UnitMenu;

import controller.GUIControllers.MainMenuGUIController;
import controller.GUIControllers.UnitMenuController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.ObjectsPackage.People.Soldier.SoldierName;
import view.show.ProfileMenu.EditUsernameMenu;

import java.util.ArrayList;
import java.util.Objects;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;
import static model.Map.GUI.Unit.UnitPane.getBackground;

public class DropUnitMenu extends Application {
    private Pane pane;
    private ArrayList<HBox> hBoxes;
    private SoldierName selected;
    private TextField numberOfSoldiers;

    private StackPane getStackPane(SoldierName soldier) {
        Image image = soldier.getImage();
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setPreserveRatio(true);
        StackPane stackPane = new StackPane(imageView);
        stackPane.setPrefSize(70, 70);
        imageView.setId(soldier.getType());
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new WhatImage());

        Tooltip.install(stackPane, new Tooltip(soldier.getType()));

        addEffects(imageView);
        return stackPane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        pane = new Pane();
        initNumberOfSoldiers();
        Pane editPane = EditUsernameMenu.getEditPane(getSoldiers(), width * 0.4, height * 0.7);

        editPane.setPrefSize(width * 0.4, height * 0.7);
        selected = null;

        pane.setPrefSize(width, height);

        pane.setBackground(getBackground(width, height));
        pane.getChildren().add(editPane);
        editPane.setLayoutX(width * 0.3);
        editPane.setLayoutY(height * 0.15);

        Scene scene = new Scene(pane);
        stage.setFullScreenExitHint("");
        stage.setTitle("Stronghold");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    private void initNumberOfSoldiers() {
        numberOfSoldiers = new TextField();
        numberOfSoldiers.setPromptText("Number of soldiers");
        numberOfSoldiers.setPrefSize(100, 25);
        numberOfSoldiers.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);" +
                                          " -fx-prompt-text-fill: black; -fx-font: 20 System");
    }

    private Button initBackButton() {
        Button backButton = getBackButton(UnitMenuController::showUnitMenu);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
        return backButton;
    }

    private Button initConfirmButton() {
        Image image = new Image(MainMenuGUIController.class.getResource("/images/Buttons/bg.jpg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              new BackgroundSize(
                                                                      100,
                                                                      40,
                                                                      false, false, false, false
                                                              ));

        Button button = new Button("Confirm");
        button.setBackground(new Background(backgroundImage));
        button.setPrefSize(100, 40);
        button.setFont(new Font("Bell MT", 18));
        button.setStyle("-fx-text-fill: yellow");
        button.setAlignment(Pos.CENTER);
        button.setOnAction(new confirm());
        return button;
    }

    private VBox getSoldiers() {
        Text text = new Text("Select a soldier");
        text.setStyle("-fx-font: 20 System; -fx-font-weight: bold");
        Button backButton = initBackButton();
        Button confirmButton = initConfirmButton();
        HBox hBox = new HBox(numberOfSoldiers, confirmButton);
        hBox.setSpacing(15);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(text, getPictures(), backButton, hBox);
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private VBox getPictures() {
        hBoxes = new ArrayList<>();
        for (int i = 0; i < SoldierName.values().length / 5; i++) {
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            hBoxes.add(hBox);
        }

        for (SoldierName soldier : SoldierName.values())
            hBoxes.get(soldier.ordinal() / 5).getChildren().add(getStackPane(soldier));

        VBox vBox = new VBox(hBoxes.get(0), hBoxes.get(1), hBoxes.get(2), hBoxes.get(3));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        return vBox;
    }

    private void addEffects(ImageView imageView) {
        ColorAdjust colorAdjustHover = new ColorAdjust();
        colorAdjustHover.setBrightness(-0.25);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        imageView.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> imageView.setEffect(colorAdjustHover));
        imageView.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
                                     imageView.setEffect(
                                             selected != null && Objects.equals(imageView.getId(), selected.getType())
                                                     ? colorAdjust
                                                     : null);
                                 }
        );
    }

    private void setSelected(SoldierName soldierName) {
        HBox hBox = hBoxes.get(soldierName.ordinal() / 5);
        StackPane stackPane = (StackPane) hBox.getChildren().get(soldierName.ordinal() % 5);
        ImageView imageView = (ImageView) stackPane.getChildren().get(0);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        imageView.setEffect(colorAdjust);
        selected = soldierName;
    }

    private void setNotSelected() {
        if (selected == null) return;
        HBox hBox = hBoxes.get(selected.ordinal() / 5);
        StackPane stackPane = (StackPane) hBox.getChildren().get(selected.ordinal() % 5);
        ImageView imageView = (ImageView) stackPane.getChildren().get(0);
        imageView.setEffect(null);
        selected = null;
    }

    private class WhatImage implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            String img = event.getPickResult().getIntersectedNode().getId();
            SoldierName selected = SoldierName.getSoldierNameByType(img);

            assert selected != null;
            setNotSelected();
            setSelected(selected);
        }
    }

    private class confirm implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            int number;
            try {
                number = Integer.parseInt(numberOfSoldiers.textProperty().get());
                if (number < 0) throw new RuntimeException();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Invalid number").show();
                return;
            }

            if (selected == null) {
                UnitMenuController.showUnitMenu(null);
            }

            if (!UnitMenuController.canAfford(selected, number)) {
                new Alert(Alert.AlertType.ERROR, "You don't have enough coins").show();
                return;
            }

            if (!UnitMenuController.hasEnoughArmour(selected, number)) {
                new Alert(Alert.AlertType.ERROR, "You don't have enough armour").show();
                return;
            }

            if (!UnitMenuController.hasEnoughWeapons(selected, number)) {
                new Alert(Alert.AlertType.ERROR, "You don't have enough weapons").show();
                return;
            }

            UnitMenuController.dropUnits(selected, number);

            UnitMenuController.showUnitMenu(null);
        }
    }
}
