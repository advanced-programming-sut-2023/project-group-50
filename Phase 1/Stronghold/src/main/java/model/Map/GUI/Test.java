package model.Map.GUI;

import controller.UserDatabase.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Map.GroundType;
import model.Map.Map;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.RandomGenerator.RandomGenerator;
import model.UserColor.UserColor;

public class Test extends Application {
    private Map map;

    @Override
    public void start(Stage stage) throws Exception {
        initMap();
//        Pane mapPane = MapPane.getMapPane(map, 20, 20, 0, 0);

        Pane mapPane = new MiniMap(map).getMiniMap(500, 500);

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
                             10,
                             5,
                             UserColor.RED, map);

        for (int i = 0; i < 500; i++) {
            int x = RandomGenerator.getRandomNumber(0, 99);
            int y = RandomGenerator.getRandomNumber(0, 99);
            int s = RandomGenerator.getRandomNumber(0, 1);
            if (s == 0) map.addObject(Building.getBuildingByType(RandomGenerator.randomFrom(buildingTypes), user, x, y),
                                      x,
                                      y);
            else map.getUnitByXY(x, y).setTexture(RandomGenerator.randomFrom(groundTypes));
        }

        map.addObject(Building.getBuildingByType(BuildingType.PALACE, user, 50, 5), 50, 5);
    }
}

