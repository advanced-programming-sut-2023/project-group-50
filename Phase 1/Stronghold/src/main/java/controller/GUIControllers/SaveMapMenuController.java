package controller.GUIControllers;

import controller.UserDatabase.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import model.Save.MapSave.MapLoader;
import view.show.MainMenu.MainMenu;
import view.show.ProfileMenu.GetSaveNameMenu;

import java.util.Objects;

import static controller.GUIControllers.MainMenuGUIController.getButton;
import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;
import static view.show.ProfileMenu.EditUsernameMenu.getEditPane;

public class SaveMapMenuController {
    private final User user;

    public SaveMapMenuController(User user) {
        this.user = user;
    }

    public Pane getPane() {
        double height = Screen.getPrimary().getBounds().getHeight();
        double width = Screen.getPrimary().getBounds().getWidth();

        Pane pane = new Pane();
        pane.setPrefSize(width, height);
        pane.setBackground(getBackground(width, height));

        Pane maps;
        try {
            maps = getMaps(width * 0.8, height * 0.8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        pane.getChildren().add(maps);
        maps.setLayoutY(height * 0.1);
        maps.setLayoutX(width * 0.1);

        initBackButton(pane);

        return pane;
    }

    private void initBackButton(Pane pane) {
        Button backButton = getBackButton(UnitMenuController::showMainMenu);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
    }

    private Button getSaveButton() {
        Button button = getButton("Save Current Map", 50, new save());
        button.setText("Save Current");
        return button;
    }

    private Pane getMaps(double width, double height) throws Exception {
        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(width, height);

        Text text = new Text("Select a map");
        text.setStyle("-fx-font: 30 Algerian; -fx-font-weight: bold");

        vBox.getChildren().addAll(text,
                                  getMapsScrollPane(width, height * 0.65),
                                  getSaveButton());

        return getEditPane(vBox, width, height);
    }

    private ScrollPane getMapsScrollPane(double width, double height) throws Exception {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setBackground(Background.EMPTY);

        scrollPane.setPrefSize(width, height);
        scrollPane.setContent(getMapsVBox(width));

        return scrollPane;
    }

    private VBox getMapsVBox(double width) throws Exception {
        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(width);

        for (String mapName : Objects.requireNonNull(MapLoader.getMaps(user)))
            vBox.getChildren().addAll(getMapHBox(mapName, width * 0.65));

        return vBox;
    }

    private HBox getMapHBox(String mapName, double width) throws Exception {
        HBox hBox = new HBox();
        hBox.setMaxWidth(width);
        hBox.setPrefHeight(100);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);" +
                              " -fx-background-radius: 20");

        addEvents(mapName, hBox);

        Text name = new Text("\t" + mapName + "\t");
        name.setFont(new Font("System", 25));
        name.setStyle("-fx-font-weight: bold");

        Text date = new Text(MapLoader.getDate(user, mapName));
        date.setFont(new Font("System", 15));

        hBox.getChildren().addAll(
                name, date
        );

        return hBox;
    }

    private void addEvents(String mapName, HBox hBox) {
        hBox.setOnMouseEntered(mouseEvent -> hBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);" +
                                                                   " -fx-background-radius: 20"));
        hBox.setOnMouseExited(mouseEvent -> hBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);" +
                                                                  " -fx-background-radius: 20"));
        hBox.setOnMouseClicked(mouseEvent -> {
            try {
                user.getGovernment().setMap(MapLoader.getMap(user, mapName));
                UnitMenuController.showMainMenu(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    private Background getBackground(double width, double height) {
        Image image = new Image(
                Objects.requireNonNull(SaveMapMenuController.class.getResource("/images/background/saveMenu.png"))
                        .toExternalForm()
        );

        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        width, height,
                        false, false, false, false
                )
        );

        return new Background(backgroundImage);
    }

    private class save implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            GetSaveNameMenu getSaveNameMenu = new GetSaveNameMenu();
            getSaveNameMenu.init(user);
            try {
                getSaveNameMenu.start(MainMenu.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
