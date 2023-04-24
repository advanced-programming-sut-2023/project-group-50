package model.ObjectsPackage.People.NonSoldier;

import model.ObjectsPackage.People.Person;

public class NonSoldier extends Person {
    private Job job;
    private int productivity;

    protected NonSoldier(Job job) {
        super(false, 0, 0); //TODO: check life and speed
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
