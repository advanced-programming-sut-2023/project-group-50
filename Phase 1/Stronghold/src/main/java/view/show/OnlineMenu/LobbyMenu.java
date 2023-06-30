package view.show.OnlineMenu;

import Server.Client;
import controller.GUIControllers.ProfileMenuGUIController;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Government.GUI.GovernmentPane;
import model.Government.Government;
import model.RandomGenerator.RandomGenerator;
import model.Request.Request;
import view.show.MainMenu.MainMenu;

import java.util.ArrayList;
import java.util.Objects;

import static model.Map.GUI.Unit.UnitPane.getButtonUtil;
import static view.show.ProfileMenu.EditUsernameMenu.getEditPane;

public class LobbyMenu extends Application {
    private User user;
    private ScrollPane scrollPane;
    private double scrollWidth;
    private double scrollHeight;

    static Text getText(String string) {
        Text text = new Text(string);
        text.setFont(Font.font("Old English Text MT", FontWeight.BOLD, 45));
        return text;
    }

    private static Request getRequest(ActionEvent actionEvent) {
        String name = ((Button) actionEvent.getSource()).getId();
        return Users.getRequest(name);
    }

    private VBox getRequestData(Request request) {
        VBox data = new VBox();
        data.setAlignment(Pos.CENTER_LEFT);
        data.setPrefHeight(150);

        data.setPrefWidth(scrollWidth * 0.4);

        Text name = new Text(request.name());
        name.setFont(Font.font("Andalus", FontWeight.BOLD, 25));

        Text admin = new Text("Admin: " + request.admin());
        admin.setFont(Font.font("System", FontWeight.BOLD, 20));

        Text players = new Text(request.users().size() + " of " + request.numberOfPlayers());
        players.setFont(Font.font("System", FontWeight.NORMAL, 20));


        data.getChildren().addAll(name, admin, players);
        return data;
    }

    public void init(User user) {
        this.user = Users.getUser(user.getUserName());
        Client.getData();
        if (user.getRequest() != null && Users.getRequest(user.getRequest().name()) != user.getRequest())
            Users.getUser(user.getUserName()).setRequest(Users.getRequest(user.getRequest().name()));
    }

    @Override
    public void start(Stage stage) throws Exception {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        StackPane pane = new StackPane();
        pane.setPrefSize(width, height);

        pane.setBackground(getBackground(width, height));

        initBackButton(width, pane);

        VBox vBox = getRequestsVBox(width * 0.75, height * 0.8);

        pane.getChildren().add(getEditPane(vBox, width * 0.8, height * 0.9));

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Objects.requireNonNull(
                GovernmentPane.class.getResource("/css.css")).toExternalForm()
        );
        stage.setScene(scene);
        stage.setTitle("Stronghold");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    private void initBackButton(double width, StackPane pane) {
        Button backButton = ProfileMenuGUIController.getBackButton(this::showOnlineMenu);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(width - 125);
    }

    private void showOnlineMenu(ActionEvent actionEvent) {
        OnlineMenu onlineMenu = new OnlineMenu();
        onlineMenu.init(user);
        try {
            onlineMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private VBox getRequestsVBox(double width, double height) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setPrefSize(width, height);

        this.scrollHeight = height * 0.75;
        this.scrollWidth = width;
        Text text = getText("Lobby");
        HBox buttons = getButtons();
        scrollPane = getRequestsScrollPane();
        vBox.getChildren().addAll(text, scrollPane, buttons);

        return vBox;
    }

    private HBox getButtons() {
        Button refresh = getButtonUtil("Refresh", 50, this::refresh);
        Button newRequest = getButtonUtil("New Request", 50, this::newRequest);

        HBox buttons = new HBox(refresh, newRequest);
        buttons.setAlignment(Pos.CENTER);
        return buttons;
    }

    private void newRequest(ActionEvent actionEvent) {

        if (user.getRequest() != null) {
            new Alert(Alert.AlertType.ERROR, "You currently are in a game");
            return;
        }

        NewRequestMenu newRequestMenu = new NewRequestMenu();
        newRequestMenu.init(user);
        try {
            newRequestMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void refresh(ActionEvent actionEvent) {
        Client.getData();
        try {
            LobbyMenu lobbyMenu = new LobbyMenu();
            lobbyMenu.init(Users.getUser(user.getUserName()));
            lobbyMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ScrollPane getRequestsScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.setContent(getRequests());
        scrollPane.setPrefSize(scrollWidth, scrollHeight);
        scrollPane.setMaxWidth(scrollWidth);
        return scrollPane;
    }

    private VBox getRequests() {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(scrollWidth);

        if (user.getRequest() != null)
            vBox.getChildren().add(getRequestHBox(user.getRequest()));
        else
            for (Request request : RandomGenerator.tenRandomsFrom(new ArrayList<>(Users.getRequests())))
                vBox.getChildren().add(getRequestHBox(request));

        return vBox;
    }

    private HBox getRequestHBox(Request request) {
        HBox hBox = getEmptyHBox();
        hBox.setMinWidth(scrollWidth * 0.8);
        hBox.setPrefHeight(200);
        hBox.setAlignment(Pos.CENTER);


        hBox.getChildren().addAll(getRequestData(request), getRequestButtons(request));

        return hBox;
    }

    private VBox getRequestButtons(Request request) {
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setPrefHeight(150);
        buttons.setPrefWidth(scrollWidth * 0.4);
        buttons.setSpacing(15);

        Button start = getButtonUtil("Start", 50, this::startGame);
        Button joinOrLeave;
        if (user.getRequest() != null && user.getRequest().equals(request))
            joinOrLeave = getButtonUtil("Leave", 50, this::leave);
        else joinOrLeave = getButtonUtil("Join", 50, this::join);

        start.setId(request.name());
        joinOrLeave.setId(request.name());

        buttons.getChildren().addAll(start, joinOrLeave);
        return buttons;
    }

    private void leave(ActionEvent actionEvent) {
        Client.getData();
        Request request = getRequest(actionEvent);
        if (request == null) {
            Users.getUser(user.getUserName()).setRequest(null);
            refresh(null);
            return;
        }
        if (user.getRequest() != null && Users.getRequest(user.getRequest().name()) != user.getRequest())
            Users.getUser(user.getUserName()).setRequest(Users.getRequest(user.getRequest().name()));

        if (user.getRequest() == null || !user.getRequest().equals(request)) {
            new Alert(Alert.AlertType.ERROR, "You're not a part of this game");
            return;
        }

        request.remove(user.getUserName());
        Users.getUser(user.getUserName()).setRequest(null);
        if (request.isEmpty()) {
            Users.removeRequest(request.name());
        }

        Client.sendData();

        refresh(null);
    }

    private void join(ActionEvent actionEvent) {
        Client.getData();
        Request request = getRequest(actionEvent);
        if (request == null) {
            refresh(null);
            return;
        }
        if (user.getRequest() != null && Users.getRequest(user.getRequest().name()) != user.getRequest())
            Users.getUser(user.getUserName()).setRequest(Users.getRequest(user.getRequest().name()));

        if (request.isFull() && !request.users().contains(user.getUserName())) {
            new Alert(Alert.AlertType.ERROR, "This game is full").show();
            return;
        }


        request.add(user.getUserName());
        Users.getUser(user.getUserName()).setRequest(request);
        new Alert(Alert.AlertType.INFORMATION, "You joined the game, waiting for the game to start");

        Client.sendData();

        refresh(null);
    }

    private void startGame(ActionEvent actionEvent) {
        Request request = getRequest(actionEvent);

        if (!request.canStart(user.getUserName())) {
            new Alert(Alert.AlertType.ERROR, "This game cannot be started!").show();
            return;
        }

        Client.getData();

        request.start(user.getUserName());

        Client.sendData();
    }

    private HBox getEmptyHBox() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setBackground(GovernmentPane.getDataBackground(scrollWidth, 200));


        hBox.setBackground(new Background(new BackgroundImage(
                new Image(Government.class.getResource("/images/background/scroll.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        scrollWidth, 200,
                        false, false, false, false
                )
        )));
        return hBox;
    }

    private Background getBackground(double width, double height) {
        Image image = new Image(LobbyMenu.class.getResource("/images/background/Lobby.png").toExternalForm());
        return new Background(new BackgroundImage(image,
                                                  BackgroundRepeat.NO_REPEAT,
                                                  BackgroundRepeat.NO_REPEAT,
                                                  BackgroundPosition.CENTER,
                                                  new BackgroundSize(
                                                          width, height, false, false, false, false
                                                  )));
    }
}
