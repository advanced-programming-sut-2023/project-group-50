package model.RandomGenerator;

import java.util.Random;

public class RandomGenerator {
    private static final Random rand = new Random();

    public static <T> T randomFrom(T... items) {
        return items[rand.nextInt(items.length)];
    }

    /**
     * generates random number in [low, high]
     **/
    public static int getRandomNumber(int low, int high) {
        return rand.nextInt(high - low + 1) + low;
    }

    public static double getRandomIntersection(double lo, double hi) {
        return rand.nextDouble(lo, hi - lo);
    }
}
