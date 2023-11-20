package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.databinding.ItemCategoryCustomerBinding;
import com.example.sallerapp.model.CategoryCustomer;

import java.util.ArrayList;
import java.util.List;

public class CategoryCustomerAdapter extends RecyclerView.Adapter<CategoryCustomerAdapter.ViewHolder> {

    private List<CategoryCustomer> categoryCustomerList;
    private List<CategoryCustomer> listFilterCategory;
    private ICATEGORYCUSTOMER linstener;
    public interface ICATEGORYCUSTOMER{
        void click (CategoryCustomer categoryCustomer);
        void update (CategoryCustomer categoryCustomer);
    }

    public CategoryCustomerAdapter(List<CategoryCustomer> categoryCustomerList) {
        this.categoryCustomerList = categoryCustomerList;
        this.listFilterCategory = new ArrayList<>(categoryCustomerList);
    }

    @NonNull
    @Override
    public CategoryCustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryCustomerBinding binding = ItemCategoryCustomerBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    public void setData(List<CategoryCustomer> categoryCustomerList){
        this.categoryCustomerList = categoryCustomerList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryCustomer categoryCustomer = categoryCustomerList.get(position);
        holder.binding.NameCategoryCustomer.setText( (position + 1) + "." + categoryCustomer.getNameCategory());
    }

    @Override
    public int getItemCount() {
        return categoryCustomerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryCustomerBinding binding;
        public ViewHolder(@NonNull  ItemCategoryCustomerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void filterCategoryCustomer(String character){
        categoryCustomerList.clear();
        if (character.isEmpty()) categoryCustomerList.addAll(listFilterCategory);
        else{
            listFilterCategory.forEach(item ->{
                if (item.getNameCategory().contains(character)) categoryCustomerList.add(item);
            });
        }
        notifyDataSetChanged();
    }
}
