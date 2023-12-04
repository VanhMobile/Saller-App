package com.example.sallerapp.funtions;

import android.app.ProgressDialog;
import android.content.Context;

public class MyDialog {
    private static ProgressDialog progressDialog;
    public static void showProgressDialog(String mess, Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(mess);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
