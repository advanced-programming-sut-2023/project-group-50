package model;

import controller.Menus.GameMenuController;
import controller.UserDatabase.User;

import java.util.ArrayList;

public class Game {
    private final ArrayList<User> players;
    private final int id;

    public Game(ArrayList<User> players, int id) {
        this.players = players;
        this.id = id;
    }

    public String play(GameMenuController gameMenuController, User player, int id) {
        //TODO: fill here
        return null;
    }
}
