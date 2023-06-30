package view.show.Menus;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import view.show.MusicPlayer;

public class MenuButton extends StackPane {

        public MenuButton (String name) {
            Rectangle bg = new Rectangle(110,35);
            bg.setOpacity(1);

            bg.setFill ( new ImagePattern (new Image ( MenuButton.class.getResource("/images/background/button.png").toExternalForm())) );
            Text text = new Text(name);
            text.setFill( Color.BLACK);
            text.setFont( Font.font("Times New Roman", FontWeight.SEMI_BOLD,20));
            text.setTranslateX ( bg.getTranslateX ()+30-3.75*name.length ()/2 );
            text.setTranslateY ( bg.getTranslateY ()+5 );

            setAlignment( Pos.CENTER);
            getChildren().addAll(bg, text);
            setOnMouseEntered(event -> {
                text.setFill(Color.WHITE);
                bg.setOpacity ( 0.7 );
            });
            setOnMouseExited(event -> {
                bg.setOpacity ( 1 );
                text.setFill(Color.BLACK);
            });
            setOnMousePressed(event -> {
                text.setFill(Color.AZURE);
                MusicPlayer.playMusic ( getClass ().getResource ( "/images/click-button-131479.mp3" ).toString () );
            });
            setOnMouseReleased(event -> {
                bg.setOpacity ( 1 );
                text.setFill(Color.BLACK);
            });
        }




    }


