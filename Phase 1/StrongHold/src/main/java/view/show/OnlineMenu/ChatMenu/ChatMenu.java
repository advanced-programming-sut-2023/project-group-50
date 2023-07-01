package view.show.OnlineMenu.ChatMenu;

import Server.Client;
import Server.Server;
import controller.GUIControllers.MainMenuGUIController;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Massenger.Chat;
import model.Massenger.Group;
import model.Massenger.PrivateChat;
import view.show.MainMenu.MainMenu;
import view.show.OnlineMenu.OnlineMenu;
import view.show.ProfileMenu.EditUsernameMenu;

import java.net.Socket;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;
import static model.Map.GUI.Unit.UnitPane.getButtonUtil;
import static model.Map.GUI.Unit.UnitPane.initButton;

public class ChatMenu extends Application {
    private Pane pane;
    private User user;
    private TextField chatId;

    public static Button getButton(String string, EventHandler<ActionEvent> eventHandler) {
        Image image = new Image(MainMenuGUIController.class.getResource("/images/Buttons/bg.jpg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              new BackgroundSize(
                                                                      200,
                                                                      50,
                                                                      false, false, false, false
                                                              ));

        Button button = new Button(string);
        button.setBackground(new Background(backgroundImage));
        button.setPrefSize(200, 50);
        button.setFont(new Font("Bell MT", 18));
        button.setStyle("-fx-text-fill: yellow");
        button.setAlignment(Pos.CENTER);
        button.setOnAction(eventHandler);

        initButton(button);

        return button;
    }

    public static Background getBackground(double width, double height) {
        Image image = new Image(MainMenuGUIController.class.getResource("/images/background/Chat.jpg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              new BackgroundSize(width,
                                                                                 height,
                                                                                 false,
                                                                                 false,
                                                                                 false,
                                                                                 false)
        );


        return new Background(backgroundImage);
    }

    public void init(String username) {
        Client.getData();
        this.user = Users.getUser(username);
    }

    @Override
    public void start(Stage stage) throws Exception {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        pane = new Pane();
        Pane editPane = EditUsernameMenu.getEditPane(getVBox(width * 0.5, 50),
                                                     width * 0.5, height * 0.5);

        editPane.setPrefSize(width * 0.5, height * 0.5);

        pane.setPrefSize(width, height);

        pane.setBackground(getBackground(width, height));
        pane.getChildren().add(editPane);
        editPane.setLayoutX(width * 0.25);
        editPane.setLayoutY(height * 0.25);

        Scene scene = new Scene(pane);
        stage.setFullScreenExitHint("");
        stage.setTitle("Stronghold");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    private Button initBackButton() {
        Button backButton = getBackButton(this::back);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
        return backButton;
    }

    private Button initConfirmButton(EventHandler<ActionEvent> eventHandler) {
        return getButton("Join Chat", eventHandler);
    }

    private VBox getVBox(double width, double height) {
        Text text = new Text("Chat Menu");
        text.setFont(Font.font("Bell MT", FontWeight.BOLD, 25));
        Button backButton = initBackButton();
        Button confirm = initConfirmButton(new joinChat());
        Button newChat = getButtonUtil("New Group", 50, new createChat());
        Button publicChat = getButtonUtil("Public Chat", 50, this::publicChat);
        HBox hBox = new HBox(backButton, confirm, newChat, publicChat);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        VBox vBox = new VBox(
                text,
                (chatId = getTextField("Enter chat id", width * 0.85, height)),
                hBox
        );
        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private void publicChat(ActionEvent actionEvent) {
        PublicChatMenu publicChatMenu = new PublicChatMenu();
        try {
            publicChatMenu.init(user.getUserName(), new Socket("127.0.0.1", Server.publicReceivingPort));
            publicChatMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TextField getTextField(String text, double width, double height) {
        TextField field = new TextField();
        field.setPromptText(text);
        field.setMaxSize(width * 0.75, height);
        field.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);" +
                               " -fx-prompt-text-fill: black; -fx-font: 20 System");
        return field;
    }

    private void back(ActionEvent actionEvent) {
        OnlineMenu onlineMenu = new OnlineMenu();
        onlineMenu.init(user);
        try {
            onlineMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showChat(String id) {
        Chat chat = Users.getChat(id);

        Client.sendChatData();
        PrivateChatMenu privateChatMenu = new PrivateChatMenu();
        try {
            privateChatMenu.init(user.getUserName(),
                                 new Socket("127.0.0.1", Server.publicReceivingPort),
                                 chat);
            privateChatMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private class joinChat implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String id = chatId.getText();

            if (id.isBlank()) {
                new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
                return;
            }
            Client.getData();

            if (Users.getUser(id) != null) {
                String chatId = user.getUserName().compareTo(id) > 0
                        ? user.getUserName() + " → " + id
                        : id + " → " + user.getUserName();

                if (Users.getChat(chatId) == null) {
                    PrivateChat privateChat = new PrivateChat(user.getUserName(), chatId, chatId);
                    privateChat.addMember(id);
                    Users.getChats().put(chatId, privateChat);
                }
                showChat(chatId);
                return;
            }

            if (!Users.ChatExists(id)) {
                new Alert(Alert.AlertType.ERROR, "ID not found!").show();
            }
        }
    }

    private class createChat implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String id = chatId.getText();

            if (id.isBlank()) {
                new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
                return;
            }
            Client.getData();

            if (Users.getUser(id) != null) {
                new Alert(Alert.AlertType.ERROR, "Cannot name a group after a username").show();
                return;
            }

            if (Users.ChatExists(id)) {
                new Alert(Alert.AlertType.ERROR, "ID is duplicate!").show();
                return;
            }

            Users.getChats().put(user.getUserName(), new Group(user.getUserName(), id, id));
            Client.sendChatData();

            showChat(id);
        }
    }
}
