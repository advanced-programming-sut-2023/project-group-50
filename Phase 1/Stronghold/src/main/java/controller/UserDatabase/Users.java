package controller.UserDatabase;

import java.util.HashMap;
import java.util.Map;

public class Users {
    private static HashMap<String, User> users;

    public Users() {
        Users.users = new HashMap<>();
    }

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


}
