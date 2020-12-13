package com.scs.managers;

import com.scs.models.Inventory;
import com.scs.models.Item;

public class ScanManager {

    private Inventory inventory = new Inventory();

    public ScanManager() {}

    // Simulation of scanning
    public Item scan(Integer itemCode) {
        Item item = inventory.getItem(itemCode);

        return item;
    }
}
