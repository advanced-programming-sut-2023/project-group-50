package model.ObjectsPackage.Weapons;

import controller.UserDatabase.User;
import javafx.scene.image.Image;
import model.ObjectsPackage.ObjectType;
import model.ObjectsPackage.Objects;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.Save.MapSave.AnonymousObject;

public class Weapon extends Objects {
    private final WeaponName weaponName;
    private final Soldier Owner;


    public Weapon(WeaponName weaponName, Soldier soldier, User owner) {
        super(ObjectType.WEAPON, owner);
        this.weaponName = weaponName;
        Owner = soldier;
    }

    private void hit(Objects object) {
        object.applyDamage(Owner);
    }

    public WeaponName getWeaponName() {
        return weaponName;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public AnonymousObject getAnonymous() {
        return new AnonymousObject(ObjectType.WEAPON, weaponName);
    }
}
