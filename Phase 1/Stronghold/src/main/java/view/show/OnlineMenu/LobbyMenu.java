package view.show.OnlineMenu;

import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import model.RandomGenerator.RandomGenerator;
import model.Request.Request;

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

    public void init(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage stage) throws Exception {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        StackPane pane = new StackPane();
        pane.setPrefSize(width, height);

        pane.setBackground(getBackground(width, height));

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

    private VBox getRequestsVBox(double width, double height) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setPrefSize(width, height);

        this.scrollHeight = height * 0.75;
        this.scrollWidth = width;
        Text text = getText("Lobby");
        Button button = getButtonUtil("Refresh", 50, this::refresh);
        scrollPane = getRequestsScrollPane();

        vBox.getChildren().addAll(text, scrollPane, button);

        return vBox;
    }

    private void refresh(ActionEvent actionEvent) {
        VBox vBox = (VBox) scrollPane.getParent();
        vBox.getChildren().removeAll();
        Text text = getText("Lobby");
        Button button = getButtonUtil("Refresh", 50, this::refresh);
        scrollPane = getRequestsScrollPane();

        vBox.getChildren().addAll(text, scrollPane, button);
    }

    private ScrollPane getRequestsScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.setContent(getRequests());
        scrollPane.setPrefSize(scrollWidth, scrollHeight);
        return scrollPane;
    }

    private VBox getRequests() {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        for (Request request : RandomGenerator.tenRandomsFrom(new ArrayList<>(Users.getRequests())))
            vBox.getChildren().add(getRequestHBox(request));

        return vBox;
    }

    private HBox getRequestHBox(Request request) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setBackground(GovernmentPane.getDataBackground(scrollWidth, 200));
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
