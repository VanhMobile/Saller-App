package com.example.sallerapp.controller.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.sallerapp.R;
import com.example.sallerapp.adapter.ListProductAdapter;
import com.example.sallerapp.controller.view.ProductActivity;
import com.example.sallerapp.database.ProductDao;
import com.example.sallerapp.databinding.DialogAddProductBinding;
import com.example.sallerapp.databinding.FragmentCreateBillBinding;
import com.example.sallerapp.model.Product;

import java.util.ArrayList;

public class CreateBillFragment extends Fragment {

    private FragmentCreateBillBinding createBillBinding;
    ListProductAdapter productAdapter;

    public CreateBillFragment() {
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
        createBillBinding = FragmentCreateBillBinding.inflate(getLayoutInflater());
        initView();
        return createBillBinding.getRoot();
    }

    private void initView() {
        createBillBinding.tvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDiaLogAddPro();
            }
        });
    }

    private void ShowDiaLogAddPro() {
        DialogAddProductBinding addProductBinding = DialogAddProductBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(addProductBinding.getRoot());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT
                , WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ProductDao.getProducts("Shop_1", new ProductDao.GetData() {
            @Override
            public void getData(ArrayList<Product> products) {
                productAdapter = new ListProductAdapter(products, new ListProductAdapter.Click() {
                    @Override
                    public void clickBtnAdd(Product product) {
                        addCartShop(product);
                    }

                    @Override
                    public void clickItem(Product product) {
                        addCartShop(product);

                    }
                });

                addProductBinding.reyAddProDialog.setAdapter(productAdapter);
                DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
                addProductBinding.reyAddProDialog.addItemDecoration(itemDecoration);
                if (isAdded()){
                    addProductBinding.reyAddProDialog.setLayoutManager(new LinearLayoutManager(requireActivity()));
                }

                addProductBinding.searchProDialog.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        productAdapter.filter(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });

        addProductBinding.tvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ProductActivity.class);
                intent.putExtra("product", "addProduct");
                startActivity(intent);
            }
        });

        addProductBinding.saveDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addCartShop(Product product) {
    }
}