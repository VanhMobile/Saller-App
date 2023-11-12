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
import com.example.sallerapp.funtions.MoneyFormat;
import com.example.sallerapp.model.CartShop;
import com.example.sallerapp.model.Product;

import java.util.ArrayList;

public class CartShopAdapter extends RecyclerView.Adapter<CartShopAdapter.ViewHolder> {

    private ArrayList<CartShop> cartShops;
    private String typeBill;
    private Click click;

    public CartShopAdapter(ArrayList<CartShop> cartShops, String typeBill, Click click) {
        this.cartShops = cartShops;
        this.typeBill = typeBill;
        this.click = click;
    }
    public void setTypeBill(String typeBill) {
        this.typeBill = typeBill;
        notifyDataSetChanged();
    }

    public String getTypeBill() {
        return typeBill;
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
            holder.binding.priceProduct.setText(MoneyFormat.moneyFormat(cartShop.getProduct().getRetailPrice()));
        }else {
            holder.binding.priceProduct.setText(MoneyFormat.moneyFormat(cartShop.getProduct().getWholeSalePrice()));
        }

        holder.binding.quantity.setText(cartShop.getQuantity() +"");
        holder.binding.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.up(cartShop.getProduct(),getTypeBill());
            }
        });

        holder.binding.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.down(cartShop.getProduct(),getTypeBill());
            }
        });
    }

    public interface Click{
        void up(Product product, String typeBill);
        void down(Product product,String typeBill);
    }

    public void setData(ArrayList<CartShop> cartShops){
        this.cartShops = cartShops;
        notifyDataSetChanged();
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
