package model.ObjectsPackage;

import controller.UserDatabase.User;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;

import java.util.HashMap;

public class Storage extends Building {
    private final HashMap<String, Integer> capacity;
    private final Storage nextStorage;

    public Storage(BuildingType type, User owner, int x, int y, int maxHp, Storage nextStorage) {
        super(type, owner, x, y, maxHp);
        this.nextStorage = nextStorage;
        capacity = new HashMap<>();
    }

    public int getCapacity(String resource) {
        return capacity.getOrDefault(resource, 0);
    }

    public void addCapacity(String resource, int count) {
        int initialCapacity = capacity.getOrDefault(resource, 0);
        capacity.put(resource, initialCapacity + count);
    }

    public boolean hasNextStorage() {
        return nextStorage != null;
    }

    public Storage getNextStorage() {
        return nextStorage;
    }
}
