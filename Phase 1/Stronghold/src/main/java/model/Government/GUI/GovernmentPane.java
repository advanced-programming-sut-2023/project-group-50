package model.Government.GUI;

import controller.GUIControllers.GovernmentMenuGUIController;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import model.Government.Government;
import model.Map.GUI.MiniMap.MiniMap;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingSet;
import model.ObjectsPackage.Buildings.BuildingType;
import model.RandomGenerator.RandomBuilding;
import view.show.ProfileMenu.ShowProfileMenu;

import java.net.URL;

import static controller.GUIControllers.ProfileMenuGUIController.getEditBackgroundImage;

public class GovernmentPane {
    public static Government government;
    public static BuildingType selectedBuilding;
    private static Pane pane;
    private static Pane mainPane;

    public static Pane getPane(Government government, Pane mainPane) {
        GovernmentPane.mainPane = mainPane;
        GovernmentPane.government = government;
        double width = Screen.getPrimary().getBounds().getWidth();
        pane = new Pane();
        pane.setPrefSize(width, 200);

        Region shadow = shadow(width - 50, 200 * 0.7);
        pane.getChildren().add(shadow);
        shadow.setLayoutY(200 * (1 - 0.7) / 2);
        shadow.setLayoutX(25);

        pane.getChildren().add(getHBox(width, 200, government));
        Pane miniMap = new MiniMap(government.getMap()).getMiniMap(200, 200);
        addChildren(width, pane, miniMap);
        return pane;
    }

    private static Region shadow(double width, double height) {
        Region region = new Region();
        region.setPrefSize(width, height);

        region.setStyle("\n" +
                                "    -fx-border-color: black;\n" +
                                "    -fx-border-style: solid;\n" +
                                "    -fx-border-width: 2.5;\n" +
                                "    -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 20, 0.9, 0, 0);\n");
        return region;
    }

    private static void addChildren(double width, Pane pane, Pane miniMap) {
        Text gov = new Text("Government");
        Text buildings = new Text("Buildings");

        gov.rotateProperty().set(90);
        buildings.rotateProperty().set(-90);
        gov.setFont(new Font("Old English Text MT", 20));
        buildings.setFont(new Font("Old English Text MT", 20));

        pane.getChildren().addAll(miniMap, gov, buildings);
        miniMap.setLayoutX(width / 2 - 100);
        gov.setLayoutX(width - 80);
        buildings.setLayoutX(-10);
        gov.setLayoutY(100);
        buildings.setLayoutY(100);
    }

    private static HBox getHBox(double width, double height, Government government) {
        HBox hBox = new HBox();
        hBox.setPrefSize(width, height);

        hBox.getChildren().addAll(getBuildings(width / 2, height, government),
                                  getGovernmentData(width / 2, height, government));

        hBox.setSpacing(-10);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    private static HBox getBuildings(double width, double height, Government government) {
        HBox hBox = new HBox();
        hBox.setPrefSize(width, height);
        hBox.setBackground(getDataBackground(width, height));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(15);

        TabPane tabPane = getTabPane(width, height);

        hBox.getChildren().add(tabPane);
        tabPane.setTranslateX(tabPane.getTranslateX() + 45);

        return hBox;
    }

    private static TabPane getTabPane(double width, double height) {
        double regionHeight = height * 0.6;
        double regionWidth = width - 150;

        TabPane tabPane = new TabPane();
        getBuildingTabs(tabPane, regionHeight);
        tabPane.setMaxSize(regionWidth, regionHeight);
        tabPane.setPrefSize(regionWidth, regionHeight);
        return tabPane;
    }

    private static void getBuildingTabs(TabPane tabPane, double height) {
        for (BuildingSet buildingSet : BuildingSet.values())
            tabPane.getTabs().add(getTab(buildingSet.getName(), buildingSet, height));
    }

    private static Tab getTab(String string, BuildingSet buildingSet, double height) {
        Tab tab = new Tab(string);
        ScrollPane scrollPane = new ScrollPane();
        HBox hBox = getBuildingsTab(buildingSet, height - 50);
        scrollPane.setContent(hBox);
        tab.setContent(scrollPane);
        scrollPane.setPrefWidth(scrollPane.getWidth() + 200);

        tab.setStyle("-fx-background-color: rgb(0, 0, 0, 0); -fx-font-weight: bold");

        return tab;
    }

    private static HBox getBuildingsTab(BuildingSet buildingSet, double height) {
        HBox hBox = new HBox();
        hBox.setMaxHeight(height);
        hBox.setPrefHeight(height);

        for (BuildingType buildingType : buildingSet.getBuildingTypes())
            hBox.getChildren().add(getBuildingVBox(buildingType, height));

        hBox.getChildren().add(getBuildingVBox(null, height));

        hBox.setAlignment(Pos.BOTTOM_LEFT);
        hBox.setSpacing(15);
        return hBox;
    }

    private static Node getBuildingVBox(BuildingType buildingType, double height) {
        Text text = new Text(buildingType == null ? "" : buildingType.getType());
        Image image;
        if (buildingType == null) {
            String building = RandomBuilding.getBuilding(null);
            URL url = Building.class.getResource("/phase2-assets/" + building);
            image = new Image(url.toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(height * 0.65);
            imageView.setPreserveRatio(true);
            VBox vBox = new VBox(imageView, text);
            vBox.setSpacing(5);
            vBox.setAlignment(Pos.BOTTOM_CENTER);
            return vBox;
        } else
            image = buildingType.getImage();

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height * 0.65);
        imageView.setPreserveRatio(true);

        VBox vBox = new VBox(imageView, text);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.BOTTOM_CENTER);

        imageView.setId(buildingType.name());

        imageView.setOnMouseEntered(GovernmentPane::mouseEntered);
        imageView.setOnMouseExited(GovernmentPane::mouseExited);
        imageView.setOnMouseClicked(GovernmentPane::mouseClicked);
        return vBox;
    }

    private static void mouseClicked(MouseEvent mouseEvent) {
        BuildingType buildingType = getBuildingTypeOnMouse(mouseEvent);

        if (government.canAfford(buildingType)) {
            selectedBuilding = buildingType;
            mainPane.setCursor(getCursor(buildingType));
        }
    }

    private static BuildingType getBuildingTypeOnMouse(MouseEvent mouseEvent) {
        return BuildingType.getByName(mouseEvent.getPickResult().getIntersectedNode().getId());
    }

    private static void mouseEntered(MouseEvent mouseEvent) {
        BuildingType buildingType = getBuildingTypeOnMouse(mouseEvent);
        mainPane.setCursor(getCursor(buildingType));
    }

    private static void mouseExited(MouseEvent mouseEvent) {
        mainPane.setCursor(selectedBuilding == null ? Cursor.DEFAULT : getCursor(selectedBuilding));
    }

    private static Cursor getCursor(BuildingType buildingType) {
        if (government.canAfford(buildingType) && selectedBuilding == null)
            return Cursor.DEFAULT;

        Image image = selectedBuilding != null ? getChosenBuildingCursor() : getNotAvailableImage();

        Dimension2D dim = ImageCursor.getBestSize(Screen.getPrimary().getBounds().getWidth(),
                                                  Screen.getPrimary().getBounds().getHeight());

        return new ImageCursor(image, dim.getWidth(), dim.getHeight());
    }

    public static Image getChosenBuildingCursor() {
        return new Image(GovernmentPane.class.getResource("/images/Cursors/selected.png").toExternalForm());
    }

    public static Image getUnavailableTileCursor() {
        return new Image(GovernmentPane.class.getResource("/images/Cursors/unavailable.png").toExternalForm());
    }

    private static Image getNotAvailableImage() {
        return new Image(GovernmentPane.class.getResource("/images/Cursors/not.png").toExternalForm());
    }

    private static void selectedBuilding(MouseEvent mouseEvent) {
        String img = mouseEvent.getPickResult().getIntersectedNode().getId();
        System.out.println(img);
    }

    private static HBox getGovernmentData(double width, double height, Government government) {
        HBox hBox = new HBox();
        hBox.setPrefSize(width, height);
        hBox.setBackground(getDataBackground(width, height));
        hBox.setAlignment(Pos.CENTER);

        HBox items = gethBox(width - 100, height, government);
        hBox.getChildren().add(items);

        return hBox;
    }

    private static HBox gethBox(double width, double height, Government government) {
        HBox items = new HBox();
        items.setPrefSize(width - 100, height - 50);
        items.getChildren().add(getPopularity((width - 100) / 4, height - 50, government));
        items.getChildren().add(getPopulation((width - 100) / 4, height - 50, government));
        items.getChildren().add(getRates((width - 100) / 4, height - 50, government));
        items.setAlignment(Pos.CENTER);
        items.setSpacing(15);
        return items;
    }

    private static HBox getRates(double width, double height, Government government) {
        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER_LEFT);
        vBox1.setSpacing(5);
        vBox1.setPrefSize(width / 2, height);

        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER_LEFT);
        vBox2.setSpacing(5);
        vBox2.setPrefSize(width / 2, height);

        double size = (height * 0.7 - vBox1.getSpacing() * 2) / 3;

        HBox coins = getRateHbox("%.2f".formatted(government.getCoins()), IconName.COIN, size);
        HBox food = getRateHbox(String.valueOf(government.getRateFood()), IconName.FOOD, size);
        HBox resources = getRateHbox(String.valueOf(government.getResources()), IconName.RESOURCES, size);
        HBox fear = getRateHbox(String.valueOf(government.getFearRate()), IconName.FEAR, size);
        HBox religion = getRateHbox(String.valueOf(government.checkReligionPopularity()), IconName.RELIGION, size);
        HBox tax = getRateHbox(String.valueOf(government.getTaxRate()), IconName.TAX, size);

        vBox1.getChildren().addAll(coins, fear, religion);
        vBox2.getChildren().addAll(food, resources, tax);

        HBox hBox = new HBox();
        hBox.setPrefSize(width, height);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(vBox1, vBox2);

        return hBox;
    }

    private static HBox getRateHbox(String string, IconName iconName, double size) {
        ImageView imageView = iconName.getImage(size);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        Text text = new Text(string);
        hBox.getChildren().addAll(imageView, text);
        return hBox;
    }

    private static VBox getPopulation(double width, double height, Government government) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPrefSize(width, height);

        VBox pop = getvBox("Current Population", government.getPopulation());
        VBox maxPop = getvBox("Maximum Population", government.getMaxPopulation());

        vBox.getChildren().addAll(maxPop, pop);

        return vBox;
    }

    private static VBox getvBox(String name, int value) {
        VBox pop = new VBox();
        Text top = new Text(name);
        top.setStyle("-fx-font: 15 System; -fx-font-weight: bold");
        Text bot = new Text(String.valueOf(value));
        bot.setStyle("-fx-font: 15 System");
        pop.setAlignment(Pos.CENTER);
        pop.setSpacing(5);
        pop.getChildren().addAll(top, bot);
        return pop;
    }

    private static VBox getPopularity(double width, double height, Government government) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        vBox.setPrefSize(width, height);

        int popularity = government.getPopularity();
        double size = Math.min(width, height);
        ImageView popularityImage = GovernmentMenuGUIController.getPopularityImage(popularity, size);

        StackPane stackPane = getPopularityStackPane(popularity, size, popularityImage);
        Text popularityName = new Text("Popularity");
        popularityName.setStyle("-fx-font: 15 System; -fx-font-weight: bold");

        vBox.getChildren().addAll(stackPane, popularityName);

        return vBox;
    }

    private static StackPane getPopularityStackPane(int popularity, double size, ImageView popularityImage) {
        Circle circle = new Circle();
        circle.setRadius(size / 4);
        circle.setFill(new ImagePattern(popularityImage.getImage()));
        Text text = new Text(String.valueOf(popularity));
        text.setStyle("-fx-font: 20 System; -fx-font-weight: bold");
        return new StackPane(circle, text);
    }

    private static Background getDataBackground(double width, double height) {
        String path = "/images/background/scroll.png";
        BackgroundImage backgroundImage =
                getEditBackgroundImage(ShowProfileMenu.class.getResource(path), new BackgroundSize(width,
                                                                                                   height,
                                                                                                   false,
                                                                                                   false,
                                                                                                   false,
                                                                                                   false)
                );


        return new Background(backgroundImage);
    }
}
