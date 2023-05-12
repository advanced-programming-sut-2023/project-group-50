package model.ObjectsPackage.People.Soldier;

import controller.UserDatabase.User;
import model.ObjectsPackage.Buildings.BuildingType;
import model.ObjectsPackage.Buildings.Gate;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.Weapons.Weapon;
import model.ObjectsPackage.Weapons.WeaponName;

import java.util.ArrayList;

public class GroupSoldier extends Soldier {
    private final ArrayList<Soldier> group;
    private final GroupModeName groupMode;
    private final SoldierName type;
    private final boolean protection;
    private boolean isPatrolling;
    private Soldier attacker;
    private Weapon weapon;
    private int hp = 1000;

    public GroupSoldier(ArrayList<Soldier> group,
                        SoldierName type,
                        boolean protection,
                        User owner) {
        super(type, owner);
        this.group = group;
        this.type = type;
        this.protection = protection;
        isPatrolling = false;
        groupMode = getGroupModeName(group, type);
    }

    private GroupModeName getGroupModeName(ArrayList<Soldier> group, SoldierName type) {
        final GroupModeName groupMode;
        switch (type) {
            case ENGINEER -> {
                groupMode = GroupModeName.ENGINEER;
            }
            case TUNNELER -> {
                groupMode = GroupModeName.TUNNELER;
            }
            default -> {
                if (group.get(0) instanceof Archer) groupMode = GroupModeName.ARCHER;
                else groupMode = GroupModeName.INFANTRY;
            }
        }
        return groupMode;
    }

    public GroupModeName getGroupMode() {
        return groupMode;
    }

    public SoldierName getType() {
        return type;
    }

    public boolean isPatrolling() {
        return isPatrolling;
    }

    public void setPatrolling(boolean patrolling) {
        isPatrolling = patrolling;
    }

    public ArrayList<Soldier> getGroup() {
        return group;
    }

    public void addSoldier(Soldier soldier) {
        group.add(soldier);
    }

    public void moveGroup(int x, int y) {
        if (!getWeapon().getWeaponName().isCanMove()) return;
        for (Soldier soldier : group) soldier.move(x, y);
        setX(group.get(0).getX());
        setY(group.get(0).getY());
    }

    public void startPatrollingGroup(int x1, int y1, int x2, int y2) {
        if (!getWeapon().getWeaponName().isCanMove()) return;
        for (Soldier soldier : group) {
            soldier.setPatrolling(true);
            soldier.startPatrolling(x1, y1, x2, y2);
        }
        setPatrolling(true);
    }

    public void stopGroup() {
        if (!getWeapon().getWeaponName().isCanMove()) return;
        for (Soldier soldier : group) soldier.endPatrolling();
        setPatrolling(false);
    }

    public void attackEnemy(Objects enemy) {
        for (Soldier soldier : group)
            soldier.attack(enemy);
    }

    public void defendAgainstAttacker() {
        for (Soldier soldier : group)
            soldier.defendAgainstAttacker();
    }

    public void archerAttack(int x, int y) {
        for (Soldier soldier : group)
            if (soldier instanceof Archer archer)
                archer.attack(x, y);
    }

    public void archerDefense(int x, int y) {
        for (Soldier soldier : group)
            if (soldier instanceof Archer archer)
                archer.defend(x, y);
    }

    public int getRange() {
        return group.isEmpty() ? 0 : group.get(0).getWeapon().getWeaponName().getRange();
    }

    public void disband() {
        getOwner().getGovernment().addUnDeployedSoldier(group);
        group.clear();
    }

    public void digTunnel(int x, int y) {
        if (group.isEmpty()) return;
        if (group.get(0) instanceof Tunneler tunneler)
            tunneler.digTunnel(x, y);
    }

    public void build(BuildingType buildingType) {
        if (group.isEmpty()) return;
        if (group.get(0) instanceof Engineer engineer)
            engineer.build(buildingType);
    }

    public void placePitchDitch(int x, int y) {
        if (group.isEmpty()) return;
        if (group.get(0) instanceof Engineer engineer)
            engineer.placePitchDitch(x, y);
    }

    public void captureGate(Gate gate) {
        if (group.isEmpty()) return;
        for (Soldier soldier : group)
            if (soldier instanceof Infantry infantry)
                infantry.captureGate(gate);
    }

    public boolean isAttacked() {
        return attacker != null;
    }

    public void applyDamage(Soldier enemy) {
        if (!(getWeapon().getWeaponName() == WeaponName.BATTERING_RAM ||
                getWeapon().getWeaponName() == WeaponName.CATAPULT ||
                getWeapon().getWeaponName() == WeaponName.FIRE_THROWER)) {
            super.applyDamage(enemy);
            return;
        }

        hp -= enemy.getType().getAttackPower();
        if (hp <= 0) disband();
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
