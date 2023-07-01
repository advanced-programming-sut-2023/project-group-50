package model.Massenger;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public enum Reaction implements Serializable {
    LOL,
    CRY,
    MAD,
    AGREE,
    DISAGREE,
    LIKE;

    public ImageView getEmoji(double size) {
        Image image = new Image(Reaction.class.getResource(
                "/images/reactions/" + this.name() + ".png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(size);
        return imageView;
    }
}
