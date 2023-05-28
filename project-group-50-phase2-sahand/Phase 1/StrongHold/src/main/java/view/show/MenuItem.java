package view.show;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuItem extends StackPane {

        public MenuItem(String name) {
            Rectangle bg = new Rectangle(200,30);
            bg.setOpacity(0);
            Text text = new Text(name);
            text.setFill( Color.BLACK);
            text.setFont( Font.font("Times New Roman", FontWeight.SEMI_BOLD,20));

            setAlignment( Pos.CENTER);
            getChildren().addAll(bg, text);
            setOnMouseEntered(event -> {
                text.setFill(Color.GOLD);
            });
            setOnMouseExited(event -> {
                text.setFill(Color.BLACK);
            });
            setOnMousePressed(event -> {
                text.setFill(Color.WHITE);

            });
            setOnMouseReleased(event -> {
                bg.setFill(Color.BLACK);
            });
        }




    }


