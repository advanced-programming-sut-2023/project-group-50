package view.show.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Map.GUI.MapPane.MapPane;
import model.Map.Unit;

import java.io.Serializable;

public class FireAnimation extends Transition implements Serializable {

    private static final int State1 = 34;
    private static final int State2 = 14;
    private static final int State3 = 16;
    private final Unit unit;
    private transient final Rectangle show;
    private int number;

    public FireAnimation(Unit unit) {
        this.unit = unit;
        this.setCycleDuration(Duration.millis(200));
        this.setCycleCount(-1);
        number = 0;
        MapPane.Pair pair = MapPane.getXY(MapPane.getTileHeight(), MapPane.getTileWidth(),
                                          MapPane.getTopLeftX(), MapPane.getTopLeftY(), unit.getX(), unit.getY());
        show = new Rectangle(pair.x, pair.y, 10, 5);
    }

    @Override
    protected void interpolate(double frac) {

        int pictureNumber = 0;

        if (!unit.isOnFire()) {
            this.stop();
            this.unit.setFireAnimation(null);

        } else {
            switch (unit.getStateFire()) {
                case 1 -> pictureNumber = State1;
                case 2 -> pictureNumber = State2;
                case 3 -> pictureNumber = State3;
            }
        }
        this.number++;
        if (this.number > pictureNumber) {
            this.number = 1;
        }


        show.setFill(new ImagePattern(new Image(
                FireAnimation.class.getResource(
                        "/images/animation/fire/" + unit.getStateFire() + "/" + number + ".png").toExternalForm())));
    }
}
