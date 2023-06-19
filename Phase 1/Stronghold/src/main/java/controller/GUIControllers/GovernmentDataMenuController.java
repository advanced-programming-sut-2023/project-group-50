package controller.GUIControllers;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import model.Government.GUI.IconName;
import model.Government.Government;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.People.NonSoldier.Job;
import model.ObjectsPackage.People.Soldier.SoldierName;
import model.ObjectsPackage.Resource;
import model.ObjectsPackage.Weapons.WeaponName;

import java.util.Objects;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;
import static model.Map.GUI.Unit.UnitPane.getBackground;

public class GovernmentDataMenuController {
    private static Government government;
    private Pane pane;

    public GovernmentDataMenuController() {
        initPane();
    }

    public static void init(Government government) {
        GovernmentDataMenuController.government = government;
    }

    private static ScrollPane getScrollPane(double width, double height, VBox vBox) {
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setPrefSize(width, height);
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scrollPane;
    }

    public Pane getPane() {
        return pane;
    }

    private void initPane() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        pane = new Pane();
        pane.setPrefSize(width, height);

        pane.setBackground(getBackground(width, height));
        StackPane stackPaneUnit = getGovernmentData(width * 0.8, height * 0.9);
        pane.getChildren().addAll(stackPaneUnit);
        stackPaneUnit.setLayoutX(width * 0.1);
        stackPaneUnit.setLayoutY(height * 0.05);

        initBackButton();
    }

    private void initBackButton() {
        Button backButton = getBackButton(UnitMenuController::showMainMenu);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
    }

    private StackPane getGovernmentData(double width, double height) {
        return new StackPane(ProfileMenuGUIController.getDataBackgroundImage(height, width),
                             getData(width * 0.7, height * 0.75));
    }

    private GridPane getData(double width, double height) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        gridPane.add(getResourcePane(width / 2, height / 2), 0, 0);
        gridPane.add(getBuildingsPane(width / 2, height / 2), 0, 1);
        gridPane.add(getPeoplePane(width / 2, height / 2), 1, 0);
        gridPane.add(getDataPane(width / 2, height / 2), 1, 1);
        gridPane.setPrefSize(width, height);
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    private ScrollPane getDataPane(double width, double height) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPrefWidth(width);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().add(getBoldText("Data", width));
        for (IconName iconName : IconName.values())
            vBox.getChildren().add(getDataHBox(iconName, width));
        vBox.getChildren().add(getBoldText("Palace", width));
        vBox.getChildren().add(getPalaceHBox(width));
        vBox.getChildren().add(getLordHBox(width));
        vBox.getChildren().add(getLadyHBox(width));

        return getScrollPane(width, height, vBox);
    }

    private HBox getLordHBox(double width) {
        ImageView imageView = new ImageView(SoldierName.THE_LORD.getImage());
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        StackPane image = new StackPane(imageView);
        image.setPrefSize(50, 50);

        Text text = getText("Lord's health: " + government.getLord().getHp());

        HBox hBox = new HBox(image, text);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setMaxWidth(width - 50);
        return hBox;
    }

    private HBox getLadyHBox(double width) {
        ImageView imageView = new ImageView(Job.LADY.getImage());
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        StackPane image = new StackPane(imageView);
        image.setPrefSize(50, 50);

        Text text = getText("Lady's health: " + government.getLadyState());

        HBox hBox = new HBox(image, text);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setMaxWidth(width - 50);
        return hBox;
    }

    private HBox getPalaceHBox(double width) {
        ImageView imageView = new ImageView(BuildingType.PALACE.getImage());
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);

        Rectangle rectangle = new Rectangle(50, 50);
        rectangle.setFill(government.getColor());

        StackPane image = new StackPane(rectangle, imageView);
        image.setPrefSize(50, 50);

        Text text = getText("Palace: " + government.getPalaceState());

        HBox hBox = new HBox(image, text);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setMaxWidth(width - 50);
        return hBox;
    }

    private ScrollPane getPeoplePane(double width, double height) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPrefWidth(width);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().add(getBoldText("Soldiers", width));
        for (SoldierName soldierName : SoldierName.values())
            vBox.getChildren().add(getSoldierHBox(soldierName, width));
        vBox.getChildren().add(getBoldText("NonSoldiers", width));
        for (Job job : Job.values())
            vBox.getChildren().add(getNonSoldierHBox(job, width));

        return getScrollPane(width, height, vBox);
    }

    private HBox getSoldierHBox(SoldierName soldierName, double width) {
        ImageView imageView = new ImageView(soldierName.getImage());
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        StackPane image = new StackPane(imageView);
        image.setPrefSize(50, 50);

        Text text = getText(soldierName.getType() + ": " + government.getSoldierCount(soldierName));

        HBox hBox = new HBox(image, text);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setMaxWidth(width - 50);
        return hBox;
    }

    private HBox getDataHBox(IconName iconName, double width) {
        ImageView imageView = iconName.getImage(50);

        Text text = getText(iconName.getType() + ": " + government.getIconData(iconName));

        HBox hBox = new HBox(imageView, text);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setMaxWidth(width - 50);
        return hBox;
    }

    private HBox getNonSoldierHBox(Job job, double width) {
        ImageView imageView = new ImageView(job.getImage());
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        StackPane image = new StackPane(imageView);
        image.setPrefSize(50, 50);

        Text text = getText(job.getType() + ": " + government.getNonSoldierCount(job));

        HBox hBox = new HBox(image, text);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setMaxWidth(width - 50);
        return hBox;
    }

    private ScrollPane getBuildingsPane(double width, double height) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPrefWidth(width);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().add(getBoldText("Buildings", width));
        for (BuildingType buildingType : BuildingType.values())
            vBox.getChildren().add(getBuildingHBox(buildingType, width));

        return getScrollPane(width, height, vBox);
    }

    private HBox getBuildingHBox(BuildingType buildingType, double width) {
        ImageView imageView = new ImageView(buildingType.getImage());
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Text text = getText(buildingType.getType() + ": " + government.getBuildingCount(buildingType));

        HBox hBox = new HBox(imageView, text);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setMaxWidth(width - 50);
        return hBox;
    }

    private ScrollPane getResourcePane(double width, double height) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPrefWidth(width);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().add(getBoldText("Resources", width));
        vBox.getChildren().add(getCoins(width));
        for (Resource resource : Resource.values())
            vBox.getChildren().add(getResourceHBox(resource, width));

        vBox.getChildren().add(getBoldText("Weapons", width));
        for (WeaponName weaponName : WeaponName.values())
            vBox.getChildren().add(getWeaponHBox(weaponName, width));

        return getScrollPane(width, height, vBox);
    }

    private HBox getBoldText(String string, double width) {
        Text text = getText(string);
        text.setStyle("-fx-font: 25 Algerian; -fx-font-weight: bold");
        HBox hBox = new HBox(text);
        hBox.setPrefSize(width, 100);
        hBox.setAlignment(Pos.CENTER);
        hBox.setBackground(new Background(new BackgroundImage(
                new Image(Government.class.getResource("/images/background/scroll.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        width, 100,
                        false, false, false, false
                )
        )));
        return hBox;
    }

    private HBox getWeaponHBox(WeaponName weaponName, double width) {
        ImageView imageView = new ImageView(weaponName.getImage());
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Text text = getText(weaponName.getName() + ": " + government.getWeaponAmount(weaponName));

        HBox hBox = new HBox(imageView, text);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setMaxWidth(width - 50);
        return hBox;
    }

    private HBox getCoins(double width) {
        ImageView imageView = new ImageView(Objects.requireNonNull(
                Resource.class.getResource("/images/Resources/Coins.png")).toExternalForm()
        );
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Text text = getText("Coins: " + government.getCoins());

        HBox hBox = new HBox(imageView, text);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setMaxWidth(width - 50);
        return hBox;
    }

    private HBox getResourceHBox(Resource resource, double width) {
        ImageView imageView = new ImageView(resource.getImage());
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Text text = getText(resource.getName() + ": " + government.getResourceAmount(resource) + " (rate: " +
                                    government.getResourceRate(resource) + ")");

        HBox hBox = new HBox(imageView, text);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setMaxWidth(width - 50);
        return hBox;
    }

    private Text getText(String string) {
        Text text = new Text(string);
        text.setFont(new Font("System", 15));

        return text;
    }
}
