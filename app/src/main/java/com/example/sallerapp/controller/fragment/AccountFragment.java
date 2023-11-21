package com.example.sallerapp.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.sallerapp.controller.view.LoginActivity;
import com.example.sallerapp.database.AccountDao;
import com.example.sallerapp.databinding.DialogUpdatePasswordBinding;
import com.example.sallerapp.databinding.FragmentAccountBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.AccountBuilder;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.ShopAccount;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding accountBinding;
    ShopAccount shopAccount = SingleAccount.getInstance().getShopAccount();
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private ArrayList<ShopAccount> accounts;



    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        accountBinding = FragmentAccountBinding.inflate(inflater, container, false);
        initView();
        return accountBinding.getRoot();
    }

    private void initView() {
        accounts = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        accountBinding.afNameShop.setText(shopAccount.getShopName());
        accountBinding.afAddressShop.setText(shopAccount.getAddress());
        accountBinding.afEmailShop.setText(shopAccount.getEmail());
        accountBinding.afPhonenumberShop.setText(shopAccount.getNumberPhone());

        AccountDao.GetShopAccounts(new AccountDao.GetData() {
            @Override
            public void getData(ArrayList<ShopAccount> shopAccounts) {
                accounts.clear();
                accounts.addAll(shopAccounts);
            }
        });
        accountBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { 
                logout();
            }
        });

        accountBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn muốn sửa lại thông tin shop");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       upDateInforShopAccount();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        accountBinding.afUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUpdatePasswordBinding updatePasswordBinding = DialogUpdatePasswordBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(requireContext());
                dialog.setContentView(updatePasswordBinding.getRoot());
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                updatePasswordBinding.passwordOld.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.toString().equals(shopAccount.getPassword())){
                            updatePasswordBinding.errorMass.setVisibility(View.GONE);
                        }else{
                            updatePasswordBinding.errorMass.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                Validations.isPassword(updatePasswordBinding.passwordNew,updatePasswordBinding.errorMass2);
                updatePasswordBinding.btnUpDataPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int count = 0;
                        if (updatePasswordBinding.passwordOld.getText().toString().trim().equals(shopAccount.getPassword())){
                            updatePasswordBinding.errorMass.setVisibility(View.GONE);
                        }else{
                            updatePasswordBinding.errorMass.setVisibility(View.VISIBLE);
                            count ++;
                        }

                        if (!Validations.isPasswordPress(updatePasswordBinding.passwordNew,updatePasswordBinding.errorMass2)){
                            count ++;
                        }

                        if (count != 0){
                            return;
                        }

                        shopAccount.setPassword(updatePasswordBinding.passwordNew.getText().toString().trim());
                        AccountDao.insertShopAccount(shopAccount);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void upDateInforShopAccount() {
        int count = 0;

        if (Validations.isEmptyPress(accountBinding.afNameShop)){
            count ++;
        }

        if (!Validations.isEmptyPress(accountBinding.afEmailShop)){
            if (!Validations.isEmailPress(accountBinding.afEmailShop)){
                count ++;
            }
        }else{
            count ++;
        }

        if (!Validations.isEmptyPress(accountBinding.afPhonenumberShop)){
            if (!Validations.isPhoneNumberPress(accountBinding.afPhonenumberShop)){
                count ++;
            }
        }else{
            count ++;
        }

        if (Validations.isEmptyPress(accountBinding.afAddressShop)){
            count ++;
        }

        for (ShopAccount item : accounts){
            if (!item.getShopId().equals(shopAccount.getShopId())){
                if (item.getEmail().equals(accountBinding.afEmailShop.getText().toString().trim())){
                    Toast.makeText(requireContext(),"Email đã tồn tại",Toast.LENGTH_SHORT).show();
                    count ++;
                }
                if (item.getNumberPhone().equals(accountBinding.afPhonenumberShop.getText().toString().trim())){
                    Toast.makeText(requireContext(),"Sđt đã tồn tại",Toast.LENGTH_SHORT).show();
                    count ++;
                }
            }
        }

        if (count != 0){
            return;
        }

        String nameShop =  accountBinding.afNameShop.getText().toString().trim();
        String emailShop = accountBinding.afEmailShop.getText().toString().trim();
        String numberPhone = accountBinding.afPhonenumberShop.getText().toString().trim();
        String address = accountBinding.afAddressShop.getText().toString().trim();

        shopAccount.setShopName(nameShop);
        shopAccount.setAddress(address);
        shopAccount.setEmail(emailShop);
        shopAccount.setNumberPhone(numberPhone);

        AccountDao.insertShopAccount(shopAccount);
    }

    private void logout() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth.signOut();
                googleSignInClient.signOut();
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
                // Chuyển đến màn hình đăng nhập hoặc màn hình khác theo nhu cầu của bạn
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}