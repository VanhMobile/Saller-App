package com.example.sallerapp.controller.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.database.AccountDao;
import com.example.sallerapp.databinding.ActivityLoginBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.AccountBuilder;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.ShopAccount;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding loginBinding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        loginBinding.edtVt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("pls")
                        .setDescription("abc")
                        .setNegativeButtonText("Hủy")
                        .build();
                getPrompt().authenticate(promptInfo);
            }
        });
        initView();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void initView() {

        loginBinding.btnLoginLogin.setOnClickListener(view -> {
                    int check = 0;
                    if (Validations.isEmptyPress(loginBinding.userName)) {
                        check++;
                    }

                    if (!Validations.isEmptyPress(loginBinding.password)) {
                        if (!Validations.isPasswordPress(loginBinding.password)) {
                            check++;
                        }
                    } else {
                        check++;
                    }
                    if (check != 0){
                        return;
                    }
                    AccountDao.GetShopAccounts(new AccountDao.GetData() {
                        boolean isLogin = false;

                        @Override
                        public void getData(ArrayList<ShopAccount> shopAccounts) {


                            String userName = loginBinding.userName.getText().toString();
                            String pass = loginBinding.password.getText().toString();

                            for (ShopAccount item: shopAccounts) {
                                if (((item.getNumberPhone().equals(userName))|| (item.getEmail().equals(userName)))&&(item.getPassword().equals(pass))){
                                    SingleAccount.getInstance().setShopAccount(item);
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                    isLogin=true;
                                    break;
                                }
                            }
                            if (!isLogin){
                                Toast.makeText(LoginActivity.this, "Thông tin đăng nhập sai", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
        );

        loginBinding.btnLoginSignup.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));

        loginBinding.txtRemember.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ConfirmPasswordActivity.class)));

        loginBinding.btnGoogle.setOnClickListener(v -> {
            loginGG();
        });
    }


    private void loginGG() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        AccountDao.GetShopAccounts(new AccountDao.GetData() {
                            boolean isCheck = false;
                            FirebaseUser user = mAuth.getCurrentUser();
                            @Override
                            public void getData(ArrayList<ShopAccount> shopAccounts) {
                                for (ShopAccount account : shopAccounts){
                                    if (account.getShopId().equals(user.getUid()) || account.getEmail().equals(user.getEmail())){
                                        SingleAccount.getInstance().setShopAccount(account);
                                        isCheck = true;
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                                if (!isCheck){
                                    ShopAccount shopAccount = new AccountBuilder()
                                            .addIdAccount(user.getUid())
                                            .addShopName("My Shop")
                                            .addEmail(user.getEmail())
                                            .addNumberPhone(user.getPhoneNumber()+"")
                                            .addPassword("#mBBmyShop123")
                                            .addAddress("")
                                            .build();
                                    AccountDao.insertShopAccount(shopAccount);
                                    SingleAccount.getInstance().setShopAccount(shopAccount);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private BiometricPrompt getPrompt(){
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                notifyUser(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                notifyUser("Đăng nhập thành công");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                notifyUser("Vui lòng thử lại");
            }
        };
        BiometricPrompt biometricPrompt = new BiometricPrompt(this , executor , callback);
        return biometricPrompt;

    }
    private void notifyUser(String message){
        Toast.makeText(this , message , Toast.LENGTH_SHORT).show();
    }

}