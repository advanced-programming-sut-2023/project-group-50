package model.Item;

import java.io.Serializable;

public class Item implements Serializable {
    private final int price;
    private final String name;
    private final int count;

    public Item(int price, String name, int count) {
        this.price = price;
        this.name = name;
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Item{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }

    public String getName() {
        return name;
    }
}
