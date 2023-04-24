package model.ObjectsPackage.Buildings;

import model.User;

public class FoodProcessingBuildings extends Building {
    private int popularityRate;
    private int wineUsageRate;
    private int rate;

    protected FoodProcessingBuildings(BuildingType type, User owner, int x, int y, int maxHp, int popularityRate, int wineUsageRate, int rate) {
        super(type, owner, x, y, maxHp);
        this.popularityRate = popularityRate;
        this.wineUsageRate = wineUsageRate;
        this.rate = rate;
    }

    public String convertWheatToFlour() {
        //TODO: fill here
        return null;
    }

    public String serveWine() {
        //TODO: fill here
        return null;
    }
}
