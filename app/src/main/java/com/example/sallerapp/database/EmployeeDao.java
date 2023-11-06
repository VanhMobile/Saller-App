package com.example.sallerapp.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sallerapp.model.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeDao {

    private static String TAG = EmployeeDao.class.getSimpleName();
    private static ArrayList<Employee> employees = new ArrayList<>();

    // hàm thêm một nhân viên vào trong data base vào firebase kết hợp build design pattern
    public static void insertEmployee(Employee employee, String idShopAccount) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.child(idShopAccount)
                .child("Employees")
                .child(employee.getIdEmployee())
                .setValue(employee);

        // tạo account cho nhân viên
        db.child("EmployeeAccount")
                .child(employee.getIdEmployee())
                .setValue(employee);
    }

    public static  ArrayList<Employee> getEmployees(String idShopAccount){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(idShopAccount).child("Employees").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Employee employee = dataSnapshot.getValue(Employee.class);
                        employees.add(employee);
                        Log.e(TAG,employee.getIdEmployee());
                    }
                }else{
                    Log.e(TAG,"không có dữ liệu trong Employees");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG,"ko thể đọc dữ liệu db: " + error);
            }
        });

        return employees;
    }
}
