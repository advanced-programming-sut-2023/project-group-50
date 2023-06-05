package model.Map.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Map.Map;

import java.net.URL;

public class MiniMap {
    private final Map map;

    public MiniMap(Map map) {
        this.map = map;
    }

    private ImageView getBackground(double height, double width) {
        String path = "/phase2-assets/Tiles/palki2.jpg";
        URL url = MiniMap.class.getResource(path);
        Image image = new Image(url.toExternalForm());

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        return imageView;
    }

    public Pane getMiniMap(double height, double width) {
        StackPane pane = new StackPane();

        pane.setPrefSize(width, height);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(getBackground(height, width), getVBox(height * 0.9, width * 0.9));
        pane.setStyle("-fx-border-color: black;\n" +
                              "    -fx-border-style: solid;\n" +
                              "    -fx-border-width: 2.5;");

        return pane;
    }

    private VBox getVBox(double height, double width) {
        VBox vBox = new VBox();

        double pxHeight = height / map.getYSize();
        double pxWidth = width / map.getXSize();
        for (int y = 0; y < map.getYSize(); y++)
            vBox.getChildren().add(getHBox(pxWidth, pxHeight, y));

        vBox.setMaxSize(width, height);
        vBox.setPrefSize(width, height);
        vBox.setStyle("-fx-border-color: black;\n" +
                              "    -fx-border-style: solid;\n" +
                              "    -fx-border-width: 2;");

        return vBox;
    }

    private Node getHBox(double pxWidth, double pxHeight, int y) {
        HBox hBox = new HBox();

        for (int x = 0; x < map.getXSize(); x++) {
            if (map.getUnitByXY(x, y).hasPalace())
                hBox.getChildren().add(getPixel(pxWidth, pxHeight, map.getUnitByXY(x, y).getPalaceOwnerColor()));
            else if (map.getUnitByXY(x, y).hasBuilding())
                hBox.getChildren().add(getPixel(pxWidth, pxHeight, Color.SADDLEBROWN));
            else if (map.getUnitByXY(x, y).getTexture().isGreen())
                hBox.getChildren().add(getPixel(pxWidth, pxHeight, Color.LAWNGREEN));
            else if (map.getUnitByXY(x, y).getTexture().isWater())
                hBox.getChildren().add(getPixel(pxWidth, pxHeight, Color.BLUE));
            else
                hBox.getChildren().add(getPixel(pxWidth, pxHeight, Color.DARKKHAKI));
        }

        return hBox;
    }

    private Pane getPixel(double pxWidth, double pxHeight, Color color) {
        Pane pane = new Pane();
        pane.setPrefSize(pxWidth, pxHeight);
        pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        return pane;
    }
}
