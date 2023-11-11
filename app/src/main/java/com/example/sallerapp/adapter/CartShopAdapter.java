package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sallerapp.R;
import com.example.sallerapp.databinding.ItemProductBillBinding;
import com.example.sallerapp.model.CartShop;

import java.util.ArrayList;

public class CartShopAdapter extends RecyclerView.Adapter<CartShopAdapter.ViewHolder> {

    private ArrayList<CartShop> cartShops;

    private String typeBill;

    public CartShopAdapter(ArrayList<CartShop> cartShops, String typeBill) {
        this.cartShops = cartShops;
        this.typeBill = typeBill;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBillBinding binding = ItemProductBillBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartShop cartShop = cartShops.get(position);
        Glide.with(holder.binding.imgProduct.getContext())
                .load(cartShop.getProduct().getImgPath())
                .error(R.drawable.product_img)
                .into(holder.binding.imgProduct);
        int index = position + 1;
        holder.binding.nameProduct.setText(index + ". " + cartShop.getProduct().getProductName());
        if (typeBill.equals("Giá bán lẻ")){

        }
    }

    @Override
    public int getItemCount() {
        return cartShops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemProductBillBinding binding;

        public ViewHolder(@NonNull ItemProductBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
