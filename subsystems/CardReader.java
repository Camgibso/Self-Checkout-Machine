package com.scs.subsystems;

import com.scs.models.Auth;

public class CardReader {

    public CardReader() {

    }

    public Auth readCreditCard(String cardNum) {
        Auth auth = new Auth();

        if (cardNum.equals("40001111")) {
            auth.setAuthCode("AUTH4000");
            auth.setFourDigits("1111");
        }

        return auth;
    }

    public Auth readDebitCard(String cardNum, String PIN) {
        Auth auth = new Auth();

        if (cardNum.equals("40001111") &&
        PIN.equals("1234")) {
            auth.setAuthCode("AUTH4000");
            auth.setFourDigits("1111");
        }

        return auth;
    }
}
