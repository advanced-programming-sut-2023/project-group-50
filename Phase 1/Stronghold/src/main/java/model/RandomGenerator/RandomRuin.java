package model.RandomGenerator;

public class RandomRuin {
    public static String getRandomRuin() {
        int randomNumber = RandomGenerator.getRandomNumber(0, 511);
        return "Tiles/tile_land_and_stones.gm1/collection" + randomNumber + ".png";
    }
}
