package model.ObjectsPackage.Buildings;

import controller.UserDatabase.User;
import model.Government.Government;
import model.ObjectsPackage.Resource;

public class Mine extends Building {
    private final int rate;

    protected Mine(BuildingType type, User owner, int x, int y, int maxHp, int rate) {
        super(type, owner, x, y, maxHp);
        this.rate = rate;
    }

    public void produce() {
        switch (getType()) {
            case IRON_MINE -> produce(Resource.IRON);
            case PITCH_RIG -> produce(Resource.PITCH);
            case QUARRY -> produce(Resource.STONE);
            case WOODCUTTER -> produce(Resource.WOOD);
            default -> throw new IllegalStateException("Unexpected value: " + getType());
        }
    }

    public void produce(Resource resource) {
        Government government = getOwner().getGovernment();
        government.setResourceAmount(resource, government.getResourceAmount(resource) + rate);
    }
}
