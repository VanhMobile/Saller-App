package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sallerapp.R;
import com.example.sallerapp.controller.fragment.AddProductFragment;
import com.example.sallerapp.controller.fragment.CreateBillFragment;
import com.example.sallerapp.funtions.MyFragment;

public class BillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        initView();
    }

    private void initView() {
        MyFragment.replaceFragment(BillActivity.this.getSupportFragmentManager()
                , R.id.fragmentBill
                , new CreateBillFragment()
                , true);
    }
}