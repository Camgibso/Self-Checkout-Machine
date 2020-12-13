package com.scs.models;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Product
public class Item {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleFloatProperty price;
    private SimpleFloatProperty discount;
    private SimpleIntegerProperty quantity;
    private SimpleIntegerProperty inventoryLevel;

    public Item() {
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.price = new SimpleFloatProperty();
        this.discount = new SimpleFloatProperty();
        this.quantity = new SimpleIntegerProperty();
        this.inventoryLevel = new SimpleIntegerProperty();
    }

    public Item(String name, String description, Float price, Float discount, Integer quantity, Integer inventoryLevel) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleFloatProperty(price);
        this.discount = new SimpleFloatProperty(discount);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.inventoryLevel = new SimpleIntegerProperty(inventoryLevel);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public float getPrice() {
        return price.get();
    }

    public void setPrice(float price) {
        this.price.set(price);
    }

    public float getDiscount() {
        return discount.get();
    }

    public void setDiscount(float discount) {
        this.discount.set(discount);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public int getInventoryLevel() {
        return inventoryLevel.get();
    }

    public void setInventoryLevel(int inventoryLevel) {
        this.inventoryLevel.set(inventoryLevel);
    }
}
