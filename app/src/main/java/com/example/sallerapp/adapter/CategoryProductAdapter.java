package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.databinding.ItemCategoryCustomerBinding;
import com.example.sallerapp.databinding.ItemCategoryProductBinding;
import com.example.sallerapp.model.CategoryCustomer;
import com.example.sallerapp.model.CategoryProduct;

import java.util.List;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.ViewHolder>{

private List<CategoryProduct> categoryProductList;

    public CategoryProductAdapter(List<CategoryProduct> categoryProductList) {
        this.categoryProductList = categoryProductList;

    }

    private CategoryProduct linstener;
public interface ICategoryProduct{
    void click (CategoryProduct categoryProduct);
    void update (CategoryProduct categoryProduct);
}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryProductBinding binding = ItemCategoryProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryProduct categoryProduct = categoryProductList.get(position);
        holder.binding.NameCategoryProduct.setText( categoryProduct.getNameCategory());
    }

    @Override
    public int getItemCount() {
        return categoryProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryProductBinding binding;
        public ViewHolder(@NonNull ItemCategoryProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
