package model.Map.GUI.Unit;

import controller.GUIControllers.MultiUnitMenuController;
import controller.GUIControllers.SoldierMenuController.SoldierMenuController;
import controller.GUIControllers.UnitMenuController;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import model.Map.GUI.MapPane.MapPane;
import model.Map.GroundType;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.People.Soldier.Soldier;
import view.show.ProfileMenu.ShowProfileMenu;

import java.net.URL;
import java.util.HashMap;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;
import static controller.GUIControllers.ProfileMenuGUIController.getDataBackgroundImage;

public class MultiUnitPane {
    private final String username;
    private final int xFrom;
    private final int xTo;
    private final int yFrom;
    private final int yTo;
    private Pane pane;
    private HashMap<StackPane, Soldier> soldierHashMap;

    public MultiUnitPane(int xFrom, int xTo, int yFrom, int yTo, String username) {
        MultiUnitMenuController.init(xFrom, xTo, yFrom, yTo, username);
        this.username = username;
        this.xFrom = xFrom;
        this.xTo = xTo;
        this.yFrom = yFrom;
        this.yTo = yTo;
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
        return UnitPane.getButtonUtil(text, height, eventHandler);
    }

    public static Text getText(String string) {
        Text text = new Text(string);
        text.setFont(new Font("System", 15));

        return text;
    }

    private static VBox getBuildingVBox(double height, Building building) {
        ImageView imageView = new ImageView(building.getImage());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height * 0.5);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        vBox.getChildren().addAll(imageView,
                                  getText(building.getType().getType()),
                                  getText(building.getOwner().getUserName()));
        return vBox;
    }

    private static ScrollPane getScrollPane(double width, HBox hBox) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(hBox);
        scrollPane.setPrefWidth(width);
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scrollPane;
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
                getButton("Change Texture", height * 0.1, MultiUnitMenuController::changeTexture),
                getButton("Clear", height * 0.1, MultiUnitMenuController::clear)
        );

        return vBox;
    }

    private VBox getUnitData(double width, double height) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(width, height);
        vBox.setMaxSize(width, height);
        vBox.setSpacing(15);

        VBox data = new VBox(getTextureHBox(width * 0.5, height * 0.1),
                             getOwnerHBox(width * 0.5, height * 0.1),
                             getBuildingHBox(width * 0.5, height * 0.1),
                             getPeopleHBox(width * 0.5, height * 0.1));


        data.setAlignment(Pos.CENTER);
        data.setMaxSize(width * 0.5, height * 0.5);
        data.setSpacing(10);

        vBox.getChildren().addAll(getUnitImage(height * 0.4),
                                  data);
        return vBox;
    }

    private HBox getBuildingHBox(double width, double height) {
        HBox hBox = gethBox(width, height, 15);

        if (MultiUnitMenuController.hasBuilding()) {
            for (Building building : MultiUnitMenuController.getBuildings()) {
                VBox vBox = getBuildingVBox(height, building);
                hBox.getChildren().add(vBox);
            }

            ScrollPane scrollPane = getScrollPane(width, hBox);
            return new HBox(scrollPane);
        } else
            hBox.getChildren().add(getText("No buildings are in this unit"));

        return hBox;
    }

    private HBox getOwnerHBox(double width, double height) {
        HBox hBox = gethBox(width, height, 15);

        if (MultiUnitMenuController.hasOwner()) {
            for (User user : MultiUnitMenuController.getOwners()) {
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                vBox.setSpacing(5);
                vBox.getChildren().addAll(getOwnerAvatar(user, height),
                                          getUserColor(10, height, user),
                                          getText(user.getUserName()));

                hBox.getChildren().add(vBox);
            }

            ScrollPane scrollPane = getScrollPane(width, hBox);
            return new HBox(scrollPane);
        } else
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

    private Rectangle getUserColor(double height, double width, User user) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(height);
        rectangle.setWidth(width);

        rectangle.setFill(user.getColor().toColor());

        return rectangle;
    }

    private ImageView getOwnerAvatar(User user, double height) {
        ImageView imageView = new ImageView(user.getAvatar().toExternalForm());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);

        return imageView;
    }

    private HBox getTextureHBox(double width, double height) {
        HBox hBox = gethBox(width, height, 15);

        for (GroundType texture : MultiUnitMenuController.getTextures()) {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(10);
            vBox.getChildren().addAll(getTileBackground(texture, height, height),
                                      getText(texture.getType()));

            hBox.getChildren().add(vBox);
        }

        ScrollPane scrollPane = getScrollPane(width, hBox);
        return new HBox(scrollPane);
    }

    private Node getPeopleHBox(double width, double height) {
        HBox hBox = gethBox(width, height, 5);
        hBox.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = getScrollPane(width, hBox);

        soldierHashMap = new HashMap<>();

        for (Person person : MultiUnitMenuController.getPeople()) {
            StackPane personStackPane = getPersonStackPane(height * 0.8, person);
            hBox.getChildren().add(personStackPane);
            if (person instanceof Soldier soldier) {
                soldierHashMap.put(personStackPane, soldier);
                personStackPane.setOnMouseClicked(this::handleSoldier);
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
        Pane smallPane = MapPane.getSmallPane(Users.getUser(username).getGovernment().getMap(),
                                              xFrom, yFrom, xTo, yTo, width, width);
        VBox vBox = new VBox(smallPane);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(width, width);
        return vBox;
    }

    private ImageView getTileBackground(GroundType texture, double tileHeight, double tileWidth) {
        ImageView imageView = new ImageView(texture.getImage());
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

