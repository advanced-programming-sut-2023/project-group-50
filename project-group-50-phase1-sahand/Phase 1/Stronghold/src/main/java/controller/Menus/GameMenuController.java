package controller.Menus;

import controller.UserDatabase.User;
import controller.control.Commands;
import controller.control.Error;
import model.Game;
import model.Map.GroundType;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.ObjectType;
import view.GameMenu;

import java.util.regex.Matcher;

import static controller.Menus.LoginController.*;
public class GameMenuController {
    private  User currentUser;
    private  Game game;
    private final GameMenu gameMenu;

    public GameMenuController(GameMenu gameMenu) {
        this.gameMenu=gameMenu;
    }

    public String dropBuilding(Matcher matcher){
        matcher.find ();
        String input=matcher.group ();

        Error error=checkHasField(input, Commands.X);
        if (!error.truth) {
            return error.errorMassage;
        }
        int x=Integer.parseInt(error.errorMassage);
        if(x>this.currentUser.getGovernment ().getMap ().getxSize () || x<0){
            return "The x value is not within the map range";
        }

        error=checkHasField(input,Commands.Y);
        if (!error.truth) {
            return error.errorMassage;
        }
        int y=Integer.parseInt(error.errorMassage);

        if(y>this.currentUser.getGovernment ().getMap ().getySize () || y<0){
            return "The y value is not within the map range";
        }

        error=checkType (input);
        if(!error.truth){
            return error.errorMassage;
        }
        BuildingType buildingType=BuildingType.checkType (error.errorMassage);



        return "";
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

        if(BuildingType.checkType (type)==null){
            return new Error ("You should enter valid type building",false);

        }

        return new Error(type, true);


    }


    private Error canPlaceBuilding(int x, int y, BuildingType buildingType){
        Unit unit=this.currentUser.getGovernment ().getMap ().getUnitByXY (x,y);

        if(this.currentUser.getGovernment ().getMap ().getObjectByXY (x,y, ObjectType.BUILDING)!=null){
            return new Error ("There is a building at these coordinates",false)ک
        }
        if(this.currentUser.getGovernment ().getMap ().getObjectByXY (x,y, ObjectType.TREE)!=null){
            return new Error ("There is a tree at these coordinates",false)ک
        }
        if(this.currentUser.getGovernment ().getMap ().getObjectByXY (x,y, ObjectType.ROCK)!=null){
            return new Error ("There is a building at these coordinates",false)ک
        }

        GroundType groundType=unit.getTexture ();
        if(groundType.equals (GroundType.STONE) || groundType.equals (GroundType.BEACH)){
            return new Error ("You can't drop building in this ground",false)
        }

        if(buildingType.equals (BuildingType.APPLE_ORCHARD)
                || buildingType.equals (BuildingType.HOPS_FARMER)
                || buildingType.equals (BuildingType.WHEAT_FARMER)){
            if(!(groundType.equals (GroundType.GRASS)
                    || groundType.equals (GroundType.MEADOW))){
                return new Error ("You only need to place the farm in the grass or meadow",false)
            }
            return new Error ("",true);
        }

        if (buildingType.equals (BuildingType))






    }


}
