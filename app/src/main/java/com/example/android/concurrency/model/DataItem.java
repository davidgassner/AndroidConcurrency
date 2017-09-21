package com.example.android.concurrency.model;

import java.text.NumberFormat;

public class DataItem {

    private String itemName;
    private double price;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return itemName + " (" + numberFormat.format(price) + ")";
    }
}
