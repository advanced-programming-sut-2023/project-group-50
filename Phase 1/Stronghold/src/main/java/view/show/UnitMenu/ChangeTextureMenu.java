package view.show.UnitMenu;

import controller.GUIControllers.MultiUnitMenuController;
import controller.GUIControllers.UnitMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Map.GroundType;
import view.show.ProfileMenu.EditUsernameMenu;

import java.util.ArrayList;
import java.util.Objects;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;
import static model.Map.GUI.Unit.UnitPane.getBackground;

public class ChangeTextureMenu extends Application {
    private Pane pane;
    private ArrayList<HBox> hBoxes;
    private boolean isMulti = false;

    public void init(boolean multi) {
        this.isMulti = multi;
    }

    @Override
    public void start(Stage stage) throws Exception {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        pane = new Pane();
        Pane editPane = EditUsernameMenu.getEditPane(getTextures(), width * 0.5, height * 0.5);

        editPane.setPrefSize(width * 0.5, height * 0.5);

        pane.setPrefSize(width, height);

        pane.setBackground(getBackground(width, height));
        pane.getChildren().add(editPane);
        editPane.setLayoutX(width * 0.25);
        editPane.setLayoutY(height * 0.25);

        Scene scene = new Scene(pane);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setTitle("Stronghold");
        stage.setScene(scene);
        stage.show();
    }

    private Button initButton() {
        Button backButton = getBackButton(
                isMulti
                        ? MultiUnitMenuController::showMultiUnitMenu
                        : UnitMenuController::showUnitMenu);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
        return backButton;
    }

    private VBox getTextures() {
        Text text = new Text("Select a texture");
        text.setStyle("-fx-font: 20 System; -fx-font-weight: bold");
        Button button = initButton();

        VBox vBox = new VBox(text, getPictures(), button);
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private VBox getPictures() {
        hBoxes = new ArrayList<>();
        for (int i = 0; i < GroundType.values().length / 4; i++) {
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            hBoxes.add(hBox);
        }

        for (GroundType texture : GroundType.values()) {
            Image image = texture.getImage();
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(40);
            imageView.setPreserveRatio(true);
            imageView.setId(texture.getType());
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new WhatImage());

            Tooltip.install(imageView, new Tooltip(texture.getType()));

            addEffects(imageView);

            hBoxes.get(texture.ordinal() / 4).getChildren().add(imageView);
        }

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
        imageView.addEventFilter(MouseEvent.MOUSE_EXITED, e -> imageView.setEffect(
                Objects.equals(imageView.getId(),
                               isMulti
                                       ? MultiUnitMenuController.getTexture().getType()
                                       : UnitMenuController.getTexture().getType()
                )
                        ? colorAdjust : null)
        );
    }

    private void setSelected(GroundType texture) {
        HBox hBox = hBoxes.get(texture.ordinal() / 4);
        ImageView imageView = (ImageView) hBox.getChildren().get(texture.ordinal() % 4);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        imageView.setEffect(colorAdjust);
        if (!isMulti) UnitMenuController.setTexture(texture);
        else MultiUnitMenuController.setTexture(texture);
    }

    private void setNotSelected(GroundType texture) {
        HBox hBox = hBoxes.get(texture.ordinal() / 4);
        ImageView imageView = (ImageView) hBox.getChildren().get(texture.ordinal() % 4);
        imageView.setEffect(null);
    }

    private class WhatImage implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            GroundType texture = isMulti ? MultiUnitMenuController.getTexture() : UnitMenuController.getTexture();
            String img = event.getPickResult().getIntersectedNode().getId();
            GroundType selected = GroundType.get(img);

            assert selected != null;
            setNotSelected(texture);
            setSelected(selected);
        }
    }
}
