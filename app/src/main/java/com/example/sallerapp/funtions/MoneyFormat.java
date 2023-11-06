package com.example.sallerapp.funtions;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyFormat {

    // hàm định dạng tiền trong lập trình dùng lớp number format dùng hàm locale định dạng vị trí việt nam
    public static String moneyFormat(int price){
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeVN);
        return currencyFormat.format(price);
    }
}
