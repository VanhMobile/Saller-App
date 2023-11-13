package com.example.sallerapp.controller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.database.AccountDao;
import com.example.sallerapp.databinding.ActivityLoginBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.AccountBuilder;
import com.example.sallerapp.desgin_pattern.single_pantter.CartShopSingle;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.Product;
import com.example.sallerapp.model.ShopAccount;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding loginBinding;
    private FirebaseAuth mAuth;
//    GoogleSignInClient mGoogleSignInClient;
//    GoogleSignInOptions gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        initView();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void initView() {

        Validations.isEmpty(loginBinding.userName);
        Validations.isPassword(loginBinding.password);
        loginBinding.btnLoginLogin.setOnClickListener(view ->
                AccountDao.GetShopAccounts(new AccountDao.GetData() {
                    int count = 0;

                    @Override
                    public void getData(ArrayList<ShopAccount> shopAccounts) {
                        String userName = loginBinding.userName.getText().toString();
                        String pass = loginBinding.password.getText().toString();
                        if (Validations.isEmptyPress(loginBinding.userName)) {
                            count++;
                            return;
                        }

                        if (!Validations.isEmptyPress(loginBinding.password)) {
                            if (!Validations.isPasswordPress(loginBinding.password)) {
                                count++;
                                return;
                            }
                        } else {
                            count++;
                            return;
                        }
                        shopAccounts.forEach(o -> {

                            if (o.getShopId().equals(userName)) {
                                loginBinding.userName.setError(null);
                                count = 0;
                            } else {
                                loginBinding.userName.setError("Không tồn tại shop");
                                count++;
                                return;
                            }
                            if (o.getPassword().equals(pass)) {
                                loginBinding.password.setError(null);
                                count = 0;
                            } else {
                                loginBinding.password.setError("Sai pass");
                                count++;

                            }
                            if (count != 0) {
//                                    Toast.makeText(LoginActivity.this, "Bạn quên mật khẩu?", Toast.LENGTH_SHORT).show();

                                return;
                            }

                            Log.d("COUNT", count + "");

                            if (count == 0) {
                                Toast.makeText(LoginActivity.this, "Login thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                SingleAccount singleAccount = SingleAccount.getInstance();
                                ShopAccount account = new ShopAccount();
                                //Set thông tin vào account
                                account.setShopId(userName);
                                account.setShopName(userName);
                                account.setPassword(pass);
                                account.setNumberPhone("");
                                account.setEmail("");
                                account.setAddress("");

                                singleAccount.setShopAccount(account);
                                finish();
                                Log.d("ACCOUNT", account + "");
                            }
                        });

                    }
                }));

        loginBinding.btnLoginSignup.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));

        loginBinding.btnGoogle.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, GoogleActivity.class));
            Log.d("ACCOUNT", "Da click gg");
            finish();
        });
    }


}