package model.Government;

import controller.UserDatabase.User;
import model.Map.Map;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.People.NonSoldier.Job;
import model.ObjectsPackage.People.NonSoldier.NonSoldier;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.People.PersonState;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.People.Soldier.SoldierName;
import model.ObjectsPackage.Resource;
import model.ObjectsPackage.Weapons.WeaponName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Government implements Serializable {
    private final User user;
    private final HashMap<Resource, Integer> resources;
    private int coins;
    private Map map;
    private HashMap<PersonState, ArrayList<Person>> people;
    private ArrayList<Building> buildings;

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
        this.buildings=new ArrayList<> ();
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
        if(personState.equals (PersonState.JOBLESS)){
            Building building=noneActiveBuilding ();
            if(building!=null){
                peopleGetJob (1,Building.getJobByBuildingType (building.getType ()),building);
            }
        }
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

    public void peopleGetJob(int number , Job job,Building building){
      ArrayList<Person> jobLess=  this.people.get (PersonState.JOBLESS);
      ArrayList<Person> worker=  this.people.get (PersonState.WORKER);
      HashMap<String,Integer> residents=building.getResidents ();
      for (int i=0;i<number;i++){
          Person person= jobLess.get (0);
          NonSoldier nonSoldier=new NonSoldier (job,this.user,building);
          nonSoldier.setLife (person.getLife ());
          nonSoldier.setStarving (person.getStarving ());
          nonSoldier.setIncome (person.getIncome ());
          jobLess.remove (0);
          worker.add (nonSoldier);
      }
      residents.replace ("worker",residents.get ("worker")+number);
      if(residents.get ("worker")>=Building.numberOfWorker (building.getType ())){
          building.setActive (true);
      }else {
          building.setActive (false);
      }

        return;

    }

    private Building noneActiveBuilding(){
        if(buildings.isEmpty ()){
            return null;
        }
        for (Building building:this.buildings){
            if(!building.isActive ()){
                return building;
            }
        }
        return  null;
    }
    public void addUndeployedSoldier(int count, SoldierName soldierName,User owner){
        for (int i=0;i<count;i++){
          Soldier soldier= Soldier.getSoldierByType (soldierName,owner);
          this.addPeopleByState (soldier,PersonState.UNDEPLOYED_SOLDIER);
        }
        this.weapons.replace (Soldier.getWeaponName (soldierName),this.weapons.get (Soldier.getWeaponName (soldierName))-count);
        this.coins=this.coins-soldierName.getGoldCost ()*count;
    }

}
