package com.example.sallerapp.desgin_pattern.single_pantter;

import com.example.sallerapp.model.ShopAccount;

public class SingleAccount {
    private static SingleAccount instance;

    private ShopAccount shopAccount = new ShopAccount();

    private static SingleAccount getInstance(){
        if (instance == null){
            instance = new SingleAccount();
        }
        return instance;
    }

    public ShopAccount getShopAccount() {
        return shopAccount;
    }

    public void setShopAccount(ShopAccount shopAccount) {
        this.shopAccount = shopAccount;
    }
}
