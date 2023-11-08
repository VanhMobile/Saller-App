package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sallerapp.databinding.ItemListEmployyeeBinding;
import com.example.sallerapp.model.CategoryCustomer;
import com.example.sallerapp.model.Employee;

import java.util.List;

public class ListEmployeeAdapter extends RecyclerView.Adapter<ListEmployeeAdapter.ViewHodel>{
    private List<Employee> employeeList;
    private ILISTEMPLOYEE linstener;
    public interface ILISTEMPLOYEE{
        void click (Employee employee);
        void update (Employee employee);
    }


    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListEmployyeeBinding binding = ItemListEmployyeeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHodel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        ItemListEmployyeeBinding binding ;
        public ViewHodel(@NonNull ItemListEmployyeeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
