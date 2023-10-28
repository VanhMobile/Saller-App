package com.example.sallerapp.funtions;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MyFragment {

    // hàm chuyển fragment chuyền
    public static void replaceFragment(FragmentManager fragmentManager
            , int containerId
            , Fragment fragment
            , boolean isTransition) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // hiệu ứng vào true bật , false tắt
        if (isTransition) {
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        }
        fragmentTransaction.replace(containerId, fragment)
                .commit();
    }

    // trở lại fragment trước với một hiệu ứng
    public static void backFragment(FragmentManager fragmentManager
            , int containerId
            , Fragment fragment
            , boolean isTransition) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // hiệu ứng vào true bật , false tắt
        if (isTransition) {
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        fragmentTransaction.replace(containerId, fragment)
                .commit();
    }
}
