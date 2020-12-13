package com.scs.managers;

import com.scs.models.Auth;
import com.scs.models.Inventory;
import com.scs.models.Item;

import java.util.List;

//
public class CheckoutManager {

    private ScanManager scanManager = new ScanManager();
    private PaymentManager paymentManager = new PaymentManager();
    private ReceiptManager receiptManager = new ReceiptManager();
    private OrderManager orderManager = new OrderManager();

    private Inventory inventory = new Inventory();

    private List<Item> items;

    private boolean isOrder = false;

    private Auth auth;

    public CheckoutManager() {
        items = inventory.createOrder();

        auth = new Auth();
    }

    // Simulation of scanning
    public Item scan(Integer itemCode) {
        Item item = scanManager.scan(itemCode);
        items.add(item);

        return item;
    }

    // Simulation of validating age
    public boolean validateAge(Integer age) {
        if (age >= 18) {
            return true;
        }

        return false;
    }

    // Simulation of paying with cash
    public Float payWithCash(Float cash, Float total) {
        float difference = paymentManager.payWithCash(cash, total);
        if (difference >= 0) {
            orderManager.updateInventory(items);
        }

        return difference;
    }

    // Simulation of paying with credit card
    public Boolean payWithCreditCard(String cardNum) {
        auth = paymentManager.payWithCreditCard(cardNum);
        if (!(auth.getAuthCode().isEmpty())) {
            orderManager.updateInventory(items);
            return true;
        }

        return false;
    }

    // Simulation of paying with debit card
    public Boolean payWithDebitCard(String cardNum, String PIN) {
        auth = paymentManager.payWithDebitCard(cardNum, PIN);
        if (!(auth.getAuthCode().isEmpty())) {
            orderManager.updateInventory(items);
            return true;
        }

        return false;
    }

    // Simulation of generating receipt
    public String generateReceipt(Integer kPAYMENT_METHOD, String customerName, Float total, Float cash, Float change) {
        auth.setCustomerName(customerName);
        String receipt = receiptManager.generateReceipt(items, kPAYMENT_METHOD, auth, total, cash, change);

        // append order
        isOrder = true;

        return receipt;
    }

    // Create an empty order after removing invalid order
    public void resetItems() {
        // remove invalid order
        if (!isOrder) {
            inventory.removeEnd();
            isOrder = false;
        }

        items = inventory.createOrder();
    }
}
