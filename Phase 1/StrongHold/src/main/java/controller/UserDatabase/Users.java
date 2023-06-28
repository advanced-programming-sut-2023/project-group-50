package controller.UserDatabase;

import controller.control.SecurityQuestion;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Users implements Serializable {
    private static HashMap<String, User> users = new HashMap<>();

    public static User getUser(String username) {
        if (Users.users.isEmpty()) {
            return null;
        }
        if (Users.users.containsKey(username)) {
            return Users.users.get(username);
        }
        return null;
    }

    public static void addUser(User user) {
        Users.users.put(user.getUserName(), user);
    }

    public static boolean checkRepetitiousEmail(String email) {
        for (Map.Entry<String, User> userEntry : Users.users.entrySet()) {
            if (userEntry.getValue().getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public static HashMap<String, User> getUsers() {
        return users;
    }

    public static void setUsers(HashMap<String, User> users) {
        Users.users = users;
    }

    public static void updateUsers() {
        User[] table = users.values().toArray(new User[0]);

        updateAllUsers();
        Arrays.sort(table);

        int i = 1;
        for (User user : table) user.setRank(i++);
    }

    private static void updateAllUsers() {
        for (User user : users.values()) user.updateScore();
    }

    public static boolean usernameExists(String name) {
        return getUser(name) != null;
    }

    public static void removeUser(User user) {
        users.remove(user.getUserName());
    }

    public static void setSecurityQuestion(String username, int choice, String answer) {
        users.get(username).setSecurityQuestion(SecurityQuestion.values()[choice]);
        users.get(username).setSecurityQuestionAnswer(answer);
    }
}
