package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;
import model.Government.Government;
import model.ObjectsPackage.Resource;
import model.ObjectsPackage.Storage;
import model.ObjectsPackage.Weapons.WeaponName;

public class Workshops extends Building {
    private final int rate;
    private Resource fromResource;
    private WeaponName[] toWeapon;
    private Resource[] toResource;

    protected Workshops(BuildingType type, User owner, int x, int y, int maxHp, int rate) {
        super(type, owner, x, y, maxHp);
        this.rate = rate;
        getResources(type);
    }

    private void getResources(BuildingType type) {
        switch (type) {
            case ARMOURER -> {
                fromResource = Resource.IRON;
                toResource = null;
                toWeapon = new WeaponName[]{WeaponName.METAL_ARMOUR};
            }
            case BLACKSMITH -> {
                fromResource = Resource.IRON;
                toResource = null;
                toWeapon = new WeaponName[]{WeaponName.MACE, WeaponName.SWORDS};
            }
            case FLETCHER -> {
                fromResource = Resource.WOOD;
                toResource = null;
                toWeapon = new WeaponName[]{WeaponName.BOW, WeaponName.CROSSBOW};
            }
            case POLETURNER -> {
                fromResource = Resource.WOOD;
                toResource = null;
                toWeapon = new WeaponName[]{WeaponName.SPEAR, WeaponName.PIKE};
            }
            case TANNERS_WORKSHOP -> {
                fromResource = Resource.COW;
                toResource = null;
                toWeapon = new WeaponName[]{WeaponName.LEATHER_ARMOUR};

            }
            case OIL_SMELTER -> {
                fromResource = null;
                toResource = new Resource[]{Resource.OIL};
                toWeapon = null;
            }
            case MILL -> {
                fromResource = Resource.WHEAT;
                toResource = new Resource[]{Resource.FLOUR};
                toWeapon = null;
            }
            case APPLE_ORCHARD -> {
                fromResource = null;
                toResource = new Resource[]{Resource.APPLE};
                toWeapon = null;
            }
            case DIARY_FARMER -> {
                fromResource = null;
                toResource = new Resource[]{Resource.CHEESE, Resource.COW};
                toWeapon = null;
            }
            case HOPS_FARMER -> {
                fromResource = null;
                toResource = new Resource[]{Resource.HOPS};
                toWeapon = null;
            }
            case HUNTER_POST -> {
                fromResource = null;
                toResource = new Resource[]{Resource.MEAT};
                toWeapon = null;
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public boolean produce(Object object) {
        if (!(object instanceof Resource || object instanceof WeaponName)) return false;

        Government government = getOwner().getGovernment();

        if (government.getResourceAmount(fromResource) <= 0)
            return false;

        government.setResourceAmount(fromResource, government.getResourceAmount(fromResource) - 1);
        Storage storage = government.getFirstEmptyStorageForObject(object);

        if (storage == null) return false;
        if (!storage.addOne(object)) return false;

        for (int i = 1; i < rate; i++) {
            if (storage.isFull()) storage = government.getFirstEmptyStorageForObject(object);
            if (storage == null) return false;
            if (!storage.addOne(object)) return false;
        }

        return true;
    }

    public int getRate() {
        return rate;
    }

    public Resource getFromResource() {
        return fromResource;
    }

    public WeaponName[] getToWeapon() {
        return toWeapon;
    }

    public Resource[] getToResource() {
        return toResource;
    }

    public void produce() {
        if (toResource != null)
            for (Resource resource : toResource)
                produce(resource);

        if (toWeapon != null)
            for (WeaponName weaponName : toWeapon)
                produce(weaponName);
    }
}
