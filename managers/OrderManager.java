package com.scs.managers;

import com.scs.models.Inventory;
import com.scs.models.Item;
import com.scs.models.Order;

import java.util.List;

public class OrderManager {

    private Inventory inventory = new Inventory();

    public OrderManager() {

    }

    public void updateInventory(List<Item> items) {
        Order order = new Order(items, totalRevenue(items));
        inventory.saveOrder(order);
        inventory.updateWithOrder(items);
    }

    // Calculate total revenue of an order
    private Float totalRevenue(List<Item> items) {
        Float total = 0.0f;
        Item temp;
        for (int i = 0; i < items.size(); i++) {
            temp = items.get(i);
            total += temp.getPrice();
        }

        return total;
    }
}
