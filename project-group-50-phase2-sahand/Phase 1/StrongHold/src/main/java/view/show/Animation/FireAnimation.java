package view.show.Animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;
import model.Government.Government;
import model.Map.GUI.MapPane.MapPane;
import model.Map.Unit;

public class FireAnimation extends Transition {

    private final Unit unit;
    private Rectangle show;
    private static final int State1=34;
    private static final int State2=14;

    private static final int State3=16;
    private  int number;
    public FireAnimation(Unit unit){
        this.unit=unit;
        this.setCycleDuration( Duration.millis(2000));
        this.setCycleCount(-1);
        number=0;

        MapPane.Pair pair= MapPane.getXY ( MapPane.getTileHeight (),MapPane.getTileWidth (),
                MapPane.getTopLeftX (),MapPane.getTopLeftY (),unit.getX (),unit.getY () );
        show=new Rectangle (pair.x,pair.y,MapPane.getTileWidth ()*1.5,MapPane.getTileHeight ()*1.5);

    }

    @Override
    protected void interpolate (double frac) {

        int pictureNumber=0;

        if(!unit.isOnFire ()){
            this.stop ();
            MapPane.pane.getChildren ().remove ( this.show );
            this.unit.setFireAnimation ( null );

        }else {
            switch (unit.getStateFire ()){
                case 1 -> pictureNumber=State1;
                case 2 -> pictureNumber=State2;
                case 3 -> pictureNumber=State3;
            }
        }
        this.number++;
        if(this.number / 7 + 1>pictureNumber){
            this.number=1;
        }

        show.setFill ( new ImagePattern ( new Image (
                FireAnimation.class.getResource ( "/images/animation/fire/"+unit.getStateFire ()+"/"+(number/7 + 1)+".png" ).toExternalForm () ) ) );
    }

    public void setShowSize(double width,double height,double x,double y ){
        this.show.setWidth ( width*1.5 );
        this.show.setHeight ( height*1.5 );
        this.show.setTranslateY ( y );
        this.show.setTranslateX ( x );
        if(!MapPane.pane.getChildren ().contains ( this.show )) MapPane.pane.getChildren ().add ( this.show );
    }


}
