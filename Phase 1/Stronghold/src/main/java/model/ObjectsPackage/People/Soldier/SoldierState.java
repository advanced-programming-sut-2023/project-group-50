package model.ObjectsPackage.People.Soldier;

public enum SoldierState {
    OFFENSIVE,
    DEFENSIVE,
    STANDING;

    public static SoldierState get(String s) {
        for(SoldierState state : SoldierState.values())
            if(state.name().toLowerCase().equals(s))
                return state;

        return null;
    }
}
