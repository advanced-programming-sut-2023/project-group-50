package model.Save.MapSave;

import controller.UserDatabase.User;
import model.Map.GroundType;
import model.Map.Unit;
import model.ObjectsPackage.Objects;

import java.io.Serializable;
import java.util.LinkedHashSet;

public record AnonymousUnit(LinkedHashSet<AnonymousObject> objects, int x, int y, GroundType texture)
        implements Serializable {
    public Unit getUnit(User user, int x, int y) {
        Unit unit = new Unit(x, y, texture);

        for (AnonymousObject anonymousObject : objects) {
            Objects object = anonymousObject.getObject(user, x, y);
            if (object != null)
                unit.getObjects().add(object);
        }

        return unit;
    }
}
