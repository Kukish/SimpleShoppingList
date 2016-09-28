package org.kukish.android.simpleshoppinglist;

/**
 * Created by Alejandro Awesome on 9/27/2016.
 */

public class ShoppingItem {
    private String name;
    private String description;
    private String quantity;
    private String uid;

    public ShoppingItem(String id, String name) {
        this.uid = id;
        this.name = name;
    }

    public ShoppingItem(String id, String name, String quantity) {
        this(id, name);
        this.quantity = quantity;
    }

    public ShoppingItem(String id, String name, String quantity, String description) {
        this(id, name, quantity);
        this.description = description;
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

    public String getUid() {
        return uid;
    }
}
