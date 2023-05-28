package model.ObjectsPackage.People.Soldier;

import controller.UserDatabase.User;
import model.ObjectsPackage.People.Person;
import model.ObjectsPackage.Weapons.Weapon;

public abstract class Soldier extends Person {
    private Weapon weapon;
    private SoldierName type;

    public Soldier(Weapon weapon, SoldierName type,User user) {
        super(true, type.getLife(), type.getSpeed(),user);
    }

    public void patrol(int x1, int y1, int x2, int y2) {
        //TODO: fill here
    }

    public boolean canPlace(int x, int y) {
        //TODO: complete this when map is done
        return true;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public SoldierName getType() {
        return type;
    }
}
