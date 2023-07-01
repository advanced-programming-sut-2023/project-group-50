package view.show.ProfileMenu;

import Server.Client;
import controller.GUIControllers.MainMenuGUIController;
import controller.UserDatabase.Users;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;

public class EditAvatarMenu extends Application {
    private Stage stage;
    private String username;
    private VBox avatars;

    private static int getI(String img) {
        Matcher matcher = Pattern.compile(".*\\((?<i>\\d+)\\)\\..*").matcher(img);

        if (!matcher.matches())
            return -1;

        return Integer.parseInt(matcher.group("i"));
    }

    private Pane getMainPane() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        Pane pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setPrefSize(width, height);
        pane.setBackground(EditUsernameMenu.getBackground(width, height));

        VBox vBox = new VBox(getPane(width / 1.5, height / 1.1));
        vBox.setPrefSize(width, height);
        vBox.setAlignment(Pos.CENTER);

        pane.getChildren().add(vBox);
        Button backButton = getBackButton(MainMenuGUIController::profile);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(width - 125);

        return pane;
    }

    private Pane getPane(double width, double height) {
        VBox vBox = new VBox();
        Text text = new Text("Choose Avatar");
        text.setStyle("-fx-font: 25 System; -fx-fill: Black; -fx-font-weight: bold");

        avatars = getAvatars();
        setSelected(getI(Objects.requireNonNull(Users.getUser(username)).getAvatar().toExternalForm()));

        HBox hBox = gethBox();

        vBox.getChildren().addAll(text, avatars, hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        vBox.setPrefSize(width, height);
        return EditUsernameMenu.getEditPane(vBox, width, height);
    }

    private VBox getAvatars() {
        VBox vBox = new VBox();

        ArrayList<HBox> hBoxes = new ArrayList<>();
        addHBoxes(hBoxes);

        for (int i = 1; i <= 45; i++)
            addImage(i, hBoxes);

        vBox.getChildren().addAll(hBoxes);
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private void addHBoxes(ArrayList<HBox> hBoxes) {
        for (int i = 0; i < 45 / 9; i++) {
            HBox hBox = new HBox();
            hBox.setSpacing(15);
            hBox.setAlignment(Pos.CENTER);
            hBoxes.add(hBox);
        }
    }

    private void addImage(int i, ArrayList<HBox> HB) {
        String s = "/phase2-assets/background/profile backgrounds/BetterAvatars/Avatar (" + i + ").png";
        URL url = EditAvatarMenu.class.getResource(s);

        assert url != null;
        Image image = new Image(url.toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setPreserveRatio(true);
        imageView.setId("Avatar (" + i + ").png");
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new WhatImage());

        addEffects(imageView);

        HB.get((i - 1) / 9).getChildren().add(imageView);
    }

    private void addEffects(ImageView imageView) {
        ColorAdjust colorAdjustHover = new ColorAdjust();
        colorAdjustHover.setBrightness(-0.25);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        imageView.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> imageView.setEffect(colorAdjustHover));
        imageView.addEventFilter(MouseEvent.MOUSE_EXITED, e -> imageView.setEffect(
                getI(imageView.getImage().getUrl()) == getI(Users.getUser(username).getAvatar().toExternalForm())
                        ? colorAdjust : null)
        );
    }

    private HBox gethBox() {
        Button button = getButton("Confirm", "green", this::handleConfirm);
        Button randomButton = getButton("Choose from files", "orange", this::handleFromFiles);

        HBox hBox = new HBox(button, randomButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        return hBox;
    }

    private void handleFromFiles(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick an avatar");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg)",
                                                                                      "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(stage);

        try {
            if (file == null)
                return;
            Objects.requireNonNull(Users.getUser(username)).setAvatar(file.toURI().toURL());
            handleConfirm(null);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleConfirm(ActionEvent actionEvent) {
        try {
            Client.sendData();
            ShowProfileMenu showProfileMenu = new ShowProfileMenu();
            showProfileMenu.init(Users.getUser(username));
            showProfileMenu.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Button getButton(String text, String color, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 20;" +
                                " -fx-text-fill: black; -fx-font: 20 System");
        button.setPrefWidth(200);
        button.setPrefHeight(50);
        button.setOnAction(eventHandler);
        return button;
    }

    public void newMenu(String username) {
        this.username = username;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Pane pane = getMainPane();

        stage.getScene().setRoot(pane);
        stage.show();
    }

    private void setSelected(int i) {
        if (i == -1) return;
        HBox hBox = (HBox) avatars.getChildren().get((i - 1) / 9);
        ImageView imageView = (ImageView) hBox.getChildren().get((i - 1) % 9);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        imageView.setEffect(colorAdjust);
    }

    private void setNotSelected(int i) {
        if (i == -1) return;
        HBox hBox = (HBox) avatars.getChildren().get((i - 1) / 9);
        ImageView imageView = (ImageView) hBox.getChildren().get((i - 1) % 9);
        imageView.setEffect(null);
    }

    private class WhatImage implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            int prevI = getI(Objects.requireNonNull(Users.getUser(username)).getAvatar().toExternalForm());
            String img = event.getPickResult().getIntersectedNode().getId();
            URL resource = EditAvatarMenu.class.getResource(
                    "/phase2-assets/background/profile backgrounds/BetterAvatars/" + img);
            Objects.requireNonNull(Users.getUser(username)).setAvatar(resource);

            int i = getI(img);

            setNotSelected(prevI);
            setSelected(i);
        }
    }
}
