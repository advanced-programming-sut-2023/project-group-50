package view.show.controller;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import view.show.Menus.LoginMenuShow;
import view.show.Menus.MenuButton;
import view.show.Menus.SignupMenuShow;
import view.show.Menus.StartMenu;
import view.show.MusicPlayer;

public class StartMenuController {


    public Parent createContent(){
        Pane pane=new Pane ();
        pane.setPrefSize ( 800,600 );
        Background background = new Background(setBackGround("/images/background/startmenu1.png",800,600));
        pane.setBackground(background);
        Node menu=createMenu ();
        MusicPlayer.playMusic ( getClass ().getResource ("/images/media/startmenu/cinematic-intro-6097.mp3").toString () );
        menu.setTranslateX ( 5 );
        menu.setTranslateY ( 150 );
        pane.getChildren ().add ( menu );

        return pane;
    }

    public Node createMenu(){
        
        MenuButton login=new MenuButton ( "LOGIN" );
        MenuButton signup=new MenuButton ( "SING UP" );
        login.setAlignment ( Pos.TOP_LEFT );
        signup.setAlignment ( Pos.TOP_LEFT );
        login.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                StartMenu.startMenuController.login();
            }
        } );
        signup.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                StartMenu.startMenuController.signup();
            }
        } );
        VBox vBox=new VBox (login,signup);
        vBox.setSpacing ( 25 );
        vBox.setAlignment ( Pos.CENTER );
        return vBox;
        
    }

    private void signup () {
        try {
            MusicPlayer.stopMusic ( getClass ().getResource ("/images/media/startmenu/cinematic-intro-6097.mp3").toString () );
            new SignupMenuShow ().start ( StartMenu.stage );

        } catch (Exception e) {
            throw new RuntimeException ( e );
        }
    }

    private void login () {
        try {
            MusicPlayer.stopMusic ( getClass ().getResource ("/images/media/startmenu/cinematic-intro-6097.mp3").toString () );
            new LoginMenuShow ().start ( StartMenu.stage );

        } catch (Exception e) {
            throw new RuntimeException ( e );
        }
    }

    public static BackgroundImage setBackGround(String url,double x,double y) {

        Image image = new Image(StartMenuController.class.getResource(url).toExternalForm(), x,y, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }




}
