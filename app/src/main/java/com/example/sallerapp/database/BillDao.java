package com.example.sallerapp.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sallerapp.model.Bill;
import com.example.sallerapp.model.CategoryProduct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BillDao {

    private static String TAG = BillDao.class.getSimpleName();

    // thêm một hóa đơn vào db
    public static void insertBill(Bill bill, String idShopAccount){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(idShopAccount)
                .child("Bills")
                .child(bill.getBillId())
                .setValue(bill);
        Log.e(TAG,"Thêm sản phẩm thành công");
    }

    public static void GetBills(String idShopAccount, GetData data){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        ArrayList<Bill> bills = new ArrayList<>();
        db.child(idShopAccount)
                .child("Bills")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                Bill bill = dataSnapshot.getValue(Bill.class);
                                bills.add(bill);
                                Log.e(TAG,bill.getBillId());
                            }
                            data.getData(bills);
                        }else{
                            Log.e(TAG,"Ko có dữ liệu ở trong bills");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG,"Ko thể đọc dữ liệu: " + error.toString());
                    }
                });
    }

    public interface GetData{
        void getData(ArrayList<Bill> bills);
    }
}
