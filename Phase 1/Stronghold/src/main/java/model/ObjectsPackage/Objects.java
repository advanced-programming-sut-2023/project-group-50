package model.ObjectsPackage;

import controller.UserDatabase.User;
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

    public void applyDamage(int damage) {
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
}
