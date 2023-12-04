package com.example.sallerapp.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.controller.view.ProductActivity;
import com.example.sallerapp.database.CategoryProductDao;
import com.example.sallerapp.databinding.FragmentAddCategoryProductBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.CategoryProductBuilder;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.IdGenerator;
import com.example.sallerapp.funtions.MyFragment;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.CategoryProduct;
import com.example.sallerapp.model.ShopAccount;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;


public class AddCategoryProductFragment extends Fragment {

    private FragmentAddCategoryProductBinding categoryProductBinding;
    ShopAccount shopAccount = SingleAccount.getInstance().getShopAccount();
    public AddCategoryProductFragment() {
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
        categoryProductBinding = FragmentAddCategoryProductBinding.inflate(inflater,container,false);
        initView();
        return categoryProductBinding.getRoot();
    }

    private void initView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        categoryProductBinding.adView.loadAd(adRequest);

        categoryProductBinding.btnSaveACP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCatePro();
            }
        });

        categoryProductBinding.imgBackACP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFragment.backFragment(requireActivity().getSupportFragmentManager()
                        , R.id.fragmentAddPro
                        , new CategoryProductFragment()
                        , true);
            }
        });

        categoryProductBinding.imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCatePro();
            }
        });
    }

    private void createCatePro() {
        int count = 0;
        if (Validations.isEmptyPress(categoryProductBinding.edtTenDanhMuc)){
            count ++;
        }

        if (count != 0){
            return;
        }

        CategoryProductDao.getCategoryProduct(shopAccount.getShopId(), new CategoryProductDao.GetData() {
            @Override
            public void getData(ArrayList<CategoryProduct> categoryProducts) {
                String id = IdGenerator.generateNextShopId(categoryProducts.size(),"LSP_");
                String nameCatePro = categoryProductBinding.edtTenDanhMuc.getText().toString();
                String note = categoryProductBinding.edtGhiChu.getText().toString();

                CategoryProduct categoryProduct = new CategoryProductBuilder()
                        .addId(id)
                        .addName(nameCatePro)
                        .addNote(note)
                        .build();

                CategoryProductDao.insertCategoryProduct(categoryProduct, shopAccount.getShopId());
                clearData();
            }
        });
    }

    private void clearData() {
        categoryProductBinding.edtTenDanhMuc.setText("");
        categoryProductBinding.edtGhiChu.setText("");
    }
}