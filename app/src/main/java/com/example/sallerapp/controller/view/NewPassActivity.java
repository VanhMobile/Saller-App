package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sallerapp.R;
import com.example.sallerapp.database.AccountDao;
import com.example.sallerapp.databinding.ActivityNewPassBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.AccountBuilder;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.ShopAccount;

import java.util.ArrayList;

public class NewPassActivity extends AppCompatActivity {

    ActivityNewPassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {

        Validations.isPassword(binding.edtpass);
        Validations.isPassword(binding.edtpass2);

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = 0;
                if (!Validations.isEmptyPress(binding.edtpass)) {
                    if (!Validations.isPasswordPress(binding.edtpass)) {
                        check++;
                    }
                } else {
                    check++;
                }
                if (!Validations.isEmptyPress(binding.edtpass2)) {
                    if (!Validations.isPasswordPress(binding.edtpass2)) {
                        check++;
                    }
                } else {
                    check++;
                }

                if (!binding.edtpass.getText().toString().equals(binding.edtpass2.getText().toString())) {
                    binding.edtpass2.setError("Không trùng mật khẩu");
                    check++;
                } else {
                    binding.edtpass2.setError(null);
                }
                if (check != 0) {
                    return;
                }
                newpass();

            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewPassActivity.this,OtpConfirmActivity.class));
                finish();
            }
        });
    }

    private void newpass() {
        ShopAccount shopAccount = SingleAccount.getInstance().getShopAccount();
        String pass = binding.edtpass.getText().toString();
        shopAccount.setPassword(pass);
        AccountDao.insertShopAccount(shopAccount);
        Toast.makeText(this,"Đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}