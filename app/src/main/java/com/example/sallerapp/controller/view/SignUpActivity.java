package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.sallerapp.R;
import com.example.sallerapp.database.AccountDao;
import com.example.sallerapp.databinding.ActivitySignUpBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.AccountBuilder;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.ShopAccount;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding signUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());
        initView();
    }

    private void initView() {

        AccountDao.GetShopAccounts(new AccountDao.GetData() {
            @Override
            public void getData(ArrayList<ShopAccount> shopAccounts) {
                signUpBinding.userName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // check trung user name ko ý là cái user name này có tồn tại chưa
                        String userName = signUpBinding.userName.getText().toString();
                        shopAccounts.forEach(o -> {
                            if (o.getShopId().equals(userName)){
                                signUpBinding.userName.setError("user name đã tồn tại");
                            }else {
                                signUpBinding.userName.setError(null);
                            }
                        });
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        Validations.isEmpty(signUpBinding.userName);
        Validations.isPassword(signUpBinding.password);
        Validations.isPassword(signUpBinding.confirmPass);
        signUpBinding.btnSignupSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                if (Validations.isEmptyPress(signUpBinding.userName)){
                    count ++;
                }

                if (!Validations.isEmptyPress(signUpBinding.password)){
                    if (!Validations.isPasswordPress(signUpBinding.password)){
                        count ++;
                    }
                }else {
                    count ++;
                }

                if (!Validations.isEmptyPress(signUpBinding.confirmPass)){
                    if (!Validations.isPasswordPress(signUpBinding.confirmPass)){
                        count ++;
                    }
                }else {
                    count ++;
                }

                if (count != 0){
                    return;
                }

                if (!signUpBinding.password.getText().toString().equals(signUpBinding.confirmPass.getText().toString())){
                    signUpBinding.confirmPass.setError("Không trùng mật khẩu");
                }else {
                    signUpBinding.confirmPass.setError(null);
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
                Toast.makeText(SignUpActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}