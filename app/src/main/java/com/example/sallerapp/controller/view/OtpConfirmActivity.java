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
        Intent intent = getIntent();
        if (intent == null) return;
        String otp = intent.getStringExtra("otp");
        binding.btnConfirm.setOnClickListener(v -> {
            int check = 0;
            if (Validations.isEmptyPress(binding.edtCheckOTP)) {
                check++;
            }
            if (check != 0){
                return;
            }
            if (binding.edtCheckOTP.getText().toString().equals(otp)) {
                startActivity(new Intent(OtpConfirmActivity.this, NewPassActivity.class));
                finish();
            }else{
                Toast.makeText(OtpConfirmActivity.this, "Sai mã xác nhận", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OtpConfirmActivity.this, ConfirmPasswordActivity.class));
                finish();
            }
        });
    }
}