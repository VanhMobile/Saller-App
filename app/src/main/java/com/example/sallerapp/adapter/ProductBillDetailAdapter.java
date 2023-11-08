package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.databinding.ItemProductBillDetailBinding;
import com.example.sallerapp.model.Bill;
import com.example.sallerapp.model.Product;

import java.util.List;

public class ProductBillDetailAdapter extends RecyclerView.Adapter<ProductBillDetailAdapter.ViewHodel>{

    private List<Bill> productbilltList;
    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBillDetailBinding binding = ItemProductBillDetailBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHodel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productbilltList.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        ItemProductBillDetailBinding binding ;
        public ViewHodel(@NonNull ItemProductBillDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
