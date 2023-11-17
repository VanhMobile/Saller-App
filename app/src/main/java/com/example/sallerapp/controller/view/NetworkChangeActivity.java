package com.example.sallerapp.controller.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.widget.Toast;

public class NetworkChangeActivity {

    private Context context;
    private boolean isFirstLogin = true; // Biến để kiểm tra lần đầu tiên đăng nhập

    public NetworkChangeActivity(Context context) {
        this.context = context;
    }

    public void startNetworkListener() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkRequest networkRequest = new NetworkRequest.Builder().build();

            connectivityManager.registerNetworkCallback(networkRequest,
                    new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onAvailable(Network network) {
                            super.onAvailable(network);
                            // Khi có kết nối mạng
                            if (!isFirstLogin) {
                                showToast("Kết nối mạng đã trở lại");
                            }
                            isFirstLogin = false;
                        }

                        @Override
                        public void onLost(Network network) {
                            super.onLost(network);
                            // Khi mất kết nối mạng
                            showToast("Không có kết nối mạng");
                        }

                        @Override
                        public void onCapabilitiesChanged(
                                Network network,
                                NetworkCapabilities networkCapabilities) {
                            super.onCapabilitiesChanged(network, networkCapabilities);
                            // Khi các khả năng của mạng thay đổi

                        }
                    });
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

