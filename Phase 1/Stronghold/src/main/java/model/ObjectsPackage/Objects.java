package model.ObjectsPackage;

import controller.UserDatabase.User;
import model.Map.GroundType;
import model.Map.Map;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.People.Soldier.GroupSoldier;
import model.ObjectsPackage.People.Soldier.Soldier;

import java.io.Serializable;

public abstract class Objects implements Serializable {
    private final ObjectType objectType;
    protected User owner;
    private int X;
    private int Y;

    protected Objects(ObjectType objectType, User owner) {
        this.objectType = objectType;
        this.owner = owner;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void applyDamage(int damage) {
        if (getOwner().getGovernment().getMap().getXY(X, Y).isProtected())
            damage = damage * 3 / 10;

        switch (objectType) {
            case PERSON -> {
                Person person = (Person) this;
                person.setLife(person.getLife() - damage);
            }
            case TREE, WEAPON, ROCK -> {
            }
            case BARRACKS, BUILDING -> {
                Building building = (Building) this;
                building.setHp(building.getHp() - damage);
            }
            case GROUP_SOLDIER -> {
                GroupSoldier groupSoldier = (GroupSoldier) this;
                for (Soldier soldier : groupSoldier.getGroup())
                    soldier.setLife(soldier.getLife() - damage);
            }
        }
    }

    public void applyDamage(Soldier enemy) {
        int damage = enemy.getType().getAttackPower();
        applyDamage(damage);

        if (this instanceof Soldier soldier)
            if (distanceTo(enemy) <= soldier.getType().getSpeed())
                soldier.setAttacker(enemy);
    }

    public int distanceTo(Objects objects) {
        Map map = objects.getOwner().getGovernment().getMap();
        Building building = (Building) map.getObjectByXY(objects.getX(), objects.getY(), ObjectType.BUILDING);
        GroundType texture = map.getXY(objects.getX(), objects.getY()).getTexture();

        if ((building != null) && !building.getOwner().equals(owner)) return Integer.MAX_VALUE;
        else if ((building == null) && !Soldier.canPlace(texture)) return Integer.MAX_VALUE;

        return Map.distance(objects.getX(), objects.getY(), getX(), getY());
    }


    public int getHp() {
        if (this instanceof Building building) return building.getHp();
        if (this instanceof Soldier soldier) return soldier.getLife();
        return 0;
    }

    public int getScore() {
        if (this instanceof Building building) return building.getType().getCoinCost();
        else if (this instanceof GroupSoldier soldiers)
            return soldiers.getType().getCoinCost() * soldiers.numberOfSoldiers();
        else if (this instanceof Soldier soldier) return soldier.getType().getCoinCost();
        else return 1;
    }
}
