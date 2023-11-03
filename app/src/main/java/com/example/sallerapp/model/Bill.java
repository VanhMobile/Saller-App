package com.example.sallerapp.model;

import java.util.ArrayList;

public class Bill {
    private String billId;
    private ArrayList<CartShop> listProduct;
    private String billType;
    private Customer customer;
    private int quantity;
    private String payMethod;
    private int sumPrice;
    private String note;

    private String idAccount;

    public Bill(String billId
            , ArrayList<CartShop> listProduct
            , String billType
            , Customer customer
            , int quantity
            , String payMethod
            , int sumPrice
            , String note
            , String idAccount) {
        this.billId = billId;
        this.listProduct = listProduct;
        this.billType = billType;
        this.customer = customer;
        this.quantity = quantity;
        this.payMethod = payMethod;
        this.sumPrice = sumPrice;
        this.note = note;
        this.idAccount = idAccount;
    }

    public Bill() {
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public ArrayList<CartShop> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<CartShop> listProduct) {
        this.listProduct = listProduct;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
