package view.show.Animation;

import javafx.animation.Transition;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Map.GUI.MapPane.MapPane;
import model.Map.Unit;

public class HealAnimation extends Transition {
    private final Unit unit;
    private Rectangle show;
    private static final int State1=16;
    private  int number;
    public HealAnimation(Unit unit){
        this.unit=unit;
        this.setCycleDuration( Duration.millis(2000));
        this.setCycleCount(1);
        number=0;

        MapPane.Pair pair= MapPane.getXY ( MapPane.getTileHeight (),MapPane.getTileWidth (),
                MapPane.getTopLeftX (),MapPane.getTopLeftY (),unit.getX (),unit.getY () );
        this.show=new Rectangle ();
        if(MapPane.pane!=null) setShowSize ( pair.x,pair.y,MapPane.getTileWidth ()*1.5,MapPane.getTileHeight ()*1.5 );
        this.setOnFinished ( event -> {
            this.unit.setHealAnimation ( null );
            MapPane.pane.getChildren ().remove ( this.show );

        } );
    }

    @Override
    protected void interpolate (double frac) {

        this.number++;
        if(this.number/5 + 1>State1){
            this.number=1;
        }
        show.setFill ( new ImagePattern ( new Image (
                FireAnimation.class.getResource ( "/images/animation/heal/"+(number / 5 + 1)+".png" ).toExternalForm () ) ) );

    }


    public void setShowSize(double width,double height,double x,double y ){
        this.show.setWidth ( width*1.5 );
        this.show.setHeight ( height*1.5 );
        this.show.setTranslateY ( y );
        this.show.setTranslateX ( x );
        if(!MapPane.pane.getChildren ().contains ( this.show )) MapPane.pane.getChildren ().add ( this.show );
    }


}
