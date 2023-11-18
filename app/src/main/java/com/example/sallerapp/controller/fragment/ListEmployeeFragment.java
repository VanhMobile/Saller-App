package com.example.sallerapp.controller.fragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.model.Employee;
import com.example.sallerapp.model.ShopAccount;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;
import java.util.List;


public class ListEmployeeFragment extends Fragment {

    private FragmentListEmployeeBinding employBinding;
    private ListEmployeeAdapter adapter;

    ShopAccount shopAccount = SingleAccount.getInstance().getShopAccount();

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
        EmployeeDao.getEmployees(shopAccount.getShopId(), new EmployeeDao.GetData() {
            @Override
            public void getData(ArrayList<Employee> employees) {
                adapter = new ListEmployeeAdapter(employees,new ListEmployeeAdapter.IListEmployee() {
                    @Override
                    public void btnClick(Employee employee) {
                        String phoneNumber = "tel:" + employee.getNumberPhone();

                        // Tạo Intent với hành động ACTION_CALL
                        Intent callIntent = new Intent(Intent.ACTION_CALL);

                        // Đặt dữ liệu URI cho số điện thoại
                        callIntent.setData(Uri.parse(phoneNumber));

                        // Kiểm tra xem có quyền CALL_PHONE hay không
                        if (requireActivity().checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            // Nếu có quyền, bắt đầu Intent
                            startActivity(callIntent);
                        } else {
                            // Nếu không có quyền, yêu cầu quyền từ người dùng
                            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                        }
                    }
                });
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