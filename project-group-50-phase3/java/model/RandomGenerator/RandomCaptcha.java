package model.RandomGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

public class RandomCaptcha {

    private static final ArrayList<Integer> numberCaptcha = new ArrayList<>(Arrays.asList(1181, 1381, 1491, 1722, 1959, 2163, 2177, 2723, 2785, 3541, 3847, 3855, 3876, 3967, 4185, 4310, 4487,
                                                                                          4578, 4602, 4681, 4924, 5326, 5463, 5771, 5849, 6426, 6553, 6601, 6733, 6960, 7415, 7609, 7755, 7825, 7905, 8003, 8070, 8368, 8455, 8506, 8555, 8583, 8692, 8776, 8972, 8996, 9061, 9386, 9582, 9633));

    static {
        //readAllFile ();

    }

    public static void readAllFile() {
        File folder = new File(RandomCaptcha.class.getResource("/Captcha/6733.png").toExternalForm()).getParentFile();

        assert folder.exists();
        System.out.println(folder.list());

        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (Pattern.compile("\\d+\\.png").matcher(fileEntry.getName()).find()) {
                RandomCaptcha.numberCaptcha.add(Integer.valueOf(fileEntry.getName().substring(0,
                                                                                              fileEntry.getName().length() -
                                                                                                      4)));
            }
        }
    }

    public static Integer getCaptcha() {
        int i = new Random().nextInt(RandomCaptcha.numberCaptcha.size() - 1);
        return RandomCaptcha.numberCaptcha.get(i);
    }
}
