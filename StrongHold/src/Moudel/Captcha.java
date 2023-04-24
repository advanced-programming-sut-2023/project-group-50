package Moudel;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Captcha {

    private static HashMap<String, List<String>> map;

    static {
        map = new HashMap<>();
        map.put("a", List.of(
                " █████ ",
                "██   ██",
                "███████",
                "██   ██",
                "██   ██"
        ));
        map.put("b", List.of(
                "██████ ",
                "██   ██",
                "██████ ",
                "██   ██",
                "██████ "
        ));
        map.put("c", List.of(
                " ██████",
                "██     ",
                "██     ",
                "██     ",
                " ██████"
        ));
        map.put("d", List.of(
                "██████ ",
                "██   ██",
                "██   ██",
                "██   ██",
                "██████ "
        ));
        map.put("e", List.of(
                "███████",
                "██     ",
                "█████  ",
                "██     ",
                "███████"
        ));
        map.put("f", List.of(
                "███████",
                "██     ",
                "█████  ",
                "██     ",
                "██     "
        ));
        map.put("g", List.of(
                " ██████ ",
                "██      ",
                "██   ███",
                "██    ██",
                " ██████ "
        ));
        map.put("h", List.of(
                "██   ██",
                "██   ██",
                "███████",
                "██   ██",
                "██   ██"
        ));
        map.put("i", List.of(
                "██",
                "██",
                "██",
                "██",
                "██"
        ));
        map.put("j", List.of(
                "     ██",
                "     ██",
                "     ██",
                "██   ██",
                " █████ "
        ));
        map.put("k", List.of(
                "██   ██",
                "██  ██ ",
                "█████  ",
                "██  ██ ",
                "██   ██"
        ));
        map.put("l", List.of(
                "██     ",
                "██     ",
                "██     ",
                "██     ",
                "███████"
        ));
        map.put("m", List.of(
                "███    ███",
                "████  ████",
                "██ ████ ██",
                "██  ██  ██",
                "██      ██"
        ));
        map.put("n", List.of(
                "███    ██",
                "████   ██",
                "██ ██  ██",
                "██  ██ ██",
                "██   ████"
        ));
        map.put("o", List.of(
                " ██████ ",
                "██    ██",
                "██    ██",
                "██    ██",
                " ██████ "
        ));
        map.put("p", List.of(
                "██████ ",
                "██   ██",
                "██████ ",
                "██     ",
                "██     "
        ));
        map.put("q", List.of(
                " ██████ ",
                "██    ██",
                "██    ██",
                "██ ▄▄ ██",
                " ██████ ",
                "    ▀▀  "
        ));
        map.put("r", List.of(
                "██████ ",
                "██   ██",
                "██████ ",
                "██   ██",
                "██   ██"
        ));
        map.put("s", List.of(
                "███████",
                "██     ",
                "███████",
                "     ██",
                "███████"
        ));
        map.put("t", List.of(
                "████████",
                "   ██   ",
                "   ██   ",
                "   ██   ",
                "   ██   "
        ));
        map.put("u", List.of(
                "██    ██",
                "██    ██",
                "██    ██",
                "██    ██",
                " ██████ "
        ));
        map.put("v", List.of(
                "██    ██",
                "██    ██",
                "██    ██",
                " ██  ██ ",
                "  ████  "
        ));
        map.put("w", List.of(
                "██     ██",
                "██     ██",
                "██  █  ██",
                "██ ███ ██",
                " ███ ███ "
        ));
        map.put("x", List.of(
                "██   ██",
                " ██ ██ ",
                "  ███  ",
                " ██ ██ ",
                "██   ██"
        ));
        map.put("y", List.of(
                "██    ██",
                " ██  ██ ",
                "  ████  ",
                "   ██   ",
                "   ██   "
        ));
        map.put("z", List.of(
                "███████",
                "   ███ ",
                "  ███  ",
                " ███   ",
                "███████"
        ));
        map.put("0", List.of(
                " ██████ ",
                "██  ████",
                "██ ██ ██",
                "████  ██",
                " ██████ "
        ));
        map.put("1", List.of(
                " ██",
                "███",
                " ██",
                " ██",
                " ██"
        ));
        map.put("2", List.of(
                "██████ ",
                "     ██",
                " █████ ",
                "██     ",
                "███████"
        ));
        map.put("3", List.of(
                "██████ ",
                "     ██",
                " █████ ",
                "     ██",
                "██████ "
        ));
        map.put("4", List.of(
                "██   ██",
                "██   ██",
                "███████",
                "     ██",
                "     ██"
        ));
        map.put("5", List.of(
                "███████",
                "██     ",
                "███████",
                "     ██",
                "███████"
        ));
        map.put("6", List.of(
                " ██████ ",
                "██      ",
                "███████ ",
                "██    ██",
                " ██████ "
        ));
        map.put("7", List.of(
                "███████",
                "     ██",
                "    ██ ",
                "   ██  ",
                "   ██  "
        ));
        map.put("8", List.of(
                " █████ ",
                "██   ██",
                " █████ ",
                "██   ██",
                " █████ "
        ));
        map.put("9", List.of(
                " █████ ",
                "██   ██",
                " ██████",
                "     ██",
                " █████ "
        ));
    }

    private String answer;
    private String captcha;

    public Captcha() {
        int length = getRandomLength();
        answer = getRandomString(length);
        captcha = generateCaptcha();
    }

    private int getRandomLength() {
        return (int) (Math.random() * 3 + 4);
    }

    private String getRandomString(int length) {
        String CHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            salt.append(CHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private String getIthLine(int line) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : answer.toCharArray())stringBuilder.append(getIthLineOfChar(c, line)).append(" ");
        return stringBuilder.toString();
    }

    private String getIthLineOfChar(char c, int i) {
        return map.get(Character.toString(c)).get(i);
    }

    private String generateCaptcha() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) stringBuilder.append(getIthLine(i)).append('\n');
        return stringBuilder.toString();
    }

    public String getCaptcha() {
        return captcha;
    }

    public boolean answerIsCorrect(String answer) {
        return this.answer.equals(answer.toLowerCase());
    }


}

