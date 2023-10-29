package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sallerapp.R;
import com.example.sallerapp.controller.fragment.AddEmployeeFragment;
import com.example.sallerapp.controller.fragment.CreateBillFragment;
import com.example.sallerapp.funtions.MyFragment;

public class EmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        initView();
    }

    private void initView() {
        MyFragment.replaceFragment(EmployeeActivity.this.getSupportFragmentManager()
                , R.id.fragmentEmployee
                , new AddEmployeeFragment()
                , true);
    }
}