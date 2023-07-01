package view.show.controller.tradeMenuShowController;

import controller.GUIControllers.GovernmentDataMenuController;
import controller.UserDatabase.Users;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.ObjectsPackage.Resource;
import model.Trade.Trade;
import model.Trade.TradeMarket;
import view.show.controller.StartMenuController;

import java.util.regex.Pattern;

import static controller.GUIControllers.GovernmentDataMenuController.getBoldText;

public class RequestTrade {

    public static Text to;
    private static Pane currentPane;
    private static Pane parentPane;
    private static Label chosenResource;
    private static Button chosenType;
    private static TextField price;
    private static Label amount;
    private static TextArea message;

    public static Pane createRequestPane(Pane parent, Button selectedUser, double width, double height) {
        Pane pane = new Pane();
        pane.setPrefSize(width - 30, height + 150);
        pane.setBackground(new Background(StartMenuController.setBackGround("/images/background/unit.jpg",
                                                                            width - 30, height + 150)));
        RequestTrade.currentPane = pane;
        RequestTrade.parentPane = parent;
        ScrollPane scrollPane;
        pane.getChildren().addAll(scrollPane = creatScroll(width / 2, height / 2), textField(selectedUser));
        scrollPane.setTranslateX(100);
        scrollPane.setTranslateY(20);
        parent.getChildren().add(pane);
        pane.setTranslateX(width + 25);
        pane.setTranslateY(115);
        return pane;
    }

    private static ScrollPane creatScroll(double width, double height) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPrefWidth(width);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(getBoldText("Resources", width));

        int i = 0;
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);
        for (Resource resource : Resource.values()) {
            if (i == 3) {
                i = 0;
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(15);
            }
            i++;
            hBox.getChildren().add(createResourceBox(resource));
        }
        return GovernmentDataMenuController.getScrollPane(width, height, vBox);
    }


    private static VBox createResourceBox(Resource resource) {
        VBox vBox = new VBox();
        Label label = new Label(resource.getName());
        label.setTextFill(Color.BLACK);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 15));

        ImageView imageView = createImageResource(resource, label);

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(7);
        vBox.getChildren().addAll(imageView, label);
        return vBox;
    }

    private static ImageView createImageResource(Resource resource, Label label) {
        ImageView imageView = new ImageView(resource.getImage());
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        imageView.setOnMouseEntered(event -> {
            imageView.setOpacity(0.7);
        });

        imageView.setOnMouseExited(event -> {
            imageView.setOpacity(1);
        });
        imageView.setOnMouseClicked(event -> {
            label.setTextFill(Color.GOLD);
            if (chosenResource != null) chosenResource.setTextFill(Color.BLACK);
            chosenResource = label;
        });
        return imageView;
    }

    private static VBox textField(Button button) {
        VBox vBox = new VBox();
        Text from = new Text("From : " + TradeMenuShowController.tradeMenuController.getCurrentUser().getUserName());
        Text to = new Text("To : " + button.getText());
        from.setTextAlignment(TextAlignment.CENTER);
        to.setTextAlignment(TextAlignment.CENTER);
        from.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 15));
        to.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 15));
        RequestTrade.to = to;
        VBox textBox = new VBox(from, to);
        textBox.setSpacing(5);
        textBox.setAlignment(Pos.CENTER);

        HBox messageBox = textFieldMessage();
        HBox amount = amountBox();
        HBox price = priceTextField();
        HBox buttons = typeButton();
        Button submit = submitButton();
        HBox box = new HBox(submit);
        box.setAlignment(Pos.CENTER);


        vBox.getChildren().addAll(textBox, amount, price, messageBox, buttons, box);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        vBox.setTranslateX(85);
        vBox.setTranslateY(230);
        return vBox;
    }

    public static void changeTo(Button button) {
        RequestTrade.to.setText("To : " + button.getText());
    }

    private static HBox textFieldMessage() {
        HBox hBox = new HBox();
        Label label = new Label("Message : ");
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 15));

        TextArea message = new TextArea();
        RequestTrade.message = message;
        message.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 15));
        message.setPrefSize(150, 50);
        message.setBackground(Background.EMPTY);
        message.setBorder(Border.stroke(Color.BLACK));

        hBox.setSpacing(7);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(label, message);
        return hBox;
    }

    private static HBox amountBox() {
        HBox hBox = new HBox();
        Label name = new Label("Amount Number : ");
        name.setTextAlignment(TextAlignment.CENTER);
        name.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 15));

        Label plus = new Label("+");
        plus.setTextAlignment(TextAlignment.CENTER);
        plus.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));

        Label mines = new Label("-");
        mines.setTextAlignment(TextAlignment.CENTER);
        mines.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));

        Label number = new Label("0");
        number.setTextAlignment(TextAlignment.CENTER);
        number.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
        RequestTrade.amount = number;
        plus.setOnMouseEntered(event -> {
            plus.setOpacity(0.7);
        });

        plus.setOnMouseExited(event -> {
            plus.setOpacity(1);
        });
        plus.setOnMouseClicked(event -> {
            number.setText(String.valueOf(Integer.parseInt(number.getText()) + 1));
        });


        plus.setOnMouseDragged(event -> {
            number.setText(String.valueOf(Integer.parseInt(number.getText()) + 1));
        });
        mines.setOnMouseEntered(event -> {
            mines.setOpacity(0.7);
        });

        mines.setOnMouseExited(event -> {
            mines.setOpacity(1);
        });
        mines.setOnMouseClicked(event -> {
            if (Integer.parseInt(number.getText()) > 0)
                number.setText(String.valueOf(Integer.parseInt(number.getText()) - 1));

        });
        mines.setOnMouseDragged(event -> {
            if (Integer.parseInt(number.getText()) > 0)
                number.setText(String.valueOf(Integer.parseInt(number.getText()) - 1));
        });


        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(name, plus, number, mines);
        return hBox;

    }

    private static HBox priceTextField() {
        HBox hBox = new HBox();
        Label name = new Label("Price : ");
        name.setTextAlignment(TextAlignment.CENTER);
        name.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 15));

        TextField price = new TextField("");
        RequestTrade.price = price;
        price.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 15));
        price.setPrefSize(150, 30);
        price.setBackground(Background.EMPTY);
        price.setBorder(Border.stroke(Color.BLACK));


        price.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Pattern.compile("\\D").matcher(newValue).find()) price.setText(oldValue);
        });


        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(name, price);
        return hBox;

    }

    private static HBox typeButton() {
        HBox hBox = new HBox();
        Button request = buttonTradeType("Request");
        Button donate = buttonTradeType("Donate");


        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.getChildren().addAll(request, donate);
        return hBox;
    }

    private static Button buttonTradeType(String name) {
        Button button = new Button(name);


        button.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 15));
        button.setTextFill(Color.WHITE);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setPrefSize(130, 40);
        button.setBackground(new Background(StartMenuController.setBackGround("/images/background/button.png", 130, 40)));


        button.setOnMouseEntered(event -> {
            button.setOpacity(0.7);
        });

        button.setOnMouseExited(event -> {
            button.setOpacity(1);
        });

        button.setOnMouseClicked(event -> {
            button.setTextFill(Color.BLACK);
            if (chosenType != null) chosenType.setTextFill(Color.WHITE);
            RequestTrade.chosenType = button;
        });

        return button;
    }

    private static Button submitButton() {
        Button button = new Button("Submit");


        button.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 15));
        button.setTextFill(Color.WHITE);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setPrefSize(130, 40);
        button.setBackground(new Background(StartMenuController.setBackGround("/images/background/button.png", 130, 40)));


        button.setOnMouseEntered(event -> {
            button.setOpacity(0.7);
        });

        button.setOnMouseExited(event -> {
            button.setOpacity(1);
            button.setTextFill(Color.WHITE);

        });

        button.setOnMouseClicked(event -> {
            button.setTextFill(Color.BLACK);

            submit();
        });

        return button;
    }

    private static void submit() {
        if (chosenResource == null) {
            new Alert(Alert.AlertType.ERROR, "Please set a Resource for trade", ButtonType.OK).show();
            return;
        }

        if (price.getText().trim().equals("")) {
            new Alert(Alert.AlertType.ERROR, "Please enter a price for trade", ButtonType.OK).show();
            return;
        }

        if (chosenType == null) {
            new Alert(Alert.AlertType.ERROR, "Please chose a type for trade", ButtonType.OK).show();
            return;
        }

        Trade trade = new Trade(TradeMenuShowController.tradeMenuController.getCurrentUser(),
                                Users.getUser(to.getText().substring(5)),
                                TradeMarket.getNextId(), Integer.parseInt(price.getText().trim()),
                                Resource.getResourceByString(chosenResource.getText())
                , Integer.parseInt(amount.getText()), message.getText(), chosenType.getText());
        TradeMarket.addTrade(trade);
        new Alert(Alert.AlertType.INFORMATION, "Making Trade Request was successful", ButtonType.FINISH).show();
        parentPane.getChildren().remove(currentPane);
        PreviousTrade.updateTradeList();
        NewTrades.updateTradeList();
        MakeTradeMenu.makeTrade = null;
        MakeTradeMenu.selectedUser.setTextFill(Color.WHITE);
        MakeTradeMenu.selectedUser = null;


    }


}
