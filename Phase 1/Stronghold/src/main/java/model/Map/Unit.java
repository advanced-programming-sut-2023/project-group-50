package model.Map;

import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Buildings.Gate;
import model.ObjectsPackage.Buildings.Tower;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.Soldier.Engineer;
import model.ObjectsPackage.People.Soldier.Soldier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Unit implements Serializable {
    private final LinkedHashSet<Objects> objects;
    private final int x;
    private final int y;
    private GroundType texture;
    private boolean isOnFire;
    private boolean isProtected;

    public Unit(int x, int y, GroundType texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        objects = new LinkedHashSet<>();
        isOnFire = false;
        isProtected = false;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public boolean isOnFire() {
        return isOnFire;
    }

    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }

    public void addObject(Objects object) {
        objects.add(object);
    }

    public void removeObject(Objects object) {
        objects.remove(object);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GroundType getTexture() {
        return texture;
    }

    public void setTexture(GroundType texture) {
        this.texture = texture;
    }

    public LinkedHashSet<Objects> getObjects() {
        return objects;
    }

    public ArrayList<String> toArrayListString() {
        List<String> out = new ArrayList<>();
        out.add(texture.getString(buildingChar() + soldierChar()));
        out.add(texture.getString(treeChar() + "#"));
        return new ArrayList<>(out);
    }

    private String buildingChar() {
        for (Objects object : objects)
            if (object.getObjectType().equals(ObjectType.BUILDING))
                return ((object instanceof Tower) || (object instanceof Gate)) ? "W" : "B";
        return "#";
    }

    private String soldierChar() {
        for (Objects object : objects)
            if (object.getObjectType().equals(ObjectType.GROUP_SOLDIER))
                return "S";
        return "#";
    }

    private String treeChar() {
        for (Objects object : objects)
            if (object.getObjectType().equals(ObjectType.TREE))
                return "T";
        return "#";
    }

    public boolean hasObjectType(BuildingType type) {
        for (Objects object : objects)
            if (object instanceof Building building && building.getType().equals(type))
                return true;
        return false;
    }

    public Building getObjectType(BuildingType type) {
        for (Objects object : objects)
            if (object instanceof Building building && building.getType().equals(type))
                return building;
        return null;
    }


    public Soldier getSoldier() {
        for (Objects object : objects)
            if (object instanceof Soldier soldier)
                return soldier;
        return null;
    }

    public int getEngineerCount() {
        int cnt = 0;
        for (Objects object : objects)
            if (object instanceof Engineer engineer)
                cnt++;
        return cnt;
    }

    public ArrayList<Soldier> getEngineers(int c) {
        ArrayList<Soldier> engineers = new ArrayList<>();
        for (Objects object : objects) {
            if (object instanceof Engineer engineer) {
                engineers.add(engineer);
                if (engineers.size() == c)
                    break;
            }
        }

        int i = 0;
        for (Objects object : objects) {
            if (object instanceof Soldier engineer) {
                if (engineer.equals(engineers.get(i))) {
                    objects.remove(engineer);
                    i++;
                    if(i == engineers.size())
                        break;
                }
            }
        }

        return engineers;
    }
}
