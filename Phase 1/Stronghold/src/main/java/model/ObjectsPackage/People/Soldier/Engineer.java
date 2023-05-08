package model.ObjectsPackage.People.Soldier;

import controller.UserDatabase.User;
import model.Direction.Direction;
import model.Government.Government;
import model.Map.GroundType;
import model.Map.Map;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Resource;

public class Engineer extends Soldier {
    private int range;

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
        government.setGold(government.getGold() - buildingType.getGoldCost());
    }

    private boolean ownerHasEnoughResources(BuildingType buildingType) {
        Government government = getOwner().getGovernment();
        if (buildingType.getIronCost() > government.getResourceAmount(Resource.IRON)) return false;
        if (buildingType.getGoldCost() > government.getGold()) return false;
        if (buildingType.getStoneCost() > government.getResourceAmount(Resource.STONE)) return false;
        if (buildingType.getWoodCost() > government.getResourceAmount(Resource.WOOD)) return false;
        if (buildingType.getPitchCost(1) > government.getResourceAmount(Resource.PITCH)) return false;
        return true;
    }

    public void pourOil(Direction direction) {
        Government government = getOwner().getGovernment();
        assert government.getResourceAmount(Resource.OIL) > 0;

        Map map = government.getMap();
        switch (direction) {
            case UP -> map.getXY(getX() - 1, getY()).setTexture(GroundType.OIL);
            case LEFT -> map.getXY(getX(), getY() - 1).setTexture(GroundType.OIL);
            case RIGHT -> map.getXY(getX(), getY() + 1).setTexture(GroundType.OIL);
            case DOWN -> map.getXY(getX() + 1, getY()).setTexture(GroundType.OIL);
        }

        government.setResourceAmount(Resource.OIL, government.getResourceAmount(Resource.OIL) - 1);
    }

    public void makeProtection(int x, int y) {
        getOwner().getGovernment().getMap().getXY(x, y).setProtected(true);
    }

    public void makeHammer(int x, int y) {
        // TODO: aa fill here
    }
}
