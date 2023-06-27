package model.ObjectsPackage.People.NonSoldier;

import controller.UserDatabase.User;
import model.ObjectsPackage.People.Person;

public class NonSoldier extends Person {
    private final Job job;
    private int productivity;

    protected NonSoldier(Job job, User user) {
        super(false, 0, 0,user); //TODO: check life and speed
        this.job = job;
        productivity = 100;
    }

    public int getProductivity() {
        return productivity;
    }

    public void setProductivity(int productivity) {
        this.productivity = productivity;
    }

    public Job getJob() {
        return job;
    }
}
