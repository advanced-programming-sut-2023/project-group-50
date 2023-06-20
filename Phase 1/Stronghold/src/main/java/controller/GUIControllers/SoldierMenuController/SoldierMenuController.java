package controller.GUIControllers.SoldierMenuController;

import controller.GUIControllers.MainMenuGUIController;
import controller.GUIControllers.ProfileMenuGUIController;
import controller.GUIControllers.UnitMenuController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import model.Map.GUI.Unit.UnitPane;
import model.Map.Map;
import model.ObjectsPackage.People.Soldier.*;
import view.show.MainMenu.MainMenu;
import view.show.SoldierMenu.AttackMenu;
import view.show.SoldierMenu.MoveMenu;
import view.show.SoldierMenu.PatrolMenu;
import view.show.SoldierMenu.SoldierMenu;

import java.util.Objects;

public abstract class SoldierMenuController {
    protected Soldier soldier;
    protected double buttonHeight;
    private Pane pane;
    private VBox buttons;

    public SoldierMenuController(Soldier soldier) {
        this.soldier = soldier;
        initPane();
    }

    private static ImageView getArmourType(double height, ArmourType armourType) {
        Image image1 = armourType.getImage();
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitHeight(height);
        imageView1.setPreserveRatio(true);
        return imageView1;
    }

    private static HBox gethBox(double width, double height) {
        HBox hBox = new HBox();
        hBox.setPrefSize(width, height);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    public static Background getBackground() {
        double height = Screen.getPrimary().getBounds().getHeight();
        double width = Screen.getPrimary().getBounds().getWidth();

        Image image = new Image(Objects.requireNonNull(
                Soldier.class.getResource("/images/background/soldierMenu.jpg")).toExternalForm()
        );
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              new BackgroundSize(width,
                                                                                 height,
                                                                                 false,
                                                                                 false,
                                                                                 false,
                                                                                 false
                                                              )
        );

        return new Background(backgroundImage);
    }

    public static boolean isInvalid(int xFrom, int xTo, int yFrom, int yTo) {
        Map map = MainMenuGUIController.getUser().getGovernment().getMap();
        return !map.isValid(xFrom, yFrom) || !map.isValid(xTo, yTo);
    }

    public static SoldierMenuController getController(Soldier soldier) {
        if (soldier instanceof Infantry infantry) return new InfantryMenuController(infantry);
        if (soldier instanceof Archer archer) return new ArcherMenuController(archer);
        if (soldier instanceof Tunneler tunneler) return new TunnelerMenuController(tunneler);
        if (soldier instanceof Engineer engineer) return new EngineerMenuController(engineer);

        throw new RuntimeException();
    }

    public SoldierMenuController getThis() {
        return this;
    }

    private void initPane() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        pane = new Pane();
        pane.setPrefSize(width, height);

        pane.setBackground(getBackground());
        StackPane stackPaneUnit = getStackPaneUnit(width * 0.8, height * 0.9);
        pane.getChildren().add(stackPaneUnit);
        stackPaneUnit.setLayoutX(width * 0.1);
        stackPaneUnit.setLayoutY(height * 0.05);

        initBackButton();
    }

    private StackPane getStackPaneUnit(double width, double height) {
        HBox mainHBox = new HBox();
        mainHBox.setPrefSize(width * 0.7, height * 0.75);
        mainHBox.setSpacing(15);

        mainHBox.getChildren().addAll(getSoldierData(width * 0.35, height * 0.75),
                                      getButtonsVBox(width * 0.35, height * 0.75));
        mainHBox.setAlignment(Pos.CENTER);

        StackPane stackPane = new StackPane(ProfileMenuGUIController.getDataBackgroundImage(height, width),
                                            mainHBox);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setPrefSize(width, height);
        return stackPane;
    }

    private VBox getButtonsVBox(double width, double height) {
        buttons = new VBox();
        buttons.setPrefSize(width, height);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);
        buttonHeight = height * 0.1;

        addButton("Change soldier state", buttonHeight, new changeSoldierState());
        addButton("Patrol", buttonHeight, new patrol());
        addButton("Move", buttonHeight, new move());
        addButton("Attack", buttonHeight, new attack());

        return buttons;
    }

    private VBox getSoldierData(double width, double height) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPrefSize(width, height);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(getSoldierType(width, height * 0.1),
                                  getSoldierPic(height * 0.5),
                                  getWeaponAndArmour(width, height * 0.1),
                                  getSoldierState(width, height * 0.1),
                                  getPatrolData(width, height * 0.1)
        );

        return vBox;
    }

    private HBox getPatrolData(double width, double height) {
        Text text;
        int[][] path = soldier.getPatrolPath();
        text = UnitPane.getText(
                soldier.isPatrolling()
                        ? "{%d, %d} to {%d, %d}".formatted(path[0][0], path[1][0], path[0][1], path[1][1])
                        : "This soldier is not patrolling"
        );
        HBox hBox = gethBox(width, height);
        hBox.getChildren().add(text);
        return hBox;
    }

    private HBox getSoldierState(double width, double height) {
        ImageView imageView = getSoldierState(height);
        Text text = UnitPane.getText(SoldierName.getName(soldier.getSoldierState().name()));

        HBox hBox = gethBox(width, height);
        hBox.getChildren().addAll(imageView, text);

        return hBox;
    }

    private ImageView getSoldierState(double height) {
        ImageView imageView = new ImageView(soldier.getSoldierState().getImage());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
        return imageView;
    }

    private HBox getSoldierType(double width, double height) {
        Text text = UnitPane.getText(soldier.getType().getType());
        text.setStyle("-fx-font: 25 Algerian; -fx-font-weight: bold");
        HBox hBox = new HBox(text);
        hBox.setPrefSize(width, height);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private HBox getWeaponAndArmour(double width, double height) {
        ImageView imageView = getWeaponImage(height);
        Text text = UnitPane.getText(soldier.getWeapon().getWeaponName().getName());

        HBox hBox = gethBox(width, height);
        hBox.getChildren().addAll(imageView, text);

        ArmourType armourType = soldier.getType().getArmourType();
        if (armourType == ArmourType.NONE) {
            return hBox;
        }

        ImageView imageView1 = getArmourType(height, armourType);
        Text text1 = UnitPane.getText(SoldierName.getName(armourType.name()) + " Armour");
        hBox.getChildren().addAll(imageView1, text1);

        return hBox;
    }

    private ImageView getWeaponImage(double height) {
        Image image = soldier.getWeapon().getWeaponName().getImage();
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
        return imageView;
    }

    private ImageView getSoldierPic(double height) {
        Image image = soldier.getImage();
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
        return imageView;
    }

    protected void initBackButton() {
        Button backButton = ProfileMenuGUIController.getBackButton(UnitMenuController::showUnitMenu);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
    }

    protected void addButton(String string, double height, EventHandler<ActionEvent> eventHandler) {
        Button button = UnitPane.getLongButtonUtil(string, height, eventHandler);
        buttons.getChildren().add(button);
    }

    public Pane getPane() {
        return pane;
    }

    public void startPatrolling(int xFrom, int yFrom, int xTo, int yTo) {
        soldier.startPatrolling(xFrom, yFrom, xTo, yTo);
    }

    public void stopPatrolling() {
        soldier.setPatrolling(false);
    }

    public void showSoldierMenu(ActionEvent ignoredActionEvent) {
        try {
            SoldierMenu soldierMenu = new SoldierMenu();
            soldierMenu.init(SoldierMenuController.getController(soldier));
            soldierMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPromptX() {
        return soldier.getNextTurnData().isMoving()
                ? String.valueOf(soldier.getNextTurnData().getToX())
                : "Enter X";
    }

    public String getPromptY() {
        return soldier.getNextTurnData().isMoving()
                ? String.valueOf(soldier.getNextTurnData().getToY())
                : "Enter Y";
    }

    public boolean cannotMoveTo(int xTo, int yTo) {
        return !soldier.canMoveTo(xTo, yTo);
    }

    public void setMoving(int xTo, int yTo) {
        soldier.getNextTurnData().setMoveTo(xTo, yTo);
    }

    public void stopMoving() {
        soldier.getNextTurnData().stopMoving();
    }

    public String getPromptAttackX() {
        return soldier.getNextTurnData().getToAttack() == null
                ? "Enter X"
                : String.valueOf(soldier.getNextTurnData().getToAttack().getX());
    }

    public String getPromptAttackY() {
        return soldier.getNextTurnData().getToAttack() == null
                ? "Enter Y"
                : String.valueOf(soldier.getNextTurnData().getToAttack().getY());
    }

    public void setAttack(int xTo, int yTo) {
        soldier.getNextTurnData().setToAttack(soldier.getOwner().getGovernment().getMap().getUnitByXY(xTo, yTo));
    }

    public void stopAttacking() {
        soldier.getNextTurnData().setToAttack(null);
    }

    private class changeSoldierState implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            SoldierState soldierState = soldier.getSoldierState();
            SoldierState[] values = SoldierState.values();
            SoldierState nextState = values[(soldierState.ordinal() + 1) % values.length];

            soldier.setSoldierState(nextState);
            showSoldierMenu(null);
        }
    }

    private class patrol implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                PatrolMenu patrolMenu = new PatrolMenu();
                patrolMenu.init(getThis());
                patrolMenu.start(MainMenu.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class move implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                MoveMenu moveMenu = new MoveMenu();
                moveMenu.init(getThis());
                moveMenu.start(MainMenu.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class attack implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                AttackMenu attackMenu = new AttackMenu();
                attackMenu.init(getThis());
                attackMenu.start(MainMenu.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
