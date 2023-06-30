package model.ObjectsPackage.People.NonSoldier;

import controller.UserDatabase.User;
import model.ObjectsPackage.Buildings.Building;
import model.ObjectsPackage.People.Person;

public class NonSoldier extends Person {
    private final Job job;
    private final Building placeOfWork;
    private int productivity;

    public NonSoldier(Job job, User owner, Building placeOfWork) {
        super(false, 20, 30, owner);
        this.job = job;
        productivity = 100;
        this.placeOfWork = placeOfWork;
        this.setX(placeOfWork.getX());
        this.setY(placeOfWork.getY());
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
