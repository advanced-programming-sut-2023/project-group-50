package controller.GUIControllers;

import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import model.Government.GUI.GovernmentPane;
import model.Map.GUI.MapPane;
import view.show.MainMenu.MainMenu;
import view.show.ProfileMenu.ShowProfileMenu;

public class MainMenuGUIController {
    private static Pane pane;
    private static Pane governmentPane;
    private static HBox profileData;
    private static User user;

    public static Pane getPane(String username) {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        user = Users.getUser(username);

        pane = new Pane();
        pane.setPrefSize(width, height);
        pane.getChildren().add(MapPane.getMapPane(user.getGovernment().getMap(),
                                                  50,
                                                  50,
                                                  0,
                                                  0));

        governmentPane = GovernmentPane.getPane(user.getGovernment(), pane);
        profileData = getUserPane(username);

        pane.getChildren().add(governmentPane);
        governmentPane.setLayoutX(0);
        governmentPane.setLayoutY(height - 100);

        pane.getChildren().add(profileData);
        profileData.setLayoutX(width - 350);
        profileData.setLayoutY(10);


        return pane;
    }

    public static Pane getPane(User user) {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        MainMenuGUIController.user = user;

        pane = new Pane();
        pane.setPrefSize(width, height);
        pane.getChildren().add(MapPane.getMapPane(user.getGovernment().getMap(),
                                                  50,
                                                  50,
                                                  0,
                                                  0));

        governmentPane = GovernmentPane.getPane(user.getGovernment(), pane);
        profileData = getUserPane(user.getUserName());

        pane.getChildren().add(governmentPane);
        governmentPane.setLayoutX(0);
        governmentPane.setLayoutY(height - 200);

        pane.getChildren().add(profileData);
        profileData.setLayoutX(width - 350);
        profileData.setLayoutY(10);

        return pane;
    }

    public static void updateGovernmentPane(String username) {
        pane.getChildren().remove(governmentPane);
        governmentPane = GovernmentPane.getPane(Users.getUser(username).getGovernment(), pane);
        pane.getChildren().add(governmentPane);

        governmentPane.setLayoutX(0);
        governmentPane.setLayoutY(pane.getHeight() - 200);
    }

    public static HBox getUserPane(String username) {
        User user = Users.getUser(username);

        double height = 200;

        HBox hBox = new HBox(getMenus(height), getUserAvatar(height));
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        return hBox;
    }

    private static VBox getMenus(double height) {
        VBox vBox = new VBox(
                getButton("Connect to server", height / 4, MainMenuGUIController::online),
                getButton("Trade Menu", height / 4, MainMenuGUIController::trade),
                getButton("Shop Menu", height / 4, MainMenuGUIController::shop),
                getButton("Profile Menu", height / 4, MainMenuGUIController::profile)
        );

        vBox.setPrefHeight(height);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }


    private static Button getButton(String text, double height, EventHandler<ActionEvent> eventHandler) {
        Image image;
        image = new Image(MainMenuGUIController.class.getResource("/images/Buttons/bg.jpg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              new BackgroundSize(
                                                                      image.getWidth() / image.getHeight() * height / 2,
                                                                      height,
                                                                      false, false, false, false
                                                              ));

        Button button = new Button(text);
        button.setBackground(new Background(backgroundImage));
        button.setPrefSize(image.getWidth() / image.getHeight() * height / 2, height);
        button.setFont(new Font("Bell MT", 18));
        button.setStyle("-fx-text-fill: yellow");
        button.setAlignment(Pos.CENTER);
        button.setOnAction(eventHandler);

        return button;
    }

    private static VBox getUserAvatar(double height) {
        Image image = new Image(user.getAvatar().toExternalForm());

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height * 0.75);

        Text text = new Text(user.getNickName());
        text.setStyle("-fx-fill: Black; -fx-font-weight: bold; " +
                              "-fx-effect: dropshadow(three-pass-box, rgba(255, 255, 100, 0.5), 50, 0.9, 0, 0)");
        text.setFont(new Font("Old English Text MT", 20));

        VBox vBox = new VBox(imageView, text);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(15);
        vBox.setPrefHeight(height);

        return vBox;
    }

    public static void profile(ActionEvent event) {
        try {
            ShowProfileMenu showProfileMenu = new ShowProfileMenu();
            showProfileMenu.init(user);
            showProfileMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void trade(ActionEvent actionEvent) {
        //TODO
    }

    private static void shop(ActionEvent actionEvent) {
        //TODO
    }

    private static void online(ActionEvent actionEvent) {
        //TODO
    }
}
