package controller.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Splitter {
    public static void split(String path, int num, String key) throws IOException {
        File file = new File(path);

        assert file.exists();

        ArrayList<File>[] by = new ArrayList[num];

        for (int i = 0; i < num; i++)
            by[i] = new ArrayList<File>();

        for (File image : Objects.requireNonNull(file.listFiles())) {
            Pattern pattern = Pattern.compile(key);
            Matcher matcher = pattern.matcher(image.getName());
            matcher.find();
            int number = Integer.parseInt(matcher.group("number"));

            by[(number - 1) % num].add(image);
        }

        for (int i = 0; i < num; i++) {
            File folder = new File(path + "\\" + i);
            if (!folder.exists()) folder.mkdirs();
            Collections.sort(by[i]);
            for (int j = 0; j < by[i].size(); j++) {
                File dest = new File(path + "\\" + i + "\\" + j + ".png");
                Files.copy(by[i].get(j).toPath(), dest.toPath());
            }
        }


        for (int i = 0; i < num; i++)
            System.out.println(by[i]);
    }

    public static void main(String[] args) throws IOException {
        String path = "D:\\Uni\\Semester2\\AP\\Stronghold\\Stronghold\\Phase 1\\Stronghold\\src\\main\\resources\\images\\Soldiers\\Tunneler\\Move";
        int number = 8;
        String regex = "move \\((?<number>\\d+)\\).png";
        split(path, number, regex);
    }
}
