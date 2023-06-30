package view.show.OnlineMenu.ChatMenu;

import Server.Client;
import Server.Packet;
import Server.ServerCommands;
import controller.GUIControllers.MainMenuGUIController;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Government.GUI.GovernmentPane;
import model.Massenger.Message;
import model.Massenger.PublicChat;
import model.Save.ChatLoader;
import model.Save.ChatSaver;
import view.show.MainMenu.MainMenu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;

public class PublicChatMenu extends Application {
    private ObjectInputStream objectInputStream;
    private Pane pane;
    private User user;
    private TextField text;
    private Socket socket;
    private ScrollPane messages;
    private double scrollWidth;
    private Thread thread;

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

    public void init(String username, Socket socket) throws IOException {
        this.socket = socket;
        new ObjectOutputStream(socket.getOutputStream()).writeObject(new Packet(ServerCommands.START_RECEIVING_PUBLIC));
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        Client.getData();
        Client.getChatData();
        this.user = Users.getUser(username);
    }

    public void init(String username) throws IOException {
        this.user = Users.getUser(username);
    }

    @Override
    public void start(Stage stage) throws Exception {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        pane = new Pane();
        VBox editPane = getVBox(width * 0.9, height * 0.8);

        editPane.setPrefSize(width * 0.9, height * 0.8);

        pane.setPrefSize(width, height);

        thread = new Thread(this::update);
        thread.start();

        pane.setBackground(getBackground(width, height));
        pane.getChildren().add(editPane);
        editPane.setLayoutX(width * 0.05);
        editPane.setLayoutY(height * 0.10);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(GovernmentPane.class.getResource("/css.css").toExternalForm());
        stage.setFullScreenExitHint("");
        stage.setTitle("Stronghold");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    private void update() {
        while (!thread.isInterrupted()) {
            try {
                System.out.println("Listening");
                ChatSaver chatSaver = (ChatSaver) objectInputStream.readObject();
                System.out.println("got message");
                ChatLoader.loadSave(chatSaver);
                Platform.runLater(this::refresh);
            } catch (IOException | ClassNotFoundException ignored) {
                thread.interrupt();
            }
        }
    }

    private Button initBackButton() {
        Button backButton = getBackButton(this::back);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
        return backButton;
    }

    private VBox getVBox(double width, double height) {
        Text text = new Text("Chat Menu");
        text.setFont(Font.font("Old English Text MT", FontWeight.EXTRA_BOLD, 50));
        StackPane stackPane = new StackPane(text);
        stackPane.setPadding(new Insets(30, 10, 10, 10));

        initBackButton();

        VBox vBox = new VBox(
                stackPane,
                (messages = getMessagesScrollPane(width * 0.9, height * 0.8)),
                getSendMessageHBox(width * 0.9, height * 0.10)
        );

        vBox.setSpacing(25);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4); -fx-background-radius: 25");

        return vBox;
    }

    private ScrollPane getMessagesScrollPane(double width, double height) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMaxSize(width, height);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.setContent(getMessagesVBox(width));
        scrollPane.setVvalue(1.0);

        return scrollPane;
    }

    private VBox getMessagesVBox(double width) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(scrollWidth = width);

        for (Message message : PublicChat.messages)
            vBox.getChildren().add(getMessageHBox(message, width));

        return vBox;
    }

    private HBox getMessageHBox(Message message, double width) {
        HBox hBox = new HBox();
        hBox.setPrefWidth(width);
        hBox.setSpacing(5);

        if (!message.getOwner().equals(user.getUserName())) {
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.getChildren().addAll(getAvatar(message.getOwner()), getContent(message, width * 0.85));
        } else {
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.getChildren().addAll(getContent(message, width * 0.85), getAvatar(message.getOwner()));
        }

        return hBox;
    }

    private VBox getContent(Message message, double width) {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMaxWidth(width);
        vBox.setPadding(new Insets(10));

        VBox box = new VBox();
        box.setSpacing(5);
        box.setMaxWidth(width - 50);

        Text username = new Text(message.getOwner());
        username.setFont(Font.font("System", FontWeight.BOLD, 16));


        Text content = new Text(message.getContent());
        content.setFont(Font.font("System", FontWeight.NORMAL, 16));
        content.setWrappingWidth(width);
        content.setTextAlignment(TextAlignment.RIGHT);

        if (!message.getOwner().equals(user.getUserName())) {
            box.setAlignment(Pos.TOP_LEFT);
            vBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-background-radius: 5;");
            username.setStyle("-fx-fill: #b20000");
            content.setTextAlignment(TextAlignment.LEFT);
        } else {
            box.setAlignment(Pos.TOP_RIGHT);
            vBox.setStyle("-fx-background-color: rgba(255, 255, 0, 0.7); -fx-background-radius: 5");
            username.setStyle("-fx-fill: #6800ff");
            content.setTextAlignment(TextAlignment.RIGHT);
        }
        box.getChildren().addAll(username, content);

        vBox.getChildren().add(box);

        return vBox;
    }

    private ImageView getAvatar(String owner) {
        ImageView avatar = new ImageView(new Image(Users.getUser(owner).getAvatar().toExternalForm()));
        avatar.setPreserveRatio(true);
        avatar.setFitWidth(50);
        return avatar;
    }

    private HBox getSendMessageHBox(double width, double height) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.setMinSize(width, height);
        hBox.setMaxSize(width, height);

        hBox.getChildren().addAll(
                (text = getTextField("Enter a message...", width - height - 10, height)),
                getSendButton(height)
        );

        hBox.setPadding(new Insets(10));

        return hBox;
    }

    private void refresh() {
        messages.setContent(getMessagesVBox(scrollWidth));
    }

    private Button getSendButton(double size) {
        Image image = new Image(
                PublicChatMenu.class.getResource("/phase2-assets/Others/Icons/send.png").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              new BackgroundSize(
                                                                      size * 0.7, size * 0.7,
                                                                      false, false, false, false
                                                              ));
        Button button = new Button();
        button.setBackground(new Background(backgroundImage));

        button.setOnAction(new confirm());
        button.setPrefSize(size, size);

        return button;
    }

    private TextField getTextField(String text, double width, double height) {
        TextField field = new TextField();
        field.setPromptText(text);
        field.setPrefSize(width * 0.75, height);
        field.setStyle("-fx-background-color: rgba(100, 255, 100, 0.5);" +
                               " -fx-prompt-text-fill: black; -fx-font: 20 System");
        return field;
    }

    private void back(ActionEvent actionEvent) {
        Client.stopReceivingUpdates(socket);
        thread.interrupt();

        ChatMenu chatMenu = new ChatMenu();
        chatMenu.init(user.getUserName());
        try {
            chatMenu.start(MainMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUser() {
        return user.getUserName();
    }

    @Override
    public void stop() throws Exception {
        Client.stopReceivingUpdates(socket);
        socket.close();
        thread.interrupt();
        super.stop();
    }

    private class confirm implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String content = text.getText();

            if (content.isBlank()) return;

            PublicChat.messages.add(new Message(user.getUserName(), content));
            Client.sendChatData();
            text.setText("");
        }
    }
}
