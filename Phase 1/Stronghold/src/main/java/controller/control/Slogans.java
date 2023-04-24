package Controllers.control;

public enum Slogans {

    NUMBER1("What is my father’s name?"),
    NUMBER2("What was my first pet’s name?"),
    NUMBER3("What is my mother’s last name?"),



    ;
    private String slogan;
    private Slogans(String slogan){
        this.slogan =slogan;
    }

    public String getSlogan() {
        return slogan;
    }
    public static String getSlogansByNumber(int number){
        Slogans[] arr= Slogans.values();
        return arr[number].getSlogan();
    }
    public static int getNumberSlogans(){
        return Slogans.values().length;
    }

}
