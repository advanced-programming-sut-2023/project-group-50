package controller.control;

import java.io.Serializable;

public enum SecurityQuestion implements Serializable {
    NUMBER1("What is my father’s name?"),
    NUMBER2("What was my first pet’s name?"),
    NUMBER3("What is my mother’s last name?"),

    ;
    private final String question;

    SecurityQuestion(String question) {
        this.question = question;
    }

    public static SecurityQuestion getByNumber(int number) {
        SecurityQuestion[] arr = SecurityQuestion.values();
        return arr[number - 1];
    }

    public static int getNumber() {
        return SecurityQuestion.values().length;
    }

    public String getQuestion() {
        return question;
    }

}
