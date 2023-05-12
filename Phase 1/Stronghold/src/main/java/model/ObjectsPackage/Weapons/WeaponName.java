package model.ObjectsPackage.Weapons;

import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Resource;

public enum WeaponName {
    BOW(BuildingType.FLETCHER, Resource.WOOD, 2, 50, true),
    CROSSBOW(BuildingType.FLETCHER, Resource.WOOD, 3, 70, true),
    SPEAR(BuildingType.POLETURNER, Resource.WOOD, 1, 1, false),
    PIKE(BuildingType.POLETURNER, Resource.WOOD, 2, 1, false),
    MACE(BuildingType.BLACKSMITH, Resource.IRON, 1, 1, false),
    SWORDS(BuildingType.BLACKSMITH, Resource.IRON, 1, 1, false),
    METAL_ARMOUR(BuildingType.ARMOURER, Resource.IRON, 1, 1, false),
    LEATHER_ARMOUR(BuildingType.ARMOURER, Resource.COW, 1, 1, false),
    HAND(null, null, 0, 0, false),
    TEETH(null, null, 0, 0, false),
    BATTERING_RAM(null, null, 0, 1, false),
    CATAPULT(null, null, 0, 50, false),
    FIRE_THROWER(null, null, 0, 10, true);
    private final BuildingType buildingType;
    private final Resource resource;
    private final int resourceCount;
    private final int range;
    private final boolean canMove;

    WeaponName(BuildingType buildingType, Resource resource, int resourceCount, int range, boolean canMove) {
        this.buildingType = buildingType;
        this.resource = resource;
        this.resourceCount = resourceCount;
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

    public int getRange() {
        return range;
    }

    public boolean isCanMove() {
        return canMove;
    }
}
