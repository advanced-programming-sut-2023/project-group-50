package view.show.controller.tradeMenuShowController;

import controller.GUIControllers.GovernmentDataMenuController;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import view.show.controller.StartMenuController;
import view.show.trademenu.TradeMenuShow;

import java.util.Map;

import static controller.GUIControllers.GovernmentDataMenuController.getBoldText;

public class MakeTradeMenu {

    public static Button selectedUser;
    public static Pane makeTrade;

    public static Pane createMakeTrade(double width, double height) {
        Pane pane = new Pane();
        pane.setBackground(Background.EMPTY);
        pane.setPrefSize(width, height);

        ScrollPane scrollPane;

        pane.getChildren().addAll(
                (scrollPane = creatScrollUser(pane, width / 2 + 100, height / 2 + 100)), createBar());
        scrollPane.setTranslateX(width / 2 - 300);
        scrollPane.setTranslateY(115);

        return pane;

    }

    private static ScrollPane creatScrollUser(Pane pane, double width, double height) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPrefWidth(width);
        vBox.setAlignment(Pos.CENTER);

        for (Map.Entry<String, User> set : Users.getUsers().entrySet()) {
//            if(!set.getValue ().equals ( TradeMenuShowController.tradeMenuController.getCurrentUser () ))
//            {
            vBox.getChildren().add(createUserBox(pane, set.getValue(), width, height));
//            }
        }
        return GovernmentDataMenuController.getScrollPane(width, height, vBox);
    }

    private static HBox createBar() {
        HBox hBox = new HBox(getBoldText("Users", 320), getBoldText("New Trade", 435));
        hBox.setSpacing(30);
        hBox.setAlignment(Pos.CENTER);
        hBox.setTranslateY(5);
        hBox.setTranslateX(130);
        return hBox;
    }

    private static HBox createUserBox(Pane pane, User user, double width, double height) {
        HBox hBox = new HBox();
        Button button = new Button(user.getUserName());
        button.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
        button.setTextFill(Color.WHITE);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setPrefSize(200, 40);
        button.setBackground(new Background(StartMenuController.setBackGround("/images/background/button.png", 200, 40)));

        button.setOnMouseEntered(event -> {
            button.setOpacity(0.7);
        });

        button.setOnMouseExited(event -> {
            button.setOpacity(1);

        });

        button.setOnMouseClicked(event -> {
            button.setTextFill(Color.BLACK);
        });

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(7);

        Rectangle rectangle = new Rectangle(40, 40);
        rectangle.setFill(new ImagePattern(new Image(user.getAvatar().toString())));
        rectangle.setOnMouseEntered(event -> {
            rectangle.setOpacity(0.7);
        });

        rectangle.setOnMouseExited(event -> {
            rectangle.setOpacity(1);
        });

        ImageView imageView = new ImageView(new Image(TradeMenuShow.class.getResource("/images/Icons/request1.png").toExternalForm()));
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        imageView.setOnMouseEntered(event -> {
            imageView.setImage(new Image(TradeMenuShow.class.getResource("/images/Icons/request2.png").toExternalForm()));
        });

        imageView.setOnMouseExited(event -> {
            imageView.setImage(new Image(TradeMenuShow.class.getResource("/images/Icons/request1.png").toExternalForm()));

        });
        imageView.setOnMouseClicked(event -> {
            openMakeRequest(pane, button, width, height);
        });
        hBox.getChildren().addAll(rectangle, button, imageView);
        return hBox;

    }

    private static void openMakeRequest(Pane pane, Button button, double width, double height) {
        if (selectedUser != null) selectedUser.setTextFill(Color.WHITE);
        selectedUser = button;
        button.setTextFill(Color.BLACK);

        if (makeTrade == null) {
            makeTrade = RequestTrade.createRequestPane(pane, button, width, height);
        } else if (selectedUser.getText().equals(RequestTrade.to.getText().substring(5))) {
            pane.getChildren().remove(makeTrade);
            makeTrade = null;
        } else {
            RequestTrade.changeTo(button);
        }

    }

}
