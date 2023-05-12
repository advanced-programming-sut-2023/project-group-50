package controller.Menus;

import controller.UserDatabase.User;
import controller.control.Commands;
import controller.control.Error;
import model.Direction.Direction;
import model.Map.GroundType;
import model.Map.Unit;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.People.Soldier.SoldierName;
import model.ObjectsPackage.Rock;
import model.ObjectsPackage.Tree;
import model.ObjectsPackage.TreeType;
import view.MapMenu;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static controller.Menus.GameMenuController.checkValue;

public class MapMenuController {

    private final MapMenu mapMenu;

    private int x;
    private int y;

    private User currentUser;

    public MapMenuController(MapMenu mapMenu) {
        this.mapMenu = mapMenu;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public MapMenu getMapMenu() {
        return mapMenu;
    }

    public String showMap(Matcher matcher) {
        matcher.find();
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

        String s = this.currentUser.getGovernment().getMap().getMap(x, y, 15, 15);
        if (s.equals("")) {
            return "invalid bounds for show";
        }
        this.x = x;
        this.y = y;
        return s;
    }

    public String moveMap(Matcher matcher) {
        matcher.find();
        String input = matcher.group();

        input = input.substring(4);
        matcher = Commands.getMatcher(Commands.DIRECTION, input);
        int x = 0;
        int y = 0;
        while (!matcher.find()) {
            String in = matcher.group();
            Matcher match = Commands.getMatcher(Commands.WORD, in);
            match.find();
            String direct = match.group();
            int value = 1;
            if (match.find()) {
                value = Integer.parseInt(match.group());
            }
            if (direct.equals("up")) {
                y = y + value;
            } else if (direct.equals("down")) {
                y = y - value;
            } else if (direct.equals("left")) {
                x = x - value;
            } else if (direct.equals("right")) {
                x = x + value;
            }
        }

        String s = this.currentUser.getGovernment().getMap().getMap(this.x + x, this.y + y, 15, 15);
        if (s.equals("")) {
            return "invalid bounds for move";
        }
        this.x = this.x + x;
        this.y = this.y + y;
        return s;

    }

    public String showDetails(Matcher matcher) {
        matcher.find();
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

        Unit unit = this.currentUser.getGovernment().getMap().getUnitByXY(x, y);
        String string = "Ground type : " + unit.getTexture() + "\n";
        GroundType texture = unit.getTexture();
        if (texture.equals(GroundType.IRON) || texture.equals(GroundType.CLIFF) || texture.equals(GroundType.OIL)) {
            string = string + "Resource : " + texture.getType() + "*" + unit.getCapacity() + "\n";
        } else {
            string = string + "There is no resource here";
        }

        if (unit.getObjects().isEmpty()) {
            return string;
        }
        ArrayList<Objects> objects = new ArrayList<>(unit.getObjects());
        for (int i = 0; i < objects.size(); i++) {
            Objects object = objects.get(i);
            int number = 1;
            if (object instanceof Soldier) {
                for (int j = i + 1; j < objects.size(); j++) {
                    Objects objectsSample = objects.get(j);
                    if (objectsSample instanceof Soldier) {
                        if (((Soldier) object).getType().equals(((Soldier) objectsSample).getType())) {
                            number++;
                            objects.remove(objectsSample);
                        }
                    }
                }
                string = string + "Soldier : " + ((Soldier) object).getType().getType() + "*" + number + "\n";
            } else if (object instanceof Building) {
                string = string + "Building : " + ((Building) object).getType().getType() + "*" + number + "\n";
            }

        }

        return string;

    }

    public String setTexture(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));
        GroundType type = GroundType.get(matcher.group("type"));

        if (x < 0 || x > currentUser.getGovernment().getMap().getXSize()) return "x is invalid!";
        if (y < 0 || y > currentUser.getGovernment().getMap().getYSize()) return "y is invalid!";
        if (type == null) return "Invalid type!";

        changeTexture(x, y, type);
        return "Texture set successfully.";
    }

    private void changeTexture(int x, int y, GroundType type) {
        currentUser.getGovernment().getMap().getXY(x, y).setTexture(type);
    }

    public String setTextureRect(Matcher matcher) {
        int x1 = Integer.parseInt(matcher.group("x1")), y1 = Integer.parseInt(matcher.group("y1")), x2 = Integer.parseInt(matcher.group("x2")), y2 = Integer.parseInt(matcher.group("y2"));
        GroundType type = GroundType.get(matcher.group("type"));

        if (boundIsInvalid(x1, y1, x2, y2)) return "Invalid bounds!";
        if (type == null) return "Invalid type!";


        for (int x = x1; x <= x2; x++)
            for (int y = y1; y <= y2; y++)
                changeTexture(x, y, type);

        return "texture set successfully.";
    }

    private boolean boundIsInvalid(int x1, int y1, int x2, int y2) {
        return (x1 < 0 || x1 > currentUser.getGovernment().getMap().getXSize()) ||
                (x2 < 0 || x2 > currentUser.getGovernment().getMap().getXSize()) ||
                (y1 < 0 || y1 > currentUser.getGovernment().getMap().getYSize()) ||
                (y2 < 0 || y2 > currentUser.getGovernment().getMap().getYSize()) || (x1 >= x2) || (y1 >= y2);
    }

    public String clear(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));

        if (x < 0 || x > currentUser.getGovernment().getMap().getXSize()) return "x is invalid!";
        if (y < 0 || y > currentUser.getGovernment().getMap().getYSize()) return "y is invalid!";

        currentUser.getGovernment().getMap().clearXY(x, y);
        return "Cleared successfully.";
    }

    public String dropRock(Matcher matcher) {
        String d = matcher.group("direction");
        Direction direction = Direction.get(d);
        int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));

        if (x < 0 || x > currentUser.getGovernment().getMap().getXSize()) return "x is invalid!";
        if (y < 0 || y > currentUser.getGovernment().getMap().getYSize()) return "y is invalid!";
        if (direction == null) return "Invalid direction!";


        currentUser.getGovernment().getMap().addObject(new Rock(direction, currentUser), x, y);
        return "Rock dropped.";
    }

    private int getNeighbourX(int x, Direction direction) {
        switch (direction) {
            case UP -> {
                return x - 1;
            }
            case LEFT, RIGHT -> {
                return x;
            }
            case DOWN -> {
                return x + 1;
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    private int getNeighbourY(int y, Direction direction) {
        switch (direction) {
            case UP, DOWN -> {
                return y;
            }
            case LEFT -> {
                return y - 1;
            }
            case RIGHT -> {
                return y + 1;
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    private boolean XYisValid(int x, int y) {
        if (x < 0 || x > currentUser.getGovernment().getMap().getXSize()) return false;
        return y >= 0 && y <= currentUser.getGovernment().getMap().getYSize();
    }

    public String dropTree(Matcher matcher) {
        String type = matcher.group("type");
        TreeType treeType = TreeType.get(type);
        int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));

        if (x < 0 || x > currentUser.getGovernment().getMap().getXSize()) return "x is invalid!";
        if (y < 0 || y > currentUser.getGovernment().getMap().getYSize()) return "y is invalid!";
        if (treeType == null) return "Invalid tree type!";

        currentUser.getGovernment().getMap().addObject(new Tree(treeType, currentUser), x, y);
        return "Tree dropped.";
    }

    public String dropBuilding(Matcher matcher) {
        String type = matcher.group("type");
        BuildingType buildingType = BuildingType.checkTypeByName(type);
        int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));

        if (x < 0 || x > currentUser.getGovernment().getMap().getXSize()) return "x is invalid!";
        if (y < 0 || y > currentUser.getGovernment().getMap().getYSize()) return "y is invalid!";
        if (buildingType == null) return "Invalid tree type!";
        GroundType texture = currentUser.getGovernment().getMap().getXY(x, y).getTexture();
        if (!Building.canPlace(buildingType, texture))
            return "Cannot place " + buildingType.getType() + " on " + texture.getType() + ".";

        currentUser.getGovernment().getMap().addObject(Building.getBuildingByType(buildingType,
                                                                                  currentUser,
                                                                                  x,
                                                                                  y),
                                                       x,
                                                       y);
        return "Building dropped.";
    }

    public String dropUnit(Matcher matcher) {
        String type = matcher.group("type");
        SoldierName name = SoldierName.getSoldierNameByType(type);
        int x = Integer.parseInt(matcher.group("x")),
                y = Integer.parseInt(matcher.group("y")),
                c = Integer.parseInt(matcher.group("c"));

        if (x < 0 || x > currentUser.getGovernment().getMap().getXSize()) return "x is invalid!";
        if (y < 0 || y > currentUser.getGovernment().getMap().getYSize()) return "y is invalid!";
        if (c < 0) return "Invalid count!";
        if (name == null) return "Invalid tree type!";
        if (!Person.canPlace(currentUser.getGovernment().getMap().getXY(x, y).getTexture()))
            return "Cannot place here.";

        currentUser.getGovernment().getMap().addObject(Soldier.getSoldierByType(name, currentUser),
                                                       x,
                                                       y);
        return "Unit dropped.";
    }
}
