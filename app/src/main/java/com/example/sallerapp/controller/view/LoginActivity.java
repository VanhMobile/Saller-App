package com.example.sallerapp.controller.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sallerapp.MainActivity;
import com.example.sallerapp.R;
import com.example.sallerapp.database.AccountDao;
import com.example.sallerapp.databinding.ActivityLoginBinding;
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
        initView();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void initView() {

        Validations.isEmpty(loginBinding.userName);
        Validations.isPassword(loginBinding.password);

        loginBinding.btnLoginLogin.setOnClickListener(view -> {
                    int check = 0;
                    if (Validations.isEmptyPress(loginBinding.userName)) {
                        check++;
                        return;
                    }

                    if (!Validations.isEmptyPress(loginBinding.password)) {
                        if (!Validations.isPasswordPress(loginBinding.password)) {
                            check++;
                            return;
                        }
                    } else {
                        check++;
                        return;
                    }
                    AccountDao.GetShopAccounts(new AccountDao.GetData() {
                        int count = 0;

                        @Override
                        public void getData(ArrayList<ShopAccount> shopAccounts) {


                            String userName = loginBinding.userName.getText().toString();
                            String pass = loginBinding.password.getText().toString();

                            shopAccounts.forEach(o -> {

                                if (o.getShopId().equals(userName)) {
                                    loginBinding.userName.setError(null);
                                    count = 0;
                                } else {
                                    count=1000;
                                    return;
                                }
                                if (o.getPassword().equals(pass)) {
                                    loginBinding.password.setError(null);
                                    count = 0;
                                } else {
                                    loginBinding.password.setError("Sai pass");
                                    count++;

                                }
                                if(count==1000){
                                    loginBinding.userName.setError("Tên shop không tồn tại");
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
                                    account.setNumberPhone(o.getNumberPhone());
                                    account.setEmail(o.getEmail());
                                    account.setAddress(o.getAddress());

                                    singleAccount.setShopAccount(account);
                                    finish();
                                    Log.d("ACCOUNT", account + "");
                                }
                            });

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

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        SingleAccount singleAccount = SingleAccount.getInstance();
                        ShopAccount account = new ShopAccount();
                        //Set thông tin vào account
                        account.setShopId(user.getUid());
                        account.setShopName(user.getDisplayName());
                        account.setPassword("#mCTLan2003");
                        account.setNumberPhone(user.getPhoneNumber());
                        account.setEmail(user.getEmail());
                        account.setAddress("");

                        singleAccount.setShopAccount(account);
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}