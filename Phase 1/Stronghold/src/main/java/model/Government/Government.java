package model.Government;

import controller.UserDatabase.User;
import javafx.scene.paint.Color;
import model.Government.GUI.IconName;
import model.Map.Map;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.*;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.NonSoldier.Job;
import model.ObjectsPackage.People.NonSoldier.NonSoldier;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.People.PersonState;
import model.ObjectsPackage.People.Soldier.*;
import model.ObjectsPackage.Resource;
import model.ObjectsPackage.Storage;
import model.ObjectsPackage.Weapons.WeaponName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;

public class Government implements Serializable {
    private final User user;
    private final ArrayList<Person> noneJob;
    private final HashMap<Resource, Integer> resources;
    private final HashMap<PersonState, ArrayList<Person>> people;
    private final ArrayList<Building> buildings;
    private final HashMap<WeaponName, Integer> weapons;
    private double coins;
    private Map map;
    private HashMap<Resource, Double> foods;

    private int popularity;

    private int rateFood;
    private int previousRateFood;
    private int taxRate;
    private int previousRateTax;
    private int fearRate;
    private ArrayList<Soldier> unDeployedSoldier;
    private Soldier lord;
    private Building lordsCastle;

    public Government(User user) {
        this.user = user;
        resources = new HashMap<>();
        weapons = new HashMap<>();
        weapons.put(WeaponName.HAND, 10000);
        weapons.put(WeaponName.TEETH, 10000);
        coins = 0;
        this.people = new HashMap<>();
        this.people.put(PersonState.WORKER, new ArrayList<>());
        this.people.put(PersonState.JOBLESS, new ArrayList<>());
        this.people.put(PersonState.DEPLOYED_SOLDIER, new ArrayList<>());
        this.people.put(PersonState.UNDEPLOYED_SOLDIER, new ArrayList<>());
        this.buildings = new ArrayList<>();
        this.rateFood = -2;
        this.popularity = 0;
        this.previousRateFood = -2;
        this.taxRate = 0;
        this.previousRateTax = 0;
        this.fearRate = 0;
        this.noneJob = new ArrayList<>();
        this.foods = new HashMap<>();
        placeLord();
    }

    public Government(User user, int X0, int Y0) {
        this.user = user;
        resources = new HashMap<>();
        weapons = new HashMap<>();
        weapons.put(WeaponName.HAND, 10000);
        weapons.put(WeaponName.TEETH, 10000);
        coins = 0;
        this.people = new HashMap<>();
        this.people.put(PersonState.WORKER, new ArrayList<>());
        this.people.put(PersonState.JOBLESS, new ArrayList<>());
        this.people.put(PersonState.DEPLOYED_SOLDIER, new ArrayList<>());
        this.people.put(PersonState.UNDEPLOYED_SOLDIER, new ArrayList<>());
        this.buildings = new ArrayList<>();
        this.rateFood = -2;
        this.popularity = 0;
        this.previousRateFood = -2;
        this.taxRate = 0;
        this.previousRateTax = 0;
        this.fearRate = 0;
        this.noneJob = new ArrayList<>();
        this.foods = new HashMap<>();
        map = new Map(100, 100);
        placeLord(user, new Pair(X0, Y0));
    }

    public Government(User user, int X0, int Y0, Map map) {
        this.user = user;
        resources = new HashMap<>();
        weapons = new HashMap<>();
        weapons.put(WeaponName.HAND, 10000);
        weapons.put(WeaponName.TEETH, 10000);
        coins = 0;
        this.people = new HashMap<>();
        this.people.put(PersonState.WORKER, new ArrayList<>());
        this.people.put(PersonState.JOBLESS, new ArrayList<>());
        this.people.put(PersonState.DEPLOYED_SOLDIER, new ArrayList<>());
        this.people.put(PersonState.UNDEPLOYED_SOLDIER, new ArrayList<>());
        this.buildings = new ArrayList<>();
        this.rateFood = -2;
        this.popularity = 0;
        this.previousRateFood = -2;
        this.taxRate = 0;
        this.previousRateTax = 0;
        this.fearRate = 0;
        this.noneJob = new ArrayList<>();
        this.map = map;
        placeLord(user, new Pair(X0, Y0));
    }

    public static double getTaxValueByRate(int rateTax) {

        if (rateTax < 0) {
            return ((rateTax + 3) * 0.2) - 1;
        } else if (rateTax > 0) {
            return ((rateTax - 1) * 0.2) + 0.6;
        } else {
            return 0;
        }
    }

    private void placeLord(User user, Pair X0) {
        lord = Soldier.getSoldierByType(SoldierName.THE_LORD, user);
        Pair xy = X0;
        lordsCastle = Building.getBuildingByType(BuildingType.PALACE, user, xy.x, xy.y);
        NonSoldier lady = new NonSoldier(Job.LADY, user, lordsCastle);
        map.getXY(xy.x, xy.y).addObject(lord);
        map.getXY(xy.x, xy.y).addObject(lordsCastle);
        map.getXY(xy.x, xy.y).addObject(lady);
        addPeopleByState(lady, PersonState.WORKER);
    }

    public Building getLordsCastle() {
        return lordsCastle;
    }

    public void setLordsCastle(Building lordsCastle) {
        this.lordsCastle = lordsCastle;
    }

    private void placeLord() {
        placeLord(user, getEmptyXY());
    }

    private Pair getEmptyXY() {
        Random random = new Random();
        Pair xy = new Pair(random.nextInt(map.getXSize()), random.nextInt(map.getYSize()));
        while (map.getXY(xy.x, xy.y).getObjects().isEmpty())
            xy = new Pair(random.nextInt(map.getXSize()), random.nextInt(map.getYSize()));
        return xy;
    }

    public void setResourceAmount(Resource resource, int value) {
        if (resource.isFood()) foods.replace(resource, (double) value);
        if (resources.get(resource) != null) resources.replace(resource, value);
        else resources.put(resource, value);
    }

    public int getResourceAmount(Resource resource) {
        if (resource.isFood()) return (int) Math.ceil(foods.getOrDefault(resource, 0.0));
        return resources.getOrDefault(resource, 0);
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
        double value = Government.getTaxValueByRate(this.previousRateTax);
        if (value < 0) {
            if (-1 * value * getPopulation() > coins) {
                this.taxRate = 0;
            } else {
                this.taxRate = this.previousRateTax;
            }
        }
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getPreviousRateFood() {
        return previousRateFood;
    }

    public void setPreviousRateFood(int previousRateFood) {
        this.previousRateFood = previousRateFood;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public HashMap<Resource, Double> getFoods() {
        return foods;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addBuildings(Building building) {
        System.out.println("added" + building.getType());
        this.buildings.add(building);

    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getRateFood() {
        return rateFood;
    }

    public void setRateFood(int rateFood) {
        this.rateFood = rateFood;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getPreviousRateTax() {
        return previousRateTax;
    }

    public void setPreviousRateTax(int previousRateTax) {
        this.previousRateTax = previousRateTax;
    }

    public void buyBuilding(BuildingType buildingType, float coefficient) {
        if ((this.getCoins() < (int) (buildingType.getCoinCost() * coefficient)) ||
                (this.resources.getOrDefault(Resource.STONE, 0) < (int) (buildingType.getStoneCost() * coefficient)) ||
                (this.resources.getOrDefault(Resource.WOOD, 0) < (int) (buildingType.getWoodCost() * coefficient)) ||
                (this.resources.getOrDefault(Resource.IRON, 0) < (int) (buildingType.getIronCost() * coefficient))
        ) {
            System.out.println("You don't hava enough resources to repair this building!");
            return;
        }
        this.setCoins(getCoins() - (int) (buildingType.getCoinCost() * coefficient));
        this.setResourceAmount(Resource.STONE,
                               this.resources.getOrDefault(Resource.STONE, 0) -
                                       (int) (buildingType.getStoneCost() * coefficient));
        this.setResourceAmount(Resource.WOOD,
                               this.resources.getOrDefault(Resource.WOOD, 0) -
                                       (int) (buildingType.getWoodCost() * coefficient));
        this.setResourceAmount(Resource.IRON,
                               this.resources.getOrDefault(Resource.IRON, 0) -
                                       (int) (buildingType.getIronCost() * coefficient));
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
        for (Soldier soldier : unDeployedSoldier)
            addPeopleByState(soldier, PersonState.UNDEPLOYED_SOLDIER);
//        this.unDeployedSoldier.addAll(unDeployedSoldier);
    }

    public ArrayList<Person> getPeopleByState(PersonState personState) {
        return this.people.get(personState);
    }

    public void addPeopleByState(Person person, PersonState personState) {
        people.putIfAbsent(personState, new ArrayList<>());
        this.people.get(personState).add(person);
        if (personState.equals(PersonState.JOBLESS)) {
            Building building = noneActiveBuilding();
            if (building != null) {
                peopleGetJob(1, Building.getJobByBuildingType(building.getType()), building);
            }
        }
    }

    public HashMap<WeaponName, Integer> getWeapons() {
        return weapons;
    }

    public void addWeaponByName(int count, WeaponName weaponName) {
        if (weapons.containsKey(weaponName)) {
            weapons.replace(weaponName, weapons.get(weaponName) + count);
        } else {
            weapons.put(weaponName, count);
        }
    }

    public void peopleGetJob(int number, Job job, Building building) {
        ArrayList<Person> jobLess = this.people.get(PersonState.JOBLESS);
        ArrayList<Person> worker = this.people.get(PersonState.WORKER);
        HashMap<String, Integer> residents = building.getResidents();
        for (int i = 0; i < number; i++) {
            Person person = jobLess.get(0);
            NonSoldier nonSoldier = new NonSoldier(job, this.user, building);
            nonSoldier.setLife(person.getLife());
            nonSoldier.setStarving(person.getStarving());
            nonSoldier.setIncome(person.getIncome());
            jobLess.remove(0);
            worker.add(nonSoldier);
        }
        if (!residents.containsKey("worker"))
            residents.put("worker", 0);
        residents.replace("worker", residents.get("worker") + number);
        building.setActive(residents.get("worker") >= Building.numberOfWorker(building.getType()));
    }

    public Storage getFirstEmptyStorage(BuildingType storageType) {
        for (int x = 0; x < map.getXSize(); x++)
            for (int y = 0; y < map.getYSize(); y++)
                for (Objects object : map.getXY(x, y).getObjects())
                    if (object instanceof Storage storage && storage.getType().equals(storageType) &&
                            !storage.isFull()) return storage;

        return null;
    }

    private Building noneActiveBuilding() {
        if (buildings.isEmpty()) {
            return null;
        }
        for (Building building : this.buildings) {
            if (!building.isActive()) {
                return building;
            }
        }
        return null;
    }

    public void addUndeployedSoldier(int count, SoldierName soldierName, User owner) {
        for (int i = 0; i < count; i++) {
            Soldier soldier = Soldier.getSoldierByType(soldierName, owner);
            this.addPeopleByState(soldier, PersonState.UNDEPLOYED_SOLDIER);
        }
        this.weapons.replace(Soldier.getWeaponName(soldierName),
                             this.weapons.get(Soldier.getWeaponName(soldierName)) - count);
        this.setCoins(this.coins - (double) soldierName.getCoinCost() * count);
    }

    public void addFoods(Resource food, Double count) {
        if (this.foods.containsKey(food)) {
            this.foods.replace(food, this.foods.get(food) + count);
        } else {
            this.foods.put(food, count);
        }
        double value = (this.previousRateFood + 2) * 0.5;
        if (value * getPopulation() > getFoodNumber()) {
            this.rateFood = -2;
        } else {
            this.rateFood = this.previousRateFood;
        }
    }

    public int checkPopularityFood() {
        int value = 0;
        if (foods.containsKey(Resource.BREAD) && foods.get(Resource.BREAD) > 0) {
            value++;
        }
        if (foods.containsKey(Resource.MEAT) && foods.get(Resource.MEAT) > 0) {
            value++;
        }
        if (foods.containsKey(Resource.CHEESE) && foods.get(Resource.CHEESE) > 0) {
            value++;
        }
        if (foods.containsKey(Resource.APPLE) && foods.get(Resource.APPLE) > 0) {
            value++;
        }

        value = value + (this.rateFood * 4) - 1;
        return value;
    }

    public int getPopulation() {
        int size = this.people.get(PersonState.JOBLESS).size();
        int size1 = this.people.get(PersonState.DEPLOYED_SOLDIER).size();
        int size2 = this.people.get(PersonState.UNDEPLOYED_SOLDIER).size();
        int size3 = this.people.get(PersonState.WORKER).size();
        int i = size +
                size1 +
                size2 +
                size3;
        return i;
    }

    public double getFoodNumber() {
        return this.foods.getOrDefault(Resource.MEAT, 0.0) +
                this.foods.getOrDefault(Resource.APPLE, 0.0) +
                this.foods.getOrDefault(Resource.BREAD, 0.0) +
                this.foods.getOrDefault(Resource.CHEESE, 0.0);
    }

    public void feedPeople() {

        double value = (this.rateFood + 2) * 0.5;
        if (getFoodNumber() == 0 || value * getPopulation() > getFoodNumber()) {
            this.rateFood = -2;
            return;
        }

        double cheeseDecrease = this.getPopulation() * value * (this.foods.get(Resource.CHEESE) / this.getFoodNumber());
        double meatDecrease = this.getPopulation() * value * (this.foods.get(Resource.MEAT) / this.getFoodNumber());
        double appleDecrease = this.getPopulation() * value * (this.foods.get(Resource.APPLE) / this.getFoodNumber());
        double breadDecrease = this.getPopulation() * value * (this.foods.get(Resource.BREAD) / this.getFoodNumber());

        this.foods.put(Resource.CHEESE, this.foods.get(Resource.CHEESE) - cheeseDecrease);
        this.foods.put(Resource.MEAT, this.foods.get(Resource.MEAT) - meatDecrease);
        this.foods.put(Resource.APPLE, this.foods.get(Resource.APPLE) - appleDecrease);
        this.foods.put(Resource.BREAD, this.foods.get(Resource.BREAD) - breadDecrease);

        if (value * getPopulation() > getFoodNumber()) {
            this.rateFood = -2;
        }
        this.popularity = this.popularity + checkPopularityFood();
    }

    public int checkPopularityTax() {
        if (this.taxRate <= 0) {
            return 7 - (this.taxRate + 3) * 2;
        } else if (this.taxRate > 0 && this.taxRate < 5) {
            return -(this.getTaxRate() * 2);
        } else {
            return -(((this.getTaxRate() - 5) * 4) + 12);
        }
    }

    public void getTaxPeople() {
        double value = getTaxValueByRate(this.taxRate);
        if (this.taxRate < 0) {
            if (-1 * value * getPopulation() > coins) {
                this.taxRate = 0;
                return;
            }

        }
        for (Entry<PersonState, ArrayList<Person>> set : this.people.entrySet()) {
            for (Person person : set.getValue()) {
                if (value > 0 && person.getIncome() < value) {
                    person.setIncome(0);
                } else {
                    person.setIncome(person.getIncome() - value);
                }
            }
        }
        this.coins = this.coins + value * getPopulation();
        if (this.taxRate < 0 && -1 * value * getPopulation() > coins) {
            this.taxRate = 0;
        }
        this.popularity = this.popularity + checkPopularityTax();
    }

    public int checkReligionPopularity() {
        int value = 0;
        for (Building building : buildings) {
            if (building instanceof ReligiousBuilding) {
                value = value + ((ReligiousBuilding) building).getPopularity();
            }
        }
        return value;
    }

    public int checkFearPopularity() {
        int value = 0;
        for (Building building : buildings) {
            value = value + Building.isGoodOrBad(building);
        }
        return value + fearRate;
    }

    public Storage getFirstEmptyStorageForObject(Object object) {
        if (object instanceof WeaponName weaponName) return getFirstEmptyStorage(BuildingType.ARMOURY);
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

    public void decrementArmourAmount(ArmourType armourType, int count) {
        WeaponName armour = getArmourByArmourType(armourType);
        if (armour == null) return;

        for (int x = 0; x < map.getXSize(); x++)
            for (int y = 0; y < map.getYSize(); y++)
                for (Objects object : map.getXY(x, y).getObjects())
                    if (object instanceof Storage storage) {
                        int dec = Math.min(storage.getCurrentCapacity(armour), count);
                        count -= dec;
                        for (int i = 0; i < dec; i++) storage.reduceByOne(armour);
                        if (count == 0)
                            return;
                    }

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
        return weapons.getOrDefault(weaponName, 0);
    }

    public void setWeaponAmount(WeaponName weaponName, int count) {
        weapons.put(weaponName, count);
    }


    @Override
    public String toString() {
        return "Government{" +
                "user=" + user.toString() +
                "\nnoneJob=" + noneJob.size() +
                "\nresources=" + resources.toString().replaceAll(", ", "\n") +
                "\npeople=" + getPopulation() +
                "\ncoins=" + coins +
                "\nfoods=" + foods.toString().replaceAll(", ", "\n") +
                "\npopularity=" + popularity +
                "\nrateFood=" + rateFood +
                "\npreviousRateFood=" + previousRateFood +
                "\ntaxRate=" + taxRate +
                "\npreviousRateTax=" + previousRateTax +
                "\nfearRate=" + fearRate +
                "\nweapons=" + weapons.toString().replaceAll(", ", "\n") +
                "\nlord=" + lord.getHp() +
                "\nlordsCastle=" + lordsCastle.getX() + ", " + lordsCastle.getY() +
                '}';
    }

    public Soldier getLord() {
        return lord;
    }

    public void moveLordToClosestPlace() {
        int X = lord.getX(), Y = lord.getY(), range = lord.getSpeed();

        Building closestBuilding = getClosestAllyBuilding(X, Y, range);
        if (closestBuilding == null) return;
        lord.move(closestBuilding.getX(), closestBuilding.getY());
        lordsCastle = closestBuilding;
    }

    private Building getClosestAllyBuilding(int X, int Y, int range) {
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

    private Building getClosestEnemyBuilding(int X, int Y, int range) {
        Building closest = null;
        int dist = Integer.MAX_VALUE;
        for (int x = Math.max(0, X - range); x < Math.min(X + range, map.getXSize()); x++) {
            for (int y = Math.max(0, Y - range); y < Math.min(Y + range, map.getYSize()); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object instanceof Building building) {
                        if (!building.isDestroyed() && !building.getOwner().equals(user)) {
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

    private Objects getClosestEnemyObject(int X, int Y, int range, User enemy) {
        Objects closest = null;
        int dist = Integer.MAX_VALUE;
        for (int x = Math.max(0, X - range); x < Math.min(X + range, map.getXSize()); x++) {
            for (int y = Math.max(0, Y - range); y < Math.min(Y + range, map.getYSize()); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object.getOwner().equals(enemy)) {
                        int curDist = Map.distance(X, Y, x, y);
                        if (curDist < dist) {
                            closest = object;
                            dist = curDist;
                        }
                    }
                }
            }
        }
        return closest;
    }

    private Objects getClosestNotOnFireEnemy(int X, int Y, int range) {
        Objects closest = null;
        int dist = Integer.MAX_VALUE;
        for (int x = Math.max(0, X - range); x < Math.min(X + range, map.getXSize()); x++) {
            for (int y = Math.max(0, Y - range); y < Math.min(Y + range, map.getYSize()); y++) {
                if (map.getXY(x, y).isOnFire()) continue;
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (!object.getOwner().equals(user)) {
                        int curDist = Map.distance(X, Y, x, y);
                        if (curDist < dist || (curDist == dist && object.getHp() > closest.getHp())) {
                            closest = object;
                            dist = curDist;
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

    public void removeDestroyedBuildings() {
        HashMap<Pair, Building> dead = new HashMap<>();
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object instanceof Building building && building.isDestroyed())
                        dead.put(new Pair(x, y), building);
                }
            }
        }

        for (Pair xy : dead.keySet()) map.getXY(xy.x, xy.y).getObjects().remove(dead.get(xy));
    }

    public void spreadFire() {
        HashSet<Pair> newFire = new HashSet<>();
        for (int x = 0; x < map.getXSize(); x++)
            for (int y = 0; y < map.getYSize(); y++)
                if (map.getXY(x, y).isOnFire()) spreadFireRandomly(newFire, x, y);

        for (Pair xy : newFire) map.getXY(xy.x, xy.y).setOnFire(true);
    }

    private void spreadFireRandomly(HashSet<Pair> newFire, int x, int y) {
        int d = new Random().nextInt(4);
        switch (d) {
            case 0 -> newFire.add(new Pair(Math.min(map.getXSize(), x + 1), y));
            case 1 -> newFire.add(new Pair(Math.max(0, x - 1), y));
            case 2 -> newFire.add(new Pair(x, Math.min(map.getYSize(), y + 1)));
            case 3 -> newFire.add(new Pair(x, Math.max(0, y - 1)));
        }
    }

    public void applyFireDamage() {
        for (int x = 0; x < map.getXSize(); x++)
            for (int y = 0; y < map.getYSize(); y++)
                if (map.getXY(x, y).isOnFire()) for (Objects objects : map.getXY(x, y).getObjects())
                    objects.applyDamage(50);
    }

    public void attackWeapons() {
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object instanceof GroupSoldier groupSoldier && groupSoldier.getWeapon() != null) {
                        switch (groupSoldier.getWeapon().getWeaponName()) {
                            case BATTERING_RAM -> {
                                handleBatteringRam(groupSoldier);
                            }
                            case CATAPULT -> {
                                handleCatapult(groupSoldier);
                            }
                            case FIRE_THROWER -> {
                                handleFireThrower(groupSoldier);
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleBatteringRam(GroupSoldier groupSoldier) {
        Objects closestEnemy = getClosestEnemyBuilding(groupSoldier.getX(),
                                                       groupSoldier.getY(),
                                                       WeaponName.BATTERING_RAM.getRange());

        if (closestEnemy != null)
            closestEnemy.applyDamage(1000);
    }

    private void handleCatapult(GroupSoldier groupSoldier) {
        Objects closestEnemy = getClosestEnemyBuilding(groupSoldier.getX(),
                                                       groupSoldier.getY(),
                                                       WeaponName.CATAPULT.getRange());
        if (closestEnemy != null)
            closestEnemy.applyDamage(100);
    }

    private void handleFireThrower(GroupSoldier groupSoldier) {
        Objects closestEnemy = getClosestNotOnFireEnemy(groupSoldier.getX(),
                                                        groupSoldier.getY(),
                                                        WeaponName.FIRE_THROWER.getRange());
        if (closestEnemy != null)
            map.getXY(closestEnemy.getX(), closestEnemy.getY()).setOnFire(true);
    }

    public void produceFoodAndResources() {
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object instanceof Mine mine) mine.produce();
                }
            }
        }

        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object instanceof Workshops workshop) workshop.produce();
                    if (object instanceof FoodProcessingBuildings foodProcessingBuilding)
                        foodProcessingBuilding.produce();
                }
            }
        }
    }

    public void addNoneJob(int capacity, Job job, Building building) {
        for (int i = 0; i < capacity; i++) addNoneJob(new NonSoldier(job, user, building));
    }

    public void addFood(Resource resource) {
        if (resource.isFood()) foods.put(resource, 0.0);
    }

    public void attackEnemy(User enemy) {
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object instanceof Soldier soldier)
                        soldier.attack(getClosestEnemyObject(x, y, soldier.getSpeed(), enemy));
                }
            }
        }
    }

    public boolean hasMarket() {
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object.getOwner().equals(user) && object instanceof Market market)
                        return true;
                }
            }
        }
        return false;
    }

    public int getMaxPopulation() {
        int cap = 0;
        for (int x = 0; x < map.getXSize(); x++)
            for (int y = 0; y < map.getYSize(); y++)
                for (Objects object : map.getXY(x, y).getObjects())
                    if (object.getOwner().equals(user) && object instanceof House house)
                        cap += house.getCapacity();
        return cap;
    }

    public int getResources() {
        int value = 0;
        for (Entry<Resource, Integer> resource : resources.entrySet())
            value += resource.getValue();
        return value;
    }

    public boolean canAfford(BuildingType buildingType) {
        if (coins < buildingType.getCoinCost()) return false;
        if (resources.getOrDefault(Resource.IRON, 0) < buildingType.getIronCost()) return false;
        if (resources.getOrDefault(Resource.WOOD, 0) < buildingType.getWoodCost()) return false;
        if (resources.getOrDefault(Resource.STONE, 0) < buildingType.getStoneCost()) return false;
        if (resources.getOrDefault(Resource.PITCH, 0) < buildingType.getPitchCost(1)) return false;
        return !buildingType.equals(BuildingType.PALACE) || map.getPalace() == null;
    }

    public boolean canAfford(BuildingType buildingType, float coeff) {
        if (coins < buildingType.getCoinCost() * coeff) return false;
        if (resources.getOrDefault(Resource.IRON, 0) < buildingType.getIronCost() * coeff) return false;
        if (resources.getOrDefault(Resource.WOOD, 0) < buildingType.getWoodCost() * coeff) return false;
        if (resources.getOrDefault(Resource.STONE, 0) < buildingType.getStoneCost() * coeff) return false;
        return !(resources.getOrDefault(Resource.PITCH, 0) < buildingType.getPitchCost(1) * coeff);
    }

    public String canRepair(Building building) {
        float cost;
        if (building.isDestroyed()) cost = 1;
        else cost = 1 - ((float) building.getHp() / (float) building.getMaxHp());

        if (!canAfford(building.getType(), cost))
            return "You don't have enough resources";

        if (building.getOwner().getGovernment().getMap().searchForEnemy(building.getX(), building.getY(),
                                                                        building.getOwner()))
            return "The enemy soldier is close";

        building.getOwner().getGovernment().buyBuilding(building.getType(), cost);
        building.repair();
        return null;
    }

    public Person joblessTo(Job job, Building building) {
        if (!people.containsKey(PersonState.JOBLESS)) {
            return null;
        }

        ArrayList<Person> jobless = people.get(PersonState.JOBLESS);
        if (jobless.size() < 1) return null;

        Person removed = jobless.remove(jobless.size() - 1);
        Unit xy = map.getXY(removed.getX(), removed.getY());
        xy.removeObject(removed);

        NonSoldier nonSoldier = new NonSoldier(job, user, building);

        addPeopleByState(nonSoldier, PersonState.WORKER);

        return nonSoldier;
    }

    public boolean canAffordSoldiers(SoldierName selected, int number) {
        return coins >= selected.getCoinCost() * number;
    }

    public boolean hasEnoughWeapons(SoldierName selected, int number) {
        return weapons.getOrDefault(Soldier.getWeaponName(selected), 0) >= number;
    }

    public boolean hasEnoughArmour(SoldierName selected, int number) {
        if (selected.getArmourType() == ArmourType.NONE) return true;
        return getArmourAmount(selected.getArmourType()) >= number;
    }

    public int getBuildingCount(BuildingType buildingType) {
        int sum = 0;
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object.getOwner().equals(user) && object instanceof Building building &&
                            building.getType().equals(buildingType))
                        sum++;
                }
            }
        }
        return sum;
    }

    public int getSoldierCount(SoldierName soldierName) {
        int sum = 0;
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object.getOwner().equals(user) && object instanceof Soldier soldier &&
                            soldier.getType().equals(soldierName))
                        sum++;
                }
            }
        }
        return sum;
    }

    public int getNonSoldierCount(Job job) {
        int sum = 0;
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object.getOwner().equals(user) && object instanceof NonSoldier nonSoldier &&
                            nonSoldier.getJob().equals(job))
                        sum++;
                }
            }
        }
        return sum;
    }

    public String getIconData(IconName iconName) {
        switch (iconName) {
            case COIN -> {
                return String.valueOf(coins);
            }
            case FOOD -> {
                return getFoodNumber() + " (rate: " + getRateFood() + ")";
            }
            case RELIGION -> {
                return String.valueOf(checkReligionPopularity());
            }
            case RESOURCES -> {
                return String.valueOf(getResources());
            }
            case FEAR -> {
                return checkFearPopularity() + " (rate: " + getFearRate() + ")";
            }
            case TAX -> {
                return String.valueOf(getTaxRate());
            }
        }

        return null;
    }

    public int getResourceRate(Resource resource) {
        int sum = 0;
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object.getOwner().equals(user) && object instanceof Workshops workshops) {
                        if (workshops.produces(resource))
                            sum++;
                        if (workshops.consumes(resource))
                            sum--;
                    }
                }
            }
        }
        return sum;
    }

    public Color getColor() {
        return user.getColor().toColor();
    }

    public String getPalaceState() {
        return lordsCastle.isDestroyed() ? "Destroyed" : String.valueOf(lordsCastle.getHp());
    }

    public String getLadyState() {
        Person lady = getLady();
        return lady == null || !lady.isAlive() ? "Dead" : String.valueOf(lady.getHp());
    }

    private Person getLady() {
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object.getOwner().equals(user) && object instanceof NonSoldier nonSoldier &&
                            nonSoldier.getJob().equals(Job.LADY))
                        return nonSoldier;
                }
            }
        }
        return null;
    }

    public static class Pair {
        public int x;
        public int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
