package com.example.sallerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.databinding.ItemListCateProDialogBinding;
import com.example.sallerapp.databinding.ItemListCustomerBinding;


import java.util.ArrayList;

public class ListTypeCustomerAdapter extends RecyclerView.Adapter<ListTypeCustomerAdapter.ViewHolder>{


    private ArrayList<String> customerArrayList;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public ListTypeCustomerAdapter(ArrayList<String> customerArrayList, Context context) {
        this.customerArrayList = customerArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListCateProDialogBinding itemListCustomerBinding = ItemListCateProDialogBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ListTypeCustomerAdapter.ViewHolder(itemListCustomerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String customer = customerArrayList.get(position);
        holder.itemListCustomerBinding.NameCategoryProduct.setText(customer);

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
        void onItemClick(String customerType);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
