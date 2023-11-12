package com.example.sallerapp.desgin_pattern.single_pantter;

import com.example.sallerapp.model.CartShop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public int SumQuantity(){
        int quantity = 0;
        for (int i = 0; i < this.getCartShops().size(); i++){
            quantity += this.getCartShops().get(i).getQuantity();
        }
        return quantity;
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
