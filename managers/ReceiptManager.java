package com.scs.managers;

import com.scs.models.Auth;
import com.scs.models.Inventory;
import com.scs.models.Item;
import com.scs.models.Receipt;
import com.scs.subsystems.ReceiptPrinter;

import java.util.List;

public class ReceiptManager {

    private Inventory inventory = new Inventory();

    private ReceiptPrinter receiptPrinter = new ReceiptPrinter();

    public ReceiptManager() {
    }

    public String generateReceipt(List<Item> items, Integer kPAYMENT_METHOD, Auth auth, Float total, Float cash, Float change) {

        Receipt receipt = new Receipt(items, kPAYMENT_METHOD, auth, total, cash, change);
        inventory.saveReceipt(receipt);

        String receiptText = receiptPrinter.print(receipt);

        return receiptText;
    }
}
