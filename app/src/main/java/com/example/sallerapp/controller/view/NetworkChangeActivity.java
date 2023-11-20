package com.example.sallerapp.controller.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class NetworkChangeActivity extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isNetworkAvailable(context)) {
            // Kết nối có sẵn
            Toast.makeText(context, "Đã kết nối với mạng", Toast.LENGTH_SHORT).show();
        } else {
            // Không có kết nối
            Toast.makeText(context, "Không có kết nối với mạng", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Kiểm tra trạng thái mạng
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }}

