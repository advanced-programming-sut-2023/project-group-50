package model.Request;

import java.io.Serializable;
import java.util.HashSet;

public record Request(HashSet<String> users, String admin, int numberOfPlayers) implements Serializable {
    public boolean isFull() {
        return users.size() == numberOfPlayers;
    }

    public void add(String string) {
        users.add(string);
    }

    public boolean start(String user) {
        //TODO: start a game
        return isFull() || (users.size() > 1 && user.equals(admin));
    }
}
