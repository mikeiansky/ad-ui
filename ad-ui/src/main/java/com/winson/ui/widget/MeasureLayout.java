package com.winson.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


/**
 * Created by Winson on 2016/1/19.
 */
public class MeasureLayout extends View {

    public static final String TAG = MeasureLayout.class.getSimpleName();

    public MeasureLayout(Context context) {
        super(context);
    }

    public MeasureLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeasureLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        FrameLayout l = null;
        LinearLayout ld = null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if () {
//            Log.d(TAG, "onMeasure width : " + MeasureSpec.getSize(widthMeasureSpec)
//                    + " , width mode : " + MeasureSpec.getMode(widthMeasureSpec)
//                    + " , height : " + MeasureSpec.getSize(heightMeasureSpec)
//                    + " , height mode : " + MeasureSpec.getMode(heightMeasureSpec)
//                    + " , AT_MOST : " + MeasureSpec.AT_MOST
//                    + " , UNSPECIFIED : " + MeasureSpec.UNSPECIFIED
//                    + " , EXACTLY : " + MeasureSpec.EXACTLY);
//        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        if (DebugUtils.debug) {
//            Log.d(TAG, "onLayout width : " + getWidth() + " , height : " + getHeight());
//        }
    }
}
