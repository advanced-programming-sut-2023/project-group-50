package model.Save.MapSave;

import controller.UserDatabase.User;
import model.Map.Map;
import model.Map.Unit;
import model.ObjectsPackage.Objects;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class AnonymousMap implements Serializable {
    @Serial
    private static final long serialVersionUID = -5142982528088815366L;
    private final HashMap<Integer, HashMap<Integer, AnonymousUnit>> anonymousUnits;
    private final int xSize;
    private final int ySize;
    private final boolean isPublic;

    public AnonymousMap(Map map, boolean isPublic) {
        xSize = map.getXSize();
        ySize = map.getYSize();
        this.isPublic = isPublic;
        this.anonymousUnits = getUnits(map);
    }

    private static AnonymousUnit getAnonymousUnit(int x, int y, Unit real) {
        AnonymousUnit unit = new AnonymousUnit(new LinkedHashSet<>(), x, y, real.getTexture());

        for (Objects realObject : real.getObjects()) {
            AnonymousObject anonymousObject = realObject.getAnonymous();
            unit.objects().add(anonymousObject);
        }
        return unit;
    }

    private HashMap<Integer, HashMap<Integer, AnonymousUnit>> getUnits(Map map) {
        HashMap<Integer, HashMap<Integer, AnonymousUnit>> out = new HashMap<>();

        for (int x = 0; x < map.getXSize(); x++) {
            out.put(x, new HashMap<>());
            for (int y = 0; y < map.getYSize(); y++) {
                Unit real = map.getUnitByXY(x, y);
                AnonymousUnit unit = getAnonymousUnit(x, y, real);
                out.get(x).put(y, unit);
            }
        }

        return out;
    }

    public Map getMap(User user) {
        Map map = new Map(xSize, ySize);

        for (int x = 0; x < xSize; x++)
            for (int y = 0; y < ySize; y++)
                map.setXY(x, y, anonymousUnits.get(x).get(y).getUnit(user, x, y));

        return map;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
