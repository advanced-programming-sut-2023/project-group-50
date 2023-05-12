package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;
import model.ObjectsPackage.Resource;

public class FoodProcessingBuildings extends Building {
    private final int popularityRate;
    private final int wineUsageRate;
    private final int rate;

    protected FoodProcessingBuildings(BuildingType type,
                                      User owner,
                                      int x,
                                      int y,
                                      int maxHp,
                                      int popularityRate,
                                      int wineUsageRate,
                                      int rate) {
        super(type, owner, x, y, maxHp);
        this.popularityRate = popularityRate;
        this.wineUsageRate = wineUsageRate;
        this.rate = rate;
    }

    public String convertWheatToFlour() {
        if (!getType().equals(BuildingType.MILL)) return "Can't produce flour here!";
        return getConversionString(Resource.WHEAT, Resource.FLOUR);
    }

    public String convertHopsToAle() {
        if (!getType().equals(BuildingType.BREWER)) return "Can't produce ale here!";
        return getConversionString(Resource.HOPS, Resource.ALE);
    }

    public String convertFlourToBread() {
        if (!getType().equals(BuildingType.BAKERY)) return "Can't produce bread here!";
        return getConversionString(Resource.FLOUR, Resource.BREAD);
    }

    private String getConversionString(Resource from, Resource to) {
        int initialAmount = getOwner().getGovernment().getResourceAmount(from);
        int finalAmount = initialAmount - 1;

        if (initialAmount <= 0) return "Not enough " + from.name().toLowerCase() + "!";
        getOwner().getGovernment().setResourceAmount(from, finalAmount);
        getOwner().getGovernment().setResourceAmount(to,
                                                     getOwner().getGovernment().getResourceAmount(to) + 1);

        return "Converted " + from.name().toLowerCase() + " to " + to.name().toLowerCase() + " successfully!";
    }
}
