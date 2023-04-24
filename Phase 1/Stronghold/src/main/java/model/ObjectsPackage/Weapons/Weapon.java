package model.ObjectsPackage.Weapons;

import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.Soldier.Soldier;

public class Weapon extends Objects {
    private final WeaponName weaponName;
    private final Soldier Owner;


    public Weapon(WeaponName weaponName, Soldier owner) {
        super(ObjectType.WEAPON);
        this.weaponName = weaponName;
        Owner = owner;
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
