package model.ObjectsPackage.People.Soldier;

import model.Map.Unit;
import model.ObjectsPackage.Buildings.Gate;

import java.io.Serializable;

public class NextTurnData implements Serializable {
    private int toX;
    private int toY;
    private boolean isMoving;
    private Unit toAttack;
    private Gate gate;

    public NextTurnData(int toX, int toY, boolean isMoving, Unit toAttack, Gate gate) {
        this.toX = toX;
        this.toY = toY;
        this.isMoving = isMoving;
        this.toAttack = toAttack;
        this.gate = gate;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public void setMoveTo(int toX, int toY) {
        isMoving = true;
        this.toX = toX;
        this.toY = toY;
    }

    public void stopMoving() {
        isMoving = false;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public Unit getToAttack() {
        return toAttack;
    }

    public void setToAttack(Unit toAttack) {
        this.toAttack = toAttack;
    }

    public boolean usesTunnel(Tunneler tunneler) {
        return tunneler.getOwner().getGovernment().getMap().getXY(toX, toY).hasTunnel();
    }
}
