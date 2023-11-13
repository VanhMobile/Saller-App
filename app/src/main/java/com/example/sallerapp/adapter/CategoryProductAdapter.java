package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.databinding.ItemCategoryCustomerBinding;
import com.example.sallerapp.databinding.ItemCategoryProductBinding;
import com.example.sallerapp.model.CategoryCustomer;
import com.example.sallerapp.model.CategoryProduct;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.ViewHolder>{

private List<CategoryProduct> categoryProductList;
    private List<CategoryProduct> categoryProductList2;


    public CategoryProductAdapter(List<CategoryProduct> categoryProductList) {
        this.categoryProductList = categoryProductList;
        this.categoryProductList2 = new ArrayList<>(categoryProductList);

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
        CategoryProduct categoryProduct = categoryProductList2.get(position);
        holder.binding.NameCategoryProduct.setText( categoryProduct.getNameCategory());
    }

    @Override
    public int getItemCount() {
        return categoryProductList2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryProductBinding binding;
        public ViewHolder(@NonNull ItemCategoryProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void TK(String a){
    categoryProductList2.clear();
        if (a.isEmpty()) {
            categoryProductList2.addAll(categoryProductList);
        } else {
            categoryProductList.forEach(o -> {
                if (o.getNameCategory().toLowerCase().contains(a.toLowerCase())){
                    categoryProductList2.add(o);
                }
            });
        }
        notifyDataSetChanged();
    }
}
