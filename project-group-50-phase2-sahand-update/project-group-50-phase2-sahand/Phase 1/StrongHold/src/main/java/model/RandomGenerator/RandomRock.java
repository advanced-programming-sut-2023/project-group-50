package model.RandomGenerator;

public class RandomRock {
    public static String getRandomRock() {
        int randomNumber = RandomGenerator.getRandomNumber(64, 127);
        return "Tiles/tile_rocks8.gm1/collection" + randomNumber + ".png";
    }
}
