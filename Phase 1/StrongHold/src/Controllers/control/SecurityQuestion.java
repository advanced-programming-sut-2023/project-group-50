package Controllers.control;

public enum SecurityQuestion {

    NUMBER1("What is my father’s name?"),
    NUMBER2("What was my first pet’s name?"),
    NUMBER3("What is my mother’s last name?"),



        ;
    private String question;
    private SecurityQuestion(String question){
        this.question=question;
    }

    public String getQuestion() {
        return question;
    }
    public static SecurityQuestion getByNumber(int number){
        SecurityQuestion[] arr= SecurityQuestion.values();
        return arr[number-1];
    }
    public static int getNumber(){
        return  SecurityQuestion.values().length;
    }

}
