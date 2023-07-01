package view.show.trademenu;

import controller.Menus.ShopMenuController;
import controller.Menus.TradeMenuController;
import controller.UserDatabase.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Government.GUI.GovernmentPane;
import view.show.Menus.ShopMenuShow;
import view.show.controller.ShopMenuShowController;
import view.show.controller.tradeMenuShowController.TradeMenuShowController;

public class TradeMenuShow extends Application {

    public static TradeMenuShowController tradeMenuShowController;
    public static Stage stage;
    private User user;


    @Override
    public void start (Stage primaryStage) throws Exception {
        TradeMenuShow.stage=primaryStage;
        Scene scene=new Scene ( TradeMenuShow.tradeMenuShowController.createContent () );
        scene.getStylesheets().add( GovernmentPane.class.getResource("/css.css").toExternalForm());
        TradeMenuShow.stage.setScene ( scene );
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        TradeMenuShow.stage.show ();
    }

    public void init(User user) {
        this.user = user;
        TradeMenuShow.tradeMenuShowController=new TradeMenuShowController ();
        TradeMenuShowController.tradeMenuController=new TradeMenuController ( user );
    }

}
