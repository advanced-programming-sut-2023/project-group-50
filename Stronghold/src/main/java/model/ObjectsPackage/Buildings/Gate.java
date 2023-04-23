package model.ObjectsPackage.Buildings;

import model.User;

public class Gate extends Building {
    private int capacity;
    private int currentPopulation;
    protected Gate(BuildingType type, User owner, int x, int y, int maxHp, int capacity) {
        super(type, owner, x, y, maxHp);
        this.capacity = capacity;
        currentPopulation = 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentPopulation() {
        return currentPopulation;
    }

    public void setCurrentPopulation(int currentPopulation) {
        this.currentPopulation = currentPopulation;
    }
}
