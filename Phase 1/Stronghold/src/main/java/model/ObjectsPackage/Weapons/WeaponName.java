package model.ObjectsPackage.Weapons;

import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Resource;

public enum WeaponName {
    //TODO: fill range and canMove and damage (add leather armor?)
    BOW(BuildingType.FLETCHER, Resource.WOOD, 2, 0, 0, true),
    CROSSBOW(BuildingType.FLETCHER, Resource.WOOD, 3, 0, 0, true),
    SPEAR(BuildingType.POLETURNER, Resource.WOOD, 1, 0, 0, true),
    PIKE(BuildingType.POLETURNER, Resource.WOOD, 2, 0, 0, true),
    MACE(BuildingType.BLACKSMITH, Resource.IRON, 1, 0, 0, true),
    SWORDS(BuildingType.BLACKSMITH, Resource.IRON, 1, 0, 0, true),
    METAL_ARMOUR(BuildingType.ARMOURER, Resource.IRON, 1, 0, 0, true),
    LEATHER_ARMOUR(BuildingType.ARMOURER, Resource.COW, 1, 0, 0, true);
    private final BuildingType buildingType;
    private final Resource resource;
    private final int resourceCount;
    private final int damage;
    private final int range;
    private final boolean canMove;

    WeaponName(BuildingType buildingType, Resource resource, int resourceCount, int damage, int range, boolean canMove) {
        this.buildingType = buildingType;
        this.resource = resource;
        this.resourceCount = resourceCount;
        this.damage = damage;
        this.range = range;
        this.canMove = canMove;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public Resource getResource() {
        return resource;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public boolean isCanMove() {
        return canMove;
    }
}
