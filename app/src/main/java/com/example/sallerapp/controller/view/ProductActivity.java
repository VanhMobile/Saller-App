package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sallerapp.R;
import com.example.sallerapp.controller.fragment.AddProductFragment;
import com.example.sallerapp.funtions.MyFragment;

public class ProductActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initView();
    }

    private void initView() {
        MyFragment.replaceFragment(ProductActivity.this.getSupportFragmentManager()
                , R.id.fragmentAddPro
                , new AddProductFragment()
                , true);
    }
}