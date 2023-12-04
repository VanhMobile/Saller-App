package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.sallerapp.R;
import com.example.sallerapp.controller.fragment.AddEmployeeFragment;
import com.example.sallerapp.controller.fragment.AddProductFragment;
import com.example.sallerapp.controller.fragment.CategoryProductFragment;
import com.example.sallerapp.controller.fragment.Fragment_list_customers;
import com.example.sallerapp.controller.fragment.ListCategoryCustomerFragment;
import com.example.sallerapp.funtions.MyFragment;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String message = intent.getStringExtra("customer");
        if (message != null){
            switch (message){
                case "listCustomer":
                    MyFragment.replaceFragment(CustomerActivity.this.getSupportFragmentManager()
                            , R.id.fragmentCustomer
                            , new Fragment_list_customers()
                            , true);
                    break;
                case "categoryCustomer":
                    MyFragment.replaceFragment(CustomerActivity.this.getSupportFragmentManager()
                            , R.id.fragmentCustomer
                            , new ListCategoryCustomerFragment()
                            , true);
                    break;
            }
        }
    }
}