package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sallerapp.R;
import com.example.sallerapp.controller.fragment.AddEmployeeFragment;
import com.example.sallerapp.controller.fragment.Fragment_list_customers;
import com.example.sallerapp.funtions.MyFragment;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        initView();
    }

    private void initView() {
        MyFragment.replaceFragment(CustomerActivity.this.getSupportFragmentManager()
                , R.id.fragmentCustomer
                , new Fragment_list_customers()
                , true);
    }
}