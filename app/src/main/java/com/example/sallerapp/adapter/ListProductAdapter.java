package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.databinding.ItemListProductsBinding;
import com.example.sallerapp.model.CategoryCustomer;
import com.example.sallerapp.model.Product;

import java.util.List;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHodel> {

    private List<Product> productList;

    private ILISTPRODUCT linstener;
    public interface ILISTPRODUCT{
        void click (Product product);
        void update (Product product);
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListProductsBinding binding = ItemListProductsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHodel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        ItemListProductsBinding binding;
        public ViewHodel(@NonNull ItemListProductsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
