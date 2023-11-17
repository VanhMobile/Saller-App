package com.example.sallerapp.funtions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class MessengerManager {


    // Phương thức mở Messenger và chuyển đến trang nhắn tin với đường link trang cụ thể
    public static void openMessengerWithLink(String messengerLink, Activity activity) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(messengerLink));
            activity.startActivity(intent);

    }

}
