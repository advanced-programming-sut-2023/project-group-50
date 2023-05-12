package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;
import model.Map.GroundType;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.Storage;

import java.util.HashMap;

public abstract class Building extends Objects {
    private final BuildingType type;
    private final User owner;
    private final int X;
    private final int Y;
    private final int maxHp;
    private final HashMap<String, Integer> residents;
    private int hp;

    protected Building(BuildingType type, User owner, int x, int y, int maxHp) {
        super(ObjectType.BUILDING, owner);
        this.type = type;
        this.owner = owner;
        X = x;
        Y = y;
        this.maxHp = maxHp;
        hp = maxHp;
        residents = new HashMap<>();
    }

    public static Building getBuildingByType(BuildingType buildingType, User owner, int x, int y) {
        switch (buildingType) {
            case SMALL_STONE_GATEHOUSE -> {
                return new Gate(buildingType,
                                owner,
                                x,
                                y,
                                1000,
                                8);
            }
            case BIG_STONE_GATEHOUSE -> {
                return new Gate(buildingType,
                                owner,
                                x,
                                y,
                                2000,
                                10);
            }
            case DRAW_BRIDGE -> {
                return new Gate(buildingType,
                                owner,
                                x,
                                y,
                                0,
                                0);
            }
            case LOOKOUT_TOWER -> {
                return new Tower(buildingType,
                                 owner,
                                 x,
                                 y,
                                 250,
                                 5,
                                 5);
            }
            case PERIMETER_TOWER -> {
                return new Tower(buildingType,
                                 owner,
                                 x,
                                 y,
                                 1000,
                                 10,
                                 10);
            }
            case TURRET -> {
                return new Tower(buildingType,
                                 owner,
                                 x,
                                 y,
                                 1500,
                                 10,
                                 10);
            }
            case SQUARE_TOWER -> {
                return new Tower(buildingType,
                                 owner,
                                 x,
                                 y,
                                 1600,
                                 15,
                                 15);
            }
            case ROUND_TOWER -> {
                return new Tower(buildingType,
                                 owner,
                                 x,
                                 y,
                                 2000,
                                 20,
                                 20);
            }
            case MERCENARY_POST, TUNNELER_GUILD -> {
                return new OffensiveStorage(buildingType,
                                            owner,
                                            x,
                                            y,
                                            500,
                                            0,
                                            0,
                                            0,
                                            0,
                                            0,
                                            0,
                                            0);
            }
            case ARMOURY -> {
                return new Storage(buildingType,
                                   owner,
                                   x,
                                   y,
                                   500,
                                   50);
            }
            case KILLING_PIT -> {
                return new OffensiveStorage(buildingType,
                                            owner,
                                            x,
                                            y,
                                            0,
                                            0,
                                            0,
                                            0,
                                            0,
                                            0,
                                            0,
                                            0);
            }
            case BARRACKS -> {
                return new Barracks(owner, x, y, 500);
            }
            case ENGINEER_GUILD -> {
                return new OffensiveStorage(buildingType,
                                            owner,
                                            x,
                                            y,
                                            500,
                                            0,
                                            0,
                                            0,
                                            4,
                                            30,
                                            0,
                                            0);
            }
            case INN, MILL, HOVEL, WATER_POT, SIEGE_TENT, STABLE -> {
                return new House(buildingType,
                                 owner,
                                 x,
                                 y,
                                 100,
                                 0);
            }
            case IRON_MINE, PITCH_RIG, QUARRY, WOODCUTTER -> {
                return new Mine(buildingType,
                                owner,
                                x,
                                y,
                                100,
                                2);
            }
            case MARKET -> {
                return new Market(buildingType,
                                  owner,
                                  x,
                                  y,
                                  100);
            }
            case OX_TETHER -> {
                return new Storage(buildingType,
                                   owner,
                                   x,
                                   y,
                                   100,
                                   1);
            }
            case STOCKPILE, GRANARY -> {
                return new Storage(buildingType,
                                   owner,
                                   x,
                                   y,
                                   100,
                                   250);
            }
            case APOTHECARY -> {
                return new Apothecary(buildingType,
                                      owner,
                                      x,
                                      y,
                                      100);
            }
            case CHAPEL -> {
                return new ReligiousBuilding(buildingType,
                                             owner,
                                             x,
                                             y,
                                             400,
                                             1);
            }
            case CHURCH -> {
                return new ReligiousBuilding(buildingType,
                                             owner,
                                             x,
                                             y,
                                             800,
                                             1);
            }
            case CATHEDRAL -> {
                return new ReligiousBuilding(buildingType,
                                             owner,
                                             x,
                                             y,
                                             1200,
                                             2);
            }
            case WELL -> {
                return new Wells(buildingType,
                                 owner,
                                 x,
                                 y,
                                 300);
            }
            case ARMOURER -> {
                return new Workshops(buildingType,
                                     owner,
                                     x,
                                     y,
                                     300,
                                     10);
            }
            case OIL_SMELTER -> {
                return new Storage(buildingType,
                                   owner,
                                   x,
                                   y,
                                   250,
                                   10);
            }
            case TUNNEL, PITCH_DITCH, CAGED_WAR_DOGS -> {
                return new Tunnel(buildingType,
                                  owner,
                                  x,
                                  y,
                                  0,
                                  x,
                                  y,
                                  x,
                                  y);
            }
            case BLACKSMITH, FLETCHER, POLETURNER, APPLE_ORCHARD, DIARY_FARMER, HOPS_FARMER, HUNTER_POST,
                    WHEAT_FARMER, BAKERY, BREWER, TANNERS_WORKSHOP -> {
                return new Workshops(buildingType,
                                     owner,
                                     x,
                                     y,
                                     300,
                                     2);
            }
            case PALACE -> {
                return new House(BuildingType.PALACE,
                                 owner,
                                 x,
                                 y,
                                 3000,
                                 10);
            }
            default -> throw new IllegalStateException("Unexpected value: " + buildingType);
        }
    }

    public static boolean isCastles(Building building) {
        BuildingType buildingType = building.type;
        switch (buildingType) {
            case SMALL_STONE_GATEHOUSE,
                    BIG_STONE_GATEHOUSE,
                    DRAW_BRIDGE,
                    LOOKOUT_TOWER,
                    PERIMETER_TOWER,
                    TURRET,
                    SQUARE_TOWER,
                    ROUND_TOWER,
                    ARMOURY,
                    MERCENARY_POST,
                    TUNNELER_GUILD,
                    KILLING_PIT,
                    BARRACKS,
                    ENGINEER_GUILD,
                    SIEGE_TENT,
                    STABLE,
                    PITCH_DITCH,
                    CAGED_WAR_DOGS,
                    OIL_SMELTER -> {
                return true;
            }

            default -> {
                return false;
            }
        }
    }

    public static boolean canPlace(BuildingType buildingType, GroundType texture) {
        switch (buildingType) {
            case DRAW_BRIDGE -> {
                return texture.equals(GroundType.RIVER);
            }
            case IRON_MINE -> {
                return texture.equals(GroundType.IRON);
            }
            case PITCH_RIG -> {
                return texture.equals(GroundType.OIL);
            }
            case QUARRY -> {
                return texture.equals(GroundType.STONE) || texture.equals(GroundType.CLIFF);
            }
            case WELL -> {
                return texture.equals(GroundType.SHALLOW_WATER) || texture.equals(GroundType.BIG_POND) ||
                        texture.equals(GroundType.SMALL_POND) || texture.equals(GroundType.BEACH) ||
                        texture.equals(GroundType.RIVER) || texture.equals(GroundType.SEA);
            }
            case APPLE_ORCHARD, DIARY_FARMER, HOPS_FARMER, HUNTER_POST, WHEAT_FARMER -> {
                return texture.equals(GroundType.GRASS) ||
                        texture.equals(GroundType.MEADOW) ||
                        texture.equals(GroundType.LAWN);
            }
            default -> {
                return texture.equals(GroundType.GROUND) || texture.equals(GroundType.GRASS) ||
                        texture.equals(GroundType.MEADOW) || texture.equals(GroundType.PLAIN) ||
                        texture.equals(GroundType.RIGGED_GROUND) || texture.equals(GroundType.LAWN);
            }
        }
    }

    public void repair() {
        hp = maxHp;
    }

    public boolean isDestroyed() {
        return hp <= 0;
    }

    private int getNumberOfResidents() {
        return residents.size();
    }

    public BuildingType getType() {
        return type;
    }

    public User getOwner() {
        return owner;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public HashMap<String, Integer> getResidents() {
        return residents;
    }


}
