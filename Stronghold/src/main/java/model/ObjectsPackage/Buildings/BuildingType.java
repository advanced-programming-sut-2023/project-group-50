package model.ObjectsPackage.Buildings;

public enum BuildingType {
    BARRACKS(0,0 ,0); //TODO: fill here
    private int rockCost;
    private int woodCost;
    private int goldCost;

    BuildingType(int rockCost, int woodCost, int goldCost) {
        this.rockCost = rockCost;
        this.woodCost = woodCost;
        this.goldCost = goldCost;
    }

    public int getRockCost() {
        return rockCost;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public int getGoldCost() {
        return goldCost;
    }
}
