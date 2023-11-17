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


import com.example.sallerapp.databinding.ActivityConfirmPasswordBinding;
import com.example.sallerapp.funtions.Validations;

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

        Validations.isPhoneNumberPress(confirmPasswordBinding.edtPhoneOTP);

        confirmPasswordBinding.btnSendOTP.setOnClickListener(v -> {
            int check = 0;
            if(Validations.isEmptyPress(confirmPasswordBinding.edtPhoneOTP)){
                check++;
                return;
            }
            if(check ==0 ){
                if(ContextCompat.checkSelfPermission(ConfirmPasswordActivity.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    sendOTP();
                    startActivity(new Intent(ConfirmPasswordActivity.this, OtpConfirmActivity.class));

                } else{
                    ActivityCompat.requestPermissions(ConfirmPasswordActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, 100);
                }
            }

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
        Random random = new Random();
        int number = random.nextInt(89999)+10000;
        String otp = String.valueOf(number);
        String phone = confirmPasswordBinding.edtPhoneOTP.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(otp + " "+ message);
        String phoneOTP = phone;
        smsManager.sendMultipartTextMessage(phoneOTP, null, parts, null, null);

        SharedPreferences preferences = getApplication().getSharedPreferences("Phone_OTP", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("phone", phoneOTP);
        editor.putString("otp", otp);
        editor.apply();
    }
}