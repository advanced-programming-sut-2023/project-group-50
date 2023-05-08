package model.Government;

import controller.UserDatabase.User;
import model.Map.Map;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.Resource;
import model.ObjectsPackage.Storage;
import model.ObjectsPackage.Weapons.WeaponName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Government implements Serializable {
    private final User user;
    private final HashMap<Resource, Integer> resources;
    private final ArrayList<Person> noneJob;
    private int coins;
    private Map map;
    private ArrayList<Soldier> unDeployedSoldier;

    public Government(User user) {
        this.user = user;
        resources = new HashMap<>();
        coins = 0;
        this.noneJob = new ArrayList<>();
    }

    public void setResourceAmount(Resource resource, int value) {
        resources.replace(resource, value);
    }

    public int getResourceAmount(Resource resource) {
        return resources.getOrDefault(resource, 0);
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void buyBuilding(BuildingType buildingType, float zarib) {
        this.coins = getCoins() - (int) (buildingType.getGoldCost() * zarib);
        this.setResourceAmount(Resource.STONE,
                               this.resources.get(Resource.STONE) - (int) (buildingType.getStoneCost() * zarib));
        this.setResourceAmount(Resource.WOOD,
                               this.resources.get(Resource.WOOD) - (int) (buildingType.getWoodCost() * zarib));
        this.setResourceAmount(Resource.IRON,
                               this.resources.get(Resource.IRON) - (int) (buildingType.getIronCost() * zarib));
    }

    public ArrayList<Person> getNoneJob() {
        return noneJob;
    }

    public void addNoneJob(Person noneJob) {
        this.noneJob.add(noneJob);
    }

    public ArrayList<Soldier> getUnDeployedSoldier() {
        return unDeployedSoldier;
    }

    public void addUnDeployedSoldier(ArrayList<Soldier> unDeployedSoldier) {
        this.unDeployedSoldier.addAll(unDeployedSoldier);
    }

    public Storage getFirstEmptyStorage(BuildingType storageType) {
        for (int x = 0; x < map.getXSize(); x++)
            for (int y = 0; y < map.getYSize(); y++)
                for (Objects object : map.getXY(x, y).getObjects())
                    if (object instanceof Storage storage && storage.getType().equals(storageType) && !storage.isFull())
                        return storage;

        return null;
    }

    public Storage getFirstEmptyStorageForObject(Object object) {
        if (object instanceof WeaponName weapon) return getFirstEmptyStorage(BuildingType.ARMOURY);
        if (object instanceof Resource resource) {
            switch (resource) {
                case WHEAT, BREAD, FLOUR, HOPS, ALE, STONE, IRON, WOOD, PITCH -> {
                    return getFirstEmptyStorage(BuildingType.STOCKPILE);
                }
                case COW -> {
                    return getFirstEmptyStorage(BuildingType.OX_TETHER);
                }
                case MEAT, OIL, APPLE, CHEESE -> {
                    return getFirstEmptyStorage(BuildingType.GRANARY);
                }
            }
        }
        return null;
    }
}
