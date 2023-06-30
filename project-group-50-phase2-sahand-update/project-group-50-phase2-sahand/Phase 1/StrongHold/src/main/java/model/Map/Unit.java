package model.Map;

import controller.UserDatabase.User;
import javafx.scene.paint.Color;
import model.ObjectsPackage.Buildings.*;
import model.ObjectsPackage.*;
import model.ObjectsPackage.People.Soldier.Engineer;
import model.ObjectsPackage.People.Soldier.Soldier;
import view.show.Animation.DiseaseAnimation;
import view.show.Animation.FireAnimation;
import view.show.Animation.HealAnimation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class Unit implements Serializable {
    private final LinkedHashSet<Objects> objects;
    private final int x;
    private final int y;
    private GroundType texture;
    private boolean isOnFire;
    private boolean isProtected;
    private int capacity;

    private int stateFire;
    private boolean hasDisease;

    private FireAnimation fireAnimation;
    private DiseaseAnimation diseaseAnimation;
    private HealAnimation healAnimation;
    public Unit(int x, int y, GroundType texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        objects = new LinkedHashSet<>();
        isOnFire = false;
        isProtected = false;
        stateFire=0;
        fireAnimation=null;
        hasDisease=false;
        diseaseAnimation=null;

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
        object.setX(this.x);
        object.setY(this.y);
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
        if (texture.equals(GroundType.IRON) || texture.equals(GroundType.CLIFF) || texture.equals(GroundType.OIL)) {
            this.capacity = new Random().nextInt(1000);
        }
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

    public boolean hasObjectByType(ObjectType type) {
        for (Objects object : objects)
            if (object.getObjectType().equals(type))
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
                    if (i == engineers.size())
                        break;
                }
            }
        }

        return engineers;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean cannotMoveUnitTo() {
        for (Objects objects1 : objects) {
            if (objects1.getObjectType().equals(ObjectType.ROCK)) return true;
            if (objects1.getObjectType().equals(ObjectType.TREE)) return true;
        }
        return false;
    }

    public boolean hasBuilding() {
        for (Objects object : objects)
            if (object instanceof Building)
                return true;
        return false;
    }

    public boolean hasObjects() {
        for (Objects object : objects)
            if (!(object instanceof Building))
                return true;
        return false;
    }

    public boolean hasPalace() {
        for (Objects object : objects)
            if (object instanceof House house && house.getType().equals(BuildingType.PALACE))
                return true;
        return false;
    }

    public Color getPalaceOwnerColor() {
        for (Objects object : objects)
            if (object instanceof House house && house.getType().equals(BuildingType.PALACE))
                return house.getOwner().getColor().toColor();
        throw new RuntimeException();
    }

    public User getOwner() {
        for (Objects object : objects)
            if (object instanceof Building building)
                return building.getOwner();
        return null;
    }

    public Building getBuilding() {
        for (Objects object : objects)
            if (object instanceof Building building)
                return building;
        return null;
    }

    public Tree getTree() {
        for (Objects object : objects)
            if (object instanceof Tree tree)
                return tree;
        return null;
    }

    public Rock getRock() {
        for (Objects object : objects)
            if (object instanceof Rock rock)
                return rock;
        return null;
    }

    public void clear(User user) {
        ArrayList<Objects> toRemove = new ArrayList<>();
        for (Objects objects1 : objects) {
            if (java.util.Objects.equals(objects1.getOwner(), user)) {
                toRemove.add(objects1);
                if (objects1 instanceof Storage storage)
                    storage.prevStorage().removeNextStorage(storage);
            }
        }

        toRemove.forEach(objects::remove);
        setTexture(GroundType.PLAIN);
    }

    public boolean hasGate() {
        for (Objects objects1 : objects)
            if (objects1 instanceof Gate)
                return true;
        return false;
    }

    public Gate getGate() {
        for (Objects objects1 : objects)
            if (objects1 instanceof Gate gate)
                return gate;
        return null;
    }

    public Tunnel getTunnel() {
        for (Objects objects1 : objects)
            if (objects1 instanceof Tunnel tunnel)
                return tunnel;
        return null;
    }

    public boolean hasTunnel() {
        return getTunnel() != null;
    }
    public int getStateFire () {
        return stateFire;
    }

    public void setStateFire (int stateFire) {
        this.stateFire = stateFire;
    }

    public void setFire(boolean isOnFire,int stateFire){
        setOnFire ( isOnFire );
        setStateFire ( stateFire );
        if(isOnFire && fireAnimation==null){
            (this.fireAnimation=new FireAnimation ( this )).play ();
        }
    }

    public FireAnimation getFireAnimation () {
        return fireAnimation;
    }

    public void setFireAnimation (FireAnimation fireAnimation) {
        this.fireAnimation = fireAnimation;
    }

    public boolean isHasDisease () {
        return hasDisease;
    }

    public void setHasDisease (boolean hasDisease) {
        this.hasDisease = hasDisease;
    }
    public void setDisease(boolean hasDisease){
        setHasDisease ( hasDisease );
        if(hasDisease && diseaseAnimation==null){
            (this.diseaseAnimation=new DiseaseAnimation ( this )).play ();
        }
    }

    public DiseaseAnimation getDiseaseAnimation () {
        return diseaseAnimation;
    }

    public void setDiseaseAnimation (DiseaseAnimation diseaseAnimation) {
        this.diseaseAnimation = diseaseAnimation;
    }

    public void setHealAnimation (HealAnimation healAnimation) {
        this.healAnimation = healAnimation;
    }

    public HealAnimation getHealAnimation () {
        return healAnimation;
    }
}
