package com.example.sallerapp.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sallerapp.model.CategoryCustomer;
import com.example.sallerapp.model.CategoryProduct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CategoryProductDao {

    private static String TAG = CategoryProductDao.class.getSimpleName();

    // thêm một loại sản phảm vào trong db
    public static void insertCategoryProduct(CategoryProduct categoryProduct, String idShopAccount) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(idShopAccount)
                .child("CategoryProducts")
                .child(categoryProduct.getIdCategory())
                .setValue(categoryProduct);
    }

    public static ArrayList<CategoryProduct> getCategoryProduct(String idShopAccount, GetData data) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        ArrayList<CategoryProduct> categoryProducts = new ArrayList<>();
        db.child(idShopAccount)
                .child("CategoryProducts").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                CategoryProduct categoryProduct = dataSnapshot.getValue(CategoryProduct.class);
                                categoryProducts.add(categoryProduct);
                                Log.e(TAG,categoryProducts.size()+"");
                            }
                        }
                        data.getData(categoryProducts);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG,error.toString());
                    }
                });
        return categoryProducts;
    }

    public interface GetData{
        void getData(ArrayList<CategoryProduct> categoryProducts);
    }
}
