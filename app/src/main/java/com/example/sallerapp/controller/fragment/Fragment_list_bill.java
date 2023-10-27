package com.example.sallerapp.controller.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.R;
import com.example.sallerapp.databinding.FragmentListBillBinding;


public class Fragment_list_bill extends Fragment {

    private FragmentListBillBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBillBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }
}