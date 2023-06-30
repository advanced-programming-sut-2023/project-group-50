package model.ObjectsPackage.Buildings;

import model.ObjectsPackage.People.Soldier.SoldierName;

import java.util.ArrayList;
import java.util.HashMap;

public enum BuildingSet {
    DEFENSE,
    HOUSE,
    RESOURCE,
    OFFENSE,
    STORAGE,
    RELIGIOUS,
    WORKSHOP,
    OTHER;

    private static final HashMap<BuildingSet, ArrayList<BuildingType>> buildingTypes = new HashMap<>();

    static {
        for (BuildingType buildingType : BuildingType.values()) {
            buildingTypes.putIfAbsent(buildingType.getSet(), new ArrayList<>());
            buildingTypes.get(buildingType.getSet()).add(buildingType);
        }
    }

    public ArrayList<BuildingType> getBuildingTypes() {
        return buildingTypes.get(this);
    }

    public String getName() {
        return SoldierName.getName(this.name());
    }
}
