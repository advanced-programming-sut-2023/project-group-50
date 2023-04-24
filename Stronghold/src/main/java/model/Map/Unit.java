package model.Map;

import model.ObjectsPackage.Buildings.Tower;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Unit {
    private LinkedHashSet<Objects> objects;
    private int x;
    private int y;
    private GroundType texture;

    public Unit(int x, int y, GroundType texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        objects = new LinkedHashSet<>();
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
            if (object.getObjectType().equals(ObjectType.BUILDING)) return object instanceof Tower ? "W" : "B";
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
}
