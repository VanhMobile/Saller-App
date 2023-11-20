package com.example.sallerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sallerapp.controller.fragment.AccountFragment;
import com.example.sallerapp.controller.fragment.AddProductFragment;
import com.example.sallerapp.controller.fragment.Fragment_list_bill;
import com.example.sallerapp.controller.fragment.HomeFragment;
import com.example.sallerapp.controller.fragment.ListEmployeeFragment;
import com.example.sallerapp.controller.fragment.ListProductsFragment;
import com.example.sallerapp.controller.view.NetworkChangeActivity;
import com.example.sallerapp.databinding.ActivityMainBinding;
import com.example.sallerapp.funtions.MyFragment;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private boolean doubleBackToExitPressedOnce = false;

    BroadcastReceiver networkChange = new NetworkChangeActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        initView();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();

        // Đặt thời gian chờ để reset trạng thái doubleBackToExitPressedOnce
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                },
                2000 // 2 giây
        );
    }

    private void initView() {
        MyFragment.replaceFragment(this.getSupportFragmentManager()
                , R.id.fragmentContainer
                , new HomeFragment()
                , false);

        // ánh xạ sự kiện khi nhấn vào bottom nav view

        mainBinding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottomNavHome){
                    MyFragment.replaceFragment(MainActivity.this.getSupportFragmentManager()
                            , R.id.fragmentContainer
                            , new HomeFragment()
                            , false);
                } else if (item.getItemId() == R.id.bottomNavBill) {
                    MyFragment.replaceFragment(MainActivity.this.getSupportFragmentManager()
                            , R.id.fragmentContainer
                            , new Fragment_list_bill()
                            , false);
                } else if (item.getItemId() == R.id.bottomNavEmployee) {
                    MyFragment.replaceFragment(MainActivity.this.getSupportFragmentManager()
                            , R.id.fragmentContainer
                            , new ListEmployeeFragment()
                            , false);
                } else if (item.getItemId() == R.id.bottomNavProduct) {
                    MyFragment.replaceFragment(MainActivity.this.getSupportFragmentManager()
                            , R.id.fragmentContainer
                            , new ListProductsFragment()
                            , false);
                } else if (item.getItemId() == R.id.bottomNavAccount) {
                    MyFragment.replaceFragment(MainActivity.this.getSupportFragmentManager()
                            , R.id.fragmentContainer
                            , new AccountFragment()
                            , false);
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Đăng ký BroadcastReceiver khi activity hoạt động
        registerReceiver(networkChange, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Hủy đăng ký BroadcastReceiver khi activity không còn hoạt động
        unregisterReceiver(networkChange);
    }
}