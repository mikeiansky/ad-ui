package com.winson.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
 * Created by Winson on 2016/1/19.
 */
public class MeasureViewGroup extends LinearLayout {

    public static final String TAG = MeasureViewGroup.class.getSimpleName();

    public MeasureViewGroup(Context context) {
        super(context);
    }

    public MeasureViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeasureViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            Log.d(TAG, "onMeasure measuerWidth : " + getMeasuredWidth()
//                    + " , measureHeight : " + getMeasuredHeight()
//                    + " , width : " + MeasureSpec.getSize(widthMeasureSpec)
//                    + " , height : " + MeasureSpec.getSize(heightMeasureSpec)
//                    + " , width mode : " + MeasureSpec.getMode(widthMeasureSpec)
//                    + " , height mode : " + MeasureSpec.getMode(heightMeasureSpec));
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        if (DebugUtils.debug) {
//            Log.d(TAG, "onLayout width : " + getWidth() + " , height : " + getHeight());
//        }
//    }
}
