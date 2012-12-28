package com.metaintellect.Tina.models;


import java.util.ArrayList;

public class Invoice {

    private float totalPrice;
    private ArrayList<InvoiceItem> items;

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<InvoiceItem> items) {
        this.items = items;
    }
}
