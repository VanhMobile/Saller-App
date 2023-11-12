package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.sallerapp.database.AccountDao;
import com.example.sallerapp.databinding.ActivitySignUpBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.AccountBuilder;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.ShopAccount;


public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding signUpBinding;
    int check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());
        initView();
    }

    private void initView() {

        AccountDao.GetShopAccounts(shopAccounts -> signUpBinding.userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // check trung user name ko ý là cái user name này có tồn tại chưa
                String userName = signUpBinding.userName.getText().toString();
                shopAccounts.forEach(o -> {
                    if (o.getShopId().equals(userName)) {
                        signUpBinding.userName.setError("user name đã tồn tại");
                        check++;
                    } else {
                        signUpBinding.userName.setError(null);
                        check =0;
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));
        Validations.isEmpty(signUpBinding.userName);
        Validations.isPassword(signUpBinding.password);
        Validations.isPassword(signUpBinding.confirmPass);
        signUpBinding.btnSignupSignup.setOnClickListener(v -> {
            int count = 0;
            if (Validations.isEmptyPress(signUpBinding.userName)) {
                count++;
            }

            if (!Validations.isEmptyPress(signUpBinding.password)) {
                if (!Validations.isPasswordPress(signUpBinding.password)) {
                    count++;
                }
            } else {
                count++;
            }

            if (!Validations.isEmptyPress(signUpBinding.confirmPass)) {
                if (!Validations.isPasswordPress(signUpBinding.confirmPass)) {
                    count++;
                }
            } else {
                count++;
            }
            if (!signUpBinding.password.getText().toString().equals(signUpBinding.confirmPass.getText().toString())) {
                signUpBinding.confirmPass.setError("Không trùng mật khẩu");
                count++;
            } else {
                signUpBinding.confirmPass.setError(null);
            }
            if (count != 0) {
                return;
            }


            // tạo account ở đây
            String userName = signUpBinding.userName.getText().toString();
            String pass = signUpBinding.password.getText().toString();
            ShopAccount shopAccount = new AccountBuilder()
                    .addIdAccount(userName)
                    .addPassword(pass)
                    .addShopName(userName)
                    .addAddress("")
                    .addEmail("")
                    .addNumberPhone("")
                    .build();

            AccountDao.insertShopAccount(shopAccount);

            if(check==0){
                Toast.makeText(SignUpActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
            if(check ==1){
                Toast.makeText(SignUpActivity.this, "User đã tồn tại, hãy tạo user khác", Toast.LENGTH_SHORT).show();
            }


        });

        signUpBinding.btnSignupLogin.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
    }
}