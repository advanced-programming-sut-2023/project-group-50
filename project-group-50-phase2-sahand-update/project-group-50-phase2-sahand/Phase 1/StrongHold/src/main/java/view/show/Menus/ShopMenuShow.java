package view.show.Menus;

import controller.Menus.ShopMenuController;
import controller.UserDatabase.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ShopMenu;
import view.show.controller.ShopMenuShowController;
import view.show.controller.SignupMenuShowController;

public class ShopMenuShow extends Application {
    public static ShopMenuShowController shopMenuShowController;
    public static Stage stage;
    private User user;


    @Override
    public void start (Stage primaryStage) throws Exception {
            ShopMenuShow.stage=primaryStage;
            Scene scene=new Scene ( ShopMenuShow.shopMenuShowController.createContent () );
            ShopMenuShow.stage.setScene ( scene );
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            ShopMenuShow.stage.show ();
    }

    public void init(User user) {
        this.user = user;
        ShopMenuShow.shopMenuShowController=new ShopMenuShowController ();
        ShopMenuShowController.shopMenuController=new ShopMenuController ( this.user );
    }

}

