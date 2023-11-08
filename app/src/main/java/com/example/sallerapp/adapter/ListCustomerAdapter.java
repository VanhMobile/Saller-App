package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.databinding.ItemListCustomerBinding;
import com.example.sallerapp.model.CategoryCustomer;
import com.example.sallerapp.model.Customer;

import java.util.List;

public class ListCustomerAdapter extends RecyclerView.Adapter<ListCustomerAdapter.ViewHodel> {

    private List<Customer> customerList;
    private ILISTCUSTOMER linstener;
    public interface ILISTCUSTOMER{
        void click (Customer customer);
        void update (Customer customer);
    }

    @NonNull
    @Override
    public ListCustomerAdapter.ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListCustomerBinding binding = ItemListCustomerBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHodel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        ItemListCustomerBinding binding;
        public ViewHodel(@NonNull ItemListCustomerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
