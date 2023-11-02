package com.example.sallerapp.funtions;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class IdGenerator {
    private static final String SHOP_ID_KEY = "shop_id";
    private static final String BILL_ID_KEY = "bill_id";
    private static final String CUS_ID_KEY = "bill_id";
    private static int count = 0;
    private static int countBill = 0;
    private static int countCus = 0;

    public static String generateNextShopId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        count = sharedPreferences.getInt(SHOP_ID_KEY, 0);

        String shopId = "Shop_" + ++count;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHOP_ID_KEY, count);
        editor.apply();

        return shopId;
    }

    public static String generateNextBillId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        count = sharedPreferences.getInt(BILL_ID_KEY, 0);

        String billId = "HĐ" + ++countBill;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(BILL_ID_KEY, countBill);
        editor.apply();

        return billId;
    }

    public static String generateNextProductId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        count = sharedPreferences.getInt(CUS_ID_KEY, 0);

        String proID = "SP" + ++countCus;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CUS_ID_KEY, countCus);
        editor.apply();

        return proID;
    }
}
