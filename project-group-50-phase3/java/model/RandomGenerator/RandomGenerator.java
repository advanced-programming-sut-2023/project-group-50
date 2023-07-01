package model.RandomGenerator;

import model.Request.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
        return ThreadLocalRandom.current().nextDouble(lo, hi);
    }

    public static ArrayList<Request> tenRandomsFrom(ArrayList<Request> requests) {
        Collections.shuffle(requests);
        if (requests.size() > 10)
            requests = (ArrayList<Request>) requests.subList(0, 10);
        return requests;
    }
}
