package com.example.sallerapp.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sallerapp.model.CategoryProduct;
import com.example.sallerapp.model.ShopAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccountDao {
    private static String TAG = AccountDao.class.getSimpleName();


    // hàm tạo tài khoản shop khi đăng ký fireBase kết hợp với build design pattern
    public static void insertShopAccount(ShopAccount account) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("ShopAccount").child(account.getShopId()).setValue(account);
    }


    // lấy list account shop
    public static void GetShopAccounts(GetData data){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        ArrayList<ShopAccount> shopAccounts = new ArrayList<>();
        db.child("ShopAccount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // check snap có dữ liệu ko
                if (snapshot.exists()){
                    // chạy vòng lặp thêm dữ liệu vào danh sách
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        ShopAccount shopAccount = dataSnapshot.getValue(ShopAccount.class);
                        shopAccounts.add(shopAccount);
                        Log.e(TAG,shopAccount.getShopId());
                    }

                    data.getData(shopAccounts);

                }else{
                    // snap ko có dữ liệu
                    Log.e(TAG,"không có dữ liệu trong ShopAccount");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Log ra lỗi không thể đọc đc
                Log.e(TAG,"không thể đọc dữ liệu");
            }
        });
    }

    public interface GetData{
        void getData(ArrayList<ShopAccount> shopAccounts);
    }
}
