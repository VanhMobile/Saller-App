package com.example.sallerapp.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.database.CustomerDao;
import com.example.sallerapp.databinding.BottomDialogCustomerTypeBinding;
import com.example.sallerapp.databinding.FragmentAddCustomerBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.CustomerBuilder;
import com.example.sallerapp.funtions.MyFragment;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.Customer;
import com.google.android.gms.ads.AdRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


public class Fragment_add_customer extends Fragment {

    private FragmentAddCustomerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddCustomerBinding.inflate(inflater,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);


        binding.btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inSertCustomer();
            }
        });

        binding.CustomerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogCustomerTypeBinding typeBinding = BottomDialogCustomerTypeBinding.inflate(getLayoutInflater());
                BottomSheetDialog customerDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogThem);
                customerDialog.setContentView(typeBinding.getRoot());

                typeBinding.btnAddTypeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyFragment.replaceFragment(requireActivity().getSupportFragmentManager()
                                , R.id.fragmentContainer
                                , new AddCategoryCustomerFragment()
                                , false);
                    }
                });
                

            }
        });
    }

    private void inSertCustomer() {
        int cout = 0;
        Validations.isEmpty(binding.CustomerName);
        Validations.isEmpty(binding.CustomerAddress);
        Validations.isPhoneNumber(binding.CustomerPhoneNumber);

    }

}