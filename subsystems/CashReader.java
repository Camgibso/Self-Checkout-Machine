package com.scs.subsystems;

public class CashReader {

    public CashReader() {
    }

    public boolean readCash(Float cash) {
        if (cash > 0) {
            return true;
        }

        return false;
    }
}
