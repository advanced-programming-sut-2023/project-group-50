package view.show.Menus;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import view.show.MusicPlayer;

public class MenuButton extends StackPane {

    public MenuButton(String name) {
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(MenuButton.class.getResource("/images/Buttons/bg.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(150, 50, false, false, false, false)

        );

        setBackground(new Background(backgroundImage));
        setPrefSize(150, 50);

        Text text = new Text(name);
        text.setFill(Color.YELLOW);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));

        getChildren().addAll(text);
        setOnMouseEntered(event -> {
            text.setFill(Color.WHITE);
        });
        setOnMouseExited(event -> {
            text.setFill(Color.YELLOW);
        });
        setOnMousePressed(event -> {
            text.setFill(Color.AZURE);
            MusicPlayer.playMusic(getClass().getResource("/images/click-button-131479.mp3").toString());
        });
        setOnMouseReleased(event -> {
            text.setFill(Color.YELLOW);
        });
        setAlignment(Pos.CENTER);
    }


}


