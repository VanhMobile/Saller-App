package com.example.sallerapp.controller.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.databinding.ActivityLoginGoogleBinding;
import com.example.sallerapp.funtions.Validations;
import com.google.firebase.auth.FirebaseAuth;

public class GoogleActivity extends AppCompatActivity {
    private ActivityLoginGoogleBinding loginGoogleBinding;
    int check;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginGoogleBinding = ActivityLoginGoogleBinding.inflate(getLayoutInflater());
        setContentView(loginGoogleBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        initView();
    }

    private void initView() {

        loginGoogleBinding.btnConfirm.setOnClickListener(v -> {
                    int count = 0;
                    if (Validations.isEmptyPress(loginGoogleBinding.googleEmail)) {
                        count++;
                    }

                    if (!Validations.isEmptyPress(loginGoogleBinding.googlePassword)) {
                        if (!Validations.isPasswordPress(loginGoogleBinding.googlePassword)) {
                            count++;
                        }
                    } else {
                        count++;
                    }
                    if (count != 0) {
                        return;
                    }
                    String email = loginGoogleBinding.googleEmail.getText().toString();
                    String pass = loginGoogleBinding.googlePassword.getText().toString();

                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(GoogleActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(GoogleActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(GoogleActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

        );


        loginGoogleBinding.btnBack.setOnClickListener(v -> startActivity(new Intent(GoogleActivity.this, LoginActivity.class)));
    }
}
