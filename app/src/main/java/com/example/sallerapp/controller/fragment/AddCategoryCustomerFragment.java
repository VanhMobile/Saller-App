package com.example.sallerapp.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.database.CategoryCustomerDao;
import com.example.sallerapp.databinding.FragmentAddCategoryCustomerBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.CategoryCustomerBuilder;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.IdGenerator;
import com.example.sallerapp.funtions.MyFragment;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.CategoryCustomer;
import com.example.sallerapp.model.ShopAccount;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

public class AddCategoryCustomerFragment extends Fragment {

    FragmentAddCategoryCustomerBinding categoryCustomerBinding;
    ShopAccount shopAccount = SingleAccount.getInstance().getShopAccount();
    public AddCategoryCustomerFragment() {
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
        categoryCustomerBinding = FragmentAddCategoryCustomerBinding.inflate(inflater,container,false);
        initView();
        return categoryCustomerBinding.getRoot();
    }

    private void initView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        categoryCustomerBinding.adView.loadAd(adRequest);

        categoryCustomerBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCategory();
            }
        });

        categoryCustomerBinding.iconSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCategory();
            }
        });

        categoryCustomerBinding.imgBackACP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(requireActivity(), MainActivity.class));
               requireActivity().finish();
            }
        });
    }

    private void insertCategory() {
        if (!Validations.isEmptyPress(categoryCustomerBinding.edtCategoryName)){
            CategoryCustomerDao.getCategoryCustomers(shopAccount.getShopId(), new CategoryCustomerDao.GetData() {
                @Override
                public void getData(ArrayList<CategoryCustomer> categoryCustomers) {
                    CategoryCustomer categoryCustomer = new CategoryCustomerBuilder()
                            .addId(IdGenerator.generateNextShopId(categoryCustomers.size(), "LKH_"))
                            .addName(categoryCustomerBinding.edtCategoryName.getText().toString())
                            .addNote(categoryCustomerBinding.edtNote.getText().toString()).build();
                    CategoryCustomerDao.insertCategoryCustomer(categoryCustomer, shopAccount.getShopId());
                    Toast.makeText(requireContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    clearData();
                }
            });
        }
    }

    private void clearData() {
        categoryCustomerBinding.edtCategoryName.setText("");
        categoryCustomerBinding.edtNote.setText("");
    }
}