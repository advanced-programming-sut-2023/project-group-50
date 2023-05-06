package model.Government;

import controller.UserDatabase.User;
import model.Map.Map;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Government implements Serializable {
    private final User user;
    private final HashMap<Resource, Integer> resources;
    private int coins;
    private Map map;
    private ArrayList<Person> noneJob;
    private ArrayList<Soldier> unDeployedSoldier;

    public Government(User user) {
        this.user = user;
        resources = new HashMap<>();
        coins = 0;
        this.noneJob=new ArrayList<> ();
    }

    public void setResourceAmount(Resource resource, int value) {
        resources.replace(resource, value);
    }

    public int getResourceAmount(Resource resource) {
        return resources.getOrDefault(resource, 0);
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
    public void buyBuilding(BuildingType buildingType,float zarib){
        this.coins= getCoins ()-(int)(buildingType.getGoldCost ()*zarib);
        this.setResourceAmount (Resource.STONE,this.resources.get (Resource.STONE) -(int)(buildingType.getStoneCost ()*zarib));
        this.setResourceAmount (Resource.WOOD,this.resources.get (Resource.WOOD) -(int)(buildingType.getWoodCost ()*zarib));
        this.setResourceAmount (Resource.IRON,this.resources.get (Resource.IRON) -(int)(buildingType.getIronCost ()*zarib));
    }

    public ArrayList<Person> getNoneJob() {
        return noneJob;
    }

    public void addNoneJob(Person noneJob) {
        this.noneJob.add ( noneJob);
    }

    public ArrayList<Soldier> getUnDeployedSoldier() {
        return unDeployedSoldier;
    }

    public void addUnDeployedSoldier(ArrayList<Soldier unDeployedSoldier) {
        this.unDeployedSoldier = unDeployedSoldier;
    }
}
