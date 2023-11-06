package com.example.sallerapp.model;

import java.util.ArrayList;

public class Product {
    private String imgPath;
    private String productId;
    private String productName;
    private int cost;
    private int retailPrice;
    private int wholeSalePrice;
    private int quantity;
    private String properties;
    private String date;
    private String note;
    private ArrayList<String> listBill;

    public Product(String imgPath
            , String productId
            , String productName
            , int cost
            , int retailPrice
            , int wholeSalePrice
            , int quantity
            , String properties
            , String date
            , String note) {
        this.imgPath = imgPath;
        this.productId = productId;
        this.productName = productName;
        this.cost = cost;
        this.retailPrice = retailPrice;
        this.wholeSalePrice = wholeSalePrice;
        this.quantity = quantity;
        this.properties = properties;
        this.date = date;
        this.note = note;
    }

    public Product(String imgPath
            , String productId
            , String productName
            , int cost
            , int retailPrice
            , int wholeSalePrice
            , int quantity
            , String properties
            , String date
            , String note
            , ArrayList<String> listBill) {
        this.imgPath = imgPath;
        this.productId = productId;
        this.productName = productName;
        this.cost = cost;
        this.retailPrice = retailPrice;
        this.wholeSalePrice = wholeSalePrice;
        this.quantity = quantity;
        this.properties = properties;
        this.date = date;
        this.note = note;
        this.listBill = listBill;
    }

    public Product() {
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
    }

    public int getWholeSalePrice() {
        return wholeSalePrice;
    }

    public void setWholeSalePrice(int wholeSalePrice) {
        this.wholeSalePrice = wholeSalePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}