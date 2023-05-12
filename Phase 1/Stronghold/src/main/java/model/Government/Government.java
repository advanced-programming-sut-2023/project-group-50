package model.Government;

import controller.UserDatabase.User;
import model.Map.Map;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.People.Soldier.*;
import model.ObjectsPackage.Resource;
import model.ObjectsPackage.Storage;
import model.ObjectsPackage.Weapons.WeaponName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Government implements Serializable { //TODO: Jobs
    private final User user;
    private final ArrayList<Person> noneJob;
    private HashMap<Resource, Integer> resources;
    private HashMap<WeaponName, Integer> weapons;
    private int coins;
    private Map map;
    private ArrayList<Soldier> unDeployedSoldier;
    private Soldier lord;
    private Building lordsCastle;

    public Government(User user) {
        this.user = user;
        resources = new HashMap<>();
        weapons = new HashMap<>();
        coins = 0;
        this.noneJob = new ArrayList<>();
        placeLord();
    }

    public Building getLordsCastle() {
        return lordsCastle;
    }

    public void setLordsCastle(Building lordsCastle) {
        this.lordsCastle = lordsCastle;
    }

    private void placeLord() {
        lord = Soldier.getSoldierByType(SoldierName.THE_LORD, user);
        Pair xy = getEmptyXY();
        lordsCastle = Building.getBuildingByType(BuildingType.PALACE, user, xy.x, xy.y);
        map.getXY(xy.x, xy.y).addObject(lord);
        map.getXY(xy.x, xy.y).addObject(lordsCastle);
    }

    private Pair getEmptyXY() {
        Random random = new Random();
        Pair xy = new Pair(random.nextInt(map.getXSize()), random.nextInt(map.getYSize()));
        while (map.getXY(xy.x, xy.y).getObjects().isEmpty())
            xy = new Pair(random.nextInt(map.getXSize()), random.nextInt(map.getYSize()));
        return xy;
    }

    public void setResourceAmount(Resource resource, int value) {
        resources.replace(resource, value);
    }

    public int getResourceAmount(Resource resource) {
        return resources.getOrDefault(resource, 0);
    }

    public int getGold() {
        return coins;
    }

    public void setGold(int coins) {
        this.coins = coins;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void buyBuilding(BuildingType buildingType, float zarib) {
        this.coins = getGold() - (int) (buildingType.getGoldCost() * zarib);
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
                case OIL -> {
                    return getFirstEmptyStorage(BuildingType.OIL_SMELTER);
                }
                case COW -> {
                    return getFirstEmptyStorage(BuildingType.OX_TETHER);
                }
                case MEAT, APPLE, CHEESE -> {
                    return getFirstEmptyStorage(BuildingType.GRANARY);
                }
            }
        }
        return null;
    }

    public int getArmourAmount(ArmourType armourType) {
        WeaponName armour = getArmourByArmourType(armourType);
        if (armour == null) return 0;

        int sum = 0;
        for (int x = 0; x < map.getXSize(); x++)
            for (int y = 0; y < map.getYSize(); y++)
                for (Objects object : map.getXY(x, y).getObjects())
                    if (object instanceof Storage storage)
                        sum += storage.getCurrentCapacity(armour);

        return sum;
    }

    private WeaponName getArmourByArmourType(ArmourType armourType) {
        switch (armourType) {
            case LEATHER -> {
                return WeaponName.LEATHER_ARMOUR;
            }
            case METAL -> {
                return WeaponName.METAL_ARMOUR;
            }
            case NONE -> {
                return null;
            }

            default -> throw new IllegalStateException("Unexpected value: " + armourType);
        }
    }

    public int getWeaponAmount(WeaponName weaponName) {
        return weapons.get(weaponName);
    }

    public void setWeaponAmount(WeaponName weaponName, int count) {
        weapons.put(weaponName, count);
    }

    @Override
    public String toString() {
        return "Government:" +
                "\nuser=" + user.getUserName() +
                "\nnoneJob=" + noneJob +
                "\nresources=" + resources.toString().replaceAll(", ", "\n") +
                "\nweapons=" + weapons.toString().replaceAll(", ", "\n") +
                "\ncoins=" + coins +
                "\nunDeployedSoldier=" + unDeployedSoldier.size();
    }

    public Soldier getLord() {
        return lord;
    }

    public void moveLordToClosestPlace() {
        int X = lord.getX(), Y = lord.getY(), range = lord.getSpeed();

        Building closestBuilding = getClosestBuilding(X, Y, range);
        if (closestBuilding == null) return;
        lord.move(closestBuilding.getX(), closestBuilding.getY());
        lordsCastle = closestBuilding;
    }

    private Building getClosestBuilding(int X, int Y, int range) {
        Building closest = null;
        int dist = Integer.MAX_VALUE;
        for (int x = Math.max(0, X - range); x < Math.min(X + range, map.getXSize()); x++) {
            for (int y = Math.max(0, Y - range); y < Math.min(Y + range, map.getYSize()); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object instanceof Building building) {
                        if (!building.isDestroyed() && building.getOwner().equals(user)) {
                            int curDist = Map.distance(X, Y, x, y);
                            if (curDist < dist || (curDist == dist && building.getHp() > closest.getHp())) {
                                closest = building;
                                dist = curDist;
                            }
                        }
                    }
                }
            }
        }
        return closest;
    }

    public void defend() {
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object instanceof Soldier soldier) {
                        if (soldier.isAttacked())
                            soldier.defendAgainstAttacker();
                    } else if (object instanceof GroupSoldier groupSoldier && groupSoldier.isAttacked())
                        groupSoldier.defendAgainstAttacker();
                }
            }
        }
    }

    public void removeDeadSoldiers() {
        HashMap<Pair, Soldier> dead = new HashMap<>();
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object instanceof Soldier soldier)
                        if (!soldier.isAlive())
                            dead.put(new Pair(x, y), soldier);
                }
            }
        }

        for (Pair xy : dead.keySet()) map.getXY(xy.x, xy.y).getObjects().remove(dead.get(xy));
    }

    public void patrolAll() {
        HashSet<Soldier> moving = new HashSet<>();
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object instanceof Soldier soldier)
                        if (soldier.isPatrolling())
                            moving.add(soldier);
                }
            }
        }

        for (Soldier soldier : moving) soldier.patrol();
    }

    public void fillUpEmptyEngineers() {
        HashSet<Engineer> empty = new HashSet<>();
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object instanceof Engineer engineer)
                        if (!engineer.hasOil())
                            empty.add(engineer);
                }
            }
        }

        for (Engineer engineer : empty) engineer.moveTowardsClosestOilStorage();
    }

    public Storage getClosestNonEmptyOilStorage(Engineer engineer) {
        Storage closestStorage = null;
        int dist = Integer.MAX_VALUE;
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object instanceof Storage storage && storage.getType().equals(BuildingType.OIL_SMELTER) &&
                            !storage.isEmpty()) {
                        int curDist = engineer.distanceTo(storage);
                        if (curDist < dist) {
                            closestStorage = storage;
                            dist = curDist;
                        }
                    }
                }
            }
        }

        return closestStorage;
    }

    private class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
