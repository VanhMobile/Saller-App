package com.example.sallerapp.desgin_pattern.build_pantter;

import com.example.sallerapp.model.Customer;

public class CustomerBuilder {
    private Customer customer = new Customer();

    public CustomerBuilder addId(String id){
        customer.setCustomerId(id);
        return this;
    }

    public CustomerBuilder addName(String name){
        customer.setCustomerName(name);
        return this;
    }

    public CustomerBuilder addAddress(String address){
        customer.setAddress(address);
        return this;
    }

    public CustomerBuilder addNumberPhone(String numberPhone){
        customer.setNumberPhone(numberPhone);
        return this;
    }

    public CustomerBuilder addCustomerType(String type){
        customer.setCustomerType(type);
        return this;
    }

    public CustomerBuilder addNote(String note){
        customer.setNote(note);
        return this;
    }

    public Customer build(){
        return customer;
    }
}
