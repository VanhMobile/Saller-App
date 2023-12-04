package com.example.sallerapp.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sallerapp.model.Bill;
import com.example.sallerapp.model.CategoryCustomer;
import com.example.sallerapp.model.CategoryProduct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryCustomerDao {

    private static String TAG = CategoryCustomerDao.class.getSimpleName();

    //thêm một danh mục khách hàng trong db
    public static void insertCategoryCustomer(CategoryCustomer categoryCustomer, String idShopAccount) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(idShopAccount)
                .child("CategoryCustomers")
                .child(categoryCustomer.getIdCategory())
                .setValue(categoryCustomer);
    }

    public static void getCategoryCustomers(String idShopAccount,GetData data){
        ArrayList<CategoryCustomer> categoryCustomers = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(idShopAccount)
                .child("CategoryCustomers")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                CategoryCustomer categoryCustomer = dataSnapshot.getValue(CategoryCustomer.class);
                                categoryCustomers.add(categoryCustomer);
                                Log.e(TAG,categoryCustomer.getIdCategory());
                            }
                        }else{
                            Log.e(TAG,"Ko có dữ liệu ở trong CategoryCustomers");
                        }
                        data.getData(categoryCustomers);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG,"Ko thể đọc dữ liệu: " + error.toString());
                    }
                });
    }

    public interface GetData{
        void getData(ArrayList<CategoryCustomer> categoryCustomers);
    }
}
