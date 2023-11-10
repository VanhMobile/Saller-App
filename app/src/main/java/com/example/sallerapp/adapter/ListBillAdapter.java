package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sallerapp.databinding.ItemListBillBinding;
import com.example.sallerapp.funtions.MoneyFormat;
import com.example.sallerapp.model.Bill;

import java.util.ArrayList;

public class ListBillAdapter extends RecyclerView.Adapter<ListBillAdapter.ViewHolder>{

    private ArrayList<Bill> billArrayList;

    public ListBillAdapter(ArrayList<Bill> billArrayList) {
        this.billArrayList = billArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBillBinding itemListBillBinding = ItemListBillBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ViewHolder(itemListBillBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = billArrayList.get(position);
        holder.itemListBillBinding.idBill.setText(bill.getBillId());
        holder.itemListBillBinding.customerName.setText(bill.getCustomer().getCustomerName());
        holder.itemListBillBinding.priceBill.setText(MoneyFormat.moneyFormat(bill.getSumPrice()));

        // thắc mắc ngày tạo bill ?
    }

    @Override
    public int getItemCount() {
        return billArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemListBillBinding itemListBillBinding;
        public ViewHolder(@NonNull ItemListBillBinding itemListBillBinding) {
            super(itemListBillBinding.getRoot());
            this.itemListBillBinding = itemListBillBinding;
        }
    }
}
