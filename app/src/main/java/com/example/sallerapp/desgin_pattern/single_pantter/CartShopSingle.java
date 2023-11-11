package com.example.sallerapp.desgin_pattern.single_pantter;

import com.example.sallerapp.model.CartShop;

import java.util.ArrayList;

public class CartShopSingle {
    private static CartShopSingle instance;
    private ArrayList<CartShop> cartShops = new ArrayList<>();

    public static CartShopSingle getInstance(){
        if (instance == null){
            instance = new CartShopSingle();
        }
        return instance;
    }

    public ArrayList<CartShop> getCartShops() {
        return cartShops;
    }

    public void setCartShops(ArrayList<CartShop> cartShops) {
        this.cartShops = cartShops;
    }
}
