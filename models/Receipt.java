package com.scs.models;

import java.util.List;

public class Receipt {

    private List<Item> items;
    private Integer paymentMethod;
    private Auth auth;
    private Float total;
    private Float cash;
    private Float change;

    public Receipt(List<Item> items, Integer paymentMethod, Auth auth, Float total, Float cash, Float change) {
        this.items = items;
        this.paymentMethod = paymentMethod;
        this.auth = auth;
        this.total = total;
        this.cash = cash;
        this.change = change;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getCash() {
        return cash;
    }

    public void setCash(Float cash) {
        this.cash = cash;
    }

    public Float getChange() {
        return change;
    }

    public void setChange(Float change) {
        this.change = change;
    }
}
