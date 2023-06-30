package view.show.controller;

import controller.GUIControllers.ProfileMenuGUIController;
import controller.Menus.SignupController;
import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import controller.control.Error;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
import model.RandomGenerator.RandomCaptcha;
import view.show.Menus.MenuButton;
import view.show.Menus.SignupMenuShow;
import view.show.Menus.StartMenu;
import view.show.MusicPlayer;
import view.show.ProfileMenu.EditSecurityQuestionMenu;

import java.util.Timer;
import java.util.TimerTask;

import static controller.Menus.SignupController.userNameIsValid;


public class SignupMenuShowController {

    public  VBox username;

    public VBox password;
    public VBox email;

    public VBox nickname;
    public  VBox slogan;

    public  VBox menu;

    public static Integer captchaNumber;

    public Pane pane;
    public VBox question;
    public User user;
    private boolean done = false;
    private TextField answer;
    private VBox checkBoxes;
    private int choice;

    public Parent createContent(){
        Pane pane=new Pane ();
        pane.setPrefSize ( 800,600 );
        Background background = new Background(StartMenuController.setBackGround(( "/images/background/m1HMsV.jpg" ),800,600));
         pane.setBackground(background);
        VBox menu=new VBox (creatUsernameField (),(this.password=creatPasswordField ()),createEmail (),createNickname (),createSlogan (),createButtons ());

        MusicPlayer.playMusic ( SignupMenuShowController.class.getResource ( "/images/floating-in-time-145495.mp3" ).toExternalForm () );

        menu.setAlignment ( Pos.CENTER );
        menu.setSpacing ( 15 );
        menu.setTranslateY ( 100 );
        menu.setTranslateX ( 100 );
        pane.getChildren ().add ( menu );
        this.menu=menu;
        this.pane=pane;
        return pane;
    }

    private Node creatUsernameField(){
        HBox hBox=new HBox ();

        TextField username = getTextField ("Username");
        ImageView userImage = getImage ( "/images/icons/user1.png" , "/images/icons/user2.png" , username );

        hBox.getChildren ().addAll ( userImage,username);
        hBox.setAlignment ( Pos.CENTER );
        hBox.setSpacing ( 7 );
        VBox vBox=new VBox (hBox);
        vBox.setAlignment ( Pos.CENTER );
        vBox.setSpacing ( 5 );

        Label error=new Label ();

        error.setTextFill ( Color.RED );
        HBox errorBox=new HBox ();
        errorBox.setAlignment ( Pos.CENTER );
        errorBox.setSpacing ( 5 );

        ImageView errorImage = getErrorImage ();
        errorBox.getChildren ().addAll ( errorImage,error );
        username.textProperty ().addListener ( (observable , oldValue , newValue) -> {
            addError ( errorBox, userNameIsValid ( username.getText () ) );
            if(!username.getText ().equals ( "" )){
                if ( hBox.getChildren ().size () == 2 ) {
                    hBox.getChildren ().add ( createReset ( username )  );
                }
            }else {
                hBox.getChildren ().remove ( 2 );
            }

        } );

        vBox.getChildren ().add ( errorBox );
        this.username=vBox;
        return vBox;

    }

    public static TextField getTextField (String input) {
        TextField text =new TextField ();
        text.setPromptText ( input );
        text.setStyle ( "    -fx-max-width: 200;\n" +
                "    -fx-min-width: 200;\n" +
                "    -fx-font-family: \"Verdana\";\n" +
                "    -fx-font-size: 13px;\n" +
                "    -fx-text-fill: -fx-text-inner-color;\n" +
                "    -fx-highlight-fill: derive(-fx-control-inner-background,-20%);\n" +
                "    -fx-highlight-text-fill: -fx-text-inner-color;\n" +
                "    -fx-prompt-text-fill: transparent;\n" +
                "    -fx-background-color: transparent;\n" +
                "    -fx-background-insets: 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-border-color: transparent transparent #616161 transparent;\n" +
                "    -fx-border-width: 1;\n" +
                "    -fx-border-insets: 0 0 1 0;\n" +
                "    -fx-border-style: hidden hidden solid hidden;\n" +
                "    -fx-cursor: text;\n" +
                "    -fx-padding: 0.166667em 0em 0.333333em 0em;\n" +
                "    -fx-prompt-text-fill: Black;\n" );
        return text;
    }

    public static ImageView getImage (String normal , String color , TextField text) {
        ImageView image = new ImageView ( new Image ( SignupMenuShowController.class.getResource ( normal ).toExternalForm () ) );
        image.setLayoutX ( 110 );
        image.setLayoutY ( 100 );
        image.setFitHeight ( 30 );
        image.setFitWidth ( 25 );
        image.setPickOnBounds ( true );
        image.setOnMouseEntered ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                image.setImage ( new Image ( SignupMenuShowController.class.getResource ( color ).toExternalForm () ) );
                image.setOpacity ( 1 );

            }
        } );

        image.setOnMouseExited ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                image.setImage ( new Image ( SignupMenuShowController.class.getResource ( normal ).toExternalForm () ) );
                image.setOpacity ( 1 );

            }
        } );

        image.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                image.setOpacity ( 0.5 );
                text.requestFocus ();
            }
        } );
        return image;
    }



    public  static  void addError(HBox hBox, Error error){
        if(!error.truth ) {
            ((ImageView)hBox.getChildren ().get ( 0 )).setOpacity ( 1 );
        }else {
            ((ImageView)hBox.getChildren ().get ( 0 )).setOpacity ( 0 );
        }
        ((Label)hBox.getChildren ().get ( 1 )).setText ( error.errorMassage );
    }


    public static VBox creatPasswordField(){
        HBox hBox=new HBox ();

        PasswordField password=new PasswordField ();
        ImageView passwordImage=getImage ( "/images/icons/lock1.png" , "/images/icons/lock2.png" , password );
        password.setPromptText ( "Password" );
        password.setStyle ( "    -fx-max-width: 200;\n" +
                "    -fx-min-width: 200;\n" +
                "    -fx-font-family: \"Verdana\";\n" +
                "    -fx-font-size: 13px;\n" +
                "    -fx-text-fill: -fx-text-inner-color;\n" +
                "    -fx-highlight-fill: derive(-fx-control-inner-background,-20%);\n" +
                "    -fx-highlight-text-fill: -fx-text-inner-color;\n" +
                "    -fx-prompt-text-fill: transparent;\n" +
                "    -fx-background-color: transparent;\n" +
                "    -fx-background-insets: 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-border-color: transparent transparent #616161 transparent;\n" +
                "    -fx-border-width: 1;\n" +
                "    -fx-border-insets: 0 0 1 0;\n" +
                "    -fx-border-style: hidden hidden solid hidden;\n" +
                "    -fx-cursor: text;\n" +
                "    -fx-padding: 0.166667em 0em 0.333333em 0em;\n" +
                "    -fx-prompt-text-fill: Black;\n" );

        ImageView eyeImage = getEyeImage ( hBox , password );


        hBox.getChildren ().addAll (  passwordImage,password,eyeImage );
        hBox.setAlignment ( Pos.CENTER );
        hBox.setSpacing ( 7 );
        VBox vBox=new VBox (hBox);
        vBox.setAlignment ( Pos.CENTER );
        vBox.setSpacing ( 5 );

        Label error=new Label ();
        error.setTextFill ( Color.RED );

        HBox errorBox=new HBox ();
        errorBox.setAlignment ( Pos.CENTER );
        errorBox.setSpacing ( 5 );

        ImageView errorImage = getErrorImage ();
        errorBox.getChildren ().addAll ( errorImage,error );
        password.textProperty ().addListener ( (observable , oldValue , newValue) -> {
            addError ( errorBox,SignupController.passwordIsValid ( password.getText () ) );
            if(!password.getText ().equals ( "" )){
                if ( hBox.getChildren ().size () == 3 ) {
                    hBox.getChildren ().add ( createReset ( password )  );
                }
            }else {
                hBox.getChildren ().remove ( 3 );
            }
        } );

        vBox.getChildren ().addAll ( createRandom ( "password",password ),errorBox );

        return vBox;

    }

    public static void passwordToShow (HBox hBox,PasswordField passwordField) {
        TextField passwordText=getTextField ( "" );
        passwordText.setText ( passwordField.getText () );
        int i=hBox.getChildren ().indexOf ( passwordField );
        hBox.getChildren ().remove ( passwordField );
        hBox.getChildren ().add ( i,passwordText );
        new Timer ().schedule ( new TimerTask () {
            @Override
            public void run () {
                Platform.runLater ( new TimerTask () {
                    @Override
                    public void run () {
                        hBox.getChildren ().remove ( passwordText );
                        hBox.getChildren ().add ( i , passwordField );
                    }
                } );
            }
        },500 );

    }

    private Node createEmail(){
        HBox hBox=new HBox ();

        TextField email = getTextField ("example@im.khar");
        ImageView emailImage = getImage ( "/images/icons/email1.png" , "/images/icons/email2.png" , email );

        hBox.getChildren ().addAll ( emailImage ,email );
        hBox.setAlignment ( Pos.CENTER );
        hBox.setSpacing ( 7 );
        VBox vBox=new VBox (hBox);
        vBox.setAlignment ( Pos.CENTER );
        vBox.setSpacing ( 5 );

        Label error=new Label ();

        error.setTextFill ( Color.RED );

        HBox errorBox=new HBox ();
        errorBox.setAlignment ( Pos.CENTER );
        errorBox.setSpacing ( 5 );

        ImageView errorImage = getErrorImage ();
        errorBox.getChildren ().addAll ( errorImage,error );
        errorImage.setOpacity ( 0 );
        vBox.getChildren ().add ( errorBox );
        email.textProperty ().addListener ( (observable , oldValue , newValue) -> {
            if(!error.getText ().equals ( "" )) {
                addError ( errorBox, SignupController.emailIsValid ( email.getText () ) );
            }
            if(!email.getText ().equals ( "" )){
                if ( hBox.getChildren ().size () == 2 ) {
                    hBox.getChildren ().add ( createReset ( email )  );
                }
            }else {
                hBox.getChildren ().remove ( 2 );
            }

             } );

        this.email=vBox;
        return vBox;
    }
    public static ImageView getErrorImage () {
        ImageView errorImage = new ImageView ( new Image ( SignupMenuShowController.class.getResource ( "/images/icons/w.png" ).toExternalForm () ) );
        errorImage.setLayoutX ( 110 );
        errorImage.setLayoutY ( 100 );
        errorImage.setFitHeight ( 12 );
        errorImage.setFitWidth ( 10 );
        errorImage.setPickOnBounds ( true );
        errorImage.setOpacity ( 0 );
        return errorImage;
    }


    private Node createNickname(){
        HBox hBox=new HBox ();

        TextField nick = getTextField ("Nickname");
        ImageView nickImage = getImage ( "/images/icons/nick1.png" , "/images/icons/nick2.png" , nick );

        hBox.getChildren ().addAll ( nickImage , nick );
        hBox.setAlignment ( Pos.CENTER );
        hBox.setSpacing ( 7 );
        VBox vBox=new VBox (hBox);
        vBox.setAlignment ( Pos.CENTER );
        vBox.setSpacing ( 5 );

        Label error=new Label ();

        error.setTextFill ( Color.RED );

        HBox errorBox=new HBox ();
        errorBox.setAlignment ( Pos.CENTER );
        errorBox.setSpacing ( 5 );

        ImageView errorImage = getErrorImage ();
        errorBox.getChildren ().addAll ( errorImage,error );
        errorImage.setOpacity ( 0 );
        vBox.getChildren ().add ( errorBox );
        nick.textProperty ().addListener ( (observable , oldValue , newValue) -> {
            if(!error.getText ().equals ( "" )) {
                if ( nick.getText ().trim ().equals ( "" ) ) {
                    addError ( errorBox , new Error ( "You should enter a nickname" , false ));
                }else {
                    addError ( errorBox , new Error ( "" , true ));
                }
            }

            if(!nick.getText ().equals ( "" )){
                if ( hBox.getChildren ().size () == 2 ) {
                    hBox.getChildren ().add ( createReset ( nick )  );
                }
            }else {
                hBox.getChildren ().remove ( 2 );
            }
        } );


        this.nickname=vBox;
        return vBox;
    }


    private Node createButtons(){
        HBox hBox=new HBox ();
        MenuButton submit=new MenuButton ( "Submit" );
        MenuButton back=new MenuButton ( "Back" );

        submit.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                submit ();
            }
        } );

        back.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                try {
                    new StartMenu ().start ( SignupMenuShow.stage );
                } catch (Exception e) {
                    throw new RuntimeException ( e );
                }
            }
        } );

        submit.setAlignment ( Pos.TOP_LEFT );
        back.setAlignment ( Pos.TOP_LEFT );

        hBox.setSpacing ( 10 );
        hBox.getChildren ().addAll ( submit,back );
        hBox.setAlignment ( Pos.CENTER );
        return hBox;
    }

    private void submit(){

        boolean flag=true;

        Error error ;
        if(((TextField)((HBox)username.getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText ().trim ().equals ( "" )){
                error=new Error ( "You should enter a username",false );
        }else {
            error=SignupController.userNameIsValid ( ((TextField)((HBox)username.getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText () );
        }
        addError ( (HBox) username.getChildren ().get ( 1 ),error );

        if(!error.truth){
            flag=false;
        }

        if(((PasswordField)((HBox)password.getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText ().equals ( "" )){
            error=new Error ( "You should enter a password",false );
        }else {
            error=SignupController.passwordIsValid (  ((PasswordField)((HBox)password.getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText () );
        }
        addError ( ((HBox)password.getChildren ().get ( 2 )),error );

        if(!error.truth){
            flag=false;
        }

        if(((TextField)((HBox)email.getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText ().trim ().equals ( "" )){
            error=new Error ( "You should enter a email",false );
        }else {
            error=SignupController.emailIsValid (((TextField)((HBox)email.getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText () );
        }
        addError ( (HBox)email.getChildren ().get ( 1 ),error );

        if(!error.truth){
            flag=false;
        }

        if(((TextField)((HBox)nickname.getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText ().trim ().equals ( "" )){
            error=new Error ( "You should enter a nickname",false );
        }else {
            error=new Error ( "",true );
        }
        addError ( (HBox)nickname.getChildren ().get ( 1 ),error );

        if(!error.truth){
            flag=false;
        }
        String slogan="";
        if(this.slogan.getChildren ().size ()==4){
            if(((TextField)((HBox)this.slogan.getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText ().trim ().equals ( "" )){
                error=new Error ( "You should enter a slogan",false );
            }else {
                error=new Error ( "",true );
            }
            addError ( (HBox)this.slogan.getChildren ().get ( 2 ),error );
            if(!error.truth){
                flag=false;
            }

        }

        if(!flag){
            MusicPlayer.playMusic ( getClass ().getResource ( "/images/error-89612.mp3" ).toString () );
            return;
        }


       this.user= SignupController.createUser (((TextField)((HBox)username.getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText ().trim (),
                ((PasswordField)((HBox)password.getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText (),
                ((TextField)((HBox)email.getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText ().trim (),
                ((TextField)((HBox)nickname.getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText ().trim (),
                slogan
                );



        createPickQuestion ();



    }

    private Node createSlogan(){

        HBox hBox=new HBox ();
        TextField slogan = getTextField ("Slogan");
        ImageView sloganImage = getImage ( "/images/icons/slogan1.png" , "/images/icons/slogan2.png" , slogan );
        hBox.getChildren ().addAll ( sloganImage , slogan  );
        hBox.setAlignment ( Pos.CENTER );
        hBox.setSpacing ( 7 );
        VBox vBox=new VBox ();
        vBox.setAlignment ( Pos.CENTER );
        vBox.setSpacing ( 5 );

        Label error=new Label ();
        error.setTextFill ( Color.RED );
        HBox errorBox=new HBox ();
        errorBox.setAlignment ( Pos.CENTER );
        errorBox.setSpacing ( 5 );
        ImageView errorImage = getErrorImage ();
        errorBox.getChildren ().addAll ( errorImage,error );
        errorImage.setOpacity ( 0 );
        slogan.textProperty ().addListener ( (observable , oldValue , newValue) -> {
            if(!error.getText ().equals ( "" )) {
                if ( slogan.getText ().trim ().equals ( "" ) ) {
                    addError ( errorBox, new Error ( "You should enter a slogan" , false ));
                }else {
                    addError ( errorBox , new Error ( "" , true ));
                }
            }

            if(!slogan.getText ().equals ( "" )){
                if ( hBox.getChildren ().size () == 2 ) {
                    hBox.getChildren ().add ( createReset ( slogan )  );
                }
            }else {
                hBox.getChildren ().remove ( 2 );
            }
        } );


        Label text=new Label ("Slogan");
        text.setFont ( Font.font("Times New Roman", FontWeight.SEMI_BOLD,20) );
        text.setTextFill ( Color.BLACK );
        ImageView downImage = new ImageView (new Image ( SignupMenuShowController.class.getResource ( "/images/icons/down.png" ).toExternalForm () ));
        downImage.setLayoutX ( 100 );
        downImage.setLayoutY ( 90 );
        downImage.setFitHeight ( 25 );
        downImage.setFitWidth ( 20 );
        downImage.setPickOnBounds ( true );

        ImageView upImage = new ImageView (new Image ( SignupMenuShowController.class.getResource ( "/images/icons/up.png" ).toExternalForm () ));
        upImage.setLayoutX ( 100 );
        upImage.setLayoutY ( 90 );
        upImage.setFitHeight ( 25 );
        upImage.setFitWidth ( 20 );
        upImage.setPickOnBounds ( true );

        HBox showSlogan=new HBox (text, downImage );
        showSlogan.setAlignment ( Pos.CENTER );
        showSlogan.setSpacing ( 50 );
        showSlogan.setOnMouseEntered ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                showSlogan.setOpacity ( 0.7 );
            }
        } );
        showSlogan.setOnMouseExited ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                showSlogan.setOpacity ( 1 );

            }
        } );

        showSlogan.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                if(vBox.getChildren ().size ()==4){
                    vBox.getChildren ().remove ( 0 );
                    vBox.getChildren ().remove ( 0 );
                    vBox.getChildren ().remove ( 0 );
                    showSlogan.getChildren ().remove ( 1 );
                    showSlogan.getChildren ().add ( downImage );


                }else if(vBox.getChildren ().size ()==1){
                    vBox.getChildren ().add ( 0,errorBox );
                    vBox.getChildren ().add ( 0,createRandom ( "slogan",slogan ) );
                    vBox.getChildren ().add ( 0,hBox );
                    showSlogan.getChildren ().remove ( 1 );
                    showSlogan.getChildren ().add ( upImage );
                }
            }
        } );
        vBox.getChildren ().add ( showSlogan );
        this.slogan=vBox;
        return vBox;
    }




    private static Node createRandom (String random , TextField textField){
        HBox hBox=new HBox ();

        Text text=new Text ();
        Label label=new Label ("random "+random);
        hBox.getChildren ().addAll ( label,text );
        hBox.setSpacing ( 10 );
        hBox.setAlignment ( Pos.CENTER_LEFT );

        Label use=new Label ("use");
        use.setTextFill ( Color.BLACK );
        label.setTextFill ( Color.BLACK );

        label.setOnMouseEntered ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                label.setOpacity ( 0.7 );
                label.setTextFill ( Color.WHITE );

            }
        } );
        label.setOnMouseExited ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                label.setOpacity ( 1 );
                label.setTextFill ( Color.BLACK );

            }
        } );

        label.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                if(random.equals ( "password" )){
                    text.setText ( "Your random "+random+" is : "+SignupController.randomPassword () );
                }else {
                    text.setText ( SignupController.randomSlogan () );
                }
                if(hBox.getChildren ().size ()==2) {
                    hBox.getChildren ().add ( use );
                }
            }
        } );


        use.setOnMouseEntered ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                use.setOpacity ( 0.7 );
                use.setTextFill ( Color.WHITE );

            }
        } );
        use.setOnMouseExited ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                use.setOpacity ( 1 );
                use.setTextFill ( Color.BLACK );

            }
        } );


        use.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                if(random.equals ( "password" )){
                    textField.setText ( text.getText ().substring ( 26 ) );
                }else {
                    textField.setText ( text.getText ().substring ( 17 ) );
                }
                text.setText ( "" );
                hBox.getChildren ().remove ( 2 );

            }
        } );


        return hBox;
    }


    public  static   Node createReset(TextField textField){
        ImageView image = new ImageView (new Image ( SignupMenuShowController.class.getResource ( "/images/icons/close.png" ).toExternalForm () ));
        image.setLayoutX ( 90 );
        image.setLayoutY ( 80 );
        image.setFitHeight ( 15 );
        image.setFitWidth ( 15 );
        image.setPickOnBounds ( true );

        image.setOnMouseEntered ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                image.setOpacity ( 0.7 );
            }
        } );

        image.setOnMouseExited ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                image.setOpacity ( 1 );
            }
        } );

        image.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                    textField.setText ( "" );

                }
        } );

        image.setSmooth ( true );

        return image;

    }


    private void createPickQuestion(){

        VBox questions=new VBox ( ProfileMenuGUIController.getCheckBoxes ( -1 ) );
        questions.setPrefSize ( 100,100 );

        questions.setAlignment(Pos.CENTER_LEFT);
        questions.setSpacing(15);
        this.checkBoxes=questions;


        TextField field=getTextField ( "Answer" );
        HBox answerBox =new HBox (field);
        field.setMaxWidth(800/3 * 2 / 3);
        this.answer=field;
        answerBox.setAlignment ( Pos.CENTER );
        Label answerError=new Label ();
        answerError.setTextFill ( Color.RED );
        HBox errorBoxAnswer=new HBox ();
        errorBoxAnswer.setAlignment ( Pos.CENTER_LEFT );
        errorBoxAnswer.setSpacing ( 5 );

        ImageView errorAnswerImage = getErrorImage ();
        errorBoxAnswer.getChildren ().addAll ( errorAnswerImage,answerError );

        questions.getChildren ().addAll ( answerBox,errorBoxAnswer );




        VBox vBox=new VBox (questions );
        vBox.setAlignment ( Pos.CENTER );
        vBox.setSpacing ( 15 );


        HBox button =new HBox ();
        MenuButton submit=new MenuButton ( "Submit" );
        MenuButton back=new MenuButton ( "Back" );

        submit.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                checkQuestion();
            }
        } );

        back.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                pane.getChildren ().remove ( 0 );
                pane.getChildren ().add ( menu );
                Users.removeUser ( user );

            }
        } );


        answer.textProperty ().addListener ( (observable , oldValue , newValue) -> {
            if(! answerError.getText ().equals ( "" )) {
                if ( answer.getText ().trim ().equals ( "" ) ) {
                    addError ( errorBoxAnswer, new Error ( "You should enter a answer" , false ));
                }else {
                    addError ( errorBoxAnswer , new Error ( "" , true ));
                }
            }

            if(!answer.getText ().equals ( "" )){
                if ( answerBox.getChildren ().size () == 1 ) {
                    answerBox.getChildren ().add ( createReset ( answer )  );
                }
            }else {
                answerBox.getChildren ().remove ( 1 );
            }
        } );


        submit.setAlignment ( Pos.TOP_LEFT );
        back.setAlignment ( Pos.TOP_LEFT );

        button.setSpacing ( 10 );
        button.getChildren ().addAll ( submit,back );
        button.setAlignment ( Pos.CENTER );




        vBox.getChildren ().addAll ( createCaptcha (),button );
        vBox.setTranslateY ( 100 );
        vBox.setTranslateX ( 100 );
        this.question=vBox;

        this.pane.getChildren ().remove ( 0 );
        this.pane.getChildren ().add ( vBox );

    }

    public void checkQuestion(){
        boolean flag=true;
        Error error=new Error ( "",true );

        if( EditSecurityQuestionMenu.getChoice ( this.checkBoxes )==-1 ){
            addError ( (HBox) this.checkBoxes.getChildren ().get ( 4),new Error ( "You should choose a question",false ) );
            flag=false;
        }

        if(this.answer.getText ().trim ().equals ( "" )){
            addError ( (HBox) this.checkBoxes.getChildren ().get ( 4),new Error ( "You should enter answer",false ) );
            flag=false;
        }



        if(((TextField)((HBox)((VBox)this.question.getChildren ().get ( 1 )).getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText ().trim ().equals ( "" )){
            error=new Error ( "You should enter captcha",false);
            flag=false;
        }else if (! ((TextField)((HBox)((VBox)this.question.getChildren ().get ( 1 )).getChildren ().get ( 0 )).getChildren ().get ( 1 )).getText ().trim ().equals ( String.valueOf ( SignupMenuShowController.captchaNumber ) ) )
        {
            error=new Error ( "Your captcha is wrong try again",false );
            captchaNumber=RandomCaptcha.getCaptcha ();
            ((Rectangle)((HBox)((VBox)this.question.getChildren ().get ( 1 )).getChildren ().get ( 0 )).getChildren ().get ( 0 )).setFill (  new ImagePattern (new Image (RandomCaptcha.class.getResource ( "/Captcha/"+captchaNumber+".png").toExternalForm ())));
            ((TextField)((HBox)((VBox)this.question.getChildren ().get ( 1 )).getChildren ().get ( 0 )).getChildren ().get ( 1 )).setText ( "" );
            flag=false;

        }
        addError ( (HBox) ((VBox)this.question.getChildren ().get ( 1 )).getChildren ().get ( 2 ) ,error );



        if(!flag){
            MusicPlayer.playMusic ( getClass ().getResource ( "/images/error-89612.mp3" ).toString () );
            return;
        }


        choice = EditSecurityQuestionMenu.getChoice(checkBoxes);
        Users.setSecurityQuestion(user.getUserName (), choice, answer.getText().trim ());


        try {
            new StartMenu ().start ( SignupMenuShow.stage );
        } catch (Exception e) {
            throw new RuntimeException ( e );
        }


    }
    public static ImageView getEyeImage (HBox hBox , PasswordField password) {
        ImageView eyeImage =new ImageView (new Image ( SignupMenuShowController.class.getResource ( "/images/icons/show1.png" ).toExternalForm () ));
        eyeImage.setLayoutX ( 110 );
        eyeImage.setLayoutY ( 100 );
        eyeImage.setFitHeight ( 30 );
        eyeImage.setFitWidth ( 25 );
        eyeImage.setPickOnBounds ( true );
        eyeImage.setOnMouseEntered ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                eyeImage.setImage ( new Image ( SignupMenuShowController.class.getResource ( "/images/icons/show2.png" ).toExternalForm () ) );
                eyeImage.setOpacity ( 1 );

            }
        } );

        eyeImage.setOnMouseExited ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                eyeImage.setImage ( new Image ( SignupMenuShowController.class.getResource ( "/images/icons/show1.png" ).toExternalForm () ) );
                eyeImage.setOpacity ( 1 );

            }
        } );

        eyeImage.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                eyeImage.setOpacity ( 0.5 );
                passwordToShow( hBox , password );
            }
        } );
        return eyeImage;
    }


    public static VBox createCaptcha(){
        HBox captchaBox =new HBox ();

        TextField captcha = getTextField ("Enter captcha");
        Rectangle captchaImage =new Rectangle (100,50);
        SignupMenuShowController.captchaNumber=RandomCaptcha.getCaptcha ();
        captchaImage.setFill (  new ImagePattern (new Image (RandomCaptcha.class.getResource ( "/Captcha/"+captchaNumber+".png").toExternalForm ())));


        captchaBox.getChildren ().addAll ( captchaImage , captcha  );
        captchaBox.setAlignment ( Pos.CENTER );
        captchaBox.setSpacing ( 7 );

        Label captchaError =new Label ();
        Label again=new Label ("change");
        HBox tryAgain=new HBox (again);
        again.setTextFill ( Color.BLACK );

        again.setOnMouseEntered ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                again.setOpacity ( 0.7 );
                again.setTextFill ( Color.WHITE );

            }
        } );
        again.setOnMouseExited ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                again.setOpacity ( 1 );
                again.setTextFill ( Color.BLACK );

            }
        } );


        again.setOnMouseClicked ( new EventHandler<MouseEvent> () {
            @Override
            public void handle (MouseEvent event) {
                captchaNumber=RandomCaptcha.getCaptcha ();
                captchaImage.setFill (  new ImagePattern (new Image (RandomCaptcha.class.getResource ( "/Captcha/"+captchaNumber+".png").toExternalForm ())));

            }
        } );



        captchaError.setTextFill ( Color.RED );
        HBox errorBox=new HBox ();
        errorBox.setAlignment ( Pos.CENTER_LEFT );
        errorBox.setSpacing ( 5 );

        ImageView errorImage = getErrorImage ();
        errorBox.getChildren ().addAll ( errorImage, captchaError );


        captcha.textProperty ().addListener ( (observable , oldValue , newValue) -> {
            if(! captchaError.getText ().equals ( "" )) {
                if ( captcha.getText ().equals ( "" ) ) {
                    addError ( errorBox, new Error ( "You should enter a captcha" , false ));
                }else {
                    addError ( errorBox , new Error ( "" , true ));
                }
            }

            if(!captcha.getText ().equals ( "" )){
                if ( captchaBox.getChildren ().size () == 2 ) {
                    captchaBox.getChildren ().add ( createReset ( captcha )  );
                }
                }else {
                captchaBox.getChildren ().remove ( 2 );
                }
        } );

        return new VBox (captchaBox,tryAgain,errorBox);

    }



}
