package com.scs.managers;

import com.scs.models.Auth;
import com.scs.subsystems.CardReader;
import com.scs.subsystems.CashReader;

public class PaymentManager {

    private CashReader cashReader = new CashReader();
    private CardReader cardReader = new CardReader();

    public static Integer kCASH = 0xf0;
    public static Integer kCARD = 0xf1;

    private Auth auth;

    public PaymentManager() {
        auth = new Auth();
    }

    // Simulation of paying with cash
    public Float payWithCash(Float cash, Float total) {
        if (!cashReader.readCash(cash)) {
            return -1.0f;
        }

        float difference = cash - total;
        if (cash >= total) {
            return difference;
        } else {
            return difference;
        }
    }

    // Simulation of paying with credit card
    public Auth payWithCreditCard(String cardNum) {
        auth = cardReader.readCreditCard(cardNum);

        return auth;
    }

    // Simulation of paying with debit card
    public Auth payWithDebitCard(String cardNum, String PIN) {
        auth = cardReader.readDebitCard(cardNum, PIN);

        return auth;
    }
}
