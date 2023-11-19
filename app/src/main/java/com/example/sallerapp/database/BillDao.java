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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
                .orderByChild("date")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                Bill bill = dataSnapshot.getValue(Bill.class);
                                bills.add(0,bill);
                                Log.e(TAG,bill.getBillId());
                            }
                        }else{
                            Log.e(TAG,"Ko có dữ liệu ở trong bills");
                        }
                        data.getData(bills);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG,"Ko thể đọc dữ liệu: " + error.toString());
                    }
                });
    }

    public static void bill30Day(String idShopAccount, GetData data){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        ArrayList<Bill> bills = new ArrayList<>();
        // Lấy ngày đầu tiên và cuối cùng của tháng hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date endDate = calendar.getTime();

        // Định dạng ngày thành chuỗi để so sánh với dữ liệu trong Firebase
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String startDateString = dateFormat.format(startDate);
        Log.e(TAG,startDateString);
        String endDateString = dateFormat.format(endDate);
        Log.e(TAG,endDateString);
        db.child(idShopAccount)
                .child("Bills").orderByChild("date").startAt(startDateString).endAt(endDateString)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                Bill bill = dataSnapshot.getValue(Bill.class);
                                bills.add(bill);
                                Log.e(TAG,bill.getBillId());
                            }
                        }else{
                            Log.e(TAG,"Ko có dữ liệu ở trong bills");
                        }
                        data.getData(bills);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG,"Ko thể đọc dữ liệu: " + error.toString());
                    }
                });
    }


    public static void billDay(String idShopAccount, GetData data){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        ArrayList<Bill> bills = new ArrayList<>();
        // Lấy ngày đầu tiên và cuối cùng của tháng hiện tại
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // Định dạng ngày thành chuỗi để so sánh với dữ liệu trong Firebase
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String startDateString = dateFormat.format(now);
        db.child(idShopAccount)
                .child("Bills").orderByChild("date").equalTo(startDateString)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                Bill bill = dataSnapshot.getValue(Bill.class);
                                bills.add(bill);
                                Log.e(TAG,bill.getBillId());
                            }
                        }else{
                            Log.e(TAG,"Ko có dữ liệu ở trong bills");
                        }
                        data.getData(bills);
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
