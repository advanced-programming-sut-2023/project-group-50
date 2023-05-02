package model.ObjectsPackage.People.NonSoldier;

import model.ObjectsPackage.People.Person;

public class NonSoldier extends Person {
    private final Job job;
    private int productivity;

    protected NonSoldier(Job job) {
        super(false, 20, 30);
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
