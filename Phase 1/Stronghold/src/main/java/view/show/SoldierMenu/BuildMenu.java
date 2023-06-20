package view.show.SoldierMenu;

import controller.GUIControllers.MainMenuGUIController;
import controller.GUIControllers.SoldierMenuController.EngineerMenuController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import model.ObjectsPackage.Buildings.BuildingType;
import view.show.ProfileMenu.EditUsernameMenu;

import java.util.ArrayList;
import java.util.Objects;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;
import static model.Map.GUI.Unit.UnitPane.getBackground;

public class BuildMenu extends Application {
    private Pane pane;
    private ArrayList<HBox> hBoxes;
    private BuildingType selected;
    private EngineerMenuController engineerMenuController;

    public void init(EngineerMenuController engineerMenuController) {
        this.engineerMenuController = engineerMenuController;
    }

    private StackPane getStackPane(BuildingType buildingType) {
        Image image = buildingType.getImage();
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setPreserveRatio(true);

        if (imageView.getFitWidth() > 70) {
            imageView.setFitWidth(70);
        }

        StackPane stackPane = new StackPane(imageView);
        stackPane.setPrefSize(70, 70);
        imageView.setId(buildingType.getType());
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new WhatImage());

        Tooltip.install(stackPane, new Tooltip(buildingType.getType()));

        addEffects(imageView);
        return stackPane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        pane = new Pane();
        Pane editPane = EditUsernameMenu.getEditPane(getBuilding(), width * 0.7, height * 0.7);

        editPane.setPrefSize(width * 0.7, height * 0.7);
        selected = null;

        pane.setPrefSize(width, height);

        pane.setBackground(getBackground(width, height));
        pane.getChildren().add(editPane);
        editPane.setLayoutX(width * 0.15);
        editPane.setLayoutY(height * 0.15);

        Scene scene = new Scene(pane);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setTitle("Stronghold");
        stage.setScene(scene);
        stage.show();
    }

    private Button initBackButton() {
        Button backButton = getBackButton(engineerMenuController::showSoldierMenu);
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
                                                                      25,
                                                                      false, false, false, false
                                                              ));

        Button button = new Button("Confirm");
        button.setBackground(new Background(backgroundImage));
        button.setPrefSize(100, 50);
        button.setFont(new Font("Bell MT", 18));
        button.setStyle("-fx-text-fill: yellow");
        button.setAlignment(Pos.CENTER);
        button.setOnAction(new confirm());
        return button;
    }

    private VBox getBuilding() {
        Text text = new Text("Select a building");
        text.setStyle("-fx-font: 20 System; -fx-font-weight: bold");
        Button backButton = initBackButton();
        Button confirmButton = initConfirmButton();
        HBox hBox = new HBox(backButton, confirmButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        VBox vBox = new VBox(text, getPictures(), hBox);
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private VBox getPictures() {
        hBoxes = new ArrayList<>();
        for (int i = 0; i < BuildingType.values().length / 10; i++) {
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            hBoxes.add(hBox);
        }

        for (BuildingType buildingType : BuildingType.values())
            hBoxes.get(buildingType.ordinal() / 10).getChildren().add(getStackPane(buildingType));

        VBox vBox = new VBox(hBoxes.get(0), hBoxes.get(1), hBoxes.get(2), hBoxes.get(3), hBoxes.get(4));
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

    private void setSelected(BuildingType buildingType) {
        HBox hBox = hBoxes.get(buildingType.ordinal() / 10);
        StackPane stackPane = (StackPane) hBox.getChildren().get(buildingType.ordinal() % 10);
        ImageView imageView = (ImageView) stackPane.getChildren().get(0);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        imageView.setEffect(colorAdjust);
        selected = buildingType;
    }

    private void setNotSelected() {
        if (selected == null) return;
        HBox hBox = hBoxes.get(selected.ordinal() / 10);
        StackPane stackPane = (StackPane) hBox.getChildren().get(selected.ordinal() % 10);
        ImageView imageView = (ImageView) stackPane.getChildren().get(0);
        imageView.setEffect(null);
        selected = null;
    }

    private class WhatImage implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            String img = event.getPickResult().getIntersectedNode().getId();
            BuildingType selected = BuildingType.checkTypeByName(img);

            assert selected != null;
            setNotSelected();
            setSelected(selected);
        }
    }

    private class confirm implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (selected == null) {
                engineerMenuController.showSoldierMenu(null);
                return;
            }

            if (!engineerMenuController.canAfford(selected)) {
                new Alert(Alert.AlertType.ERROR, "You don't have enough resources").show();
                return;
            }

            if (!engineerMenuController.canPlace(selected)) {
                new Alert(Alert.AlertType.ERROR, "Can't place building here").show();
                return;
            }

            if (selected == BuildingType.PALACE && engineerMenuController.hasPalace()) {
                new Alert(Alert.AlertType.ERROR, "You already have a palace").show();
                return;
            }

            engineerMenuController.place(selected);

            engineerMenuController.showSoldierMenu(null);
        }
    }
}
