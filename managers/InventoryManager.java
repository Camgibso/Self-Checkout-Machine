package com.scs.managers;

import com.scs.models.Inventory;
import com.scs.models.Item;

import java.util.List;

public class InventoryManager {
    private Inventory inventory = new Inventory();

    public InventoryManager() {
    }

    public List<Item> getInventory() {
        return inventory.inventory;
    }

    public void append(Item item) {
        inventory.saveItem(item);
    }

    public void update(Item item) {
        inventory.updateOrSave(item);
    }

    public Item findItem(Integer index) {
        return inventory.findItem(index);
    }

    public boolean updateQuantity(String name, Integer quantity) {
        if (inventory.updateQuantity(name, quantity)) {
            return true;
        } else {
            return false;
        }
    }
}
