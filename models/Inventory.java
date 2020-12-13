package com.scs.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Inventory {
    public static List<Item> inventory;
    public static List<Order> orders;

    public static List<Receipt> receipts;

    public static List<Report> reports;

    public Inventory() {
        inventory = new ArrayList<>();
        orders = new ArrayList<>();
        receipts = new ArrayList<>();
        reports = new ArrayList<>();

        if (inventory.isEmpty()) {
            loadSamples();
        }
    }

    public List<Item> createOrder() {
        List<Item> items = new ArrayList<>();
        Order order = new Order(items, 0.0f);
        orders.add(order);

        return items;
    }

    public void removeEnd() {
        orders.remove(orders.size() - 1);
    }

    public void saveOrder(Order order) {
        orders.add(order);
    }

    //
    public void updateWithOrder(List<Item> order) {
        order.forEach(item -> {
//            System.out.println(item.getQuantity());
            findOneAndUpdate(item.getName());
        });
    }

    public void saveReceipt(Receipt receipt) {
        receipts.add(receipt);
    }

    public void saveReport(Report report) {
        reports.add(report);
    }

    // Update item or save item if can't find it
    public boolean updateOrSave(Item item) {
        Item tempItem = findItem(item.getName());

        if (tempItem != null) {
            tempItem.setDescription(item.getDescription());
            tempItem.setPrice(item.getPrice());
            tempItem.setDiscount(item.getDiscount());
            tempItem.setQuantity(item.getQuantity());
            tempItem.setInventoryLevel(item.getInventoryLevel());
        } else {
            saveItem(item);
        }

        return true;
    }

    public boolean saveItem(Item item) {
        inventory.add(item);

        return true;
    }

    public Item findItem(String name) {
        Item temp;
        for (int i = 0; i < inventory.size(); i++) {
            temp = inventory.get(i);
            if (temp.getName().equals(name)) {
                return temp;
            }
        }

        return null;
    }

    public Item findItem(Integer index) {
        Item temp;
        for (int i = 0; i < inventory.size(); i++) {
            temp = inventory.get(i);
            if (i == index) {
                return temp;
            }
        }

        return null;
    }

    public Item getItem(Integer itemCode) {
        Item item = inventory.get(new Random().nextInt(inventory.size()));

        return item;
    }

    public boolean updateQuantity(String name, Integer quantity) {
        Item temp;
        for (int i = 0; i < inventory.size(); i++) {
            temp = inventory.get(i);
            if (temp.getName().equals(name)) {
                temp.setQuantity(temp.getQuantity() + quantity);

                return true;
            }
        }

        return false;
    }

    // Update the quantity
    private void findOneAndUpdate(String name) {
        inventory.forEach(item -> {
            if (item.getName().equals(name)) {
                item.setQuantity(item.getQuantity() - 1);
            }
        });
    }

    // Samples
    private void loadSamples() {

        Item a = new Item("Tomato", "Item #1", 2.99f, 0.0f, 20, 10);
        Item b = new Item("Notebook", "Item #2", 3.99f, 0.2f, 10, 5);
        Item c = new Item("Alcohol", "Item #3", 10.99f, 0.0f, 30, 15);
        Item d = new Item("T-Shirt", "Item #4", 12.99f, 0.0f, 50, 60);

        inventory.add(a);
        inventory.add(b);
        inventory.add(c);
        inventory.add(d);
    }
}
