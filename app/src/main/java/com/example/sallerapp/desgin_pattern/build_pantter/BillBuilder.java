package com.example.sallerapp.desgin_pattern.build_pantter;

import com.example.sallerapp.model.Bill;
import com.example.sallerapp.model.CartShop;
import com.example.sallerapp.model.Customer;

import java.util.ArrayList;

public class BillBuilder {
    private Bill bill = new Bill();

    public BillBuilder addId(String id){
        bill.setBillId(id);
        return this;
    }

    public BillBuilder addProducts(ArrayList<CartShop> cartShops){
        bill.setListProduct(cartShops);
        return this;
    }

    public BillBuilder addBillType(String type){
        bill.setBillType(type);
        return this;
    }

    public BillBuilder addCustomer(Customer customer){
        bill.setCustomer(customer);
        return this;
    }

    public BillBuilder addQuantity(int quantity){
        bill.setQuantity(quantity);
        return this;
    }

    public BillBuilder addPayMethod(String payMethod){
        bill.setPayMethod(payMethod);
        return this;
    }

    public BillBuilder addTotalPrice(int totalPrice){
        bill.setSumPrice(totalPrice);
        return this;
    }

    public BillBuilder addNote(String note){
        bill.setNote(note);
        return this;
    }

    public BillBuilder addIdAccount(String idAccount){
        bill.setIdAccount(idAccount);
        return this;
    }

    public Bill build(){
        return bill;
    }
}
