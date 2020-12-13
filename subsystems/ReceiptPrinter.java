package com.scs.subsystems;

import com.scs.managers.PaymentManager;
import com.scs.models.Receipt;

public class ReceiptPrinter {

    public ReceiptPrinter() {
    }

    public String print(Receipt receipt) {
        String receiptText = "";
        for (int i = 0; i < receipt.getItems().size(); i++) {
            receiptText += String.format("%d. %s - %s - %d\n", (i + 1), receipt.getItems().get(i).getName(), receipt.getItems().get(i).getDescription(), 1);
        }

        receiptText += String.format("TOTAL: $%f\n", receipt.getTotal());

        if (receipt.getPaymentMethod().equals(PaymentManager.kCASH)) {
            receiptText += String.format("CASH: $%f\n", receipt.getCash());
            receiptText += String.format("CHANGE: $%f\n", receipt.getChange());
        } else if (receipt.getPaymentMethod().equals(PaymentManager.kCARD)) {
            receiptText += String.format("PAYMENT METHOD: %s\n", "CREDIT/DEBIT CARD");
            receiptText += String.format("LAST 4 DIGITS: %s\n", receipt.getAuth().getFourDigits());
            receiptText += String.format("SIGNATURE: %s\n", receipt.getAuth().getCustomerName());
        }

//        System.out.println(receiptText);
        return receiptText;
    }
}
