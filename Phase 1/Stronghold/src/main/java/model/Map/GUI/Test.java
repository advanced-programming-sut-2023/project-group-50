package model.Map.GUI;

import controller.UserDatabase.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Map.GroundType;
import model.Map.Map;
import model.ObjectsPackage.Buildings.BuildingType;
import model.RandomGenerator.RandomGenerator;
import model.UserColor.UserColor;

public class Test extends Application {
    private Map map;

    @Override
    public void start(Stage stage) throws Exception {
        initMap();
        Pane mapPane = MapPane.getMapPane(map, 18, 18, -25, 25);

        Scene scene = new Scene(mapPane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Stronghold");
        stage.show();
    }

    private void initMap() {
        map = new Map(100, 100);

        GroundType[] groundTypes = GroundType.values();
        BuildingType[] buildingTypes = BuildingType.values();

        User user = new User("Sahand",
                             "Ap",
                             "shndap",
                             "bjbdkw",
                             "wewd",
                             5,
                             5,
                             UserColor.PURPLE, map);

        for (int i = 0; i < 500; i++) {
            int x = RandomGenerator.getRandomNumber(0, 99);
            int y = RandomGenerator.getRandomNumber(0, 99);
            GroundType groundType;
            do {
                groundType = RandomGenerator.randomFrom(groundTypes);
            } while (groundType.isWater());
            map.getXY(x, y).setTexture(groundType);
        }

        for (int i = 30; i < 50; i++) for (int j = 30; j < 50; j++) map.getXY(i, j).setTexture(GroundType.SEA);
    }
}

