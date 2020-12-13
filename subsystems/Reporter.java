package com.scs.subsystems;

import com.scs.models.Item;
import com.scs.models.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reporter {

    public Reporter() {
    }

    // Total number of sold items and total revenue
    public String printRevenue(List<Order> orders) {
        Map<String, Integer> soldItems = new HashMap<>();
        Float totalRevenue = 0.0f;

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            List<Item> items = order.getItems();
            for (int j = 0; j < items.size(); j++) {
                String name = items.get(j).getName();
                if (soldItems.containsKey(name)) {
                    soldItems.put(name, soldItems.get(name) + 1);
                } else {
                    soldItems.put(name, 1);
                }
            }

            totalRevenue += order.getTotal();
        }

        String reportText = "Sold Items:\n";
        for (Map.Entry<String, Integer> entry : soldItems.entrySet()) {
            reportText += String.format("%s - Total(%d)\n", entry.getKey(), entry.getValue());
        }
        reportText += String.format("Total Revenue: $%f\n", totalRevenue);

        return reportText;
    }

    // Items' quantity are below inventory level
    public String printInventory(List<Item> inventory) {
        String reportText = "";

        for (Item item : inventory) {
            if (item.getQuantity() < item.getInventoryLevel()) {
                reportText += String.format("%s - Quantity(%d) - InventoryLevel(%d)\n", item.getName(), item.getQuantity(), item.getInventoryLevel());
            }
        }

        return reportText;
    }
}
