package com.example.sallerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sallerapp.R;
import com.example.sallerapp.databinding.ItemListEmployyeeBinding;
import com.example.sallerapp.model.Bill;
import com.example.sallerapp.model.CategoryCustomer;
import com.example.sallerapp.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class ListEmployeeAdapter extends RecyclerView.Adapter<ListEmployeeAdapter.ViewHodel>{

    private List<Employee> employeeList;
    private List<Employee> employeeList2;

    IListEmployee click;

    public ListEmployeeAdapter(List<Employee> employeeList,IListEmployee click) {
        this.employeeList = employeeList;
        this.employeeList2 = new ArrayList<>(employeeList);
        this.click = click;
    }

    public interface IListEmployee{
        void btnClick (Employee employee);
    }
    @NonNull
    @Override
    public ListEmployeeAdapter.ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListEmployyeeBinding binding = ItemListEmployyeeBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);

        return new ViewHodel(binding);
    }

    public void setData(List<Employee> employeeList){
        this.employeeList = employeeList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        Employee employee = employeeList2.get(position);
        int index = position + 1;
        holder.binding.nameEmployee.setText(index+". "+employee.getName());
        holder.binding.sdtEmployee.setText("Sđt: "+employee.getNumberPhone());
        holder.binding.emailEmployee.setText("Email: " + employee.getEmail());
        Glide.with(holder.binding.imgPath.getContext())
                .load(employee.getImgPath())
                .error(R.drawable.product_img)
                .into(holder.binding.imgPath);
        holder.binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.btnClick(employee);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeList2.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        ItemListEmployyeeBinding binding ;
        public ViewHodel(@NonNull ItemListEmployyeeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void seachEmployee(String a){
        employeeList2.clear();
        if (a.isEmpty()) {
            employeeList2.addAll(employeeList);
        } else {
            employeeList.forEach(o -> {
                if (o.getName().toLowerCase().contains(a.toLowerCase())
                        || o.getEmail().toLowerCase().contains(a.toLowerCase())
                        || o.getNumberPhone().toLowerCase().contains(a.toLowerCase())){
                    employeeList2.add(o);
                }
            });
        }
        notifyDataSetChanged();
    }
}