package controller.GUIControllers.SoldierMenuController;

import model.ObjectsPackage.People.Soldier.Archer;

public class ArcherMenuController extends SoldierMenuController {
    private final Archer archer;

    public ArcherMenuController(Archer soldier) {
        super(soldier);
        this.archer = soldier;
    }

    public boolean cannotAttack(int xTo, int yTo) {
        return archer.isInRange(xTo, yTo);
    }
}
