package com.example.sallerapp.desgin_pattern.build_pantter;

import com.example.sallerapp.model.Product;

public class ProductBuilder {

    public ProductBuilder(){

    }
    private Product product = new Product();

    public ProductBuilder addImgPath(String imgPath){
        product.setImgPath(imgPath);
        return this;
    }

    public ProductBuilder addId(String id){
        product.setProductId(id);
        return this;
    }

    public ProductBuilder addProductName(String productName){
        product.setProductName(productName);
        return this;
    }
    public ProductBuilder addCost(int cost){
        product.setCost(cost);
        return this;
    }

    public ProductBuilder addRetailPrice(int retailPrice){
        product.setRetailPrice(retailPrice);
        return this;
    }

    public ProductBuilder addWholeSalePrice(int wholeSalePrice){
        product.setWholeSalePrice(wholeSalePrice);
        return this;
    }

    public ProductBuilder addQuantity(int quantity){
        product.setQuantity(quantity);
        return this;
    }

    public ProductBuilder addAttribute(String attribute){
        product.setProperties(attribute);
        return this;
    }

    public ProductBuilder addDate(String date){
        product.setDate(date);
        return this;
    }

    public ProductBuilder addNote(String note){
        product.setNote(note);
        return this;
    }

    public Product build(){
        return product;
    }
}
