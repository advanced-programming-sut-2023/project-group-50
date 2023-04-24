package controller.control;

public enum Slogans {
    NUMBER1("Let’s hunt!"),
    NUMBER2("Duty before death"),
    NUMBER3("Always vigilant"),
    NUMBER4("If you wish for peace, prepare for war"),
    NUMBER5("As far as the world extends"),
    NUMBER6("On the Sea We Are Glorious"),
    NUMBER7("Nothing Stands In Our Way")
    ;
    private final String slogan;

    Slogans(String slogan) {
        this.slogan = slogan;
    }

    public static String getSlogansByNumber(int number) {
        Slogans[] arr = Slogans.values();
        return arr[number].getSlogan();
    }

    public static int getNumberSlogans() {
        return Slogans.values().length;
    }

    public String getSlogan() {
        return slogan;
    }

}
