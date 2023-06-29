package model.Save.MapSave;

import controller.UserDatabase.User;
import model.Map.Map;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class MapSaver {
    private final String path;
    private final User user;
    private final String name;

    public MapSaver(User user, String name, boolean isPublic) throws IOException {
        this.user = user;
        this.name = name;
        this.path = save(user.getGovernment().getMap(), isPublic);
    }

    public static boolean exists(User creator, String name) {
        try {
            String path = "Stronghold/src/main/resources/Database/Map/%s/%s.map".formatted(creator.getUserName(), name);
            return Files.exists(Path.of(path));
        } catch (Exception e) {
            return false;
        }
    }

    private String save(Map map, boolean isPublic) throws IOException {
        File saved = getFile();

        FileOutputStream fileOutputStream = new FileOutputStream(saved);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

        outputStream.writeObject(new AnonymousMap(map, isPublic));

        outputStream.close();
        fileOutputStream.close();

        return "Stronghold/src/main/resourcesDatabase/Map/%s/%s.map".formatted(user.getUserName(), name);
    }

    private File getFile() throws IOException {
        String base = "Stronghold/src/main/resources/Database/Map/";
        File userFolder = new File(base + user.getUserName());

        if (!userFolder.exists())
            userFolder.mkdirs();

        File saved = new File(userFolder.getPath() + "/" + name + ".map");

        if (!saved.exists())
            saved.createNewFile();

        return saved;
    }
}
