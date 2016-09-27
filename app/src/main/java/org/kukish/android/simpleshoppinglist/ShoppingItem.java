package org.kukish.android.simpleshoppinglist;

/**
 * Created by Sq on 9/27/2016.
 */

public class ShoppingItem {
    private String name;
    private String description;
    private String quantity;

    public ShoppingItem(String name) {
        this.name = name;
    }

    public ShoppingItem(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public ShoppingItem(String name, String description, String quantity) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return name + ": " + quantity;
    }
}
