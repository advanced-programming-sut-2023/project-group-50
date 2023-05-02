package model.ObjectsPackage.People.Soldier;

import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;

import java.util.ArrayList;

public class GroupSoldier extends Objects {
    private final ArrayList<Soldier> group;
    private final GroupModeName groupMode;
    private final SoldierName type;
    private final boolean protection;

    protected GroupSoldier(ArrayList<Soldier> group, GroupModeName groupMode, SoldierName type, boolean protection) {
        super(ObjectType.GROUP_SOLDIER, owner);
        this.group = group;
        this.groupMode = groupMode;
        this.type = type;
        this.protection = protection;
    }

    public void addSoldier(Soldier soldier) {
        group.add(soldier);
    }

    public void moveGroup(int x, int y) {
        //TODO: fill here after map is done
    }

    public void patrolGroup(int x1, int y1, int x2, int y2) {
        //TODO: fill here after map is done
    }

    public void stopGroup(int x1, int y1, int x2, int y2) {
        //TODO: fill here after map is done
    }

    //TODO: fill strategies

    public void checkMode() {
        //TODO: what?
    }

    public void attackEnemy(Object enemy) {

    }

    public void archerAttack(int x, int y) {

    }

    public void checkRange() {

    }

    public void disband() {

    }

    public void digTunnel(int x, int y) {

    }

    public void build(BuildingType buildingType) {

    }

    public void digDitch(int x, int y) {

    }

    public void captureGate(int x, int y) {

    }
}
