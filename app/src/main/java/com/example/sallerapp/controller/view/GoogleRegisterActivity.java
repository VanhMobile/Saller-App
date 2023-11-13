package com.example.sallerapp.controller.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.database.AccountDao;
import com.example.sallerapp.databinding.ActivitySignupGoogleBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.AccountBuilder;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.ShopAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GoogleRegisterActivity extends AppCompatActivity {
    private ActivitySignupGoogleBinding signupGoogleBinding;
    int check;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signupGoogleBinding = ActivitySignupGoogleBinding.inflate(getLayoutInflater());
        setContentView(signupGoogleBinding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        initView();
    }

    private void initView() {

        AccountDao.GetShopAccounts(shopAccounts -> signupGoogleBinding.googleEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = signupGoogleBinding.googleEmail.getText().toString();

                String[] parts = email.split("@");
                String userName = parts[0];
                shopAccounts.forEach(o -> {
                    if (o.getEmail().equals(email) || o.getShopId().equals(userName)) {
                        signupGoogleBinding.googleEmail.setError("Email đã tồn tại");
                        check++;
                        return;
                    } else {
                        signupGoogleBinding.googleEmail.setError(null);
                        check = 0;
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));

        Validations.isEmpty(signupGoogleBinding.googleEmail);
        Validations.isPassword(signupGoogleBinding.googlePassword);
        signupGoogleBinding.btnConfirm.setOnClickListener(v -> {
            int count = 0;
            if (Validations.isEmptyPress(signupGoogleBinding.googleEmail)) {
                count++;
            }

            if (!Validations.isEmptyPress(signupGoogleBinding.googlePassword)) {
                if (!Validations.isPasswordPress(signupGoogleBinding.googlePassword)) {
                    count++;
                }
            } else {
                count++;
            }
            if (count != 0) {
                return;
            }
            String email = signupGoogleBinding.googleEmail.getText().toString();
            String pass = signupGoogleBinding.googlePassword.getText().toString();


            String[] parts = email.split("@");
            String userName = parts[0];
            if(check==0){
            SingleAccount singleAccount = SingleAccount.getInstance();
            ShopAccount account = new ShopAccount();
            //Set thông tin vào account
            account.setShopId(userName);
            account.setShopName(userName);
            account.setPassword(pass);
            account.setNumberPhone("");
            account.setEmail(email);
            account.setAddress("");

            singleAccount.setShopAccount(account);
            Log.d("ACCOUNT", account+ "");


            ShopAccount shopAccount;
            shopAccount = new AccountBuilder()
                    .addIdAccount(userName)
                    .addPassword(pass)
                    .addShopName(userName)
                    .addAddress("")
                    .addEmail(email)
                    .addNumberPhone("")
                    .build();

            AccountDao.insertShopAccount(shopAccount);

                Toast.makeText(GoogleRegisterActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(GoogleRegisterActivity.this, MainActivity.class));
                finish();
            }
            if(check !=0){
                Toast.makeText(GoogleRegisterActivity.this, "User đã tồn tại, hãy tạo user khác", Toast.LENGTH_SHORT).show();
                return;
            }
//            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
//
//                if (task.isSuccessful()) {
//                    Toast.makeText(GoogleRegisterActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(GoogleRegisterActivity.this, MainActivity.class));
//                } else {
//                    Toast.makeText(GoogleRegisterActivity.this, "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
//                    Log.d("Logggg", "" + task);
//                }
//            });

        });

        signupGoogleBinding.btnBack.setOnClickListener(v -> startActivity(new Intent(GoogleRegisterActivity.this, SignUpActivity.class)));
    }
}
