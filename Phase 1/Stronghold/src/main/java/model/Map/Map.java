package model.Map;

import model.ObjectsPackage.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Map implements Serializable {
    private final HashMap<Integer, HashMap<Integer, Unit>> map;
    private final int xSize;
    private final int ySize;

    public Map(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        map = new HashMap<>();

        for (int i = 0; i < xSize; i++) {
            map.put(i, new HashMap<>());
            for (int j = 0; j < ySize; j++) map.get(i).put(j, new Unit(i, j, GroundType.GROUND));
        }
    }

    private static boolean invalidBounds(int x1, int y1, int x2, int y2) {
        return x2 < x1 || y2 < y1;
    }

    private static int maxOrZero(int num) {
        num = Math.max(0, num);
        return num;
    }

    private static String getMapString(ArrayList<ArrayList<ArrayList<String>>> arrayLists, String line) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ArrayList<ArrayList<String>> row : arrayLists) {
            stringBuilder.append(line).append("\n");
            for (int i = 0; i < 2; i++) {
                stringBuilder.append("|");
                for (ArrayList<String> cell : row)
                    stringBuilder.append("|").append(cell.get(i)).append("|");
                stringBuilder.append("|\n");
            }
        }
        stringBuilder.append(line);
        return stringBuilder.toString();
    }

    private static String getLine(int x1, int x2) {
        int cols = x2 - x1 + 1;
        return "-".repeat(Math.max(0, 3 * cols + 1));
    }

    public void addObject(Objects object, int x, int y) {
        map.get(x).get(y).addObject(object);
    }

    public void removeObject(Objects object, int x, int y) {
        map.get(x).get(y).removeObject(object);
    }

    public HashMap<Integer, HashMap<Integer, Unit>> getMap() {
        return map;
    }

    public String getMap(int x1, int y1, int xLength, int yLength) {
        int x2 = getX2(x1, xLength);
        int y2 = getY2(y1, yLength);
        x1 = maxOrZero(x1);
        y1 = maxOrZero(y1);

        if (invalidBounds(x1, y1, x2, y2)) return "";

        ArrayList<ArrayList<ArrayList<String>>> arrayLists = getArrayList(x1, y1, x2, y2);
        String line = getLine(x1, x2);

        return getMapString(arrayLists, line);
    }

    private int getY2(int y1, int yLength) {
        int y2 = Math.min(y1 + yLength, ySize);
        return y2;
    }

    private int getX2(int x1, int xLength) {
        int x2 = Math.min(x1 + xLength, xSize);
        return x2;
    }

    private ArrayList<ArrayList<ArrayList<String>>> getArrayList(int x1, int y1, int x2, int y2) {
        ArrayList<ArrayList<ArrayList<String>>> arrayLists = new ArrayList<>();
        for (int i = x1; i <= x2; i++) {
            arrayLists.add(new ArrayList<>());
            for (int j = y1; j <= y2; j++) arrayLists.get(i - x1).add(map.get(i).get(j).toArrayListString());
        }
        return arrayLists;
    }

    public Unit getXY(int x, int y) {
        return map.get(x).get(y);
    }
}
