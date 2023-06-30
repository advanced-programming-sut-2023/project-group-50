package model.ObjectsPackage.People.Soldier;

import controller.UserDatabase.User;
import model.Direction.Direction;
import model.Government.Government;
import model.Map.GroundType;
import model.Map.Map;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Resource;
import model.ObjectsPackage.Storage;

public class Engineer extends Soldier {
    private int range;
    private boolean hasOil;

    public Engineer(User owner) {
        super(SoldierName.ENGINEER, owner);
    }

    public void build(BuildingType building) {
        assert ownerHasEnoughResources(building);

        getOwner().getGovernment().getMap().addObject(
                Building.getBuildingByType(building,
                                           getOwner(),
                                           getX(),
                                           getY()),
                getX(),
                getY());

        removeResourcesFromOwner(building);
    }

    private void removeResourcesFromOwner(BuildingType buildingType) {
        Government government = getOwner().getGovernment();
        government.setResourceAmount(Resource.IRON,
                                     government.getResourceAmount(Resource.IRON) - buildingType.getIronCost());
        government.setResourceAmount(Resource.STONE,
                                     government.getResourceAmount(Resource.STONE) - buildingType.getStoneCost());
        government.setResourceAmount(Resource.WOOD,
                                     government.getResourceAmount(Resource.WOOD) - buildingType.getWoodCost());
        government.setResourceAmount(Resource.PITCH,
                                     government.getResourceAmount(Resource.PITCH) - buildingType.getPitchCost(1));
        government.setCoins(government.getCoins() - buildingType.getCoinCost());
        if (buildingType.getNumberOfWorkers() > 0)
            government.getNoneJob().subList(0, buildingType.getNumberOfWorkers()).clear();
    }

    private boolean ownerHasEnoughResources(BuildingType buildingType) {
        Government government = getOwner().getGovernment();
        if (buildingType.getIronCost() > government.getResourceAmount(Resource.IRON)) return false;
        if (buildingType.getCoinCost() > government.getCoins()) return false;
        if (buildingType.getStoneCost() > government.getResourceAmount(Resource.STONE)) return false;
        if (buildingType.getWoodCost() > government.getResourceAmount(Resource.WOOD)) return false;
        if (buildingType.getPitchCost(1) > government.getResourceAmount(Resource.PITCH)) return false;
        return buildingType.getNumberOfWorkers() <= government.getNoneJob().size();
    }

    public void pourOil(Direction direction) {
        assert hasOil;

        Government government = getOwner().getGovernment();
        Map map = government.getMap();
        switch (direction) {
            case UP -> map.getXY(getX() - 1, getY()).setTexture(GroundType.OIL);
            case LEFT -> map.getXY(getX(), getY() - 1).setTexture(GroundType.OIL);
            case RIGHT -> map.getXY(getX(), getY() + 1).setTexture(GroundType.OIL);
            case DOWN -> map.getXY(getX() + 1, getY()).setTexture(GroundType.OIL);
        }

        hasOil = false;
    }

    public void makeProtection(int x, int y) {
        getOwner().getGovernment().getMap().getXY(x, y).setProtected(true);
    }

    public void makeBatteringRam(int x, int y) {
        // TODO: aa fill here
    }

    public void placePitchDitch(int x, int y) {
        Unit xy = getOwner().getGovernment().getMap().getXY(x, y);
        if (!xy.getObjects().isEmpty()) return;
        xy.addObject(Building.getBuildingByType(BuildingType.PITCH_DITCH, getOwner(), x, y));
    }

    public boolean hasOil() {
        return hasOil;
    }

    public void moveTowardsClosestOilStorage() {
        Storage closestOilStorage = getOwner().getGovernment().getClosestNonEmptyOilStorage(this);

        if (closestOilStorage == null) return;
        moveClosest(closestOilStorage.getX(), closestOilStorage.getY());
    }
}
