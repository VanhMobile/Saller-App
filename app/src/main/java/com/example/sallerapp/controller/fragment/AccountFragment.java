package com.example.sallerapp.controller.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.sallerapp.database.AccountDao;
import com.example.sallerapp.databinding.FragmentAccountBinding;
import com.example.sallerapp.desgin_pattern.build_pantter.AccountBuilder;
import com.example.sallerapp.desgin_pattern.single_pantter.SingleAccount;
import com.example.sallerapp.funtions.Validations;
import com.example.sallerapp.model.ShopAccount;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding accountBinding;
    private SingleAccount singleAccount = SingleAccount.getInstance();
    private ShopAccount currentAccount = singleAccount.getShopAccount();
    int check = 0;

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

        Validations.isEmpty(accountBinding.afNameShop);

        accountBinding.afUpdatePassword.setOnClickListener(v -> update());

        accountBinding.btnLogout.setOnClickListener(view -> getActivity().finish());


    }

    private void update() {
        String nameShop, email, numberphone, address;
        nameShop = accountBinding.afNameShop.getText().toString();
        email = accountBinding.afEmailShop.getText().toString();
        numberphone = accountBinding.afPhonenumberShop.getText().toString();
        address = accountBinding.afAddressShop.getText().toString();

        int count = 0;
        if (Validations.isEmptyPress(accountBinding.afNameShop)) {
            count++;
        }
        if(!Validations.isEmptyPress(accountBinding.afPhonenumberShop)){
            if(!Validations.isPhoneNumberPress(accountBinding.afPhonenumberShop)){

                count++;
            }
        }
        if (!Validations.isEmptyPress(accountBinding.afEmailShop)){
            if(!Validations.isEmailPress(accountBinding.afEmailShop)){

                count++;
            }
        }
        if (count != 0) {
            return;
        }

        if(count ==0){
            SingleAccount singleAccount = SingleAccount.getInstance();
            ShopAccount account = new ShopAccount();
            //Set thông tin vào account
            account.setShopName(nameShop);
            account.setNumberPhone(numberphone);
            account.setEmail(email);
            account.setAddress(address);
            singleAccount.setShopAccount(account);


            ShopAccount shopAccount = new AccountBuilder()
                    .addIdAccount(currentAccount.getShopId())
                    .addPassword(currentAccount.getPassword())
                    .addShopName(nameShop)
                    .addAddress(address)
                    .addEmail(email)
                    .addNumberPhone(numberphone)
                    .build();
            AccountDao.insertShopAccount(shopAccount);

            Log.d("ACCOUNT", account + "");
        }



    }

//    private void checkNameShop() {
//        AccountDao.GetShopAccounts(shopAccounts -> accountBinding.afNameShop.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                String nameShop = accountBinding.afNameShop.getText().toString();
//                shopAccounts.forEach(o -> {
//                    if (o.getShopName().equals(nameShop)) {
//                        accountBinding.afNameShop.setError("Tên shop bị trùng, vui lòng đổi");
//                        check++;
//                        return;
//                    } else {
//                        accountBinding.afNameShop.setError(null);
//                        check = 0;
//                    }
//                    if (check != 0) {
//                        return;
//                    }
//                });
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        }));
//
//
//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountBinding.afNameShop.setText(currentAccount.getShopName());
        accountBinding.afEmailShop.setText(currentAccount.getEmail());
        accountBinding.afPhonenumberShop.setText(currentAccount.getNumberPhone());
        accountBinding.afAddressShop.setText(currentAccount.getAddress());

        update();
        Log.d("Loggggg", "" + currentAccount.getShopName());


    }
}