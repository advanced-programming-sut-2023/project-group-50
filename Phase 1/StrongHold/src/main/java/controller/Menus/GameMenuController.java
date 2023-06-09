package controller.Menus;

import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import controller.control.Commands;
import controller.control.Error;
import model.Direction.Direction;
import model.Game;
import model.Map.GroundType;
import model.Map.Map;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Buildings.Gate;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.PersonState;
import model.ObjectsPackage.People.Soldier.*;
import model.ObjectsPackage.Resource;
import model.ObjectsPackage.Weapons.Weapon;
import model.ObjectsPackage.Weapons.WeaponName;
import view.GameMenu;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static controller.Menus.LoginController.checkHasField;
import static controller.Menus.LoginController.removeDoubleCoutString;

public class GameMenuController {
    private final GameMenu gameMenu;
    private final Game game;
    private User currentUser;
    private Building selectedBuilding;
    private Unit selectedUnit;

    public GameMenuController(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
        game = new Game(new ArrayList<>(), 500);
    }

    public static Error checkValue(String input, Commands commands, User currentUser) {
        Error error = checkHasField(input, commands);
        if (!error.truth) {
            return error;
        }
        int x = Integer.parseInt(error.errorMassage);
        if (x > currentUser.getGovernment().getMap().getXSize() || x < 0) {
            return new Error("The " + commands.name() + " value is not within the map range", false);
        }
        return new Error(error.errorMassage, true);
    }

    public Game getGame() {
        return game;
    }

    public String setSelectedUnit(Matcher matcher) {
        String input = matcher.group();

        Error error = checkValue(input, Commands.X, currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int x = Integer.parseInt(error.errorMassage);

        error = checkValue(input, Commands.Y, currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int y = Integer.parseInt(error.errorMassage);
        if (this.currentUser.getGovernment().getMap().getObjectByXY(x, y, ObjectType.PERSON) == null) {
            return "There isn't a person at these coordinates";
        }
        Soldier soldier = (Soldier) this.currentUser.getGovernment().getMap().getObjectByXY(x, y, ObjectType.PERSON);

        if (!soldier.getOwner().equals(this.currentUser)) {
            return "This building isn't yours";
        }
        this.setSelectedUnit(x, y);
        if (!soldier.isAlive()) {
            System.out.println("This soldier is dead");
        }
        System.out.println("Hp of this soldier is : " + soldier.getHp());

        this.selectedUnit = currentUser.getGovernment().getMap().getUnitByXY(x, y);

        return "Your unit is selected";
    }

    public void setSelectedUnit(int x, int y) {
        this.selectedUnit = currentUser.getGovernment().getMap().getUnitByXY(x, y);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
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

    public String dropBuilding(Matcher matcher) {
        //matcher.find();
        String input = matcher.group();

        Error error = checkValue(input, Commands.X, currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int x = Integer.parseInt(error.errorMassage);

        error = checkValue(input, Commands.Y, currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int y = Integer.parseInt(error.errorMassage);

        error = checkType(input);
        if (!error.truth) {
            return error.errorMassage;
        }
        String type = error.errorMassage;
        if (BuildingType.checkTypeByName(type) == null) {
            return "You should enter valid type building";
        }
        BuildingType buildingType = BuildingType.checkTypeByName(type);

        error = canPlaceBuilding(x, y, buildingType);
        if (!error.truth) {
            return error.errorMassage;
        }
        String errorString = checkResource(buildingType, 1);
        if (errorString != null) return errorString;

        Building building = Building.getBuildingByType(buildingType, this.currentUser, x, y);
        if (this.currentUser.getGovernment().getPeopleByState(PersonState.JOBLESS).size() <
                Building.numberOfWorker(buildingType)) {
            System.out.println("Your building doesn't have any worker");
            this.currentUser.getGovernment().peopleGetJob(this.currentUser.getGovernment().getPeopleByState(PersonState.JOBLESS).size(), Building.getJobByBuildingType(buildingType), building);
        } else {
            this.currentUser.getGovernment().peopleGetJob(Building.numberOfWorker(buildingType), Building.getJobByBuildingType(buildingType), building);
        }


        this.currentUser.getGovernment().getMap().addObject(building, x, y);
        this.currentUser.getGovernment().buyBuilding(buildingType, 1);
        this.currentUser.getGovernment().addBuildings(building);
        return "You drop building successfully";
    }

    private String checkResource(BuildingType buildingType, float zarib) {
        if (Math.ceil(buildingType.getCoinCost() * zarib) > this.currentUser.getGovernment().getCoins()) {
            return "You haven't enough coin to build this " + buildingType.getType();
        }
        if ((int) Math.ceil(buildingType.getStoneCost() * zarib) >
                this.currentUser.getGovernment().getResourceAmount(Resource.STONE)) {
            return "You haven't enough stone to build this " + buildingType.getType();
        }
        if ((int) Math.ceil(buildingType.getWoodCost() * zarib) >
                this.currentUser.getGovernment().getResourceAmount(Resource.WOOD)) {
            return "You haven't enough wood to build this " + buildingType.getType();
        }
        if ((int) Math.ceil(buildingType.getIronCost() * zarib) >
                this.currentUser.getGovernment().getResourceAmount(Resource.IRON)) {
            return "You haven't enough iron to build this " + buildingType.getType();
        }
        return null;
    }

    private Error checkType(String input) {
        Matcher matcher = Commands.getMatcher(Commands.TYPE, input);

        if (!matcher.find()) {
            return new Error("You should enter all field!\nEnter a " + Commands.TYPE.name(), false);
        }
        String type = matcher.group();
        type = type.substring(6);
        type = removeDoubleCoutString(type);
        if (matcher.find()) {
            return new Error(
                    "Invalid command!\nYou should enter all field!\nEnter " + Commands.TYPE.name() + " once", false);

        }
        type = type.trim();
        if (type.equals("")) {
            return new Error("You should fill " + Commands.TYPE.name() + " field!", false);
        }

        return new Error(type, true);


    }

    private Error canPlaceBuilding(int x, int y, BuildingType buildingType) {
        Unit unit = this.currentUser.getGovernment().getMap().getUnitByXY(x, y);

        Error x1 = canPlaceObject(x, y);
        if (x1 != null) return x1;

        GroundType groundType = unit.getTexture();
        if (!(groundType.equals(GroundType.GROUND) ||
                groundType.equals(GroundType.GRASS) ||
                groundType.equals(GroundType.RIGGED_GROUND) ||
                groundType.equals(GroundType.CLIFF) ||
                groundType.equals(GroundType.IRON) ||
                groundType.equals(GroundType.LAWN) ||
                groundType.equals(GroundType.MEADOW) ||
                groundType.equals(GroundType.PLAIN)
        )) {
            return new Error("You can't drop building in this ground type", false);
        }

        if (buildingType.equals(BuildingType.APPLE_ORCHARD)
                || buildingType.equals(BuildingType.HOPS_FARMER)
                || buildingType.equals(BuildingType.WHEAT_FARMER)) {
            if (!(groundType.equals(GroundType.GRASS)
                    || groundType.equals(GroundType.MEADOW))) {
                return new Error("You only need to place the farm in the grass or meadow", false);
            }
            return new Error("", true);

        } else if (buildingType.equals(BuildingType.IRON_MINE)) {
            if (!groundType.equals(GroundType.IRON)) {
                return new Error("Iron mine just can place in iron ground", false);
            }
            return new Error("", true);
        } else if (buildingType.equals(BuildingType.QUARRY)) {
            if (!groundType.equals(GroundType.CLIFF)) {
                return new Error("Quarry just can place in iron ground", false);
            }
            return new Error("", true);
        } else if (buildingType.equals(BuildingType.PITCH_RIG)) {
            if (!groundType.equals(GroundType.PLAIN)) {
                return new Error("Pitch rig just can place in iron ground", false);
            }
            return new Error("", true);
        } else {
            if (groundType.equals(GroundType.CLIFF) ||
                    groundType.equals(GroundType.IRON) ||
                    groundType.equals(GroundType.PLAIN)
            ) {
                return new Error("You can't drop building in this place", false);
            }
            return new Error("", true);
        }
    }

    public Error canPlaceObject(int x, int y) {
        if (this.currentUser.getGovernment().getMap().getObjectByXY(x, y, ObjectType.BUILDING) != null) {
            return new Error("There is a building at these coordinates", false);
        }
        if (this.currentUser.getGovernment().getMap().getObjectByXY(x, y, ObjectType.TREE) != null) {
            return new Error("There is a tree at these coordinates", false);
        }
        if (this.currentUser.getGovernment().getMap().getObjectByXY(x, y, ObjectType.ROCK) != null) {
            return new Error("There is a rock at these coordinates", false);
        }
        return null;
    }

    public String selectBuilding(Matcher matcher) {
        //matcher.find();
        String input = matcher.group();

        Error error = checkValue(input, Commands.X, currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int x = Integer.parseInt(error.errorMassage);

        error = checkValue(input, Commands.Y, currentUser);
        if (!error.truth) {
            return error.errorMassage;
        }
        int y = Integer.parseInt(error.errorMassage);
        if (this.currentUser.getGovernment().getMap().getObjectByXY(x, y, ObjectType.BUILDING) == null) {
            return "There isn't a building at these coordinates";
        }
        Building building = (Building) this.currentUser.getGovernment().getMap().getObjectByXY(x, y, ObjectType.BUILDING);

        if (!building.getOwner().equals(this.currentUser)) {
            return "This building isn't yours";
        }
        this.setSelectedBuilding(building);
        if (Building.isCastles(building)) {
            if (building.isDestroyed()) {
                System.out.println("This building is destroyed");
            } else {
                System.out.println("Hp of this  building is : " + building.getHp());
            }
        }
        return "Your building is selected";
    }

    public String repair() {
        if (this.selectedBuilding == null) {
            return "Please first select a building";
        }
        float cost = 0;
        if (this.selectedBuilding.isDestroyed()) {
            cost = 1;
        } else {
            cost = 1 - ((float) selectedBuilding.getHp() / (float) selectedBuilding.getMaxHp());
        }
        String string = checkResource(selectedBuilding.getType(), cost);
        if (string != null) {
            return string;
        }

        if (this.currentUser.getGovernment().getMap().searchForEnemy(this.selectedBuilding.getX(), this.selectedBuilding.getY(), this.selectedBuilding.getOwner())) {
            return "The enemy soldier is close";
        }
        this.currentUser.getGovernment().buyBuilding(selectedBuilding.getType(), cost);
        this.selectedBuilding.repair();
        return "You repair selected building successfully";

    }


    public String createUnit(Matcher matcher) {
        //matcher.find();
        String input = matcher.group();
        if (this.selectedBuilding == null) {
            return "Please first select a building";
        }

        Error error = checkType(input);
        if (!error.truth) {
            return error.errorMassage;
        }
        String type = error.errorMassage;

        if (SoldierName.getSoldierNameByType(type) == null) {
            return "You should enter valid type soldier";
        }
        SoldierName soldierName = SoldierName.getSoldierNameByType(type);

        error = checkHasField(input, Commands.COUNT);
        if (!error.truth) {
            return error.errorMassage;
        }
        int count = Integer.parseInt(error.errorMassage);

        if (soldierName.getCoinCost() * count > this.currentUser.getGovernment().getCoins()) {
            return "You haven't enough coin";
        }

        WeaponName weaponName = Soldier.getWeaponName(soldierName);
        if (!this.currentUser.getGovernment().getWeapons().containsKey(weaponName)) {
            return "You haven't any this type weapon";
        }
        if (this.currentUser.getGovernment().getWeapons().get(weaponName) < count) {
            return "You haven't enough weapons";
        }

        if (this.currentUser.getGovernment().getPeopleByState(PersonState.JOBLESS).size() < count) {
            return "You haven't enough people";
        }

        if (SoldierName.isArab(soldierName)) {
            if (!selectedBuilding.getType().equals(BuildingType.MERCENARY_POST)) {
                return "You can't create arab soldier in this building";
            }

        } else {
            if (!(selectedBuilding.getType().equals(BuildingType.BARRACKS) ||
                    selectedBuilding.getType().equals(BuildingType.ENGINEER_GUILD))) {
                return "You can't create european soldier in this building";
            }
            if ((soldierName.equals(SoldierName.ENGINEER))) {
                if (!(selectedBuilding.getType().equals(BuildingType.ENGINEER_GUILD))) {
                    return "You just can create Engineer in Engineer guild";
                }
            }
        }
        this.currentUser.getGovernment().addUndeployedSoldier(count, soldierName, this.currentUser);
        return "You create a unit";
    }


    public String moveUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));
        if (x < 0 || x > currentUser.getGovernment().getMap().getXSize()) return "x is invalid!";
        if (y < 0 || y > currentUser.getGovernment().getMap().getYSize()) return "y is invalid!";
        if (selectedUnit == null) return "No unit is currently selected!";

        int successful = 0, failed = 0;
        for (Objects object : selectedUnit.getObjects()) {
            if (object instanceof Soldier soldier) {
                if (soldier.canMoveTo(x, y)) {
                    soldier.move(x, y);
                    successful++;
                } else failed++;
            }
        }

        return "Moved " + successful + " objects successfully and " + failed + " failed.";
    }

    public String patrolUnit(Matcher matcher) {
        int x1 = Integer.parseInt(matcher.group("x1")), y1 = Integer.parseInt(matcher.group("y1")), x2 = Integer.parseInt(matcher.group("x2")), y2 = Integer.parseInt(matcher.group("y2"));
        if (x1 < 0 || x1 > currentUser.getGovernment().getMap().getXSize()) return "x1 is invalid!";
        if (y1 < 0 || y1 > currentUser.getGovernment().getMap().getYSize()) return "y1 is invalid!";
        if (x2 < 0 || x2 > currentUser.getGovernment().getMap().getXSize()) return "x2 is invalid!";
        if (y2 < 0 || y2 > currentUser.getGovernment().getMap().getYSize()) return "y2 is invalid!";
        if (selectedUnit == null) return "No building is currently selected!";

        for (Objects object : selectedUnit.getObjects())
            if (object instanceof Soldier soldier) soldier.startPatrolling(x1, y1, x2, y2);

        return "Unit started patrolling.";
    }

    public String setUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));
        if (x < 0 || x > currentUser.getGovernment().getMap().getXSize()) return "x is invalid!";
        if (y < 0 || y > currentUser.getGovernment().getMap().getYSize()) return "y is invalid!";
        if (selectedUnit == null) return "No unit is currently selected!";

        for (Objects object : selectedUnit.getObjects())
            if (object instanceof Soldier soldier) soldier.setSoldierState(SoldierState.get(matcher.group("s")));

        return "Unit state set.";
    }


    public String attackEnemy(Matcher matcher) {
        String enemyName = matcher.group("e");

        if (!game.playerExists(enemyName)) return "Invalid enemy.";
        User enemy = Users.getUser(enemyName);
        currentUser.getGovernment().attackEnemy(enemy);

        return null;
    }


    public String archerAttack(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));
        if (x < 0 || x > currentUser.getGovernment().getMap().getXSize()) return "x is invalid!";
        if (y < 0 || y > currentUser.getGovernment().getMap().getYSize()) return "y is invalid!";
        if (selectedUnit == null) return "No unit is currently selected!";

        int successful = 0, failed = 0;
        for (Objects object : selectedUnit.getObjects()) {
            if (object instanceof Archer archer) {
                if (archer.isInRange(x, y)) {
                    archer.attack(x, y);
                    successful++;
                } else failed++;
            }
        }

        return successful + " archers attacked successfully and " + failed + " failed.";
    }

    public String pourOil(Matcher matcher) {
        Direction direction = Direction.get(matcher.group("d"));

        if (direction == null) return "Invalid direction!";
        if (selectedUnit == null) return "No unit is currently selected!";

        int successful = 0, failed = 0;
        for (Objects object : selectedUnit.getObjects()) {
            if (object instanceof Engineer engineer) {
                if (engineer.hasOil()) {
                    engineer.pourOil(direction);
                    successful++;
                } else failed++;
            }
        }

        return successful + " engineers poured oil successfully and " + failed + " failed.";
    }

    public String digTunnel(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));
        if (x < 0 || x > currentUser.getGovernment().getMap().getXSize()) return "x is invalid!";
        if (y < 0 || y > currentUser.getGovernment().getMap().getYSize()) return "y is invalid!";
        if (selectedUnit == null) return "No unit is currently selected!";

        int successful = 0;
        for (Objects object : selectedUnit.getObjects()) {
            if (object instanceof Tunneler tunneler) {
                tunneler.digTunnel(x, y);
                successful++;
            }
        }

        return successful + " tunnelers dug tunnel successfully.";
    }

    public String build(Matcher matcher) {
        BuildingType type = BuildingType.checkTypeByName(matcher.group("q"));
        if (type == null) return "Invalid building name.";
        if (selectedUnit == null) return "No unit is selected.";

        System.out.println(selectedUnit.getX() + " " + selectedUnit.getY());

        for (Objects object : selectedUnit.getObjects()) {
            System.out.println(object.getObjectType());
            if (object instanceof Engineer engineer) {
                try {
                    engineer.build(type);
                    return "Building built successfully.";
                } catch (Exception e) {
                    return "Not enough resources!";
                }
            }
        }

        return "Not enough engineers in this unit!";
    }

    public String disband() {
        selectedUnit = null;
        return "Unit disbanded.";
    }

    public String nextTurn() {
        String lastUser = game.getCurrentPlayer().getUserName();
        boolean userLost = !game.nextTurn();
        currentUser = game.getCurrentPlayer();
        selectedBuilding = null;
        selectedUnit = null;

        StringBuilder stringBuilder = new StringBuilder();
        if (userLost)
            if (game.gameIsFinished())
                stringBuilder.append("Game Finished! Winner: ").append(game.getWinner().getUserName()).append('\n');
            else stringBuilder.append(lastUser).append(" lost!\n");

        return stringBuilder
                + game.getCurrentPlayer().getUserName() + " is now playing.\nTurn no. " + game.getCurrentTurnNumber();
    }

    public String getUserData() {
        return currentUser.getGovernment().toString();
    }

    public boolean gameIsFinished() {
        return game.gameIsFinished();
    }

    public String captureGate(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));
        if (x < 0 || x > currentUser.getGovernment().getMap().getXSize()) return "x is invalid!";
        if (y < 0 || y > currentUser.getGovernment().getMap().getYSize()) return "y is invalid!";
        if (selectedUnit == null) return "No unit is selected!";
        if (selectedUnit.getSoldier() == null) return "No soldier in this unit!";
        if (Map.distance(selectedBuilding.getX(), selectedBuilding.getY(), x, y) > selectedUnit.getSoldier().getSpeed())
            return "The gate is too far away!";
        if (selectedUnit.getSoldier() instanceof Infantry infantry)
            return "There are no soldiers in this unit who can capture the gate!";

        for (Objects objects : getCurrentUser().getGovernment().getMap().getXY(x, y).getObjects()) {
            if (objects instanceof Gate gate) {
                Infantry infantry = (Infantry) selectedUnit.getSoldier();
                infantry.captureGate(gate);
                return "Gate captured.";
            }
        }

        return "No gate to be captured.";
    }

    public String makeBatteringRam() {
        if (selectedUnit == null) return "No unit is selected!";
        if (selectedUnit.getEngineerCount() < 4)
            return "There are not enough engineers in this unit!";

        if (!Soldier.canPlace(selectedUnit.getTexture())) return "Cannot place battering ram here!";
        makeGroup(WeaponName.BATTERING_RAM, 4);

        return "BatteringRam made.";
    }

    private void makeGroup(WeaponName weaponName, int c) {
        GroupSoldier groupSoldier = new GroupSoldier(selectedUnit.getEngineers(c),
                                                     SoldierName.ENGINEER,
                                                     false,
                                                     getCurrentUser());
        Weapon weapon = new Weapon(weaponName, groupSoldier, currentUser);
        groupSoldier.setWeapon(weapon);
        selectedUnit.addObject(groupSoldier);
    }

    public String makeCatapult() {
        if (selectedUnit == null) return "No unit is selected!";
        if (selectedUnit.getEngineerCount() < 3)
            return "There are not enough engineers in this unit!";

        if (!Soldier.canPlace(selectedUnit.getTexture())) return "Cannot place catapult here!";
        makeGroup(WeaponName.CATAPULT, 3);

        return "Catapult made.";
    }

    public String makeProtection() {
        if (selectedUnit == null) return "No unit is selected!";
        if (selectedUnit.getEngineerCount() < 1)
            return "There are not enough engineers in this unit!";

        if (!Soldier.canPlace(selectedUnit.getTexture())) return "Cannot place protection here!";
        selectedUnit.setProtected(true);

        return "Protection made.";
    }

    public String makeFireThrower() {
        if (selectedUnit == null) return "No unit is selected!";
        if (selectedUnit.getEngineerCount() < 1)
            return "There are not enough engineers in this unit!";

        if (!Soldier.canPlace(selectedUnit.getTexture())) return "Cannot place fire thrower here!";
        makeGroup(WeaponName.FIRE_THROWER, 1);

        return "Fire thrower made.";
    }

    public boolean canStart() {
        return game.canStart();
    }

    public String cannotStartMessage() {
        if (game.isOvercrowded()) return "Cannot start a game with this many people!";
        if (game.isUndercrowded()) return "Cannot start a game with one player!";
        return null;
    }
}


