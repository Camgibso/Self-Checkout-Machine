package com.scs.models;

import java.util.List;

public class Order {
    private List<Item> items;
    private Float total;

    public Order(List<Item> items, Float total) {
        this.items = items;
        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
