package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;
import model.ObjectsPackage.People.Person;

public class Apothecary extends Building {
    protected Apothecary(BuildingType type, User owner, int x, int y, int maxHp) {
        super(type, owner, x, y, maxHp);
    }

    public void Heal(Person person) {
        person.heal();
    }
}
