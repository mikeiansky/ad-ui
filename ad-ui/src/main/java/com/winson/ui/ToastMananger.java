package com.winson.ui;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by Winson on 2016/1/6.
 */
public class ToastMananger {

    public static void showToast(Context context, String message, int length) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, message, length).show();
    }

    public static void showToast(Context context, @StringRes int resId, int length) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, resId, length).show();
    }

}
