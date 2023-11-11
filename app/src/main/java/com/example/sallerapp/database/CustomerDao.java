package com.example.sallerapp.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sallerapp.model.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerDao {
    // thêm một Khách hàng vào db
    private static String TAG = CustomerDao.class.getSimpleName();
    public static void insertCustomer(com.example.sallerapp.model.Customer customer, String idShopAccount) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(idShopAccount)
                .child("Customers")
                .child(customer.getCustomerId())
                .setValue(customer);
    }

    // đọc dữ liệu sản phẩm của một xuống
    public static void getCustomers(String idShopAccount, GetData data){
        ArrayList<Customer> customers = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(idShopAccount).child("Customers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // check snap có dữ liệu ko
                if (snapshot.exists()){
                    // snap có dữ liệu chạy vòng lặp for insert nó vào list
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Customer customer = dataSnapshot.getValue(Customer.class);
                        customers.add(customer);
                        Log.e(TAG,customer.getCustomerId());
                    }
                    data.getData(customers);
                } else {
                    // snap ko có dữ liệu
                    Log.e(TAG,"Không có dữ liệu trong customers");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // lỗi đọc dữ liệu
                Log.e(TAG,"Không đọc được dữ liệu từ database: "+ error.toString());
            }
        });
    }

    public interface GetData{
        void getData(ArrayList<Customer> customers);
    }
}
