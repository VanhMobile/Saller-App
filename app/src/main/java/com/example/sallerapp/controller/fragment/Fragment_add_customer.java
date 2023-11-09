package com.example.sallerapp.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.R;
import com.example.sallerapp.databinding.FragmentAddCustomerBinding;
import com.google.android.gms.ads.AdRequest;


public class Fragment_add_customer extends Fragment {

    private FragmentAddCustomerBinding customerBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        customerBinding = FragmentAddCustomerBinding.inflate(inflater,container,false);
        initView();
        return customerBinding.getRoot();
    }

    private void initView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        customerBinding.adView.loadAd(adRequest);
    }
}