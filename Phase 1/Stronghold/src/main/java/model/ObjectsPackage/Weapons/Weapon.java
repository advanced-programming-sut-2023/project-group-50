package model.ObjectsPackage.Weapons;

import controller.UserDatabase.User;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.Soldier.Soldier;

public class Weapon extends Objects {
    private final WeaponName weaponName;
    private final Soldier Owner;


    public Weapon(WeaponName weaponName, Soldier soldier, User owner) {
        super(ObjectType.WEAPON, owner);
        this.weaponName = weaponName;
        Owner = soldier;
    }

    private void hit() {
        //TODO: fill here
    }

    private void move(int x, int y) {
        //TODO: fill here
    }

    public WeaponName getWeaponName() {
        return weaponName;
    }

    public Soldier getOwner() {
        return Owner;
    }
}
