package com.example.sallerapp.controller.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;


import com.example.sallerapp.database.AccountDao;
import com.example.sallerapp.databinding.ActivityConfirmPasswordBinding;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.ShopAccount;

import java.util.ArrayList;
import java.util.Random;

public class ConfirmPasswordActivity extends AppCompatActivity {
    private ActivityConfirmPasswordBinding confirmPasswordBinding;


    String message = "là mã otp của bạn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        confirmPasswordBinding = ActivityConfirmPasswordBinding.inflate(getLayoutInflater());
        setContentView(confirmPasswordBinding.getRoot());
        initView();
    }

    private void initView() {

        confirmPasswordBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfirmPasswordActivity.this, LoginActivity.class));
                finish();
            }
        });

        Validations.isPhoneNumber(confirmPasswordBinding.edtPhoneOTP);

        confirmPasswordBinding.btnSendOTP.setOnClickListener(v -> {
            AccountDao.GetShopAccounts(new AccountDao.GetData() {
                @Override
                public void getData(ArrayList<ShopAccount> shopAccounts) {
                    int check = 0;
                    if(!Validations.isEmptyPress(confirmPasswordBinding.edtPhoneOTP)){
                        if (!Validations.isPhoneNumberPress(confirmPasswordBinding.edtPhoneOTP)){
                            check ++;
                        }
                    }else{
                        check ++;
                    }
                    for (ShopAccount item: shopAccounts){
                        if (item.getNumberPhone().equals(confirmPasswordBinding.edtPhoneOTP.getText().toString())){
                            if(check ==0 ){
                                if(ContextCompat.checkSelfPermission(ConfirmPasswordActivity.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                                    sendOTP();
                                    SingleAccount.getInstance().setShopAccount(item);
                                } else{
                                    ActivityCompat.requestPermissions(ConfirmPasswordActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, 100);
                                }
                            }
                        }
                    }
                }
            });
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==100){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                sendOTP();
            }else {
                Toast.makeText(this, "Permission deniel!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void sendOTP() {
        int min = 100000;
        int max = 999999;
        Random random = new Random();
        int randomNumber = random.nextInt((max - min) + 1) + min;
        String otp = String.valueOf(randomNumber);
        String phone = confirmPasswordBinding.edtPhoneOTP.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(otp + " "+ message);
        String phoneOTP = phone;
        smsManager.sendMultipartTextMessage(phoneOTP, null, parts, null, null);
        Intent intent = new Intent(ConfirmPasswordActivity.this, OtpConfirmActivity.class);
        intent.putExtra("otp",otp);
        startActivity(intent);
    }
}