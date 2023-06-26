package model.Save.MapSave;

import controller.UserDatabase.User;
import model.Map.Map;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class MapLoader {
    public static Map getMap(User user, String name) throws Exception {
        return getMap("Stronghold/src/main/resources/Database/Map/%s/%s.map".formatted(user.getUserName(), name));
    }

    private static Map getMap(String path) throws Exception {
        assert exists(path);

        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        return (Map) objectInputStream.readObject();
    }

    private static boolean exists(String path) {
        try {
            return new File(path).exists();
        } catch (Exception e) {
            return false;
        }
    }

    public static ArrayList<String> getMaps(User user) {
        String base = "Stronghold/src/main/resources/Database/Map/";
        File userFolder = new File(base + user.getUserName());

        if (!userFolder.exists())
            return new ArrayList<>();

        return Arrays.stream(Objects.requireNonNull(userFolder.listFiles())).map(file -> {
            return file.getName().substring(0, file.getName().indexOf(".map"));
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public static String getDate(User user, String name) throws Exception {
        String base = "Stronghold/src/main/resources/Database/Map/%s/%s.map".formatted(user.getUserName(), name);
        Path path = Path.of(base);

        FileTime lastModifiedTime = Files.getLastModifiedTime(path);
        return lastModifiedTime.toString();
    }
}
