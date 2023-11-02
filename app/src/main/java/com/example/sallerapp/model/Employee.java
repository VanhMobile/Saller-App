package com.example.sallerapp.model;

public class Employee {
    private String imgPath;
    private String name;
    private String email;
    private String numberPhone;
    private String address;
    private String password;
    private String idShop;

    public Employee( String imgPath, String name, String email, String numberPhone, String address, String password, String idShop) {
        this.imgPath = imgPath;
        this.name = name;
        this.email = email;
        this.numberPhone = numberPhone;
        this.address = address;
        this.password = password;
        this.idShop = idShop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdShop() {
        return idShop;
    }

    public void setIdShop(String idShop) {
        this.idShop = idShop;
    }

    public Employee(){

    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
