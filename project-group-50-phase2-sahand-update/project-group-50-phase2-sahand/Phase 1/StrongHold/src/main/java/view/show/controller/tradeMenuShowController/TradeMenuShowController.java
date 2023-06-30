package view.show.controller.tradeMenuShowController;

import controller.GUIControllers.ProfileMenuGUIController;
import controller.Menus.ShopMenuController;
import controller.Menus.TradeMenuController;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import view.show.Menus.ShopMenuShow;
import view.show.controller.ShopMenuShowController;
import view.show.controller.StartMenuController;
import view.show.trademenu.TradeMenuShow;

import static controller.GUIControllers.ProfileMenuGUIController.getBackButton;
import static view.show.controller.tradeMenuShowController.MakeTradeMenu.createMakeTrade;

public class TradeMenuShowController {
    public static TradeMenuController tradeMenuController;
    private static Text coins;




    public Parent createContent(){
        Pane pane=new Pane ();
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        pane.setPrefSize(width, height);
        pane.setBackground( new Background ( StartMenuController.setBackGround ("/phase2-assets/background/backgrounds/Backgrounds For Menus - yac/z3qbm9.jpg",width, height)));
        StackPane stackPane=new StackPane ( ProfileMenuGUIController.getDataBackgroundImage(0.9*height,0.8* width),
                createPanes ( 0.8*0.7* width ,0.9*0.75*height));
        pane.getChildren ().add ( stackPane);
        stackPane.setLayoutX(width * 0.1);
        stackPane.setLayoutY(height * 0.05);
        createCoins ( pane );
        pane.getChildren ().add ( initBackButton () );
        return pane;
    }


    private TabPane createPanes(double width,double height){
        TabPane tabPane=new TabPane ();
        tabPane.setBackground ( Background.EMPTY );
        tabPane.setPrefSize ( width, height );
        tabPane.setLayoutX ( 50 );
        tabPane.setLayoutY ( 100 );

        Tab makeTrade=new Tab ("Make new Trade");
        makeTrade.setClosable ( false );
        makeTrade.setContent ( createMakeTrade (width, height ) );


        Tab yourTrade =new Tab ("My Trade");
        yourTrade.setClosable ( false );
        yourTrade.setContent ( PreviousTrade.createPrevious ( width, height ) );


        Tab mySuggestion =new Tab ("My Suggestion");
        mySuggestion.setClosable ( false );
        mySuggestion.setContent ( NewTrades.createNews ( width, height ) );

        tabPane.getTabs ().addAll ( makeTrade,yourTrade, mySuggestion );
        return tabPane;
    }





    private Button initBackButton() {
        Button backButton = getBackButton( TradeMenuShowController:: showShopMenu );
        backButton.setLayoutY(25);
        backButton.setLayoutX(Screen.getPrimary().getBounds().getWidth() - 125);
        return backButton;
    }

    private static void showShopMenu (ActionEvent actionEvent) {
        ShopMenuShow shopMenuShow= new ShopMenuShow ();
        shopMenuShow.init ( TradeMenuShowController.tradeMenuController.getCurrentUser () );
        try {
            shopMenuShow.start ( TradeMenuShow.stage );
        } catch (Exception e) {
            throw new RuntimeException ( e );
        }
    }

    public static void createCoins(Pane pane){
        HBox hBox=new HBox ();
    Text text=new Text ("Coins : "+ TradeMenuShowController.tradeMenuController.getCurrentUser ().getGovernment ().getCoins () );
        text.setTextAlignment ( TextAlignment.CENTER );
        text.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,20) );
    TradeMenuShowController.coins=text;

    ImageView imageView=new ImageView (new Image (
            ShopMenuController.class.getResource ( "/images/Resources/Coins.png" ).toExternalForm () ));
        imageView.setFitWidth ( 30 );
        imageView.setFitHeight ( 30 );

        hBox.getChildren ().addAll ( imageView,text );
        hBox.setAlignment ( Pos.CENTER );
        hBox.setSpacing ( 5 );
        pane.getChildren ().add ( hBox );
        hBox.setTranslateX ( 1100 );
        hBox.setTranslateY ( 100 );

    }
    public static void updateCoins(){
        TradeMenuShowController.coins.setText ( "Coins : "+ TradeMenuShowController.tradeMenuController.getCurrentUser ()
                .getGovernment ().getCoins ()  );
    }
}
