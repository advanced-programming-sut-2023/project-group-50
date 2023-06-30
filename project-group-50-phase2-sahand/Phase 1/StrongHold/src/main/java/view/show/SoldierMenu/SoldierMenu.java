package view.show.SoldierMenu;

import controller.GUIControllers.SoldierMenuController.SoldierMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SoldierMenu extends Application {

    private SoldierMenuController soldierMenuController;

    public void init(SoldierMenuController soldierMenuController) {
        this.soldierMenuController = soldierMenuController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = soldierMenuController.getPane();

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(SoldierMenu.class.getResource("/css.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Stronghold");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }
}
