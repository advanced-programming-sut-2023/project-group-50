package model.Government;

import controller.UserDatabase.User;
import model.Map.Map;
import model.ObjectsPackage.Resource;

import java.io.Serializable;
import java.util.HashMap;

public class Government implements Serializable {
    private final User user;
    private final HashMap<Resource, Integer> resources;
    private int coins;
    private Map map;

    public Government(User user) {
        this.user = user;
        resources = new HashMap<>();
        coins = 0;
    }

    public void setResourceAmount(Resource resource, int value) {
        resources.replace(resource, value);
    }

    public int getResourceAmount(Resource resource) {
        return resources.getOrDefault(resource, 0);
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Map getMap() {
        return map;
    }
}
