package controller.UserDatabase;

import controller.control.SecurityQuestion;
import model.Request.Request;
import model.Save.MapSave.AnonymousMap;

import java.io.Serializable;
import java.util.*;

public class Users implements Serializable {
    private static HashMap<String, User> users = new HashMap<>();
    private static HashMap<String, HashSet<AnonymousMap>> maps = new HashMap<>();
    private static HashSet<Request> requests = new HashSet<>();

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

    public static ArrayList<String> getUsersAsString() {
        return new ArrayList<>(users.keySet());
    }

    public static HashMap<String, HashSet<AnonymousMap>> getMaps() {
        return maps;
    }

    public static void setMaps(HashMap<String, HashSet<AnonymousMap>> maps) {
        Users.maps = maps;
    }

    public static ArrayList<AnonymousMap> getAllMaps(User user) {
        ArrayList<AnonymousMap> out = new ArrayList<>();

        for (String creator : maps.keySet())
            for (AnonymousMap anonymousMap : maps.get(creator))
                if (creator.equals(user.getUserName())) out.add(anonymousMap);
                else if (anonymousMap.isPublic()) out.add(anonymousMap);

        return out;
    }

    public static boolean mapNameExists(User user, String name) {
        maps.putIfAbsent(user.getUserName(), new HashSet<>());
        return maps.get(user.getUserName()).stream()
                .anyMatch(anonymousMap -> anonymousMap.getName().equals(name));
    }

    public static boolean mapNameExists(String name) {
        return maps.keySet().stream().flatMap(string -> maps.get(string).stream())
                .anyMatch(anonymousMap -> anonymousMap.getName().equals(name));
    }

    public static void saveMap(User user, String name, boolean isPublic) {
        maps.putIfAbsent(user.getUserName(), new HashSet<>());
        maps.get(user.getUserName()).add(new AnonymousMap(user.getGovernment().getMap(),
                                                          isPublic,
                                                          name,
                                                          user.getUserName()));
    }

    public static String getMapOwner(String mapName) {
        return maps.keySet().stream().filter(owner -> maps.get(owner).stream()
                .anyMatch(map -> map.getName
                        ().equals(mapName))).findFirst().orElse(null);

    }

    public static HashSet<Request> getRequests() {
        return requests;
    }

    public static void setRequests(HashSet<Request> requests) {
        Users.requests = requests;
    }

    public static void addRequest(Request request) {
        requests.add(request);
    }
}
