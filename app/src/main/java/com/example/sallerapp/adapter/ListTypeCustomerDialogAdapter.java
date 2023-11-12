package com.example.sallerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.databinding.ItemListCateProDialogBinding;
import com.example.sallerapp.model.CategoryCustomer;


import java.util.ArrayList;

public class ListTypeCustomerDialogAdapter extends RecyclerView.Adapter<ListTypeCustomerDialogAdapter.ViewHolder>{


    private ArrayList<CategoryCustomer> customerArrayList;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public ListTypeCustomerDialogAdapter(ArrayList<CategoryCustomer> customerArrayList, Context context) {
        this.customerArrayList = customerArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListCateProDialogBinding itemListCustomerBinding = ItemListCateProDialogBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ListTypeCustomerDialogAdapter.ViewHolder(itemListCustomerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryCustomer customer = customerArrayList.get(position);
        holder.itemListCustomerBinding.NameCategoryProduct.setText(customer.getNameCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(customerArrayList.get(position));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ItemListCateProDialogBinding itemListCustomerBinding;
        public ViewHolder(@NonNull ItemListCateProDialogBinding itemListCustomerBinding) {
            super(itemListCustomerBinding.getRoot());
            this.itemListCustomerBinding = itemListCustomerBinding;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryCustomer customerType);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
