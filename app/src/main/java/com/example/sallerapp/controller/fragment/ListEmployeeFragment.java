package com.example.sallerapp.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.R;
import com.example.sallerapp.adapter.ListEmployeeAdapter;
import com.example.sallerapp.controller.view.EmployeeActivity;
import com.example.sallerapp.database.EmployeeDao;
import com.example.sallerapp.databinding.FragmentListEmployeeBinding;
import com.example.sallerapp.model.Employee;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;
import java.util.List;


public class ListEmployeeFragment extends Fragment {

    private FragmentListEmployeeBinding employBinding;


    private ListEmployeeAdapter adapter;

    public ListEmployeeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        employBinding = FragmentListEmployeeBinding.inflate(inflater,container,false);
        initView();
        return employBinding.getRoot();
    }

    private void initView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        employBinding.adView.loadAd(adRequest);

        EmployeeDao.getEmployees("Shop_1", new EmployeeDao.GetData() {
            @Override
            public void getData(ArrayList<Employee> employees) {
                adapter = new ListEmployeeAdapter(employees);
                employBinding.rcvEmployee.setAdapter(adapter);
                employBinding.rcvEmployee.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });



        employBinding.btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), EmployeeActivity.class));
            }
        });
    }
}