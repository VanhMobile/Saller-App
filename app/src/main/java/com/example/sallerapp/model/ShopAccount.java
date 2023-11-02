package com.example.sallerapp.model;

public class ShopAccount {
    private String shopId;
    private String address;
    private String email;
    private String numberPhone;
    private String shopName;
    private String password;

    public ShopAccount(String shopId, String address, String email, String numberPhone, String shopName, String password) {
        this.shopId = shopId;
        this.address = address;
        this.email = email;
        this.numberPhone = numberPhone;
        this.shopName = shopName;
        this.password = password;
    }

    public ShopAccount() {

    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
