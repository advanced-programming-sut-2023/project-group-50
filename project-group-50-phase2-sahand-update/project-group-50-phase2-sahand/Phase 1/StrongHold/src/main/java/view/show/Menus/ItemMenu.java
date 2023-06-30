package view.show.Menus;

import controller.Menus.ShopMenuController;
import controller.UserDatabase.Shop;
import controller.UserDatabase.User;
import controller.control.Error;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Item.Item;
import model.ObjectsPackage.Resource;
import view.show.controller.ShopMenuShowController;
import view.show.controller.StartMenuController;

import static javafx.geometry.Pos.CENTER;

public class ItemMenu extends Pane {


    private Resource resource;
    private User user;
    private int count;

    private Button sell;
    private Button buy;

    private Button value;
    private VBox menu;

    public ItemMenu(Resource resource,VBox menu){
        this.resource=resource;
        this.count=1;
        this.menu=menu;
        this.user=ShopMenuShowController.shopMenuController.getCurrentUser ();
        this.setPrefSize ( 300,250 );
        this.setBackground (new Background (   StartMenuController.setBackGround ( "/images/background/unit.jpg",300,250 )) );
        VBox vBox=new VBox (getImage (),getSlider (),buttons ());
        vBox.setAlignment ( CENTER );
        vBox.setSpacing ( 7 );
        vBox.setPrefSize ( 300,200 );
        vBox.setTranslateX ( 0 );
        vBox.setTranslateY ( 50 );


        HBox hBox=getBar ();
        hBox.setTranslateX ( 0 );
        hBox.setTranslateY ( 0 );
        hBox.setPrefSize ( 300,50 );
        this.getChildren ().addAll ( hBox,vBox );
    }


    private HBox buttons(){
        HBox hBox=new HBox ();
        hBox.setSpacing ( 7 );
        hBox.setAlignment ( CENTER );

        Button sell=buttonBuySell ( "Sell" );
        Button buy=buttonBuySell ( "Buy" );

        this.buy=buy;
        this.sell=sell;
        hBox.getChildren ().addAll ( sell,buy );
        return hBox;
    }
    private Button buttonBuySell(String name){
        Button button=new Button ();
        String text;
        if(name.equals ( "Buy" )) {
           text = name + "    " + this.resource.getBuyingPrice () ;
        }else {
            text = name + "    " + this.resource.getSellingPrice () ;
        }
        button.setText ( text );
        button.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,15) );
        button.setTextFill ( Color.WHITE );
        button.setTextAlignment ( TextAlignment.CENTER );
        button.setPrefSize ( 130,40 );
        button.setBackground ( new Background ( StartMenuController.setBackGround ( "/images/background/button.png",130,40 ) ) );


        button.setOnMouseEntered ( event ->  {
                button.setOpacity ( 0.7 );
        } );

        button.setOnMouseExited(event -> {
            button.setOpacity ( 1 );
           button.setTextFill (Color.WHITE);
        });

        button.setOnMouseClicked ( event -> {
            button.setTextFill ( Color.BLACK );
            if(name.equals ( "Buy" )){
                buy ();
            }else {
                sell ();
            }
        } );

        return button;
    }

    private void buy(){
        Item item=new Item ( this.resource.getBuyingPrice (),this.resource.getName (), this.count);
        Shop.getItems ().add ( item );
        Error result= ShopMenuShowController.shopMenuController.buy ( this.resource.getName () );
        if(result.truth){
            new Alert ( Alert.AlertType.INFORMATION , result.errorMassage).show ();
        }else {
            new Alert ( Alert.AlertType.WARNING , result.errorMassage).show ();
        }
        this.value.setText ( resource.getName ()+"    "+this.user.getGovernment ().getResourceAmount ( resource ) );
        ShopMenuShowController.coins.setText ( "Coin : "+ ShopMenuShowController.shopMenuController.getCurrentUser ().getGovernment ().getCoins () );
    }

    private void sell(){
        ShopMenuShowController.shopMenuController.addItem ( this.resource.getName (),this.resource.getSellingPrice (),this.count );
        Error result= ShopMenuShowController.shopMenuController.sell ( this.resource.getName () );
        if(result.truth){
            new Alert ( Alert.AlertType.INFORMATION , result.errorMassage).show ();
        }else {
            new Alert ( Alert.AlertType.WARNING , result.errorMassage).show ();
        }
        this.value.setText ( resource.getName ()+"    "+this.user.getGovernment ().getResourceAmount ( resource ) );
        ShopMenuShowController.coins.setText ( "Coin : "+ ShopMenuShowController.shopMenuController.getCurrentUser ().getGovernment ().getCoins () );
    }


    private Node getImage(){
        Rectangle rectangle=new Rectangle (70,70);
        rectangle.setFill ( new ImagePattern ( resource.getImage ()) );
        rectangle.setOnMouseEntered ( event ->  {
            rectangle.setOpacity ( 0.7 );
        } );

        rectangle.setOnMouseExited(event -> {
            rectangle.setOpacity ( 1 );
        });

        return rectangle;
    }


    private VBox getSlider(){

        int max=(int) (this.user.getGovernment ().getCoins ()/this.resource.getBuyingPrice ());
        if(this.user.getGovernment ().getResourceAmount ( this.resource )>max){
            max=this.user.getGovernment ().getResourceAmount ( this.resource );
        }
        Slider slider=new Slider (1,max,1);
        slider.setMaxWidth ( 140 );
        slider.setShowTickMarks(false);
        slider.setShowTickLabels(false);
//        slider.setBlockIncrement(1);
        Label label=new Label (String.valueOf ( this.count));
        label.setAlignment ( Pos.CENTER_LEFT );
        label.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,15) );

        slider.valueProperty().addListener(
                new ChangeListener<Number> () {
                    public void changed(ObservableValue<? extends Number >
                                                observable, Number oldValue, Number newValue)
                    {
                        updateCount ( newValue.intValue () );
                        label.setText ( String.valueOf ( newValue.intValue ()) );
                    }
                });



        VBox vBox=new VBox (slider,label);
        vBox.setSpacing ( 5 );
        vBox.setAlignment ( CENTER );
        return vBox;
    }

    public void updateCount(int count){
        this.count=count;
        this.sell.setText ( "Sell    "+this.count*this.resource.getSellingPrice () );
        this.buy.setText ( "Buy    "+this.count*this.resource.getBuyingPrice () );
    }


    private HBox getBar(){
        HBox hBox=new HBox ();
        ImageView up=getUpDown ( "up" );
        ImageView down=getUpDown ( "down" );
        Button button=new Button (resource.getName ()+"   "+this.user.getGovernment ().getResourceAmount ( resource ));
        button.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,15) );
        button.setTextFill ( Color.WHITE );
        button.setTextAlignment ( TextAlignment.CENTER );
        button.setPrefSize ( 200,40 );
        button.setBackground ( new Background ( StartMenuController.setBackGround ( "/images/background/button.png",200,40 ) ) );
        this.value=button;


        hBox.setAlignment ( CENTER );
        hBox.getChildren ().addAll ( up,button,down );
        hBox.setSpacing ( 0 );

        button.setOnMouseEntered ( event ->  {
            button.setOpacity ( 0.7 );
        } );

        button.setOnMouseExited(event -> {
            button.setOpacity ( 1 );
            button.setTextFill (Color.WHITE);
        });

        button.setOnMouseClicked ( event -> {
            button.setTextFill ( Color.BLACK );
            ShopMenuShowController.closeItem ( this.menu );
        } );

        return hBox;
    }

    private ImageView getUpDown(String name){
        ImageView button=new ImageView ();
        button.setFitWidth ( 30 );
        button.setFitHeight ( 30 );


        button.setImage (  new Image ( ItemMenu.class.getResource ( "/images/Buttons/"+name+"_item.png" ).toExternalForm ()));

        button.setOnMouseEntered ( event ->  {
            button.setOpacity ( 0.7 );
        } );

        button.setOnMouseExited(event -> {
            button.setOpacity ( 1 );
        });

        button.setOnMouseClicked ( event -> {
            if(name.equals ( "up" )){
                ShopMenuShowController.up ( this.resource.ordinal (),this.menu );
            }else {
                ShopMenuShowController.down ( this.resource.ordinal (),this.menu );

            }
        } );

        return button;

    }

}