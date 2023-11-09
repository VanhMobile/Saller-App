package com.example.sallerapp.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.controller.view.BillActivity;
import com.example.sallerapp.controller.view.CustomerActivity;
import com.example.sallerapp.controller.view.EmployeeActivity;
import com.example.sallerapp.controller.view.ProductActivity;
import com.example.sallerapp.database.CategoryProductDao;
import com.example.sallerapp.database.ProductDao;
import com.example.sallerapp.databinding.FragmentHomeBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.CategoryProductBuilder;
import com.example.sallerapp.desgin_pattern.build_pantter.ProductBuilder;
import com.example.sallerapp.funtions.IdGenerator;
import com.example.sallerapp.funtions.RequestPermissions;
import com.example.sallerapp.model.CategoryProduct;
import com.example.sallerapp.model.Product;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment{

    private FragmentHomeBinding homeBinding;


    public HomeFragment() {
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
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        initView();
        return homeBinding.getRoot();
    }

    private void initView() {

        Product product = new ProductBuilder().addId("SP23").addProductName("tu").build();




        AdRequest adRequest = new AdRequest.Builder().build();
        homeBinding.adView.loadAd(adRequest);
        homeBinding.adView2.loadAd(adRequest);

        homeBinding.shortcut.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ProductActivity.class);
                intent.putExtra("product", "addProduct");
                startActivity(intent);
            }
        });

        homeBinding.iconCartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), BillActivity.class));
            }
        });

        homeBinding.shortcut.createBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), BillActivity.class));
            }
        });


        homeBinding.shortcut.addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), EmployeeActivity.class));
            }
        });

        homeBinding.shortcut.customerManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),  CustomerActivity.class);
                intent.putExtra("customer", "listCustomer");
                startActivity(intent);
            }
        });

        homeBinding.shortcut.customerCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),  CustomerActivity.class);
                intent.putExtra("customer", "categoryCustomer");
                startActivity(intent);
            }
        });

        homeBinding.shortcut.productCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ProductActivity.class);
                intent.putExtra("product", "categoryProduct");
                startActivity(intent);
            }
        });
    }
}