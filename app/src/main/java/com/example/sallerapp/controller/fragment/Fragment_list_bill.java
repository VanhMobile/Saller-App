package com.example.sallerapp.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sallerapp.adapter.ListBillAdapter;
import com.example.sallerapp.controller.view.BillActivity;
import com.example.sallerapp.database.BillDao;
import com.example.sallerapp.databinding.FragmentListBillBinding;
import com.example.sallerapp.model.Bill;
import com.example.sallerapp.model.CartShop;
import com.example.sallerapp.model.Customer;
import com.example.sallerapp.model.Product;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Fragment_list_bill extends Fragment {

    private FragmentListBillBinding binding;
    private ArrayList<Bill> billArrayList;

    private ListBillAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBillBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);


        billArrayList = new ArrayList<>();
        BillDao.GetBills("Shop_1", new BillDao.GetData() {
            @Override
            public void getData(ArrayList<Bill> bills) {
                billArrayList = bills;

                adapter = new ListBillAdapter(billArrayList);
                binding.recyclerViewListBill.setAdapter(adapter);
                binding.recyclerViewListBill.setLayoutManager(new LinearLayoutManager(requireContext()));
            }
        });

        binding.btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), BillActivity.class));
            }
        });
    }
}