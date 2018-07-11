package com.winson.ui;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Winson on 2016/2/4.
 */
public class ListDialog extends Dialog {


    public ListDialog(Context context) {
        super(context);
    }

    public ListDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ListDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
