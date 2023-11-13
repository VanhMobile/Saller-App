package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sallerapp.R;
import com.example.sallerapp.databinding.ItemProductBillDetailBinding;
import com.example.sallerapp.funtions.MoneyFormat;
import com.example.sallerapp.model.CartShop;

import java.util.ArrayList;

public class DetailBillAdapter extends RecyclerView.Adapter<DetailBillAdapter.ViewHolder>{

    private ArrayList<CartShop> cartShops;
    private String typeBill;

    public DetailBillAdapter(ArrayList<CartShop> cartShops, String typeBill) {
        this.cartShops = cartShops;
        this.typeBill = typeBill;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBillDetailBinding itemProductBillDetailBinding = ItemProductBillDetailBinding.inflate(
                LayoutInflater.from(parent.getContext())
                , parent
                ,false);
        return new ViewHolder(itemProductBillDetailBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartShop cartShop = cartShops.get(position);
        int index = position + 1;
        holder.itemProductBillDetailBinding.nameProduct.setText(index + ". "+cartShop.getProduct().getProductName());
        Glide.with(holder.itemProductBillDetailBinding.imgProduct.getContext())
                .load(cartShop.getProduct().getImgPath())
                .error(R.drawable.product_img)
                .into(holder.itemProductBillDetailBinding.imgProduct);
        if (typeBill.equals("Giá bán lẻ")){
            holder.itemProductBillDetailBinding.priceProduct.setText(cartShop.getQuantity() +" x "+ MoneyFormat.moneyFormat(cartShop.getProduct().getRetailPrice()));
        }else{
            holder.itemProductBillDetailBinding.priceProduct.setText(cartShop.getQuantity() +" x "+ MoneyFormat.moneyFormat(cartShop.getProduct().getWholeSalePrice()));
        }
    }

    @Override
    public int getItemCount() {
        return cartShops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemProductBillDetailBinding itemProductBillDetailBinding;

        public ViewHolder(@NonNull ItemProductBillDetailBinding itemProductBillDetailBinding) {
            super(itemProductBillDetailBinding.getRoot());
            this.itemProductBillDetailBinding = itemProductBillDetailBinding;
        }
    }
}
