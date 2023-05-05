package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;

public class FoodProcessingBuildings extends Building {
    private final int popularityRate;
    private final int wineUsageRate;
    private final int rate;

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
