package com.example.sallerapp.controller.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.database.AccountDao;
import com.example.sallerapp.databinding.ActivitySignUpBinding;
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

import java.util.HashMap;


public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding signUpBinding;
    int check;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());
        initView();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void initView() {

        Validations.isEmpty(signUpBinding.userName);
        Validations.isPassword(signUpBinding.password);
        Validations.isPassword(signUpBinding.confirmPass);
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
                        signUpBinding.userName.setError("User name đã tồn tại");
                        check++;
                        return;
                    } else {
                        signUpBinding.userName.setError(null);
                        check = 0;
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));

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

            if (check == 0) {
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
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
            if (check == 1) {
                Toast.makeText(SignUpActivity.this, "User name đã tồn tại, hãy thay đổi user ", Toast.LENGTH_SHORT).show();
            }


        });

        signUpBinding.btnSignupLogin.setOnClickListener(v ->
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));


        signUpBinding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupGG();
            }
        });

    }

    private void signupGG() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("shopId", user.getUid());
                        map.put("shopName", user.getDisplayName());

                        database.getReference().child("user").child(user.getUid()).setValue(map);

                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);

                        ShopAccount shopAccount = new AccountBuilder()
                                .addIdAccount(user.getUid())
                                .addPassword(user.getDisplayName())
                                .addShopName(user.getDisplayName())
                                .addAddress("")
                                .addEmail(user.getDisplayName())
                                .addNumberPhone(user.getPhoneNumber())
                                .build();

                        AccountDao.insertShopAccount(shopAccount);

                        SingleAccount singleAccount = SingleAccount.getInstance();
                        ShopAccount account = new ShopAccount();
                        //Set thông tin vào account
                        account.setShopId(user.getUid());
                        account.setShopName(user.getDisplayName());
                        account.setPassword(user.getDisplayName());
                        account.setNumberPhone(user.getPhoneNumber());
                        account.setEmail(user.getEmail());
                        account.setAddress("");

                        singleAccount.setShopAccount(account);
                        finish();
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}