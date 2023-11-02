package com.example.sallerapp.funtions;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.sallerapp.R;

public class Validations {

    private static boolean isCheck = false;
    private static final String REGEX_EMAIL = "[A-Za-z0-9]+@[a-zA-Z0-9]+(\\\\.[a-zA-Z0-9]+)";
    private static final String REGEX_PASSWORD = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";


    public static boolean isValidEmpty(EditText editText, Context context){
       if(editText.getText().toString().isEmpty()){
           editText.getBackground().setTint(ContextCompat.getColor(context, R.color.error_color));
           editText.setError("Dữ liệu trống");
           isCheck = true;
       }else{
           editText.getBackground().setTint(ContextCompat.getColor(context, R.color.line));
           editText.setError(null);
       }
       return isCheck;
    }

    public static void isValidEmail(EditText editText, Context context){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().matches(REGEX_EMAIL)){
                    editText.getBackground().setTint(ContextCompat.getColor(context, R.color.error_color));
                    editText.setError("Dữ liệu trống");
                }else{
                    editText.getBackground().setTint(ContextCompat.getColor(context, R.color.line));
                    editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public static void isValidPassword(EditText editText, Context context){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().matches(REGEX_PASSWORD)){
                    editText.getBackground().setTint(ContextCompat.getColor(context, R.color.error_color));
                    editText.setError("Mật khẩu không đủ mạnh");
                }else{
                    editText.getBackground().setTint(ContextCompat.getColor(context, R.color.line));
                    editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static boolean isEmail(String email){
        return email.matches(REGEX_EMAIL);
    }

    public static boolean isPASS(String pass){
        return pass.matches(REGEX_EMAIL);
    }

    public static boolean isQuantity(int quantity){
        if (quantity <= 0){
            return false;
        }
        return true;
    }

}
