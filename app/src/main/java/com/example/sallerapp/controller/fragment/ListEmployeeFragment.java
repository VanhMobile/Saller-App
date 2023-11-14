package com.example.sallerapp.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
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
        employBinding.searchEm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.seachEmployee(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        reaLoad();
        employBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reaLoad();
                employBinding.swipeRefresh.setRefreshing(false);
            }
        });
        employBinding.btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), EmployeeActivity.class));
            }
        });
    }

    private void reaLoad() {
        AdRequest adRequest = new AdRequest.Builder().build();
        employBinding.adView.loadAd(adRequest);
        EmployeeDao.getEmployees("Shop_1", new EmployeeDao.GetData() {
            @Override
            public void getData(ArrayList<Employee> employees) {
                adapter = new ListEmployeeAdapter(employees);
                employBinding.rcvEmployee.setAdapter(adapter);
                if (isAdded()){
                    employBinding.rcvEmployee.setLayoutManager(new LinearLayoutManager(getContext()));
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
                    employBinding.rcvEmployee.addItemDecoration(dividerItemDecoration);
                }
            }
        });
    }
}