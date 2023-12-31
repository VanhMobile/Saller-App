package com.example.sallerapp.funtions;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.sallerapp.R;

import kotlin.text.Regex;

public class Validations {

    // sử lý sự kiện khi người dùng đang nhập onInput
    public static void isEmpty(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    editText.setError("Dữ liệu trống");
                } else {
                    editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // sử lý sự kiện khi người dùng nhấn nút button gửi đi sẽ check rỗng
    public static boolean isEmptyPress(EditText editText) {
        // nếu edt rỗng dữ liệu trả ra true và setError cho nó
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Dữ liệu trống");
            return true;
        } else {
            // ngược lại khi edt có dữ liệu trả ra false và hủy đu error = null
            editText.setError(null);
        }
        return false;
    }

    // check định dạng email khi đang nhập sự kiện onInput
    public static void isEmail(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // nếu sai định dạng mail trả ra một error
                Regex regex = new Regex("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
                if (!regex.matches(charSequence.toString())) {
                    editText.setError("Sai định dạng email");
                } else {
                    // nếu đúng hủy bỏ error
                    editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //check đnh dạng khi người dùng nhấn vào button
    public static boolean isEmailPress(EditText editText) {
        // check nếu edt chả ra giá trị sai định dạng thì chả ra error
        Regex regex = new Regex("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        if (!regex.matches(editText.getText().toString())) {
            editText.setError("Sai định dạng email");
        } else {
            // nếu đụng định dạng hủy bỏ error
            editText.setError(null);
        }
        return regex.matches(editText.getText().toString());
    }

    //check định dạng số điện thoại khi sự kiện onInput sảy ra
    public static void isPhoneNumber(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // nếu sai định dạng phone trả ra một error
                Regex regex = new Regex("^\\+?[0-9]{10,13}$");
                if (!regex.matches(charSequence.toString())) {
                    editText.setError("Sai định dạng số điện thoại");
                } else {
                    // đúng hủy bỏ error
                    editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // check định dạng số điện thoại khi nhân sự kiện press
    public static boolean isPhoneNumberPress(EditText editText) {
        Regex regex = new Regex("^\\+?[0-9]{10,13}$");
        if (!regex.matches(editText.getText().toString())) {
            editText.setError("Sai định dạng số điện thoại");
        } else {
            // đúng hủy bỏ error
            editText.setError(null);
        }
        return regex.matches(editText.getText().toString());
    }

    // check số lượng khi nhập
    public static void isQuantity(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int quantity = Integer.parseInt(charSequence.toString().trim());
                    if (quantity <= 0) {
                        editText.setError("Số lượng không thể âm");
                    } else {
                        editText.setError(null);
                    }
                } catch (Exception e) {
                    editText.setError("Số lượng không thể là ký tự");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // sử lý sự kiện check số lượng khí người dùng nhấn nút button
    public static boolean isQuantityPress(EditText editText) {
        try {
            int quantity = Integer.parseInt(editText.getText().toString().trim());
            if (quantity <= 0) {
                editText.setError("Số lượng không thể âm");
                return false;
            } else {
                editText.setError(null);
            }
        } catch (Exception e) {
            editText.setError("Số lượng không thể là ký tự");
            return false;
        }
        return true;
    }
    // sử lý sự kiện onInput password ít nhất 8 ký tự ít 1 số , ít nhất 1 chữ hoa , ít nhất 1 ký tự đb

    public static void isPassword(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Regex regex = new Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
                if (!regex.matches(charSequence.toString())) {
                    editText.setError("Sai định dạng password");
                } else {
                    editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static void isPassword(EditText editText, TextView errorMess) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Regex regex = new Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
                if (!regex.matches(charSequence.toString())) {
                    errorMess.setVisibility(View.VISIBLE);
                } else {
                    errorMess.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static boolean isPasswordPress(EditText editText){
        Regex regex = new Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
        if (!regex.matches(editText.getText().toString())) {
            editText.setError("Sai định dạng password");
        } else {
            editText.setError(null);
        }
        return regex.matches(editText.getText().toString());
    }

    public static boolean isPasswordPress(EditText editText, TextView errorMess){
        Regex regex = new Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
        if (!regex.matches(editText.getText().toString())) {
            errorMess.setVisibility(View.VISIBLE);
        } else {
            errorMess.setVisibility(View.GONE);
        }
        return regex.matches(editText.getText().toString());
    }


}
