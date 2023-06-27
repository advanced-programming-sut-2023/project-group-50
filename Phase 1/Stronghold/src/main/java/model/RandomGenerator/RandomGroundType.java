package model.RandomGenerator;

public class RandomGroundType {
    public static String getRandomGround() {
        return RandomGenerator.randomFrom("Tiles/Tiles/Plain1.jpg", "Tiles/Tiles/Plain2.jpg",
                                          "Tiles/Tiles/plaintest5.jpg", "Tiles/Tiles/plaintest7.jpg");
    }

    public static String getRandomRigged() {
        return RandomGenerator.randomFrom(
                "Tiles/tile_land_macros.gm1/collection85.png",
                "Tiles/tile_land_macros.gm1/collection86.png",
                "Tiles/tile_land_macros.gm1/collection87.png",
                "Tiles/tile_land_macros.gm1/collection95.png"
        );
    }

    public static String getRandomCliff() {
        int randomNumber = RandomGenerator.getRandomNumber(0, 30);
        return "Tiles/tile_land3.gm1/collection" + randomNumber + ".png";
    }

    public static String getRandomStone() {
        int randomNumber = RandomGenerator.getRandomNumber(32, 47);
        return "Tiles/tile_land3.gm1/collection" + randomNumber + ".png";
    }

    public static String getRandomPlain() {
        int randomNumber = RandomGenerator.getRandomNumber(147, 170);
        return "Tiles/tile_land3.gm1/collection" + randomNumber + ".png";
    }

    public static String getRandomIron() {
        int randomNumber = RandomGenerator.getRandomNumber(230, 237);
        return "Tiles/tile_land3.gm1/collection" + randomNumber + ".png";
    }

    public static String getRandomGrassLawnMeadow() {
        int randomNumber = RandomGenerator.getRandomNumber(0, 35);
        return "Tiles/tile_land_macros.gm1/collection" + randomNumber + ".png";
    }

    public static String getRandomWater() {
        int randomNumber = RandomGenerator.getRandomNumber(0, 41);
        return "Tiles/tile_sea_new_01.gm1/collection" + randomNumber + ".png";
    }

    public static String getRandomBeach() {
        int randomNumber = RandomGenerator.getRandomNumber(42, 73);
        return "Tiles/tile_sea_new_01.gm1/collection" + randomNumber + ".png";
    }

    public static String getRandomOil() {
        return RandomGenerator.randomFrom(
                "Tiles/tile_burnt.gm1/collection53.png",
                "Tiles/tile_burnt.gm1/collection54.png",
                "Tiles/tile_burnt.gm1/collection61.png",
                "Tiles/tile_burnt.gm1/collection62.png"
        );
    }
}
