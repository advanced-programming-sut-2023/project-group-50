package model.ObjectsPackage.Buildings;

import controller.UserDatabase.Shop;
import controller.UserDatabase.User;

public class Market extends Building {
    protected Market(BuildingType type, User owner, int x, int y, int maxHp) {
        super(type, owner, x, y, maxHp);
        if (owner.getGovernment().getShop() == null) {
            owner.getGovernment().setShop(new Shop());
        }

    }
}
