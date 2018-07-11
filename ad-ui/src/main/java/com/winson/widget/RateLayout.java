package com.winson.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;


/**
 * Created by Winson on 2016/1/25.
 */
public class RateLayout extends FrameLayout {

    public static final String TAG = RateLayout.class.getSimpleName();

    /**
     * rate value = width/height
     */
    private float mRate;
    private int mRateOritation;

    public RateLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public RateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public RateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RateLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RateLayout);
        mRate = a.getFloat(R.styleable.RateLayout_rate_layout, 1f);
        mRateOritation = a.getInteger(R.styleable.RateLayout_rate_oritation, 0);
        a.recycle();
    }

    public void setRate(float rate) {
        mRate = rate;
        requestLayout();
    }

    public void setRateOritation(int oritation) {
        mRateOritation = oritation;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpec = widthMeasureSpec;
        int heightSpec = heightMeasureSpec;
        if (mRateOritation == 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            heightSpec = MeasureSpec.makeMeasureSpec(Math.round(width * mRate), MeasureSpec.EXACTLY);
        } else {
            int height = MeasureSpec.getSize(heightMeasureSpec);
            widthSpec = MeasureSpec.makeMeasureSpec(Math.round(height * mRate), MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthSpec, heightSpec);
    }

}
