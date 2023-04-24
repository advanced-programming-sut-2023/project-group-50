package model.ObjectsPackage.Buildings;

public enum BuildingType {
    SMALL_STONE_GATEHOUSE(0, 0, 0),
    BIG_STONE_GATEHOUSE(20, 0, 0),
    DRAW_BRIDGE(0, 10, 0),
    LOOKOUT_TOWER(10, 0, 0),
    PERIMETER_TOWER(10, 0, 0),
    TURRET(15, 0, 0),
    SQUARE_TOWER(35, 0, 0),
    ROUND_TOWER(40, 0, 0),
    ARMOURY(0, 5, 0),
    BARRACKS(15, 0, 0),
    MERCENARY_POST(0, 10, 0),
    ENGINEER_GUILD(0, 10, 100),
    KILLING_PIT(0, 6, 0),
    INN(0, 20, 100),
    MILL(0, 20, 100),
    IRON_MINE(0, 20, 0),
    MARKET(0, 5, 0),
    OX_TETHER(0, 5, 0),
    PITCH_RIG(0, 20, 0),
    QUARRY(0, 20, 0),
    STOCKPILE(0, 0, 0),
    WOODCUTTER(0, 3, 0),
    APOTHECARY(0, 20, 150),
    HOVEL(0, 6, 0),
    CHAPEL(0, 0, 250),
    CHURCH(0, 0, 500),
    CATHEDRAL(0, 0, 1000),
    WELL(0, 0, 30),
    WATER_POT(0, 0, 60),
    ARMOURER(0, 20, 100),
    BLACKSMITH(0, 20, 200),
    FLETCHER(0, 20, 100),
    POLETURNER(0, 10, 100),
    OIL_SMELTER(0, 0, 100),
    PITCH_DITCH(0, 0, 0),
    CAGED_WAR_DOGS(0, 10, 100),
    SIEGE_TENT(0, 0, 0),
    STABLE(0, 20, 400),
    TUNNELER_GUILD(0, 10, 100),
    APPLE_ORCHARD(0, 5, 0),
    DIARY_FARMER(0, 10, 0),
    HOPS_FARMER(0, 15, 0),
    HUNTER_POST(0, 5, 0),
    WHEAT_FARMER(0, 15, 0),
    BAKERY(0, 10, 0),
    BREWER(0, 10, 0),
    GRANARY(0, 5, 0);
    private final int stoneCost;
    private final int woodCost;
    private final int goldCost;

    BuildingType(int stoneCost, int woodCost, int goldCost) {
        this.stoneCost = stoneCost;
        this.woodCost = woodCost;
        this.goldCost = goldCost;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public int getPitchCost(int numberOfSquares) {
        return this == PITCH_DITCH ? Math.ceilDiv(numberOfSquares, 5) * 2 : 0;
    }

    public int getIronCost() {
        return this == OIL_SMELTER ? 10 : 0;
    }
}
