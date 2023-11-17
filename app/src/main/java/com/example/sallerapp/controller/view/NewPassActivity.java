package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

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

                if(check ==0){
                    newpass();
                }


            }
        });

    }

    private void newpass() {
        SharedPreferences preferences = getSharedPreferences("Phone_OTP", MODE_PRIVATE);

        String phone = preferences.getString("phone", "");
//        String otp = preferences.getString("otp", "");
        AccountDao.GetShopAccounts(new AccountDao.GetData() {
            int count = 0;
            @Override
            public void getData(ArrayList<ShopAccount> shopAccounts) {
                String newpass = binding.edtpass.getText().toString();
                shopAccounts.forEach(o ->{
                    if(o.getNumberPhone().equals(phone)){
                        count = 0;
                        startActivity(new Intent(NewPassActivity.this, LoginActivity.class));

                        SingleAccount singleAccount = SingleAccount.getInstance();
                        ShopAccount account = new ShopAccount();
                        //Set thông tin vào account
                        account.setPassword(newpass);
                        account.setNumberPhone(phone);
                        account.setShopId(o.getShopId());
                        account.setAddress(o.getAddress());
                        account.setEmail(o.getEmail());
                        account.setShopName(o.getShopName());

                        singleAccount.setShopAccount(account);


                        ShopAccount shopAccount = new AccountBuilder()
                                .addIdAccount(o.getShopId())
                                .addPassword(newpass)
                                .addNumberPhone(phone)
                                .addAddress(o.getAddress())
                                .addShopName(o.getShopName())
                                .addEmail(o.getEmail())
                                .build();

                        AccountDao.insertShopAccount(shopAccount);
                        finish();
                    }
                });

            }
        });
    }
}