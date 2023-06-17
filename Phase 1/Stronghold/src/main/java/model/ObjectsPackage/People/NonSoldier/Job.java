package model.ObjectsPackage.People.NonSoldier;

import model.ObjectsPackage.People.Soldier.SoldierName;
import model.RandomGenerator.RandomGenerator;

public enum Job {
    LADY,
    JESTER,
    WOODCUTTER,
    HUNTER,
    FARMER,
    PEASANT,
    CHILD,
    MOTHER_AND_BABY,
    STONE_MASON,
    IRON_MINER,
    PITCH_DIGGER,
    MILL_BOY,
    BAKER,
    BREWER,
    INNKEEPER,
    DRUNKARD,
    FLETCHER,
    ARMORER,
    BLACKSMITH,
    POLETURNER,
    TANNER,
    PRIEST,
    HEALER,
    MARKER_TRADER,
    JUGGLER,
    FIRE_EATER;

    public static Job randomHousePerson() {
        return RandomGenerator.randomFrom(
                JESTER,
                PEASANT,
                CHILD,
                MOTHER_AND_BABY,
                DRUNKARD,
                JUGGLER,
                FIRE_EATER
        );
    }

    public String getType() {
        return SoldierName.getName(this.name());
    }
}
