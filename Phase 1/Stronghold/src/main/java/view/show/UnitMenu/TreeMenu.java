package view.show.UnitMenu;

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
import model.ObjectsPackage.TreeType;
import view.show.ProfileMenu.EditUsernameMenu;

import java.util.Objects;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;
import static model.Map.GUI.Unit.UnitPane.getBackground;

public class TreeMenu extends Application {
    private Pane pane;
    private HBox hBox;

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
        stage.setFullScreenExitHint("");
        stage.setTitle("Stronghold");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    private Button initButton() {
        Button backButton = getBackButton(UnitMenuController::showUnitMenu);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
        return backButton;
    }

    private VBox getTextures() {
        Text text = new Text("Select a tree type");
        text.setStyle("-fx-font: 20 System; -fx-font-weight: bold");
        Button button = initButton();

        VBox vBox = new VBox(text, getPictures(), button);
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private HBox getPictures() {
        hBox = new HBox();

        for (TreeType treeType : TreeType.values()) {
            Image image = treeType.getImage();
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
            imageView.setId(treeType.getType());
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new WhatImage());

            Tooltip.install(imageView, new Tooltip(treeType.getType()));

            addEffects(imageView);
            hBox.getChildren().add(imageView);
        }

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        return hBox;
    }

    private void addEffects(ImageView imageView) {
        ColorAdjust colorAdjustHover = new ColorAdjust();
        colorAdjustHover.setBrightness(-0.25);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        imageView.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> imageView.setEffect(colorAdjustHover));
        imageView.addEventFilter(MouseEvent.MOUSE_EXITED, e ->
                imageView.setEffect(UnitMenuController.getTree() != null &&
                                            Objects.equals(imageView.getId(),
                                                           UnitMenuController.getTree().getType().getType()) ?
                                            colorAdjust : null)
        );
    }

    private void setSelected(TreeType type) {
        ImageView imageView = (ImageView) hBox.getChildren().get(type.ordinal());
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        imageView.setEffect(colorAdjust);
        UnitMenuController.addTree(type);
    }

    private void setNotSelected(TreeType type) {
        ImageView imageView = (ImageView) hBox.getChildren().get(type.ordinal());
        imageView.setEffect(null);
    }

    private class WhatImage implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (UnitMenuController.getTree() != null)
                setNotSelected(UnitMenuController.getTree().getType());
            String img = event.getPickResult().getIntersectedNode().getId();
            TreeType selected = TreeType.get(img);

            assert selected != null;
            setSelected(selected);
        }
    }
}
