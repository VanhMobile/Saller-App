package com.example.sallerapp.controller.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeActivity extends BroadcastReceiver {

    public interface NetworkChangeListener {
        void onNetworkChanged(boolean isConnected);
    }

    private NetworkChangeListener listener;

    public NetworkChangeActivity(NetworkChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            boolean isConnected = isNetworkAvailable(context);
            listener.onNetworkChanged(isConnected);
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}

