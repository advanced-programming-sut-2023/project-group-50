package view.show.controller;

import Server.Client;
import controller.Menus.LoginController;
import controller.Menus.SignupController;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import controller.control.Error;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import model.RandomGenerator.RandomCaptcha;
import view.show.MainMenu.MainMenu;
import view.show.Menus.LoginMenuShow;
import view.show.Menus.MenuButton;
import view.show.Menus.StartMenu;
import view.show.MusicPlayer;

import static view.show.controller.SignupMenuShowController.*;

public class LoginMenuShowController {

    public VBox username;
    public VBox password;
    public VBox captcha;
    public Pane pane;
    public VBox menu;

    public Parent createContent() {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        Pane pane = new Pane();
        this.pane = pane;
        pane.setPrefSize(width, height);
        Background background = new Background(StartMenuController.setBackGround(("/images/background/thumb-1920-152789.jpg"), width, height));
        pane.setBackground(background);
        VBox menu = new VBox(creatUsernameField(), creatPasswordField(), (captcha = SignupMenuShowController.createCaptcha()), createButtons());
        menu.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 25");
        menu.setPrefSize(500, 400);

        MusicPlayer.playMusic(SignupMenuShowController.class.getResource("/images/floating-in-time-145495.mp3").toExternalForm());

        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(15);
        menu.setTranslateY((height - 400) / 2);
        menu.setTranslateX((width - 500) / 2);
        this.menu = menu;
        pane.getChildren().add(menu);
        return pane;
    }


    private Node creatUsernameField() {
        HBox hBox = new HBox();

        TextField username = getTextField("Username");
        ImageView userImage = getImage("/images/icons/user1.png", "/images/icons/user2.png", username);

        hBox.getChildren().addAll(userImage, username);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(7);
        VBox vBox = new VBox(hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);

        Label error = new Label();

        error.setTextFill(Color.RED);
        HBox errorBox = new HBox();
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setSpacing(5);

        ImageView errorImage = getErrorImage();
        errorBox.getChildren().addAll(errorImage, error);
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            if (error.getText().equals("You should enter a username")) {
                if (!username.getText().equals("")) {
                    error.setText("");
                }
            }
            if (!username.getText().equals("")) {
                if (hBox.getChildren().size() == 2) {
                    hBox.getChildren().add(createReset(username));
                }
            } else {
                hBox.getChildren().remove(2);
            }


        });

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(errorBox);
        this.username = vBox;
        return vBox;

    }


    private Node creatPasswordField() {
        HBox hBox = new HBox();

        PasswordField password = new PasswordField();
        ImageView passwordImage = getImage("/images/icons/lock1.png", "/images/icons/lock2.png", password);
        password.setPromptText("Password");
        password.setStyle("    -fx-max-width: 200;\n" +
                                  "    -fx-min-width: 200;\n" +
                                  "    -fx-font-family: \"Verdana\";\n" +
                                  "    -fx-font-size: 13px;\n" +
                                  "    -fx-text-fill: -fx-text-inner-color;\n" +
                                  "    -fx-highlight-fill: derive(-fx-control-inner-background,-20%);\n" +
                                  "    -fx-highlight-text-fill: -fx-text-inner-color;\n" +
                                  "    -fx-prompt-text-fill: transparent;\n" +
                                  "    -fx-background-color: transparent;\n" +
                                  "    -fx-background-insets: 0;\n" +
                                  "    -fx-background-radius: 0;\n" +
                                  "    -fx-border-color: transparent transparent #616161 transparent;\n" +
                                  "    -fx-border-width: 1;\n" +
                                  "    -fx-border-insets: 0 0 1 0;\n" +
                                  "    -fx-border-style: hidden hidden solid hidden;\n" +
                                  "    -fx-cursor: text;\n" +
                                  "    -fx-padding: 0.166667em 0em 0.333333em 0em;\n" +
                                  "    -fx-prompt-text-fill: Black;\n");


        ImageView eyeImage = getEyeImage(hBox, password);

        hBox.getChildren().addAll(passwordImage, password, eyeImage);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(7);
        VBox vBox = new VBox(hBox);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(5);

        Label error = new Label();
        error.setTextFill(Color.RED);

        HBox errorBox = new HBox();
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setSpacing(5);

        ImageView errorImage = getErrorImage();
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (error.getText().equals("You should enter a password")) {
                if (!password.getText().equals("")) {
                    error.setText("");
                }
            }

            if (!password.getText().equals("")) {
                if (hBox.getChildren().size() == 3) {
                    hBox.getChildren().add(createReset(password));
                }
            } else {
                hBox.getChildren().remove(3);
            }
        });

        errorBox.getChildren().addAll(errorImage, error);
        Label forgot = new Label("forgot password");
        forgot.setAlignment(Pos.CENTER_LEFT);

        forgot.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                forgot.setOpacity(0.7);
                forgot.setTextFill(Color.WHITE);

            }
        });
        forgot.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                forgot.setOpacity(1);
                forgot.setTextFill(Color.BLACK);

            }
        });


        forgot.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client.getData();
                Error error = LoginController.userNameIsExist(((TextField) ((HBox) username.getChildren().get(0)).getChildren().get(1)).getText());
                addError((HBox) username.getChildren().get(1), error);
                if (error.truth) {
                    createQuestions(Users.getUser(((TextField) ((HBox) username.getChildren().get(0)).getChildren().get(1)).getText().trim()));
                }
            }
        });

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(forgot, errorBox);
        this.password = vBox;
        return vBox;

    }


    private Node createButtons() {
        HBox hBox = new HBox();
        MenuButton login = new MenuButton("Login");
        MenuButton back = new MenuButton("Back");

        login.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                login();
            }
        });

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    new StartMenu().start(LoginMenuShow.stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        hBox.setSpacing(10);
        hBox.getChildren().addAll(login, back);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    public void login() {
        Client.getData();

        boolean flag = true;

        Error error = LoginController.userNameIsExist(((TextField) ((HBox) username.getChildren().get(0)).getChildren().get(1)).getText());

        addError((HBox) username.getChildren().get(1), error);

        if (!error.truth) {
            flag = false;
        }

        User user = Users.getUser(((TextField) ((HBox) username.getChildren().get(0)).getChildren().get(1)).getText().trim());
        error = LoginController.passwordIsRight(((PasswordField) ((HBox) password.getChildren().get(0)).getChildren().get(1)).getText(), user);
        addError(((HBox) password.getChildren().get(2)), error);

        if (!error.truth) {
            flag = false;
        }


        if (((TextField) ((HBox) this.captcha.getChildren().get(0)).getChildren().get(1)).getText().trim().equals("")) {
            error = new Error("You should enter captcha", false);
            flag = false;
        } else if (!((TextField) ((HBox) this.captcha.getChildren().get(0)).getChildren().get(1)).getText().trim().equals(String.valueOf(captchaNumber))) {
            error = new Error("Your captcha is wrong try again", false);
            captchaNumber = RandomCaptcha.getCaptcha();
            ((Rectangle) ((HBox) this.captcha.getChildren().get(0)).getChildren().get(0)).setFill(new ImagePattern(new Image(RandomCaptcha.class.getResource(
                    "/Captcha/" + captchaNumber + ".png").toExternalForm())));
            ((TextField) ((HBox) this.captcha.getChildren().get(0)).getChildren().get(1)).setText("");
            flag = false;

        }
        addError((HBox) this.captcha.getChildren().get(2), error);

        if (!flag) {
            MusicPlayer.playMusic(getClass().getResource("/images/error-89612.mp3").toString());
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your login was successful", ButtonType.OK);
        alert.showAndWait();
        MainMenu mainMenu = new MainMenu();
        Client.getData();
        mainMenu.init(user);
        try {
            mainMenu.start(LoginMenuShow.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public void createQuestions(User user) {
        VBox vBox = new VBox();
        Text text = new Text("Your security question is :\n\t\t" + user.getSecurityQuestion().getQuestion());
        text.setTextAlignment(TextAlignment.LEFT);
        text.setFont(new Font("Freestyle Script", 30));
        text.setFill(Color.BLACK);


        TextField answer = getTextField("Answer");

        HBox answerBox = new HBox(answer);
        answer.setMaxWidth(800 / 3 * 2 / 3);
        answerBox.setAlignment(Pos.CENTER);
        Label answerError = new Label();
        answerError.setTextFill(Color.RED);
        HBox errorBoxAnswer = new HBox();
        errorBoxAnswer.setAlignment(Pos.CENTER);
        errorBoxAnswer.setSpacing(5);

        answer.textProperty().addListener((observable, oldValue, newValue) -> {
            if (answerError.getText().equals("You should answer the question")) {
                if (!answer.getText().equals("")) {
                    answerError.setText("");
                }
            }

            if (!answer.getText().equals("")) {
                if (answerBox.getChildren().size() == 1) {
                    answerBox.getChildren().add(createReset(answer));
                }
            } else {
                answerBox.getChildren().remove(1);
            }

        });

        ImageView errorAnswerImage = getErrorImage();
        errorBoxAnswer.getChildren().addAll(errorAnswerImage, answerError);

        HBox hBox = new HBox();
        MenuButton next = new MenuButton("Next");
        MenuButton back = new MenuButton("Back");

        next.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                checkAnswer(user, answer, errorBoxAnswer);

            }
        });

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pane.getChildren().remove(0);
                pane.getChildren().add(menu);
                double width = Screen.getPrimary().getBounds().getWidth();
                double height = Screen.getPrimary().getBounds().getHeight();
                Background background = new Background(StartMenuController.setBackGround(("/images/background/thumb-1920-152789.jpg"), width, height));
                pane.setBackground(background);

            }
        });

        hBox.setSpacing(10);
        hBox.getChildren().addAll(next, back);
        hBox.setAlignment(Pos.CENTER);


        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        vBox.getChildren().addAll(text, answerBox, errorBoxAnswer, hBox);


        this.pane.getChildren().remove(0);

        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        pane.setBackground(new Background(
                StartMenuController.setBackGround("/images/background/thumb-1920-152789.jpg", width, height))
        );

        StackPane data = new StackPane(vBox);
        data.setBackground(new Background(StartMenuController.setBackGround("/images/background/profileData.png", 800, 600)));
        data.setPrefSize(800, 600);
        data.setAlignment(Pos.CENTER);

        this.pane.getChildren().add(data);

        data.setLayoutX((width - 800) / 2);
        data.setLayoutY((height - 600) / 2);
    }

    public void checkAnswer(User user, TextField answer, HBox errorBox) {
        Error error = new Error("", true);

        if (answer.getText().trim().equals("")) {
            error = new Error("You should answer the question", false);
        } else if (!answer.getText().trim().equals(user.getSecurityQuestionAnswer())) {
            error = new Error("Your answer is wrong", false);
        }
        addError(errorBox, error);
        if (!error.truth) {
            return;
        }

        createSetPassword(user);

    }

    public void createSetPassword(User user) {
        Client.getData();
        VBox vBox = new VBox();
        Label label = new Label("Set new password");
        VBox setPassword = SignupMenuShowController.creatPasswordField();
        ((PasswordField) ((HBox) setPassword.getChildren().get(0)).getChildren().get(1)).setText(user.getPassword());

        HBox hBox = new HBox();
        MenuButton change = new MenuButton("Change");
        MenuButton back = new MenuButton("Back");

        change.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changePassword(user, setPassword);

            }
        });

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    new LoginMenuShow().start(LoginMenuShow.stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        hBox.setSpacing(10);
        hBox.getChildren().addAll(change, back);
        hBox.setAlignment(Pos.CENTER);

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);


        vBox.getChildren().addAll(label, setPassword, hBox);
        vBox.setPrefSize(500, 500);
        vBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 25");

        this.pane.getChildren().remove(0);
        this.pane.getChildren().add(vBox);

        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

        vBox.setLayoutX((width - 500) / 2);
        vBox.setLayoutY((height - 500) / 2);


    }

    public void changePassword(User user, VBox setPassword) {

        Error error;

        if (((PasswordField) ((HBox) setPassword.getChildren().get(0)).getChildren().get(1)).getText().equals("")) {
            error = new Error("You should enter a password", false);
        } else {
            error = SignupController.passwordIsValid(((PasswordField) ((HBox) setPassword.getChildren().get(0)).getChildren().get(1)).getText());
        }
        addError(((HBox) setPassword.getChildren().get(2)), error);

        if (!error.truth) {
            return;
        }

        Users.getUser(user.getUserName()).setPassword(((PasswordField) ((HBox) setPassword.getChildren().get(0)).getChildren().get(1)).getText());
        Client.sendData();

        try {
            new LoginMenuShow().start(LoginMenuShow.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
