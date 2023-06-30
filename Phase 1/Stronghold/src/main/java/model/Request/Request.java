package model.Request;

import java.io.Serializable;
import java.util.HashSet;

public record Request(HashSet<String> users, String admin, int numberOfPlayers, String name) implements Serializable {
    public boolean isFull() {
        return users.size() == numberOfPlayers;
    }

    public void add(String string) {
        users.add(string);
    }

    public void start(String user) {
        //TODO: start a game
    }

    public boolean canStart(String userName) {
        return isFull() || (users.size() > 1 && userName.equals(admin));
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public void remove(String userName) {
        users.remove(userName);
    }
}
