package com.example.sallerapp.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sallerapp.model.CategoryProduct;
import com.example.sallerapp.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductDao {
    private static String TAG = ProductDao.class.getSimpleName();


    // hàm thêm một sản phẩm vào database vào firebase kết hợp build design pattern
    public static void insertProduct(Product product, String idShopAccount) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(idShopAccount)
                .child("Products")
                .child(product.getProductId())
                .setValue(product);
    }

    // đọc dữ liệu sản phẩm của một xuống
    public static void getProducts(String idShopAccount, GetData data){
        ArrayList<Product> products = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(idShopAccount).child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // check snap có dữ liệu ko
                if (snapshot.exists()){
                    // snap có dữ liệu chạy vòng lặp for insert nó vào list
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Product product = dataSnapshot.getValue(Product.class);
                        products.add(product);
                        Log.e(TAG,product.getProductId());
                    }
                } else {
                    // snap ko có dữ liệu
                    Log.e(TAG,"Không có dữ liệu trong products");
                }
                data.getData(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // lỗi đọc dữ liệu
                Log.e(TAG,"Không đọc được dữ liệu từ database: "+ error.toString());
            }
        });

    }

    public interface GetData{
        void getData(ArrayList<Product> products);
    }
}
