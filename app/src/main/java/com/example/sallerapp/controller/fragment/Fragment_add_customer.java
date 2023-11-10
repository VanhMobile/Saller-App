package com.example.sallerapp.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.R;
import com.example.sallerapp.database.CustomerDao;
import com.example.sallerapp.databinding.FragmentAddCustomerBinding;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.Customer;
import com.google.android.gms.ads.AdRequest;


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

        Validations.isEmpty(binding.CustomerName);
        Validations.isEmpty(binding.CustomerAddress);
        Validations.isPhoneNumber(binding.CustomerPhoneNumber);

        binding.btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (Validations.isPhoneNumberPress(binding.CustomerPhoneNumber)){
                    CustomerDao.insertCustomer(getCustomr(),"Shop_1");
                //}
            }
        });
    }

    private Customer getCustomr(){
        Customer customer = new Customer();
        customer.setCustomerId("0002");
        customer.setCustomerName(binding.CustomerName.getText().toString());
        customer.setAddress(binding.CustomerAddress.getText().toString());
        customer.setNumberPhone(binding.CustomerPhoneNumber.getText().toString());
        customer.setCustomerType("khách hàng mới");
        customer.setNote(binding.CustomerAddNote.getText().toString());
        return  customer;
    }
}