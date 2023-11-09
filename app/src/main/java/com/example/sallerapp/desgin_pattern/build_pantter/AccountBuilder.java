package com.example.sallerapp.desgin_pattern.build_pantter;

import com.example.sallerapp.model.ShopAccount;

public class AccountBuilder {
    private ShopAccount shopAccount = new ShopAccount();

    public AccountBuilder addIdAccount(String id){
        shopAccount.setShopId(id);
        return this;
    }

    public AccountBuilder addAddress(String address){
        shopAccount.setAddress(address);
        return this;
    }

    public AccountBuilder addEmail(String email){
        shopAccount.setEmail(email);
        return this;
    }

    public AccountBuilder addNumberPhone(String numberPhone){
        shopAccount.setNumberPhone(numberPhone);
        return this;
    }

    public AccountBuilder addShopName(String shopName){
        shopAccount.setShopName(shopName);
        return this;
    }

    public AccountBuilder addPassword(String password){
        shopAccount.setPassword(password);
        return this;
    }

    public ShopAccount build(){
        return shopAccount;
    }
}
