package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;
import model.Map.Map;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.ObjectsPackage.People.Soldier.SoldierName;

public class Tunnel extends Building {
    private final int xFrom;
    private final int yFrom;
    private final int xTo;
    private final int yTo;

    public Tunnel(BuildingType type, User owner, int x, int y, int maxHp, int xFrom, int yFrom, int xTo, int yTo) {
        super(type, owner, x, y, maxHp);
        this.xFrom = xFrom;
        this.yFrom = yFrom;
        this.xTo = xTo;
        this.yTo = yTo;
    }

    public void attack() {
        if (getType().equals(BuildingType.CAGED_WAR_DOGS)) {
            Map map = getOwner().getGovernment().getMap();
            for (int i = 0; i < 5; i++) {
                map.addObject(Soldier.getSoldierByType(SoldierName.LOOSE_WAR_DOG, getOwner()), xFrom, yFrom);
            }
        }
    }

    public void setOnFire() {
        if (getType().equals(BuildingType.PITCH_DITCH))
            getOwner().getGovernment().getMap().getXY(xFrom, yFrom).setOnFire(true);
    }
}
