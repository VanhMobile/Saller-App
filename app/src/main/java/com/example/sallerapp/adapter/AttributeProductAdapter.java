package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.databinding.ItemAttributeProductBinding;
import com.example.sallerapp.model.AttributeProduct;
import com.example.sallerapp.model.CartShop;

import java.util.ArrayList;

public class AttributeProductAdapter extends RecyclerView.Adapter<AttributeProductAdapter.ViewHolder>{

    ArrayList<AttributeProduct> attributeProducts;

    public Click click;

    public AttributeProductAdapter(ArrayList<AttributeProduct> attributeProducts, Click click) {
        this.attributeProducts = attributeProducts;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAttributeProductBinding attributeProductBinding = ItemAttributeProductBinding
                .inflate(LayoutInflater.from(parent.getContext())
                        , parent
                        ,false);
        return new ViewHolder(attributeProductBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttributeProduct attributeProduct = attributeProducts.get(position);
        holder.attributeProductBinding.name.setText(attributeProduct.getAttribute());
        holder.attributeProductBinding.quantity.setText(attributeProduct.getQuantity()+"");
        holder.attributeProductBinding.attPro.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                click.delete(attributeProduct);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return attributeProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemAttributeProductBinding attributeProductBinding;
        public ViewHolder(@NonNull ItemAttributeProductBinding attributeProductBinding) {
            super(attributeProductBinding.getRoot());
            this.attributeProductBinding = attributeProductBinding;
        }
    }

    public interface Click{
        void delete(AttributeProduct attributeProduct);
    }
}
