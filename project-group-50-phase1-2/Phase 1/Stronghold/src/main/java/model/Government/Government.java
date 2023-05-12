package model.Government;

import controller.UserDatabase.User;
import model.Map.Map;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Buildings.ReligiousBuilding;
import model.ObjectsPackage.Objects;
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

public class Government implements Serializable {
    private final User user;
    private final HashMap<Resource, Integer> resources;
    private double coins;
    private Map map;
    private HashMap<PersonState, ArrayList<Person>> people;
    private ArrayList<Building> buildings;
    private  HashMap<String,Double> foods;

    private int popularity;

    private int rateFood;
    private int previousRateFood;
    private int taxRate;
    private int previousRateTax;
    private int fearRate;

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
        this.rateFood=-2;
        this.popularity=0;
        this.previousRateFood=-2;
        this.taxRate =0;
        this.previousRateTax =0;
        this.fearRate=0;
    }

    public void setResourceAmount(Resource resource, int value) {
        resources.replace(resource, value);
    }

    public int getResourceAmount(Resource resource) {
        return resources.getOrDefault(resource, 0);
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
        double value= Government.getTaxValueByRate (this.previousRateTax);
        if(value<0){
            if(-1*value*getPopulation ()>coins){
                this.taxRate =0;
            }else {
                this.taxRate =this.previousRateTax;
            }
        }
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getPreviousRateFood() {
        return previousRateFood;
    }

    public void setPreviousRateFood(int previousRateFood) {
        this.previousRateFood = previousRateFood;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public HashMap<String,Double> getFoods() {
        return foods;
    }
    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addBuildings(Building building) {
        this.buildings.add (building);

    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getRateFood() {
        return rateFood;
    }

    public void setRateFood(int rateFood) {
        this.rateFood = rateFood;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getPreviousRateTax() {
        return previousRateTax;
    }

    public void setPreviousRateTax(int previousRateTax) {
        this.previousRateTax = previousRateTax;
    }

    public void buyBuilding(BuildingType buildingType, float zarib){
        this.setCoins ( getCoins ()-(int)(buildingType.getGoldCost ()*zarib));
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
        this.setCoins (this.coins-(double) soldierName.getGoldCost ()*count);
    }



    public void addFoods(String food, Double count) {
        if(this.foods.containsKey (food)){
            this.foods.replace (food,this.foods.get (food)+count);
        }else {
            this.foods.put (food,count);
        }
        double value= (double) ((this.previousRateFood+2)*0.5);
        if(value*getPopulation ()>getFoodNumber ()){
            this.rateFood=-2;
        }else {
            this.rateFood=this.previousRateFood;
        }
    }

    public int checkPopularityFood(){
        int value=0;
        if(foods.containsKey ("bread") && foods.get ("bread"  )>0){
           value++;
        }
        if(foods.containsKey ("meat") && foods.get ("meat"  )>0){
            value++;
        }if(foods.containsKey ("cheese") && foods.get ("cheese"  )>0){
            value++;
        }if(foods.containsKey ("apple") && foods.get ("apple")>0){
            value++;
        }

        value=value+(this.rateFood*4)-1;
        return value;
    }


    public int getPopulation(){
        return this.people.get (PersonState.JOBLESS).size ()+
                this.people.get (PersonState.DEPLOYED_SOLDIER).size ()+
                this.people.get (PersonState.UNDEPLOYED_SOLDIER).size ()+
                this.people.get (PersonState.WORKER).size ();
    }
    public double getFoodNumber(){
        return this.foods.get ("meat")+
                this.foods.get ("apple")+
                this.foods.get ("bread")+
                this.foods.get ("cheese");
    }


    public void feedPeople(){

        double value= (double) ((this.rateFood+2)*0.5);
        if(value*getPopulation ()>getFoodNumber ()){
            this.rateFood=-2;
        }

        double cheeseDecrease=this.getPopulation ()*value*(this.foods.get ("cheese")/this.getFoodNumber ());
        double meatDecrease=this.getPopulation ()*value*(this.foods.get ("meat")/this.getFoodNumber ());
        double appleDecrease=this.getPopulation ()*value*(this.foods.get ("apple")/this.getFoodNumber ());
        double breadDecrease=this.getPopulation ()*value*(this.foods.get ("bread")/this.getFoodNumber ());

        this.foods.put ("cheese",this.foods.get ("cheese")-cheeseDecrease);
        this.foods.put ("meat",this.foods.get ("meat")-meatDecrease);
        this.foods.put ("apple",this.foods.get ("apple")-appleDecrease);
        this.foods.put ("bread",this.foods.get ("bread")-breadDecrease);

        //TODO starving?


        if(value*getPopulation ()>getFoodNumber ()){
            this.rateFood=-2;
        }
        this.popularity=this.popularity+checkPopularityFood ();
    }
    public int checkPopularityTax() {
        if (this.taxRate <= 0) {
            return 7-(this.taxRate + 3)*2;
        }
        else if (this.taxRate > 0 && this.taxRate < 5){
            return - (this.getTaxRate () * 2);
        }else {
            return - (((this.getTaxRate () -5)* 4)+12);
        }
    }

    public void getTaxPeople(){
        double value=getTaxValueByRate (this.taxRate );
        if(this.taxRate <0){
            if(-1*value*getPopulation ()>coins){
                this.taxRate =0;
                return;
            }

        }
        for ( java.util.Map.Entry<PersonState, ArrayList<Person>> set : this.people.entrySet ( ) ) {
            for ( Person person : set.getValue ( ) ) {
                if ( value > 0 && person.getIncome ( ) < value ) {
                    person.setIncome ( 0 );
                }else {
                    person.setIncome ( person.getIncome ()-value );
                }
            }
        }
        this.coins=this.coins+value*getPopulation();
        if(this.taxRate <0 && -1*value*getPopulation ()>coins){
                this.taxRate =0;
        }
        this.popularity=this.popularity+checkPopularityTax ();
    }

    public static double getTaxValueByRate(int rateTax){

        if(rateTax<0) {
            return ((rateTax+3)*0.2 )-1;
        }
        else if (rateTax>0) {
            return ((rateTax-1)*0.2 )+0.6;
        }else {
            return  0;
        }
    }

    public int checkReligionPopularity() {

        int value = 0;
        for ( Building building : buildings ) {
            if ( building instanceof ReligiousBuilding )
                value = value + ((ReligiousBuilding) building).getPopularity ();
        }
        return value;
    }

    public  int checkFearPopularity(){
        int value=0;
        for ( Building building : buildings) {
            value=value+Building.isGoodOrBad ( building );
        }
        return value+fearRate;
    }





}
