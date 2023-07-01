package model.Map.GUI.Unit;

import Server.Client;
import controller.GUIControllers.MainMenuGUIController;
import controller.GUIControllers.SoldierMenuController.SoldierMenuController;
import controller.GUIControllers.UnitMenuController;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import model.Map.GUI.MapPane.UnitGroup;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.Storage;
import view.show.ProfileMenu.ShowProfileMenu;

import java.net.URL;
import java.util.HashMap;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;
import static controller.GUIControllers.ProfileMenuGUIController.getDataBackgroundImage;

public class UnitPane {
    private final String username;
    private final int X;
    private final int Y;
    private Pane pane;
    private HashMap<StackPane, Soldier> soldierHashMap;

    public UnitPane(String username, int X, int Y) {
        Client.getData();
        UnitMenuController.init(MainMenuGUIController.getUser(), X, Y);
        this.username = username;
        this.X = X;
        this.Y = Y;
        initPane();
        initButton();
    }

    public static Background getBackground(double width, double height) {
        String path = "/phase2-assets/background/backgrounds/Backgrounds For Menus - yac/z3qbm9.jpg";
        BackgroundImage backgroundImage =
                getEditBackgroundImage(java.util.Objects.requireNonNull(ShowProfileMenu.class.getResource(path)),
                                       new BackgroundSize(width,
                                                          height,
                                                          false,
                                                          false,
                                                          false,
                                                          false)
                );


        return new Background(backgroundImage);
    }

    public static BackgroundImage getEditBackgroundImage(URL Resource, BackgroundSize backgroundSize) {
        assert Resource != null;
        Image image = new Image(Resource.toExternalForm());

        return new BackgroundImage(image,
                                   BackgroundRepeat.NO_REPEAT,
                                   BackgroundRepeat.NO_REPEAT,
                                   BackgroundPosition.CENTER,
                                   backgroundSize);
    }

    private static HBox gethBox(double width, double height, int v) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefSize(width, height);
        hBox.setSpacing(v);
        return hBox;
    }

    private static Button getButton(String text, double height, EventHandler<ActionEvent> eventHandler) {
        return getButtonUtil(text, height, eventHandler);
    }

    public static Button getButtonUtil(String text, double height, EventHandler<ActionEvent> eventHandler) {
        Image image;
        image = new Image(java.util.Objects.requireNonNull(
                MainMenuGUIController.class.getResource("/images/Buttons/bg.jpg")).toExternalForm());
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

        initButton(button);

        return button;
    }

    public static void initButton(Button button) {
        button.setOnMouseEntered(mouseEvent -> {
            button.setStyle("-fx-text-fill: white");
        });
        button.setOnMouseExited(mouseEvent -> {
            button.setStyle("-fx-text-fill: yellow");
        });
    }

    public static Button getLongButtonUtil(String text, double height, EventHandler<ActionEvent> eventHandler) {
        Image image;
        image = new Image(java.util.Objects.requireNonNull(
                MainMenuGUIController.class.getResource("/images/Buttons/bg.jpg")).toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              new BackgroundSize(
                                                                      image.getWidth() / image.getHeight() * height /
                                                                              1.5,
                                                                      height,
                                                                      false, false, false, false
                                                              ));

        Button button = new Button(text);
        button.setBackground(new Background(backgroundImage));
        button.setPrefSize(image.getWidth() / image.getHeight() * height / 1.5, height);
        button.setFont(new Font("Bell MT", 18));
        button.setStyle("-fx-text-fill: yellow");
        button.setAlignment(Pos.CENTER);
        button.setOnAction(eventHandler);

        initButton(button);

        return button;
    }

    public static Text getText(String string) {
        Text text = new Text(string);
        text.setFont(new Font("System", 15));

        return text;
    }

    public Unit getUnit() {
        return Users.getUser(username).getGovernment().getMap().getUnitByXY(X, Y);
    }

    private void initButton() {
        Button backButton = getBackButton(UnitMenuController::showMainMenu);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
    }

    private void initPane() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        pane = new Pane();
        pane.setPrefSize(width, height);

        pane.setBackground(getBackground(width, height));
        StackPane stackPaneUnit = getStackPaneUnit(width * 0.8, height * 0.9);
        pane.getChildren().add(stackPaneUnit);
        stackPaneUnit.setLayoutX(width * 0.1);
        stackPaneUnit.setLayoutY(height * 0.05);
    }

    private StackPane getStackPaneUnit(double width, double height) {
        StackPane stackPane = new StackPane();

        stackPane.setPrefSize(width, height);

        stackPane.getChildren().addAll(getDataBackgroundImage(height, width),
                                       getUnitVBox(width * 0.7, height * 0.75));

        return stackPane;
    }

    private HBox getUnitVBox(double width, double height) {
        HBox vBox = new HBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(width, height);
        vBox.setMaxSize(width, height);

        vBox.getChildren().addAll(getUnitData(width * 0.8, height),
                                  getActionsVBox(width * 0.8, height));
        return vBox;
    }

    private VBox getActionsVBox(double width, double height) {
        VBox vBox = new VBox();
        vBox.setPrefSize(width, height);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        vBox.getChildren().addAll(
                getButton("Change texture", height * 0.1, UnitMenuController::changeTexture),
                getButton("Repair building", height * 0.1, UnitMenuController::repairBuilding),
                getButton("Drop Tree", height * 0.1, UnitMenuController::dropTree),
                getButton("Drop Rock", height * 0.1, UnitMenuController::dropRock),
                getButton("Drop Unit", height * 0.1, UnitMenuController::dropUnit),
                getButton("Clear", height * 0.1, UnitMenuController::clear)
        );
        if (getUnit().hasBuilding() && getUnit().hasObjectType(BuildingType.MARKET))
            vBox.getChildren().add(getButton("Shop Menu", height * 0.1, UnitMenuController::shop));

        return vBox;
    }

    private VBox getUnitData(double width, double height) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(width, height);
        vBox.setMaxSize(width, height);
        vBox.setSpacing(15);

        VBox data;
        if (getUnit().hasBuilding()) {
            data = new VBox(getHealth(width * 0.5, height * 0.05),
                            getTextureHBox(width * 0.5, height * 0.1),
                            getOwnerHBox(width * 0.5, height * 0.1),
                            getBuildingHBox(width * 0.5, height * 0.1),
                            getPeopleHBox(width * 0.5, height * 0.1));

            if (getUnit().getBuilding() instanceof Storage)
                data.getChildren().add(getPrevStorage(width * 0.5, height * 0.05));
        } else {
            data = new VBox(getTextureHBox(width * 0.5, height * 0.1),
                            getOwnerHBox(width * 0.5, height * 0.1),
                            getBuildingHBox(width * 0.5, height * 0.1),
                            getPeopleHBox(width * 0.5, height * 0.1));
        }

        data.setAlignment(Pos.CENTER);
        data.setMaxSize(width * 0.5, height * 0.5);
        data.setSpacing(10);

        vBox.getChildren().addAll(getUnitImage(height * 0.4),
                                  data);
        return vBox;
    }

    private HBox getPrevStorage(double width, double height) {
        HBox hBox = gethBox(width, height, 10);
        Storage storage = (Storage) getUnit().getBuilding();
        hBox.getChildren().add(getText("Next storage: " + (
                storage.getNextStorage() == null ? "Null" :
                        "{%d, %d}".formatted(storage.getNextStorage().getX(), storage.getNextStorage().getY()))));
        hBox.getChildren().add(getText(
                "Capacity: " + storage.getCurrentCapacity() + " of " + storage.getMaximumCapacity()));
        return hBox;
    }

    private HBox getHealth(double width, double height) {
        HBox hBox = new HBox();

        ProgressBar healthBar = new ProgressBar(
                (double) getUnit().getBuilding().getHp() / (double) getUnit().getBuilding().getMaxHp());
        Text text = getText(String.valueOf(getUnit().getBuilding().getHp()));

        healthBar.setPrefSize(width * 0.8, height);
        hBox.getChildren().addAll(healthBar, text);
        hBox.setPrefSize(width, height);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        return hBox;
    }

    private HBox getBuildingHBox(double width, double height) {
        HBox hBox = gethBox(width, height, 15);

        if (getUnit().hasBuilding()) {
            Building building = getUnit().getBuilding();
            ImageView imageView = new ImageView(building.getImage());
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(height);

            hBox.getChildren().addAll(imageView, getText(building.getType().getType()));
        } else
            hBox.getChildren().add(getText("No buildings are in this unit"));

        return hBox;
    }

    private HBox getOwnerHBox(double width, double height) {
        HBox hBox = gethBox(width, height, 15);

        if (getUnit().hasBuilding())
            hBox.getChildren().addAll(getOwnerAvatar(height),
                                      getUserColor(height, getUnit().getOwner()),
                                      getText(getUnit().getOwner().getNickName()));
        else
            hBox.getChildren().add(getText("This tile belongs to no one."));

        return hBox;
    }

    private Rectangle getUserColor(double height, User user) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(height);
        rectangle.setWidth(height);

        rectangle.setFill(user.getColor().toColor());

        return rectangle;
    }

    private ImageView getOwnerAvatar(double height) {
        ImageView imageView = new ImageView(getUnit().getOwner().getAvatar().toExternalForm());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);

        return imageView;
    }

    private HBox getTextureHBox(double width, double height) {
        HBox hBox = gethBox(width, height, 15);

        hBox.getChildren().addAll(getTileBackground(height, height),
                                  getText(getUnit().getTexture().getType()));

        return hBox;
    }

    private Node getPeopleHBox(double width, double height) {
        HBox hBox = gethBox(width, height, 5);
        hBox.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(hBox);
        scrollPane.setPrefSize(width, height);
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        soldierHashMap = new HashMap<>();

        for (Objects object : getUnit().getObjects()) {
            if (object instanceof Person person) {
                StackPane personStackPane = getPersonStackPane(height * 0.8, person);
                hBox.getChildren().add(personStackPane);
                if (person instanceof Soldier soldier) {
                    soldierHashMap.put(personStackPane, soldier);
                    personStackPane.setOnMouseClicked(this::handleSoldier);
                }
            }

        }

        if (hBox.getChildren().isEmpty()) {
            hBox.getChildren().add(getText("No people in this unit"));
            return hBox;
        }

        return scrollPane;
    }

    private StackPane getPersonStackPane(double height, Person person) {
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(height, height);

        ImageView imageView = new ImageView(person.getImage());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);

        stackPane.getChildren().addAll(getUserColor(height, person.getOwner()), imageView);
        return stackPane;
    }

    private VBox getUnitImage(double width) {
        UnitGroup unitGroup = new UnitGroup(getUnit(), width / 2, width);
        VBox vBox = new VBox();
        Group group = new Group();

        Group wallpaperGroup = unitGroup.getWallpaperGroup();
        Group people = unitGroup.getPeople();
        Group building = unitGroup.getBuilding();
        Group objectsGroup = unitGroup.getObjectsGroup();

        if (wallpaperGroup != null) group.getChildren().add(wallpaperGroup);
        if (people != null) group.getChildren().add(people);
        if (building != null) {
            group.getChildren().add(building);
            building.setLayoutY(building.getLayoutY() - width / 2);
        }
        if (objectsGroup != null) {
            group.getChildren().add(objectsGroup);
            objectsGroup.setLayoutY(objectsGroup.getLayoutY() - width / 2);
        }

        if (group.getLayoutBounds().getHeight() > width) {
            group.setScaleX(width / group.getLayoutBounds().getHeight());
            group.setScaleY(width / group.getLayoutBounds().getHeight());
        }

        vBox.getChildren().add(group);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private ImageView getTileBackground(double tileHeight, double tileWidth) {
        ImageView imageView = new ImageView(getUnit().getTexture().getImage());
        imageView.setFitHeight(tileHeight);
        imageView.setFitWidth(tileWidth);

        return imageView;
    }

    public Pane getPane() {
        return pane;
    }

    private void handleSoldier(MouseEvent mouseEvent) {
        StackPane stackPane = (StackPane) mouseEvent.getPickResult().getIntersectedNode().getParent();
        SoldierMenuController soldierMenuController =
                SoldierMenuController.getController(soldierHashMap.get(stackPane));
        soldierMenuController.showSoldierMenu(null);
    }
}

