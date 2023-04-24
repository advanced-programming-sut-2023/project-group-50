package model.ObjectsPackage.Weapons;

import model.ObjectsPackage.Material;

public enum WeaponName {
    ;
    private Material material;
    private int damage;
    private int range;
    private boolean canMove;

    private WeaponName(Material material, int damage, int range, boolean canMove) {
        this.material = material;
        this.damage = damage;
        this.range = range;
        this.canMove = canMove;
    }

    public Material getMaterial() {
        return material;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public boolean isCanMove() {
        return canMove;
    }
}
