package com.example.sallerapp.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.R;
import com.example.sallerapp.controller.view.EmployeeActivity;
import com.example.sallerapp.databinding.FragmentListEmployeeBinding;
import com.google.android.gms.ads.AdRequest;


public class ListEmployeeFragment extends Fragment {

    private FragmentListEmployeeBinding employBinding;


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
        employBinding.btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), EmployeeActivity.class));
            }
        });
    }
}