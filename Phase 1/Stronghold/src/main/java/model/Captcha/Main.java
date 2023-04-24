package model.Captcha;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Captcha captcha = new Captcha();
        System.out.println(captcha.getCaptcha());
        String ans;
        do {
            System.out.print("insert captcha answer: ");
            ans = scanner.nextLine();
        } while (!captcha.answerIsCorrect(ans));
    }
}
