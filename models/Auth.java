package com.scs.models;

public class Auth {
    private String customerName;
    private String fourDigits;
    private String PIN;
    private String authCode;

    public Auth() {
        customerName = "";
        fourDigits = "";
        PIN = "";
        authCode = "";
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFourDigits() {
        return fourDigits;
    }

    public void setFourDigits(String fourDigits) {
        this.fourDigits = fourDigits;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
