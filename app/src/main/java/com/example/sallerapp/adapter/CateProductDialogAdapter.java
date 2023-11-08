package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.database.CategoryProductDao;
import com.example.sallerapp.databinding.ItemListCateProDialogBinding;
import com.example.sallerapp.model.CategoryProduct;

import java.util.ArrayList;

public class CateProductDialogAdapter extends  RecyclerView.Adapter<CateProductDialogAdapter.ViewHolder> {

    private ArrayList<CategoryProduct> categoryProducts;
    private Click click;

    public CateProductDialogAdapter(ArrayList<CategoryProduct> categoryProducts, Click click) {
        this.categoryProducts = categoryProducts;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListCateProDialogBinding binding = ItemListCateProDialogBinding
                .inflate(LayoutInflater.from(parent.getContext())
                        ,parent
                        ,false);
        return new ViewHolder(binding);
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryProduct categoryProduct = categoryProducts.get(position);
        holder.binding.NameCategoryProduct.setText(categoryProduct.getNameCategory());
        holder.binding.catePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.click(categoryProduct.getNameCategory());
            }
        });
    }

    @Override
    public int getItemCount() {
        return  categoryProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemListCateProDialogBinding binding;
        public ViewHolder(@NonNull ItemListCateProDialogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Click {
        void click(String nameCatePro);
    }
}
