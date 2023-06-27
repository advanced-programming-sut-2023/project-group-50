package model.RandomGenerator;

import model.ObjectsPackage.Buildings.BuildingType;

public class RandomBuilding {
    public static String getRandomPitchDitch() {
        int randomNumber = RandomGenerator.getRandomNumber(0, 7);
        return "buildings/Military Buildings/pitch_ditches.gm1/collection" + randomNumber + ".png";
    }

    public static String getRandomKillingPit() {
        int randomNumber = RandomGenerator.getRandomNumber(0, 15);
        return "buildings/Military Buildings/killing_pits.gm1/collection" + randomNumber + ".png";
    }

    public static String getBuilding(BuildingType buildingType) {
        switch (buildingType) {
            case SMALL_STONE_GATEHOUSE -> {
                return "buildings/Military Buildings/st47_gate_wood.tgx.png";
            }
            case BIG_STONE_GATEHOUSE -> {
                return "buildings/Military Buildings/ST45_Gate_Main.tgx.png";
            }
            case DRAW_BRIDGE -> {
                return "buildings/Military Buildings/ST49_Drawbridge.tgx.png";
            }
            case LOOKOUT_TOWER -> {
                return "buildings/Military Buildings/ST74_Tower1.tgx.png";
            }
            case PERIMETER_TOWER -> {
                return "buildings/Military Buildings/ST74_Tower2.tgx.png";
            }
            case TURRET -> {
                return "buildings/Military Buildings/ST74_Tower3.tgx.png";
            }
            case SQUARE_TOWER -> {
                return "buildings/Military Buildings/ST74_Tower4.tgx.png";
            }
            case ROUND_TOWER -> {
                return "buildings/Military Buildings/ST74_Tower5.tgx.png";
            }
            case ARMOURY -> {
                return "buildings/Military Buildings/ST11_Armoury.tgx.png";
            }
            case BARRACKS -> {
                return "buildings/buildings/Barracks.png";
            }
            case MERCENARY_POST -> {
                return "buildings/Military Buildings/ST08_mercenary_post.tgx.png";
            }
            case ENGINEER_GUILD -> {
                return "buildings/Military Buildings/ST24_Engineers_Guild.tgx.png";
            }
            case KILLING_PIT -> {
                return getRandomKillingPit();
            }
            case INN -> {
                return "buildings/Food Industry Buildings/ST22_Inn.tgx.png";
            }
            case MILL -> {
                return "buildings/Other Buildings/ST34_Mill.tgx.png";
            }
            case IRON_MINE -> {
                return "buildings/Other Buildings/ST05_Iron_Mine.tgx.png";
            }
            case MARKET -> {
                return "buildings/buildings/Market.png";
            }
            case OX_TETHER -> {
                return "buildings/buildings2/Industry Buildings in Crusader/gameinfo_buildings_resources_oxtether.gif";
            }
            case PITCH_RIG -> {
                return "buildings/buildings2/Industry Buildings in Crusader/gameinfo_buildings_resources_pitchrig.gif";
            }
            case QUARRY -> {
                return "buildings/buildings/Quarry.png";
            }
            case STOCKPILE -> {
                return "buildings/buildings2/Industry Buildings in Crusader/gameinfo_buildings_resources_stockpile.gif";
            }
            case WOODCUTTER -> {
                return "buildings/buildings/Woodcutter.png";
            }
            case APOTHECARY -> {
                return "buildings/Other Buildings/ST23_Healer.tgx.png";
            }
            case HOVEL -> {
                return "buildings/buildings2/Town Buildings/sc_gameinfo_buildings_3b.png";
            }
            case CHAPEL -> {
                return "buildings/buildings2/Town Buildings/sc_gameinfo_buildings_3c.png";
            }
            case CHURCH -> {
                return "buildings/buildings2/Town Buildings/sc_gameinfo_buildings_3b.png";
            }
            case CATHEDRAL -> {
                return "buildings/buildings2/Town Buildings/sc_gameinfo_buildings_3a.png";
            }
            case WELL -> {
                return "buildings/Other Buildings/ST27_Well.tgx.png";
            }
            case WATER_POT -> {
                return "buildings/Other Buildings/st70_water_pot.tgx.png";
            }
            case ARMOURER -> {
                return "buildings/Military Buildings/ST15_Armourers_Workshop.tgx.png";
            }
            case BLACKSMITH -> {
                return "buildings/Military Buildings/ST13_Blacksmiths_Workshop.tgx.png";
            }
            case FLETCHER -> {
                return "buildings/Military Buildings/ST12_Fletchers_Workshop.tgx.png";
            }
            case POLETURNER -> {
                return "buildings/Military Buildings/ST14_Poleturners_Workshop.tgx.png";
            }
            case OIL_SMELTER -> {
                return "buildings/Military Buildings/ST28_Oil_Smelter.tgx.png";
            }
            case PITCH_DITCH -> {
                return getRandomPitchDitch();
            }
            case CAGED_WAR_DOGS -> {
                return "buildings/Military Buildings/st99_dog_cage.tgx.png";
            }
            case SIEGE_TENT -> {
                return "buildings/Other Buildings/ST80_Siege_Tent.tgx.png";
            }
            case STABLE -> {
                return "buildings/Military Buildings/ST35_Stables.tgx.png";
            }
            case TUNNELER_GUILD -> {
                return "buildings/Military Buildings/ST25_Tunnellers_Guild.tgx.png";
            }
            case APPLE_ORCHARD -> {
                return "buildings/Food Industry Buildings/ST32_Applefarm.tgx.png";
            }
            case DIARY_FARMER -> {
                return "buildings/Food Industry Buildings/ST33_Cattlefarm.tgx.png";
            }
            case HOPS_FARMER -> {
                return "buildings/Food Industry Buildings/ST31_Hopsfarm.tgx.png";
            }
            case HUNTER_POST -> {
                return "buildings/Food Industry Buildings/ST07_Hunters_Hut.tgx.png";
            }
            case WHEAT_FARMER -> {
                return "buildings/Food Industry Buildings/ST30_Wheatfarm.tgx.png";
            }
            case BAKERY -> {
                return "buildings/Food Industry Buildings/ST17_Bakers_Workshop.tgx.png";
            }
            case BREWER -> {
                return "buildings/Food Industry Buildings/ST18_Brewers_Workshop.tgx.png";
            }
            case GRANARY -> {
                return "buildings/Food Industry Buildings/ST19_Granary.tgx.png";
            }
            case TUNNEL -> {
                return "buildings/Military Buildings/ST50_Tunnel_Enterance.tgx.png";
            }
            case TANNERS_WORKSHOP -> {
                return "buildings/Military Buildings/ST16_Tanners_Workshop.tgx.png";
            }
            case PALACE -> {
                return "buildings/buildings/Palace.png";
            }
            default -> throw new IllegalStateException("Unexpected value: " + buildingType);
        }
    }
}
