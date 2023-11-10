package com.example.sallerapp.adapter;

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

    public ListCustomerAdapter(ArrayList<Customer> customerArrayList) {
        this.customerArrayList = customerArrayList;
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
}
