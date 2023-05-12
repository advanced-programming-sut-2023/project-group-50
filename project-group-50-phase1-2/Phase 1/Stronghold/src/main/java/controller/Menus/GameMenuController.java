package controller.Menus;

import controller.UserDatabase.User;
import controller.control.Commands;
import controller.control.Error;
import model.Game;
import model.Map.GroundType;
import model.Map.Map;
import model.Map.Unit;
import model.ObjectsPackage.*;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.People.NonSoldier.Job;
import model.ObjectsPackage.People.PersonState;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.People.Soldier.SoldierName;
import model.ObjectsPackage.Weapons.WeaponName;
import view.GameMenu;

import java.util.Random;
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
        String errorString = checkResource (buildingType, 1);
        if (errorString != null) return errorString;

        Building building=Building.getBuildingByType (buildingType,this.currentUser,x,y);
        if(this.currentUser.getGovernment ().getPeopleByState (PersonState.JOBLESS).size ()<Building.numberOfWorker (buildingType)){
            System.out.println ("Your building doesn't have any worker");
            this.currentUser.getGovernment ().peopleGetJob (this.currentUser.getGovernment ().getPeopleByState (PersonState.JOBLESS).size (),Building.getJobByBuildingType (buildingType),building);
        }else {
            this.currentUser.getGovernment ().peopleGetJob (Building.numberOfWorker (buildingType),Building.getJobByBuildingType (buildingType),building);
          }


        this.currentUser.getGovernment ().getMap ().addObject (building,x,y);
        this.currentUser.getGovernment ().buyBuilding (buildingType,1);
        this.currentUser.getGovernment ().addBuildings (building);
        return "You drop building successfully";
    }

    private String checkResource(BuildingType buildingType,float zarib) {
        if(Math.ceil (buildingType.getGoldCost ()*zarib)>this.currentUser.getGovernment ().getCoins ()){
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

        Error x1 = canPlaceObject ( x, y );
        if ( x1 != null ) return x1;

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
            return new Error ("You can't drop building in this ground type",false);
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

    private Error canPlaceObject(int x, int y) {
        if(this.currentUser.getGovernment ().getMap ().getObjectByXY ( x, y, ObjectType.BUILDING)!=null){
            return new java.lang.Error ( "There is a building at these coordinates", false );
        }
        if(this.currentUser.getGovernment ().getMap ().getObjectByXY ( x, y, ObjectType.TREE)!=null){
            return new java.lang.Error ( "There is a tree at these coordinates", false );
        }
        if(this.currentUser.getGovernment ().getMap ().getObjectByXY ( x, y, ObjectType.ROCK)!=null){
            return new java.lang.Error ( "There is a rock at these coordinates", false );
        }
        return null;
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
            cost=1-(selectedBuilding.getHp ()/selectedBuilding.getMaxHp ());
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
        if(this.selectedBuilding==null){
            return "Please first select a building";
        }

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

        if (soldierName.getGoldCost () * count > this.currentUser.getGovernment ().getCoins ()) {
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
       this.currentUser.getGovernment ().addUndeployedSoldier (count,soldierName,this.currentUser);
        return "You create a unit";
    }
    public String setTexture(Matcher matcher){
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

        if(GroundType.isType ( type)==null){
            return "You should enter a valid ground type";
        }
        GroundType groundType=GroundType.isType ( type);
        Unit unit= this.currentUser.getGovernment ().getMap ().getUnitByXY (x,y);
        unit.setTexture ( groundType );
        return "You change texture successfully";
    }

    public String clearUnit(Matcher matcher){
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
        Unit unit= this.currentUser.getGovernment ().getMap ().getUnitByXY (x,y);
        unit.setTexture ( GroundType.GROUND );
        unit.getObjects ().clear ();
        return "You clear unit successfully";
    }

    public String setTexturePlace(Matcher matcher){
        matcher.find ();
        String input=matcher.group ();

        Error error=checkValue (input,Commands.X1,currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int x1=Integer.parseInt(error.errorMassage);

        error=checkValue (input,Commands.Y1,currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int y1=Integer.parseInt(error.errorMassage);

        error=checkValue (input,Commands.X2,currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int x2=Integer.parseInt(error.errorMassage);

        error=checkValue (input,Commands.Y2,currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int y2=Integer.parseInt(error.errorMassage);
        error=checkType (input);

        if(!error.truth){
            return error.errorMassage;
        }
        String type=error.errorMassage;

        if(GroundType.isType ( type)==null){
            return "You should enter a valid ground type";
        }
        GroundType groundType=GroundType.isType ( type);

        if(groundType.equals ( GroundType.SMALL_POND ) || groundType.equals ( GroundType.BIG_POND )){
            return "You shouldn't set texture of a area with pond";
        }

        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                    if(this.currentUser.getGovernment ().getMap ().getObjectByXY ( i,j,ObjectType.BUILDING ) != null){
                        return "There is a building in this area";
                    }
                    if ( this.currentUser.getGovernment ().getMap ().getObjectByXY ( i,j,ObjectType.TREE ) != null ){
                        Tree tree= (Tree) this.currentUser.getGovernment ().getMap ().getObjectByXY ( i,j,ObjectType.TREE ) ;
                        if(!tree.canPlace ( groundType )){
                            return "There is a tree in this area and this type of ground is not valid for the place of the tree";
                        }
                    }
            }
        }

        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                Unit unit= this.currentUser.getGovernment ().getMap ().getUnitByXY (i,j);
                unit.setTexture ( groundType );
            }
        }
        return "You change texture of area successfully";

    }

    public  String dropRock(Matcher matcher){
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

        error=checkHasField ( input,Commands.DIRECT );

        if (!error.truth) {
            return error.errorMassage;
        }
        String direct=error.errorMassage;

        if(!(direct.equals ( "s" ) || direct.equals ( "w" ) ||direct.equals ( "n" ) ||direct.equals ( "e" ) ||direct.equals ( "random" ) ))
        {
            return "You should enter a valid direct";
        }

        if(direct.equals ( "random" )){
            int number = new Random ().nextInt(1000) ;
            if(number%4==0){
                direct="s";
            }else if ( number%4==1 ){
                direct="n";
            }else if ( number%4==2 ){
                direct="e";
            }else if ( number%4==3 ){
                direct="w";
            }
        }

        error=canPlaceRock ( x,y );
        if(!error.truth){
            return error.errorMassage;
        }

        this.currentUser.getGovernment ().getMap ().addObject ( new Rock (direct,this.currentUser),x,y );
        return "You drop rock successfully";
    }

    private Error canPlaceRock(int x , int y){
        Unit unit=this.currentUser.getGovernment ().getMap ().getUnitByXY (x,y);

        Error x1 = canPlaceObject ( x, y );
        if ( x1 != null ) return x1;

        GroundType groundType=unit.getTexture ();
        if(!(groundType.equals (GroundType.GROUND) ||
                groundType.equals (GroundType.GRASS) ||
                groundType.equals (GroundType.RIGGED_GROUND) ||
                groundType.equals (GroundType.CLIFF) ||
                groundType.equals (GroundType.IRON) ||
                groundType.equals (GroundType.LAWN) ||
                groundType.equals (GroundType.MEADOW) ||
                groundType.equals (GroundType.PLAIN) ||
                groundType.equals ( GroundType.STONE )
        )){
            return new Error ("You can't drop rock in this ground type",false);
        }
        return new Error ( "",true );
    }


    public String dropTree(Matcher matcher){
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
        if(!Tree.isValidType ( type )){
            return "You should enter a valid type of tree";
        }
        error = canPlaceObject ( x, y );

        if(!error.truth){
            return error.errorMassage;
        }
        Unit unit= this.currentUser.getGovernment ().getMap ().getUnitByXY (i,j);
        Tree tree=new Tree (type,this.currentUser);
        if(!tree.canPlace ( unit.getTexture () )){
            return "You can't drop tree in this ground type";
        }
        this.currentUser.getGovernment ().getMap ().addObject ( tree,x,y );
        return "You drop tree successfully";
    }






}