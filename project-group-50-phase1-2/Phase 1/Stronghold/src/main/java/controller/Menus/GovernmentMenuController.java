package controller.Menus;

import controller.UserDatabase.User;
import controller.control.Commands;
import controller.control.Error;
import model.Government.Government;
import view.GovernmentMenu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class GovernmentMenuController {

    private GovernmentMenu governmentMenu;
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public GovernmentMenuController (GovernmentMenu governmentMenu){
        this.governmentMenu=governmentMenu;
    }

    public String showPopularityFactor(){
        return "Food"+this.currentUser.getGovernment ().checkPopularityFood ()+
                "\nTax"+this.currentUser.getGovernment ().checkPopularityTax ()+
                "\nReligion"+this.currentUser.getGovernment ().checkReligionPopularity ()+
                "\nFear"+this.currentUser.getGovernment ().checkFearPopularity ();
    }
    public String showPopularity(){
        return "Your popularity is "+this.currentUser.getGovernment ().getPopularity ()+"%";
    }

    public String showFoodList(){
        HashMap<String,Double> hashMap= this.currentUser.getGovernment ().getFoods ();
        String string="";
        if(hashMap.containsKey ("bread")){
            string=string+"Number of bread : "+hashMap.get ("bread")+"\n";
        }else {
            string=string+"Number of bread : "+0+"\n";
        }
        if(hashMap.containsKey ("meat")){
            string=string+"Number of meat : "+hashMap.get ("meat")+"\n";
        }else {
            string=string+"Number of meat : "+0+"\n";
        }
        if(hashMap.containsKey ("apple")){
            string=string+"Number of apple : "+hashMap.get ("apple")+"\n";
        }else {
            string=string+"Number of apple : "+0+"\n";
        }
        if(hashMap.containsKey ("cheese")){
            string=string+"Number of cheese : "+hashMap.get ("cheese")+"\n";
        }else {
            string=string+"Number of cheese : "+0+"\n";
        }

        return string;

    }

    public String setRateFood(Matcher matcher){
        matcher.find ();
        String input=matcher.group ();

        Error error=LoginController.checkHasField (input, Commands.RATE);

        if(!error.truth){
            return error.errorMassage;
        }
        int rate=Integer.parseInt (error.errorMassage);

        if(rate>2 || rate<-2){
            return "Enter a valid rate food number";
        }

        double value= (double) ((rate+2)*0.5);
        if(value*this.currentUser.getGovernment ().getPopulation ()>this.currentUser.getGovernment ().getFoodNumber ()){
            return "You can't feed people with this rate because you haven't food enough";
        }
        this.currentUser.getGovernment ().setRateFood (rate);
        this.currentUser.getGovernment ().setPreviousRateFood (rate);
        return "You set rate food number successfully";
    }
    public String showRateFoodNumber(){
        return "Your rate food number is "+this.currentUser.getGovernment ().getRateFood ();
    }

    public String setTaxRate(Matcher matcher){
        matcher.find ();
        String input=matcher.group ();

        Error error=LoginController.checkHasField (input, Commands.RATE);

        if(!error.truth){
            return error.errorMassage;
        }
        int rate=Integer.parseInt (error.errorMassage);

        if(rate>8 || rate<-3){
            return "Enter a valid rate tax number";
        }

        double value= Government.getTaxValueByRate (rate);

        if(value<0){
            if(-1*value*this.currentUser.getGovernment ().getPopulation ()>this.currentUser.getGovernment ().getCoins ()){
                return "You can't give gold people with this rate because you haven't gold enough";
            }
        }
        this.currentUser.getGovernment ().setTaxRate (rate);
        this.currentUser.getGovernment ().setPreviousRateTax (rate);
        return "You set rate tax number successfully";


    }
    public String showRateTaxNumber(){
        return "Your rate tax number is "+this.currentUser.getGovernment ().getTaxRate ();
    }

    public String setFearRate(Matcher matcher){
        matcher.find ();
        String input=matcher.group ();

        Error error=LoginController.checkHasField (input, Commands.RATE);

        if(!error.truth){
            return error.errorMassage;
        }
        int rate=Integer.parseInt (error.errorMassage);

        if(rate>5 || rate<-5){
            return "Enter a valid rate fear number";
        }
        this.currentUser.getGovernment ().setFearRate ( rate );
        return "You set rate fear number successfully";

    }
}
