package model.ObjectsPackage;

import controller.UserDatabase.User;
import model.Government.Government;
import model.Map.Map;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Weapons.WeaponName;

import java.util.HashMap;
import java.util.Objects;

public class Storage extends Building {
    private final HashMap<String, Integer> currentCapacity;
    private final int maximumCapacity;
    private Storage nextStorage;
    private Storage previousStorage;


    public Storage(BuildingType type, User owner, int x, int y, int maxHp, int maximumCapacity) {
        super(type, owner, x, y, maxHp);
        this.nextStorage = null;
        this.maximumCapacity = maximumCapacity;
        currentCapacity = new HashMap<>();

        Map map = owner.getGovernment().getMap();
        checkNeighboursForStorage(type, x, y, map);
    }

    private void checkNeighboursForStorage(BuildingType type, int x, int y, Map map) {
        if (x != 0 && checkForStorage(type, x - 1, y, map)) return;
        if (x != map.getXSize() - 1 && checkForStorage(type, x + 1, y, map)) return;
        if (y != 0 && checkForStorage(type, x, y - 1, map)) return;
        if (y != map.getYSize() - 1 && checkForStorage(type, x, y + 1, map)) {
        }
    }

    private boolean checkForStorage(BuildingType type, int x, int y, Map map) {
        if (map.getXY(x, y).hasObjectType(type)) {
            Storage prevStorage = (Storage) map.getXY(x, y).getObjectType(type);
            if (prevStorage != null && prevStorage.nextStorage == null) {
                prevStorage.setNextStorage(this);
                this.previousStorage = prevStorage;
            }
            return true;
        }
        return false;
    }

    public int getCurrentCapacity(Resource resource) {
        return currentCapacity.get(resource.name());
    }

    public int getCurrentCapacity(WeaponName resource) {
        return currentCapacity.getOrDefault(resource.name(), 0);
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    private boolean reduceByOne(WeaponName weaponName) {
        if (Objects.requireNonNull(getType()) == BuildingType.ARMOURY) {
            if (currentCapacity.get(weaponName.name()) < 0) return false;
            currentCapacity.put(weaponName.name(), currentCapacity.get(weaponName.name()) - 1);
            return true;
        }
        throw new IllegalStateException("Unexpected value: " + getType());
    }

    private boolean reduceByOne(Resource resource) {
        if (Objects.requireNonNull(getType()) == BuildingType.GRANARY
                || getType() == BuildingType.STOCKPILE
                || getType() == BuildingType.OX_TETHER) {
            if (currentCapacity.get(resource.name()) < 0) return false;
            currentCapacity.put(resource.name(), currentCapacity.get(resource.name()) - 1);
            return true;
        }
        throw new IllegalStateException("Unexpected value: " + getType());
    }

    public boolean reduceByOne(Object object) {
        if (object instanceof Resource resource) return reduceByOne(resource);
        if (object instanceof WeaponName weaponName) return reduceByOne(weaponName);
        throw new IllegalStateException("Unexpected value: " + getType());
    }

    private boolean addOneWeapon(WeaponName weaponName) {
        if (Objects.requireNonNull(getType()) == BuildingType.ARMOURY) {
            if (getCurrentCapacity() >= maximumCapacity) return false;
            currentCapacity.put(weaponName.name(), currentCapacity.getOrDefault(weaponName.name(), 0) + 1);
            return true;
        }
        throw new IllegalStateException("Unexpected value: " + getType());
    }

    private boolean addOneResource(Resource resource) {
        if (Objects.requireNonNull(getType()) == BuildingType.GRANARY
                || getType() == BuildingType.STOCKPILE
                || getType() == BuildingType.OX_TETHER) {
            if (getCurrentCapacity() >= maximumCapacity) return false;
            currentCapacity.put(resource.name(), currentCapacity.get(resource.name()) + 1);
            return true;
        }
        throw new IllegalStateException("Unexpected value: " + getType());
    }

    public boolean addOne(Object object) {
        if (object instanceof WeaponName weapon) {
            addOneToGovernmentHashmaps(weapon);
            return addOneWeapon(weapon);
        }
        if (object instanceof Resource resource) {
            switch (resource) {
                case WHEAT, BREAD, FLOUR, HOPS, ALE, STONE, IRON, WOOD, PITCH -> {
                    return addIfCorrect(BuildingType.STOCKPILE, resource);
                }
                case COW -> {
                    return addIfCorrect(BuildingType.OX_TETHER, resource);
                }
                case MEAT, OIL, APPLE, CHEESE -> {
                    return addIfCorrect(BuildingType.GRANARY, resource);
                }
            }
        }
        return false;
    }

    private boolean addIfCorrect(BuildingType type, Resource resource) {
        if (!(getType().equals(type) && addOneResource(resource))) {
            return false;
        }
        addOneToGovernmentHashmaps(resource);
        return true;
    }

    private void addOneToGovernmentHashmaps(Object object) {
        Government government = getOwner().getGovernment();
        if (object instanceof WeaponName weapon)
            government.setWeaponAmount(weapon, government.getWeaponAmount(weapon) + 1);
        if (object instanceof Resource resource)
            government.setResourceAmount(resource, government.getResourceAmount(resource) + 1);
    }

    public int getCurrentCapacity() {
        return currentCapacity.values().stream().mapToInt(thisSize -> thisSize).sum();
    }

    public boolean hasNextStorage() {
        return nextStorage != null;
    }

    public Storage getNextStorage() {
        return nextStorage;
    }

    public void setNextStorage(Storage nextStorage) {
        this.nextStorage = nextStorage;
    }

    public boolean isFull() {
        return getCurrentCapacity() >= getMaximumCapacity();
    }

    public boolean isEmpty() {
        return getCurrentCapacity() == 0;
    }

    public Storage prevStorage() {
        return previousStorage;
    }

    public void removeNextStorage(Storage storage) {
        findNextStorage(storage);
    }

    private void findNextStorage(Storage storage) {
        int x = getX(), y = getY();
        Map map = getOwner().getGovernment().getMap();
        BuildingType type = getType();
        if (x != 0 && checkForNextStorage(type, x - 1, y, map, storage)) return;
        if (x != map.getXSize() - 1 && checkForNextStorage(type, x + 1, y, map, storage)) return;
        if (y != 0 && checkForNextStorage(type, x, y - 1, map, storage)) return;
        if (y != map.getYSize() - 1 && checkForNextStorage(type, x, y + 1, map, storage)) return;
        nextStorage = null;
    }

    private boolean checkForNextStorage(BuildingType type, int x, int y, Map map, Storage storage) {
        if (map.getXY(x, y).hasObjectType(type)) {
            Storage nextStorage = (Storage) map.getXY(x, y).getObjectType(type);
            if (nextStorage != null && nextStorage.previousStorage == null && nextStorage != storage) {
                nextStorage.setPreviousStorage(this);
                this.nextStorage = nextStorage;
            }
            return true;
        }
        return false;
    }

    private void setPreviousStorage(Storage storage) {
        previousStorage = storage;
    }
}
