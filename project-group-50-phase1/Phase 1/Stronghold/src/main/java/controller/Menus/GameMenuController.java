package controller.Menus;

import controller.UserDatabase.User;
import controller.control.Commands;
import controller.control.Error;
import model.Game;
import model.Map.GroundType;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.People.PersonState;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.People.Soldier.SoldierName;
import model.ObjectsPackage.Resource;
import model.ObjectsPackage.Weapons.WeaponName;
import view.GameMenu;

import java.util.regex.Matcher;

import static controller.Menus.LoginController.*;
public class GameMenuController {
    private  User currentUser;
    private  Game game;
    private final GameMenu gameMenu;
    private Building selectedBuilding;

    public GameMenuController(GameMenu gameMenu) {
        this.gameMenu=gameMenu;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }

    public Building getSelectedBuilding() {
        return selectedBuilding;
    }

    public void setSelectedBuilding(Building selectedBuilding) {
        this.selectedBuilding = selectedBuilding;
    }

    public String dropBuilding(Matcher matcher){
        matcher.find ();
        String input=matcher.group ();

        Error error=checkValue (input,Commands.X,currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int x=Integer.parseInt(error.errorMassage);

        error=checkValue (input,Commands.Y,currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int y=Integer.parseInt(error.errorMassage);

        error=checkType (input);
        if(!error.truth){
            return error.errorMassage;
        }
        String type=error.errorMassage;
        if(BuildingType.checkTypeByName (type)==null){
            return "You should enter valid type building";
        }
        BuildingType buildingType=BuildingType.checkTypeByName (type);

       error= canPlaceBuilding (x,y,buildingType);
        if(!error.truth){
            return error.errorMassage;
        }
        String buildingType1 = checkResource (buildingType, 1);
        if (buildingType1 != null) return buildingType1;

        Building building=Building.getBuildingByType (buildingType,this.currentUser,x,y);
        this.currentUser.getGovernment ().getMap ().addObject (building,x,y);
        this.currentUser.getGovernment ().buyBuilding (buildingType,1);
        return "You drop building successfully";
    }

    private String checkResource(BuildingType buildingType,float zarib) {
        if((int)Math.ceil (buildingType.getGoldCost ()*zarib)>this.currentUser.getGovernment ().getCoins ()){
            return "You haven't enough gold to build this " + buildingType.getType ();
        }
        if ((int)Math.ceil (buildingType.getStoneCost ()*zarib)>this.currentUser.getGovernment ().getResourceAmount (Resource.STONE)){
            return "You haven't enough stone to build this " + buildingType.getType ();
        }
        if ((int)Math.ceil (buildingType.getWoodCost ()*zarib)>this.currentUser.getGovernment ().getResourceAmount (Resource.WOOD)){
            return "You haven't enough wood to build this " + buildingType.getType ();
        }
        if ((int)Math.ceil (buildingType.getIronCost ()*zarib)>this.currentUser.getGovernment ().getResourceAmount (Resource.IRON)){
            return "You haven't enough iron to build this " + buildingType.getType ();
        }
        return null;
    }

    private Error checkType(String input){
        Matcher matcher = Commands.getMatcher(Commands.TYPE, input);

        if (!matcher.find()) {
            return new Error("You should enter all field!\nEnter a " + Commands.TYPE.name(), false);
        }
        String type = matcher.group();
        type = type.substring(6);
        type = removeDoubleCoutString(type);
        if (matcher.find()) {
            return new Error("Invalid command!\nYou should enter all field!\nEnter " + Commands.TYPE.name() + " once", false);

        }
        type = type.trim();
        if (type.equals("")) {
            return new Error("You should fill " + Commands.TYPE.name() + " field!", false);
        }

        return new Error(type, true);


    }


    private Error canPlaceBuilding(int x, int y, BuildingType buildingType){
        Unit unit=this.currentUser.getGovernment ().getMap ().getUnitByXY (x,y);

        if(this.currentUser.getGovernment ().getMap ().getObjectByXY (x,y, ObjectType.BUILDING)!=null){
            return new Error ("There is a building at these coordinates",false);
        }
        if(this.currentUser.getGovernment ().getMap ().getObjectByXY (x,y, ObjectType.TREE)!=null){
            return new Error ("There is a tree at these coordinates",false);
        }
        if(this.currentUser.getGovernment ().getMap ().getObjectByXY (x,y, ObjectType.ROCK)!=null){
            return new Error ("There is a building at these coordinates",false);
        }

        GroundType groundType=unit.getTexture ();
        if(!(groundType.equals (GroundType.GROUND) ||
                groundType.equals (GroundType.GRASS) ||
                groundType.equals (GroundType.RIGGED_GROUND) ||
                groundType.equals (GroundType.CLIFF) ||
                groundType.equals (GroundType.IRON) ||
                groundType.equals (GroundType.LAWN) ||
                groundType.equals (GroundType.MEADOW) ||
                groundType.equals (GroundType.PLAIN)
        )){
            return new Error ("You can't drop building in this place",false);
        }

        if (buildingType.equals (BuildingType.APPLE_ORCHARD)
                || buildingType.equals (BuildingType.HOPS_FARMER)
                || buildingType.equals (BuildingType.WHEAT_FARMER)) {
            if (! (groundType.equals (GroundType.GRASS)
                    || groundType.equals (GroundType.MEADOW))) {
                return new Error ("You only need to place the farm in the grass or meadow", false);
            }
            return new Error ("", true);

        }
        else if (buildingType.equals (BuildingType.IRON_MINE)) {
            if (! groundType.equals (GroundType.IRON)) {
                return new Error ("Iron mine just can place in iron ground", false);
            }
            return new Error ("",true);
        }else if (buildingType.equals (BuildingType.QUARRY)){
            if(!groundType.equals (GroundType.CLIFF)){
                return new Error ("Quarry just can place in iron ground", false);
            }
            return new Error ("",true);
        }
        else if (buildingType.equals (BuildingType.PITCH_RIG)) {
            if(!groundType.equals (GroundType.PLAIN)){
                return new Error ("Pitch rig just can place in iron ground", false);
            }
            return new Error ("",true);
        }else {
            if( groundType.equals (GroundType.CLIFF) ||
                    groundType.equals (GroundType.IRON) ||
                    groundType.equals (GroundType.PLAIN)
            ){
                return new Error ("You can't drop building in this place",false);
            }
            return new Error ("",true);
        }
    }
    public static Error checkValue(String input,Commands commands,User currentUser){
        Error error=checkHasField(input, commands);
        if (!error.truth) {
            return error;
        }
        int x=Integer.parseInt(error.errorMassage);
        if(x>currentUser.getGovernment ().getMap ().getxSize () || x<0){
            return new Error ("The "+commands.name ()+" value is not within the map range",false);
        }
        return new Error (error.errorMassage,true);
    }

    public String selectBuilding(Matcher matcher){
        matcher.find ();
        String input=matcher.group ();

        Error error=checkValue (input,Commands.X,currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int x=Integer.parseInt(error.errorMassage);

        error=checkValue (input,Commands.Y,currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int y=Integer.parseInt(error.errorMassage);
        if(this.currentUser.getGovernment ().getMap ().getObjectByXY (x,y, ObjectType.BUILDING)==null){
            return "There isn't a building at these coordinates";
        }
        Building building= (Building) this.currentUser.getGovernment ().getMap ().getObjectByXY (x,y, ObjectType.BUILDING);

        if(!building.getOwner ().equals (this.currentUser)){
            return "This building isn't yours";
        }
        this.setSelectedBuilding (building);
        if(Building.isCastles (building)){
            if(building.isDestroyed ()){
                System.out.println ("This building is destroyed");
            }else {
                System.out.println ("Hp of this  building is : " + building.getHp ());
            }
        }
        return "Your building is selected";
    }

    public String repair(){
        if(this.selectedBuilding==null){
            return "Please first select a building";
        }
        float cost=0;
        if(this.selectedBuilding.isDestroyed ()){
            cost=1;
        }else {
            cost=selectedBuilding.getHp ()/selectedBuilding.getMaxHp ();
        }
        String string=checkResource (selectedBuilding.getType (),cost);
        if(string!=null){
            return string;
        }

        if(this.currentUser.getGovernment ().getMap ().searchForEnemy (this.selectedBuilding.getX (),this.selectedBuilding.getY (),this.selectedBuilding.getOwner ())){
           return "The enemy soldier is close";
        }
        this.currentUser.getGovernment ().buyBuilding (selectedBuilding.getType (),cost);
        this.selectedBuilding.repair ();
        return "You repair selected building successfully";

    }


    public String createUnit(Matcher matcher) {
        matcher.find ();
        String input = matcher.group ();

        Error error = checkType (input);
        if (! error.truth) {
            return error.errorMassage;
        }
        String type = error.errorMassage;

        if (SoldierName.getSoldierNameByType (type) == null) {
            return "You should enter valid type soldier";
        }
        SoldierName soldierName = SoldierName.getSoldierNameByType (type);

        error = checkHasField (input, Commands.COUNT);
        if (! error.truth) {
            return error.errorMassage;
        }
        int count = Integer.parseInt (error.errorMassage);

        if (soldierName.getCost () * count > this.currentUser.getGovernment ().getCoins ()) {
            return "You haven't enough gold";
        }

        WeaponName weaponName=Soldier.getWeaponName (soldierName);
        if(!this.currentUser.getGovernment ().getWeapons ().containsKey (weaponName)){
            return "You haven't any this type weapon";
        }
        if(this.currentUser.getGovernment ().getWeapons ().get (weaponName)<count){
            return "You haven't enough weapons";
        }

        if (this.currentUser.getGovernment ().getPeopleByState (PersonState.JOBLESS).size ()<count) {
            return "You haven't enough people";
        }

        if (SoldierName.isArab (soldierName)) {
            if (! selectedBuilding.getType ().equals (BuildingType.MERCENARY_POST)) {
                return "You can't create arab soldier in this building";
            }

        }
        else {
            if (! (selectedBuilding.getType ().equals (BuildingType.BARRACKS) || selectedBuilding.getType ().equals (BuildingType.ENGINEER_GUILD))) {
                return "You can't create european soldier in this building";
            }
            if ((soldierName.equals (SoldierName.ENGINEER))) {
                if (! (selectedBuilding.getType ().equals (BuildingType.ENGINEER_GUILD))) {
                    return "You just can create Engineer in Engineer guild";
                }
            }
        }


        return "";
    }


    }


