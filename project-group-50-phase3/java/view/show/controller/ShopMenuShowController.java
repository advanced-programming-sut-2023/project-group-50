package view.show.controller;

import Server.Client;
import controller.GUIControllers.GovernmentDataMenuController;
import controller.GUIControllers.ProfileMenuGUIController;
import controller.Menus.ShopMenuController;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import model.ObjectsPackage.Resource;
import view.show.MainMenu.MainMenu;
import view.show.Menus.ItemMenu;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;

public class ShopMenuShowController {

    public static ShopMenuController shopMenuController;
    private static int openedItem = -1;
    private VBox items;

    public static HBox getItem(Resource resource, VBox vBox) {
        HBox hBox = new HBox();
        Button button = new Button(resource.getName() + "  :  " +
                                           ShopMenuShowController.shopMenuController.getCurrentUser().getGovernment().getResourceAmount(resource));
        button.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
        button.setTextFill(Color.WHITE);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setPrefSize(200, 50);
        button.setBackground(new Background(StartMenuController.setBackGround("/images/background/button.png", 200, 40)));

        button.setOnMouseEntered(event -> {
            button.setOpacity(0.7);
        });

        button.setOnMouseExited(event -> {
            button.setOpacity(1);
            button.setTextFill(Color.WHITE);
        });

        button.setOnMouseClicked(event -> {
            button.setTextFill(Color.BLACK);
            openItem(vBox.getChildren().indexOf(hBox), vBox);
        });
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(7);

        Rectangle rectangle = new Rectangle(40, 40);
        rectangle.setFill(new ImagePattern(resource.getImage()));
        rectangle.setOnMouseEntered(event -> {
            rectangle.setOpacity(0.7);
        });

        rectangle.setOnMouseExited(event -> {
            rectangle.setOpacity(1);
        });
        hBox.getChildren().addAll(rectangle, button);
        return hBox;
    }

    private static void openItem(int newOpenItem, VBox vBox) {
        if (openedItem != -1) closeItem(vBox);
        openedItem = newOpenItem;
        vBox.getChildren().remove(openedItem);
        vBox.getChildren().add(openedItem, new ItemMenu(Resource.getResourceByNumber(openedItem), vBox));
    }

    public static void closeItem(VBox vBox) {
        vBox.getChildren().remove(openedItem);
        vBox.getChildren().add(openedItem, getItem(Resource.getResourceByNumber(openedItem), vBox));
    }

    public static void up(int currentOpenItem, VBox vBox) {
        if (currentOpenItem == 0) return;
        openItem(currentOpenItem - 1, vBox);
    }

    public static void down(int currentOpenItem, VBox vBox) {
        if (currentOpenItem == Resource.values().length - 1) return;
        openItem(currentOpenItem + 1, vBox);
    }

    public static void showMainMenu(ActionEvent ignoredActionEvent) {
        MainMenu mainMenu = new MainMenu();
        Client.getData();
        mainMenu.init(ShopMenuShowController.shopMenuController.getCurrentUser());
        try {
            mainMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Parent createContent() {
        Pane pane = new Pane();
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        pane.setPrefSize(width, height);
        pane.setBackground(new Background(StartMenuController.setBackGround("/phase2-assets/background/backgrounds/Backgrounds For Menus - yac/z3qbm9.jpg", width, height)));
        StackPane stackPane = new StackPane(ProfileMenuGUIController.getDataBackgroundImage(
                0.9 * height, 0.8 * width), getData(0.7 * 0.8 * width, 0.9 * 0.75 * height));
        pane.getChildren().add(stackPane);
        stackPane.setLayoutX(width * 0.1);
        stackPane.setLayoutY(height * 0.05);

        pane.getChildren().add(initBackButton());
        return pane;
    }

    private Button initBackButton() {
        Button backButton = getBackButton(ShopMenuShowController::showMainMenu);
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
        return backButton;
    }

    private ScrollPane creatScroll(double width, double height) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPrefWidth(width);
        vBox.setAlignment(Pos.CENTER);
        this.items = vBox;
        for (Resource resource : Resource.values()) {
            vBox.getChildren().add(getItem(resource, vBox));
        }
        return GovernmentDataMenuController.getScrollPane(width, height, vBox);
    }

    private Pane getData(double width, double height) {
        Pane pane = new Pane();
        pane.setBackground(Background.EMPTY);
        pane.setPrefSize(width, height);
        HBox hbox;
        ScrollPane scrollPane;
        pane.getChildren().addAll((hbox = GovernmentDataMenuController.getBoldText("Shop", 0.8 * 0.7 * width)),
                                  (scrollPane = creatScroll(width / 2, height / 2)));
        hbox.setTranslateX(width / 2 - 50);
        hbox.setTranslateY(100);
        scrollPane.setTranslateX(width / 2 - 75);
        scrollPane.setTranslateY(250);
        return pane;

    }


}
