package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.sallerapp.R;
import com.example.sallerapp.controller.fragment.AddProductFragment;
import com.example.sallerapp.controller.fragment.CategoryProductFragment;
import com.example.sallerapp.controller.fragment.CreateBillFragment;
import com.example.sallerapp.funtions.MyFragment;

public class ProductActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String message = intent.getStringExtra("product");
        if (message != null){
            switch (message){
                case "addProduct":
                    MyFragment.replaceFragment(ProductActivity.this.getSupportFragmentManager()
                            , R.id.fragmentAddPro
                            , new AddProductFragment()
                            , true);
                    break;
                case "categoryProduct":
                    MyFragment.replaceFragment(ProductActivity.this.getSupportFragmentManager()
                            , R.id.fragmentAddPro
                            , new CategoryProductFragment()
                            , true);
                    break;
            }
        }
    }
}