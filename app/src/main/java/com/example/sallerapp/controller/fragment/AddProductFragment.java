package com.example.sallerapp.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.R;
import com.example.sallerapp.databinding.FragmentAddProductBinding;
import com.google.android.gms.ads.AdRequest;

public class AddProductFragment extends Fragment {

    private FragmentAddProductBinding productBinding;

    public AddProductFragment() {
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
        // Inflate the layout for this fragment
        productBinding = FragmentAddProductBinding.inflate(inflater,container,false);
        initView();
        return productBinding.getRoot();
    }

    private void initView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        productBinding.adView.loadAd(adRequest);
    }
}