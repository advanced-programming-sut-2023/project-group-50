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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Government.GUI.GovernmentPane;
import model.Massenger.Chat;
import model.Massenger.Group;
import model.Massenger.Message;
import model.Massenger.Reaction;
import model.Save.ChatLoader;
import model.Save.ChatSaver;
import view.show.MainMenu.MainMenu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;

public class PrivateChatMenu extends Application {
    private ObjectInputStream objectInputStream;
    private Pane pane;
    private User user;
    private TextField text;
    private Socket socket;
    private ScrollPane messages;
    private double scrollWidth;
    private Thread thread;
    private Message edittingMessage;
    private Chat chat;

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

    public void init(String username, Socket socket, Chat chat) throws IOException {
        this.socket = socket;
        this.chat = chat;
        new ObjectOutputStream(socket.getOutputStream()).writeObject(
                new Packet(ServerCommands.START_RECEIVING_CHAT, chat.getId())
        );
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        Client.getData();
        Client.getChatData();
        Client.sendPrivateChatData(chat);
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

    private void initBackButton() {
        Button backButton = getBackButton(this::back);
        pane.getChildren().add(backButton);
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
    }

    private VBox getVBox(double width, double height) {
        Text text = new Text(chat instanceof Group
                                     ? chat.getName() + " (" + chat.getMembers().size() + " members)"
                                     : chat.getName());
        text.setFont(Font.font("Old English Text MT", FontWeight.EXTRA_BOLD, 50));
        StackPane stackPane = new StackPane(text);
        stackPane.setPadding(new Insets(30, 10, 10, 10));

        text.setOnMouseClicked(this::showMembers);

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

        boolean needsRefresh = false;
        for (Message message : chat.getMessages()) {
            vBox.getChildren().add(getMessageHBox(message, width));
            if (!message.getOwner().equals(user.getUserName()) && !message.isSeen()) {
                needsRefresh = true;
                message.see();
            }
        }

        if (needsRefresh)
            Client.sendPrivateChatData(chat);

        return vBox;
    }

    private HBox getMessageHBox(Message message, double width) {
        HBox hBox = new HBox();
        hBox.setPrefWidth(width);
        hBox.setSpacing(5);

        if (!message.getOwner().equals(user.getUserName())) {
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.getChildren().addAll(getAvatar(message.getOwner(), 50), getContent(message, width * 0.85));
        } else {
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.getChildren().addAll(getContent(message, width * 0.85), getAvatar(message.getOwner(), 50));
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

        HBox state = getState(message, width);

        if (!message.getOwner().equals(user.getUserName())) {
            box.setAlignment(Pos.TOP_LEFT);
            vBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-background-radius: 5;");
            username.setStyle("-fx-fill: #b20000");
            content.setTextAlignment(TextAlignment.LEFT);
            state.setAlignment(Pos.BOTTOM_RIGHT);
        } else {
            box.setAlignment(Pos.TOP_RIGHT);
            vBox.setStyle("-fx-background-color: rgba(255, 255, 0, 0.7); -fx-background-radius: 5");
            username.setStyle("-fx-fill: #6800ff");
            content.setTextAlignment(TextAlignment.RIGHT);
            state.setAlignment(Pos.BOTTOM_LEFT);
        }

        box.getChildren().addAll(username, content);
        if (!state.getChildren().isEmpty())
            box.getChildren().add(state);

        vBox.getChildren().add(box);

        vBox.setOnMouseClicked(getEventHandler(message));

        return vBox;
    }

    private EventHandler<MouseEvent> getEventHandler(Message message) {
        return mouseEvent -> {
            System.out.println("Editing");
            VBox menu = new VBox();
            menu.setPrefSize(200, 120);
            menu.setSpacing(10);
            menu.setAlignment(Pos.CENTER);
            if (message.getOwner().equals(user.getUserName())) {
                HBox edit = new HBox(getEditButton(message), getDeleteButton(message));
                edit.setSpacing(5);
                edit.setAlignment(Pos.CENTER);
                menu.getChildren().add(edit);
            }

            HBox reactions = new HBox();
            for (Reaction reaction : Reaction.values()) {
                reactions.getChildren().add(getReaction(message, reaction));
            }

            reactions.setAlignment(Pos.CENTER);
            reactions.setSpacing(5);
            menu.getChildren().add(reactions);

            Button button = getCloseButton(menu);

            menu.getChildren().add(button);

            menu.setStyle(
                    "-fx-background-radius: 20; -fx-background-color: rgb(255, 250, 250); -fx-border-color: black;" +
                            "-fx-border-radius: 20;");
            pane.getChildren().add(menu);

            double X = (Screen.getPrimary().getBounds().getWidth() - 200) / 2;
            double Y = (Screen.getPrimary().getBounds().getHeight() - 120) / 2;

            menu.setLayoutX(X);
            menu.setLayoutY(Y);
        };
    }

    private Button getCloseButton(Node menu) {
        Button button = new Button("Close");
        button.setStyle("-fx-font-size: 15");
        button.setBackground(Background.EMPTY);
        button.setOnAction(actionEvent -> pane.getChildren().remove(menu));
        return button;
    }

    private ImageView getReaction(Message message, Reaction reaction) {
        ImageView emoji = reaction.getEmoji(25);
        emoji.setOnMouseClicked(mouseEvent1 -> {
            message.getReactions().put(user.getUserName(), reaction);
            Client.sendPrivateChatData(chat);
        });
        return emoji;
    }

    private Button getDeleteButton(Message message) {
        Button edit = new Button();
        edit.setBackground(getButtonBackground("delete"));
        edit.setOnAction(actionEvent -> {
            chat.remove(message);
            Client.sendPrivateChatData(chat);
        });
        return edit;
    }

    private Background getButtonBackground(String name) {
        Image image = new Image(
                PublicChatMenu.class.getResource("/images/Buttons/" + name + ".png").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundRepeat.NO_REPEAT,
                                                              BackgroundPosition.CENTER,
                                                              new BackgroundSize(
                                                                      20, 20,
                                                                      false, false, false, false
                                                              ));

        return new Background(backgroundImage);
    }

    private Button getEditButton(Message message) {
        Button edit = new Button();
        edit.setBackground(getButtonBackground("edit"));
        edit.setOnAction(actionEvent -> {
            text.setText(message.getContent());
            edittingMessage = message;
        });
        return edit;
    }

    private HBox getState(Message message, double width) {
        HBox state = new HBox();
        state.setPrefWidth(width - 50);

        if (message.getOwner().equals(user.getUserName()))
            state.getChildren().add(seenImage(message.isSeen()));

        if (!message.getReactions().isEmpty()) {
            HBox reactions = new HBox();
            reactions.setSpacing(5);
            message.getReactions().forEach((key, value) -> {
                HBox react = new HBox(getAvatar(key, 20), value.getEmoji(20));
                react.setSpacing(2.5);
                reactions.getChildren().add(react);
            });
            state.getChildren().add(reactions);
        }


        state.setPadding(new Insets(5));
        return state;
    }


    private ImageView seenImage(boolean seen) {
        String path;
        if (seen)
            path = PublicChatMenu.class.getResource("/phase2-assets/Others/some of google material design/done_all_FILL0_wght500_GRAD0_opsz48.png").toExternalForm();
        else
            path = PublicChatMenu.class.getResource("/phase2-assets/Others/some of google material design/done_FILL0_wght500_GRAD0_opsz48.png").toExternalForm();

        ImageView imageView = new ImageView(new Image(path));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(20);

        return imageView;
    }

    private ImageView getAvatar(String owner, double size) {
        ImageView avatar = new ImageView(new Image(Users.getUser(owner).getAvatar().toExternalForm()));
        avatar.setPreserveRatio(true);
        avatar.setFitWidth(size);
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
        System.out.println("Refreshing...");
        chat = Users.getChat(chat.getId());
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
        Client.stopReceivingChatUpdates(socket, chat);
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
        Client.stopReceivingChatUpdates(socket, chat);
        socket.close();
        thread.interrupt();
        super.stop();
    }

    private Button getLeaveButton() {
        Button button = new Button("Leave Group");
        button.setStyle("-fx-font-size: 15");
        button.setBackground(Background.EMPTY);
        button.setOnAction(actionEvent -> {
            chat.getMembers().remove(user.getUserName());
            Client.sendPrivateChatData(chat);
            back(null);
        });
        return button;
    }

    private void showMembers(MouseEvent mouseEvent) {
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(15);
        content.setPrefSize(500, 500);
        content.setStyle("-fx-border-radius: 25; -fx-border-color: black;" +
                                 " -fx-background-color: rgba(10, 200, 200, 0.5); -fx-background-radius: 25");

        Text title = new Text("Members");
        title.setFont(Font.font("Bell MT", FontWeight.BOLD, 30));

        ScrollPane membersScroll = new ScrollPane();
        membersScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        membersScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        membersScroll.setBackground(Background.EMPTY);
        membersScroll.setPrefSize(500, 400);
        membersScroll.setStyle("-fx-background-radius: 25; -fx-background-color: rgba(255, 255, 255, 0.5)");

        VBox membersVBox = new VBox();
        membersVBox.setPrefWidth(450);
        membersVBox.setSpacing(15);
        membersVBox.setAlignment(Pos.CENTER_LEFT);
        membersVBox.setPadding(new Insets(20));
        for (String user : chat.getMembers()) {
            Text text1 = new Text(user);
            text1.setFont(Font.font("Bell MT", 20));

            if (user.equals(this.user.getUserName()))
                text1.setStyle("-fx-text-fill: orange");

            HBox member;
            if (chat instanceof Group && chat.getOwner().equals(this.user.getUserName()))
                member = new HBox(getAvatar(user, 50), text1, new Text("\tadmin"));
            else
                member = new HBox(getAvatar(user, 50), text1);
            member.setSpacing(7);
            member.setAlignment(Pos.CENTER_LEFT);

            membersVBox.getChildren().add(member);
        }

        Button button = getCloseButton(content);
        membersScroll.setContent(membersVBox);

        content.getChildren().addAll(title, membersScroll, button);

        if (chat instanceof Group) {
            Button leave = getLeaveButton();
            content.getChildren().add(leave);
        }

        pane.getChildren().add(content);

        double X = (Screen.getPrimary().getBounds().getWidth() - 500) / 2;
        double Y = (Screen.getPrimary().getBounds().getHeight() - 500) / 2;

        content.setLayoutX(X);
        content.setLayoutY(Y);
    }

    private class confirm implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String content = text.getText();

            if (content.isBlank()) return;

            if (edittingMessage != null) {
                edittingMessage.setContent(content);
                edittingMessage = null;
            } else
                chat.addMessage(new Message(user.getUserName(), content));
            Client.sendPrivateChatData(chat);

            text.setText("");
        }
    }
}
