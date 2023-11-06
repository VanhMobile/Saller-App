package com.example.sallerapp.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sallerapp.model.CategoryProduct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryProductDao {

    private static String TAG = CategoryProductDao.class.getSimpleName();
    private static ArrayList<CategoryProduct> categoryProducts = new ArrayList<>();

    // thêm một loại sản phảm vào trong db
    public static void insertCategoryProduct(CategoryProduct categoryProduct, String idShopAccount) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(idShopAccount)
                .child("CategoryProducts")
                .child(categoryProduct.getIdCategory())
                .setValue(categoryProduct);
    }

    public static ArrayList<CategoryProduct> getCategoryProduct(String idShopAccount){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(idShopAccount)
                .child("CategoryProducts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                CategoryProduct categoryProduct = dataSnapshot.getValue(CategoryProduct.class);
                                categoryProducts.add(categoryProduct);
                                Log.e(TAG,categoryProduct.getIdCategory());
                            }
                        }else{
                            Log.e(TAG,"Ko có dữ liệu ở trong bills");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG,"Ko thể đọc dữ liệu: " + error.toString());
                    }
                });
        return categoryProducts;
    }
}
