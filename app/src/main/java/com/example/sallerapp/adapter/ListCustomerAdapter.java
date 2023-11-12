package com.example.sallerapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.databinding.ItemListCustomerBinding;
import com.example.sallerapp.model.Customer;

import java.util.ArrayList;

public class ListCustomerAdapter extends RecyclerView.Adapter<ListCustomerAdapter.ViewHolder>{

    private final String TAG = ListCustomerAdapter.class.getSimpleName();

    private ArrayList<Customer> customerArrayList;

    private ArrayList<Customer> filterCustomerList;

    Click click;


    public ListCustomerAdapter(ArrayList<Customer> customerArrayList, Click click) {
        this.customerArrayList = customerArrayList;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListCustomerBinding itemListCustomerBinding = ItemListCustomerBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(itemListCustomerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer customer = customerArrayList.get(position);
        holder.itemListCustomerBinding.customerName.setText(customer.getCustomerName());
        holder.itemListCustomerBinding.phoneNumberCustomer.setText(customer.getNumberPhone());
        holder.itemListCustomerBinding.addressCustomer.setText(customer.getAddress());
        holder.itemListCustomerBinding.iconCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.clickBtnCall(customer);
            }
        });

        holder.itemListCustomerBinding.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.clickItem(customer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemListCustomerBinding itemListCustomerBinding;
        public ViewHolder(@NonNull ItemListCustomerBinding itemListCustomerBinding) {
            super(itemListCustomerBinding.getRoot());
            this.itemListCustomerBinding = itemListCustomerBinding;
        }
    }

    public void setDATA(ArrayList<Customer> list) {
        this.customerArrayList = new ArrayList<>(list);
        this.filterCustomerList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public interface Click{
        void clickBtnCall(Customer customer);
        void clickItem(Customer customer);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterListCustomer(String character){
        customerArrayList.clear();
        if (character.isEmpty()) {
            customerArrayList.addAll(filterCustomerList);
        } else{
            filterCustomerList.forEach(item -> {
                if (item.getCustomerName().toLowerCase().contains(character)){
                    customerArrayList.add(item);
                }
            });
        }
        notifyDataSetChanged();
    }

}