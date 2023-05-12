package model;

import controller.UserDatabase.User;
import model.Government.Government;
import model.Map.Map;
import model.ObjectsPackage.People.Soldier.Soldier;
import model.UserColor.UserColor;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final ArrayList<User> players;
    private final int id;
    private int currentTurnNumber;

    public Game(ArrayList<User> players, int id) {
        this.players = players;
        this.id = id;
        currentTurnNumber = 1;
    }

    public boolean endTurn() {
        updateGovernmentData();
        updateBuildings();
        updateSoldiers();
        boolean lordIsAlive = updateLord();
        if (!lordIsAlive) players.remove(0);
        return lordIsAlive;
    }

    public boolean gameIsFinished() {
        return players.size() == 1;
    }

    public User getWinner() {
        assert gameIsFinished();
        return players.get(0);
    }

    private boolean updateLord() {
        Soldier lord = getGovernment().getLord();
        if (!lord.isAlive()) return false;
        if (getGovernment().getLordsCastle().isDestroyed())
            getGovernment().moveLordToClosestPlace();
        return true;
    }

    public boolean isPlayersTurn(User player) {
        return getCurrentPlayer().equals(player);
    }

    public User getCurrentPlayer() {
        return players.get(0);
    }

    public boolean nextTurn() {
        boolean canStillPlay = endTurn();
        if (!canStillPlay)
            return false;
        players.add(players.remove(0));
        currentTurnNumber++;
        return true;
    }

    public int getCurrentTurnNumber() {
        return currentTurnNumber;
    }

    private void updateGovernmentData() {
        Government government = getGovernment();
        government.produceFoodAndResources();
        government.feedPeople();
        government.checkFearPopularity();
        government.getTaxPeople();
    }

    private Government getGovernment() {
        return getCurrentPlayer().getGovernment();
    }

    private void updateSoldiers() {
        Government government = getGovernment();
        government.defend();
        government.removeDeadSoldiers();
        government.patrolAll();
        government.fillUpEmptyEngineers();
        government.attackWeapons();
    }

    private void updateBuildings() {
        Government government = getGovernment();
        government.spreadFire();
        government.applyFireDamage();
        government.removeDestroyedBuildings();
    }

    public boolean canStart() {
        return !isOvercrowded() && !isUndercrowded();
    }

    public boolean isOvercrowded() {
        return players.size() > 8;
    }

    public boolean isUndercrowded() {
        return players.size() < 2;
    }

    public ArrayList<UserColor> getRemainingColors() {
        ArrayList<UserColor> userColors = new ArrayList<>(List.of(UserColor.values()));
        for (User user : players)
            userColors.remove(user.getColor());
        return userColors;
    }

    public Map getMap() {
        if (players.size() == 0) return null;
        return players.get(0).getGovernment().getMap();
    }
}
