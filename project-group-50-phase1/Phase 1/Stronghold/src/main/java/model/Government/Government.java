package model.Government;

import controller.UserDatabase.User;
import model.Map.Map;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.People.PersonState;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.Resource;
import model.ObjectsPackage.Weapons.WeaponName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Government implements Serializable {
    private final User user;
    private final HashMap<Resource, Integer> resources;
    private int coins;
    private Map map;
   private HashMap<PersonState, ArrayList<Person>> people;

   private HashMap<WeaponName, Integer> weapons;

    public Government(User user) {
        this.user = user;
        resources = new HashMap<>();
        coins = 0;
        this.people=new HashMap<> ();
        this.people.put (PersonState.WORKER,new ArrayList<> ());
        this.people.put (PersonState.JOBLESS,new ArrayList<> ());
        this.people.put (PersonState.DEPLOYED_SOLDIER,new ArrayList<> ());
        this.people.put (PersonState.UNDEPLOYED_SOLDIER,new ArrayList<> ());

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

   public ArrayList<Person> getPeopleByState(PersonState personState){
        return this.people.get (personState);
   }
   public void addPeopleByState(Person person,PersonState personState){
        this.people.get (personState).add (person);
   }

    public HashMap<WeaponName, Integer> getWeapons() {
        return weapons;
    }
    public void addWeaponByName(int count,WeaponName weaponName){
        if(weapons.containsKey (weaponName)){
            weapons.replace (weaponName,weapons.get (weaponName)+count);
        }else {
            weapons.put (weaponName,count);
        }


    }
}
