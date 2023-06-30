package view.show.controller.tradeMenuShowController;

import controller.GUIControllers.GovernmentDataMenuController;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Trade.Trade;
import view.show.controller.StartMenuController;

import java.util.LinkedHashMap;
import java.util.Map;

import static controller.GUIControllers.GovernmentDataMenuController.getBoldText;

public class PreviousTrade {

    private static Pane pane;

    public static Pane createPrevious(double width, double height){
        Pane pane=new Pane ();
        pane.setBackground ( Background.EMPTY );
        pane.setPrefSize ( width, height );
        PreviousTrade.pane=pane;

        ScrollPane scrollPane;
        HBox hBox;

        pane.getChildren ().addAll (
                (scrollPane=  creatScrollTrades (width/2+100, height/2+100 )),
                (hBox=createBar ()) );
        scrollPane.setTranslateX ( width/2-180 );
        scrollPane.setTranslateY ( 160 );
        hBox.setTranslateX ( width/2-50 );
        hBox.setTranslateY ( 40 );

        return pane;
    }

    public static void updateTradeList(){
        double width=pane.getWidth () ;
        double height= pane.getHeight ();
        ScrollPane scrollPane=creatScrollTrades ( width/2+100, height/2+100 );
        scrollPane.setTranslateX ( width/2-180 );
        scrollPane.setTranslateY ( 160 );
        pane.getChildren ().remove ( 0 );
        pane.getChildren ().add ( 0,scrollPane );
    }

    private static ScrollPane creatScrollTrades ( double width , double height) {
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setPrefWidth(width);
            vBox.setAlignment( Pos.CENTER);

            Trade[] trades=new Trade[TradeMenuShowController.tradeMenuController.getCurrentUser ().
                    getTrades ().size ()];
            int i=-1;
            for ( Map.Entry<Integer,Trade> set  :TradeMenuShowController.tradeMenuController.getCurrentUser ().
                    getTrades ().entrySet () ){
                i++;
                trades[i]=set.getValue ();
            }
            for ( ;i>=0;i--){
                vBox.getChildren ().add ( createTradePane ( trades[i] ) );
            }

            return GovernmentDataMenuController.getScrollPane(width, height, vBox);
    }
    private static Pane createTradePane(Trade trade){
        Pane pane=new Pane ();
        pane.setBackground ( new Background ( StartMenuController.setBackGround ( "/images/background/unit.jpg",
                400,200))  );
        pane.setPrefSize ( 400,200  );
        pane.getChildren ().addAll ( createTexts ( trade ) , createAmount ( trade ) );
        return pane;
    }

    private static VBox createTexts(Trade trade){
        VBox vBox=new VBox ();
        Text text=new Text ("To : "+trade.getTo ().getUserName ()+"\nTrade Id : "+
                trade.getId ()+"\nTrade Type : "+trade.getType ());
        text.setFill ( Color.BLACK );
        text.setTextAlignment ( TextAlignment.LEFT );
        text.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,20) );

        Label label=new Label ();
        label.setTextFill ( Color.BLACK );
        label.setTextAlignment ( TextAlignment.CENTER );
        label.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,20) );

        if(trade.isAnswered ()){
            if(trade.isAccepted ()){
                label.setText ( "Confirmation : Accepted" );
                label.setTextFill ( Color.GREEN );
            }else {
                label.setText ( "Confirmation : Rejected" );
                label.setTextFill ( Color.RED );
            }
        }else {
            label.setText ( "Confirmation : --" );
        }

        Text message=new Text ("Message : "+trade.getMessage ());
        if(trade.isAnswered ()){
            message.setText ( "Message : "+trade.getMessage ()+"\nHer Answer : "+trade.getSecondMessage () );
        }
        message.setFill ( Color.BLACK );
        message.setTextAlignment ( TextAlignment.CENTER );
        message.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,20) );


        vBox.setSpacing ( 5 );
        vBox.setAlignment ( Pos.CENTER_LEFT );
        vBox.getChildren ().addAll ( text,label,message );
        vBox.setTranslateX ( 20 );
        vBox.setTranslateY ( 20 );
        return vBox;

    }
    private static VBox createAmount(Trade trade){
        VBox vBox=new VBox ();
        Text text=new Text (trade.getResourceType ().getName ()+"\nAmount : "+trade.getResourceAmount ()
        +"\nPrice : "+trade.getPrice ());
        text.setFill ( Color.BLACK );
        text.setTextAlignment ( TextAlignment.RIGHT );
        text.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,14) );


        ImageView imageView=new ImageView ( trade.getResourceType ().getImage () );
        imageView.setFitWidth ( 70 );
        imageView.setFitHeight ( 70 );
        imageView.setOnMouseEntered ( event ->  {
            imageView.setOpacity ( 0.7 );
        } );

        imageView.setOnMouseExited( event -> {
            imageView.setOpacity ( 1 );
        });


        vBox.setSpacing ( 10 );
        vBox.setAlignment ( Pos.CENTER );
        vBox.getChildren ().addAll ( imageView,text );
        vBox.setTranslateX ( 300 );
        vBox.setTranslateY ( 20 );
        return vBox;
    }



    private static HBox   createBar () {
        return getBoldText("Your Trades",450 );
    }
}
