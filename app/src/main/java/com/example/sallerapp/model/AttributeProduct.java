package com.example.sallerapp.model;

public class AttributeProduct {

    private String attribute;
    private int quantity;

    public AttributeProduct(String attribute, int quantity) {
        this.attribute = attribute;
        this.quantity = quantity;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
