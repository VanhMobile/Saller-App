package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sallerapp.R;
import com.example.sallerapp.databinding.ActivityOtpConfirmBinding;
import com.example.sallerapp.funtions.Validations;

public class OtpConfirmActivity extends AppCompatActivity {

    ActivityOtpConfirmBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        SharedPreferences preferences = getSharedPreferences("Phone_OTP", MODE_PRIVATE);

//        String phone = preferences.getString("phone", "");
        String otp = preferences.getString("otp", "");
        binding.btnConfirm.setOnClickListener(v -> {
            int check = 0;
            if(Validations.isEmptyPress(binding.edtCheckOTP)){
                check++;
                return;
            }
            if(check ==0 ){
                if(binding.edtCheckOTP.getText().toString().equals(otp)){
//                        Toast.makeText(OtpConfirmActivity.this, "", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OtpConfirmActivity.this, NewPassActivity.class));
                    finish();
                }
            }

        });
    }
}