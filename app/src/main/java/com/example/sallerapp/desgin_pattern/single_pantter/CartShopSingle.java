package com.example.sallerapp.desgin_pattern.single_pantter;

import com.example.sallerapp.model.CartShop;
import com.example.sallerapp.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartShopSingle {
    private static CartShopSingle instance;
    private ArrayList<Product> products = new ArrayList<>();

    public static CartShopSingle getInstance(){
        if (instance == null){
            instance = new CartShopSingle();
        }
        return instance;
    }

    public ArrayList<CartShop> getCartShops(){
        ArrayList<CartShop> cartShops = new ArrayList<>();
        Map<Product,Integer> slxh = new HashMap<>();
        for (Product product : this.getProducts()){
            slxh.put(product,slxh.getOrDefault(product,0) + 1);
        }
        for (Map.Entry<Product, Integer> entry : slxh.entrySet()) {
           cartShops.add(new CartShop(entry.getKey(), entry.getValue()));
        }
        return cartShops;
    }

    public static void setInstance(CartShopSingle instance) {
        CartShopSingle.instance = instance;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int SumQuantity(){
        return this.getProducts().size();
    }

    public int SumPrice(String typeBill){
        int price = 0;
        if (typeBill.equals("Giá bán lẻ")){
            for (int i = 0; i < this.getCartShops().size(); i++){
                price += (this.getCartShops().get(i).getQuantity() * this.getCartShops().get(i).getProduct().getRetailPrice());
            }
        }else{
            for (int i = 0; i < this.getCartShops().size(); i++){
                price += (this.getCartShops().get(i).getQuantity() * this.getCartShops().get(i).getProduct().getWholeSalePrice());
            }
        }
        return price;
    }
}