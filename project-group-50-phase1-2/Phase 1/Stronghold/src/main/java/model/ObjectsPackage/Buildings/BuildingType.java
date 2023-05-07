package model.ObjectsPackage.Buildings;

public enum BuildingType {
    SMALL_STONE_GATEHOUSE(0, 0, 0, "Small stone gatehouse"),
    BIG_STONE_GATEHOUSE(20, 0, 0, " Big stone gatehouse"),
    DRAW_BRIDGE(0, 10, 0, "Drawbridge"),
    LOOKOUT_TOWER(10, 0, 0, "Lookout tower"),
    PERIMETER_TOWER(10, 0, 0, "Perimeter tower"),
    TURRET(15, 0, 0, "Turret"),
    SQUARE_TOWER(35, 0, 0, "Square tower"),
    ROUND_TOWER(40, 0, 0, "Round tower"),
    ARMOURY(0, 5, 0, "Armoury"),
    BARRACKS(15, 0, 0, "Barrack"),
    MERCENARY_POST(0, 10, 0, "Mercenary post"),
    ENGINEER_GUILD(0, 10, 100, "Engineer guild"),
    KILLING_PIT(0, 6, 0, "Killing pit"),
    INN(0, 20, 100, "Inn"),
    MILL(0, 20, 100, "Mill"),
    IRON_MINE(0, 20, 0, "Iron mine"),
    MARKET(0, 5, 0, "Market"),
    OX_TETHER(0, 5, 0, "Ox tether"),
    PITCH_RIG(0, 20, 0, "Pitch rig"),
    QUARRY(0, 20, 0, "Quarry"),
    STOCKPILE(0, 0, 0, "Stockpile"),
    WOODCUTTER(0, 3, 0, "Woodcutter"),
    APOTHECARY(0, 20, 150, "Apothecary"),
    HOVEL(0, 6, 0, "Hovel"),
    CHAPEL(0, 0, 250, "Chapel"),
    CHURCH(0, 0, 500, "Church"),
    CATHEDRAL(0, 0, 1000, "Cathedral"),
    WELL(0, 0, 30, "Well"),
    WATER_POT(0, 0, 60, "Water pot"),
    ARMOURER(0, 20, 100, "Armourer"),
    BLACKSMITH(0, 20, 200, "Blacksmith"),
    FLETCHER(0, 20, 100, "Fletcher"),
    POLETURNER(0, 10, 100, "Poleturner"),
    OIL_SMELTER(0, 0, 100, "Oil smelter"),
    PITCH_DITCH(0, 0, 0, "Pitch ditch"),
    CAGED_WAR_DOGS(0, 10, 100, "Caged war dogs"),
    SIEGE_TENT(0, 0, 0, "Siege tent"),
    STABLE(0, 20, 400, "Stable"),
    TUNNELER_GUILD(0, 10, 100, "Tunneler guild"),
    APPLE_ORCHARD(0, 5, 0, "Apple orchard"),
    DIARY_FARMER(0, 10, 0, "Diary farmer"),
    HOPS_FARMER(0, 15, 0, "Hops farmer"),
    HUNTER_POST(0, 5, 0, "Hunter post"),
    WHEAT_FARMER(0, 15, 0, "Wheat farmer"),
    BAKERY(0, 10, 0, "Bakery"),
    BREWER(0, 10, 0, "Brewer"),
    GRANARY(0, 5, 0, "Granary");
    private final int stoneCost;
    private final int woodCost;
    private final int goldCost;
    private final String type;

    BuildingType(int stoneCost, int woodCost, int goldCost, String type) {
        this.stoneCost = stoneCost;
        this.woodCost = woodCost;
        this.goldCost = goldCost;
        this.type = type;
    }

    public static BuildingType checkTypeByName(String type) {
        for (BuildingType buildingType : BuildingType.values()) {
            if (buildingType.type.equals(type)) {
                return buildingType;
            }
        }
        return null;
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

    public String getType() {
        return type;
    }

    public int getIronCost() {
        return this == OIL_SMELTER ? 10 : 0;
    }
}
