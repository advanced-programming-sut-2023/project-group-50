package view.show.controller.tradeMenuShowController;

import controller.GUIControllers.GovernmentDataMenuController;
import controller.control.Error;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Trade.Trade;
import model.Trade.TradeMarket;
import view.show.controller.StartMenuController;

import java.util.Map;

import static controller.GUIControllers.GovernmentDataMenuController.getBoldText;

public class NewTrades {
    private static Pane pane;
    private  static VBox menu;

    private static HBox buttonsBox;
    private static HBox confirmationBox;
    private static HBox messageBox;
    public static Pane createNews(double width, double height){
        Pane pane=new Pane ();
        pane.setBackground ( Background.EMPTY );
        pane.setPrefSize ( width, height );
        NewTrades.pane=pane;

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
        menu=vBox;
        vBox.setSpacing(10);
        vBox.setPrefWidth(width);
        vBox.setAlignment( Pos.CENTER);

        for ( Map.Entry<Integer,Trade> set  : TradeMarket.getTrades ().entrySet () ){
          if ( set.getValue ().getTo ().getUserName ().equals ( TradeMenuShowController.tradeMenuController.getCurrentUser ().getUserName () ) )
              vBox.getChildren ().add (0,createTradeBox ( set.getValue () ) );
        }


        return GovernmentDataMenuController.getScrollPane(width, height, vBox);
    }
    private static Pane createTradeBox( Trade trade){
        Pane pane=new Pane ();
        pane.setBackground ( new Background ( StartMenuController.setBackGround ( "/images/background/tynk.jpg",
                400,50))  );
        pane.setPrefSize ( 400,50  );
        pane.getChildren ().addAll ( createBox (trade),getUpDown ( false,pane,trade ) );
        return pane;
    }
    private static HBox createBox(Trade trade){
        HBox hBox=new HBox ();
        Text text=new Text ("From : "+trade.getFrom ().getUserName ()+"       Id : "+trade.getId ());
        text.setFill ( Color.BLACK );
        text.setTextAlignment ( TextAlignment.CENTER );
        text.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,20) );

        Label label=new Label ("New");
        label.setTextFill ( Color.RED );
        label.setTextAlignment ( TextAlignment.RIGHT );
        label.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,15) );


        hBox.getChildren ().add ( text );
        if(!trade.isSeen ()){
            hBox.getChildren ().add ( label );
        }

        hBox.setAlignment ( Pos.CENTER );
        hBox.setSpacing ( 30 );
        hBox.setTranslateX ( 20 );
        hBox.setTranslateY ( 10 );
        return hBox;
    }
    private static ImageView getUpDown(boolean up,Pane pane,Trade trade){
        ImageView imageView=new ImageView ();
        imageView.setFitHeight ( 30 );
        imageView.setFitWidth ( 30 );
        imageView.setTranslateX ( 369 );
        if(up){
            imageView.setImage (new Image (  NewTrades.class.getResource ( "/images/Icons/up.png" ).toExternalForm ()) );
            imageView.setTranslateY ( 180 );
        }else {
            imageView.setImage (new Image (  NewTrades.class.getResource ( "/images/Icons/down.png" ).toExternalForm ()) );
            imageView.setTranslateY ( 10 );

        }
        imageView.setOnMouseEntered ( event ->  {
            imageView.setOpacity ( 0.7 );
        } );

        imageView.setOnMouseExited( event -> {
            imageView.setOpacity ( 1 );
        });

        imageView.setOnMouseClicked ( event -> {
            int i=menu.getChildren ().indexOf ( pane );
            menu.getChildren ().remove ( pane );
            if(up){
                menu.getChildren ().add ( i,createTradeBox ( trade ) );
                confirmationBox=null;
                buttonsBox=null;
            }else {
                menu.getChildren ().add ( i,createTradePane ( trade ) );
                trade.setSeen ( true);
            }
        } );




        return imageView;
    }

    private static HBox textFieldMessage(Trade trade){
        HBox hBox=new HBox ();
        Label label=new Label ("Answer : ");
        label.setTextFill ( Color.BLACK );
        label.setTextAlignment ( TextAlignment.CENTER );
        label.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,15) );
        hBox.getChildren ().addAll ( label );

        if(!trade.isAnswered ()) {
            TextArea message = new TextArea ();

            message.setFont ( Font.font ( "Times New Roman" , FontWeight.SEMI_BOLD , 15 ) );
            message.setPrefSize ( 120 , 50 );
            message.setBackground ( Background.EMPTY );
            message.setBorder ( Border.stroke ( Color.BLACK ) );
            hBox.getChildren ().add ( message );
        }else {
            label.setText ( "Answer : "+trade.getSecondMessage () );
        }
        hBox.setSpacing ( 7 );
        hBox.setAlignment ( Pos.CENTER_LEFT );
        messageBox=hBox;
        return hBox;
    }



    private static Pane createTradePane(Trade trade){
        Pane pane=new Pane ();
        pane.setBackground ( new Background ( StartMenuController.setBackGround ( "/images/background/tynk.jpg",
                400,230))  );
        pane.setPrefSize ( 400,230  );
        VBox vBox;

        pane.getChildren ().addAll (vBox= new VBox (createTexts ( trade )
                ,textFieldMessage (trade),createConfirmationBox ( trade )),createAmount ( trade ),getUpDown ( true,pane,trade ) );
        vBox.setAlignment ( Pos.CENTER_LEFT );
        vBox.setSpacing ( 14 );
        vBox.setTranslateX ( 15 );
        vBox.setTranslateY ( 20 );


        return pane;
    }

    private static Text createTexts(Trade trade){
        Text text=new Text ("From : "+trade.getFrom ().getUserName ()+"\nTrade Id : "+
                trade.getId ()+"\nTrade Type : "+trade.getType ()+"\nMessage : "+trade.getMessage ());
        text.setFill ( Color.BLACK );
        text.setTextAlignment ( TextAlignment.LEFT );
        text.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,15) );
        return text;

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
        vBox.setTranslateX ( 320 );
        vBox.setTranslateY ( 20 );
        return vBox;
    }


    private static HBox createConfirmationBox(Trade trade){

        Label label=new Label ("Confirmation : ");
        label.setTextAlignment ( TextAlignment.CENTER );
        label.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,15) );
        label.setTextFill ( Color.BLACK );
        HBox hBox=new HBox (label);
        confirmationBox=hBox;
        if( !trade.isAnswered () ){
            hBox.getChildren ().add ( buttons ( trade ) );
        }else {
            hBox.getChildren ().add ( getResult ( trade ) );
        }

        hBox.setAlignment ( Pos.CENTER );
        hBox.setSpacing ( 16 );
        return hBox;

    }



    private static HBox buttons(Trade trade){
        HBox hBox=new HBox ();
        buttonsBox=hBox;
        hBox.getChildren ().addAll ( getButton ( "Accept",trade ),getButton ( "Reject",trade ) );
        hBox.setAlignment ( Pos.CENTER );
        hBox.setSpacing ( 12 );
        return hBox;
    }

    private static Button getButton(String type,Trade trade){
        Button button = new Button(type);
        if(type.equals ( "Accept" )) {
            button.setStyle ( "-fx-background-color: green; -fx-background-radius: 20;" +
                    " -fx-text-fill: black; -fx-font: 15 System" );
        }else {
            button.setStyle ( "-fx-background-color: red; -fx-background-radius: 20;" +
                    " -fx-text-fill: black; -fx-font: 15 System" );
        }
        button.setPrefWidth(100);
        button.setPrefHeight(30);
        button.setTextAlignment ( TextAlignment.CENTER );


        button.setOnMouseEntered ( event ->  {
            button.setOpacity ( 0.7 );
        } );

        button.setOnMouseExited( event -> {
            button.setOpacity ( 1 );
        });

        button.setOnMouseClicked ( event -> {

            if(type.equals ( "Accept" )) {
                accept ( trade );

            }else {
                reject ( trade );
            }
            TradeMenuShowController.updateCoins ();
            PreviousTrade.updateTradeList ();
        } );



        return button;
    }


    private static void accept(Trade trade){
        Error error=TradeMenuShowController.tradeMenuController.acceptTrade ( trade.getId (),
                ((TextArea)messageBox.getChildren ().get ( 1 )).getText ());
        if(!error.truth){
            new Alert ( Alert.AlertType.ERROR, error.errorMassage,ButtonType.OK ).show ();
        }else {
            trade.setAnswered ( true );
            trade.setAccepted ( true );
            clearButton (trade);
            new Alert ( Alert.AlertType.INFORMATION, error.errorMassage,ButtonType.FINISH ).show ();
        }




    }

    private static void reject(Trade trade){
        trade.setAnswered ( true );
        trade.setAccepted ( false );
        clearButton (trade);

    }

    private static void clearButton(Trade trade){
        Label label = getResult ( trade );
        confirmationBox.getChildren ().remove ( buttonsBox );
            confirmationBox.getChildren ().add ( 1,label );

        ((Label)messageBox.getChildren ().get ( 0 )).setText ("Your Answer : "+((TextArea)messageBox.getChildren ().get ( 1 )).getText () );
         messageBox.getChildren ().remove ( 1 );


    }

    private static Label getResult (Trade trade) {
        Label label=new Label ();
        label.setTextAlignment ( TextAlignment.CENTER );
        label.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,15) );

        if( trade.isAccepted ()){
                label.setText ( "Accepted" );
                label.setTextFill ( Color.GREEN );
            }else {
                label.setText ( "Rejected" );
                label.setTextFill ( Color.RED );
            }
        return label;
    }


    private static HBox   createBar () {
        return getBoldText("My Suggestions",450 );
    }
}
