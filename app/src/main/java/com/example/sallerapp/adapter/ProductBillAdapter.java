package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.databinding.ItemProductBillBinding;
import com.example.sallerapp.model.Bill;

import java.util.List;

public class ProductBillAdapter extends RecyclerView.Adapter<ProductBillAdapter.ViewHodel>{

    private List<Bill> productBilllist;

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBillBinding binding = ItemProductBillBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHodel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productBilllist.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        ItemProductBillBinding binding ;
        public ViewHodel(@NonNull ItemProductBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
