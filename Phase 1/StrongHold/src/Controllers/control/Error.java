package Controllers.control;

public class Error {
    public boolean truth;
    public String errorMassage;

    public Error(String errorMassage,boolean truth){
        this.truth=truth;
        this.errorMassage=errorMassage;
    }
}
