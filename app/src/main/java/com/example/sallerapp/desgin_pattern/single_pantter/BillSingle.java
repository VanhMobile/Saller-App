package com.example.sallerapp.desgin_pattern.single_pantter;

import com.example.sallerapp.model.Bill;
import com.example.sallerapp.model.ShopAccount;

public class BillSingle {
    private static BillSingle instance;

    private Bill bill = new Bill();

    public static BillSingle getInstance(){
        if (instance == null){
            instance = new BillSingle();
        }
        return instance;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
