package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;
import model.Map.Unit;
import model.ObjectsPackage.People.Person;
import view.show.Animation.HealAnimation;

public class Apothecary extends Building {
    protected Apothecary(BuildingType type, User owner, int x, int y, int maxHp) {
        super(type, owner, x, y, maxHp);
    }

    public void Heal(Person person) {
        person.heal();
    }

    public void HealPlace(Unit unit) {
        unit.setDisease(false);
        HealAnimation healAnimation = new HealAnimation(unit);
        unit.setHealAnimation(healAnimation);
        healAnimation.play();
    }
}
