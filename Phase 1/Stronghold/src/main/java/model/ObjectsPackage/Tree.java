package model.ObjectsPackage;

public class Tree extends Objects {
    private String type;

    protected Tree(String type) {
        super(ObjectType.TREE);
        this.type = type;
    }

    public boolean canPlace(int x, int y) {
        //TODO: complete this when map is done
        return true;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
