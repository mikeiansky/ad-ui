package com.winson.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * Created by Winson on 2016/1/19.
 */
@SuppressLint("AppCompatCustomView")
public class RateImageView extends ImageView {

    public static final String TAG = RateImageView.class.getSimpleName();

    /**
     * Layout depend by width base
     */
    public static final int HORIZONTAL = 0;

    /**
     * Layout depend by height base
     */
    public static final int VERTICAL = 1;

    /**
     * rate value = width/height
     */
    private float mRate;

    /**
     * rate depend by width or height
     */
    private int mRateOrientation;

    public void setRate(float rate) {
        this.mRate = rate;
    }

    public void setRateOrientation(int rateOrientation) {
        this.mRateOrientation = rateOrientation;
    }

    public RateImageView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public RateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public RateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RateImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RateImageView);
        mRate = a.getFloat(R.styleable.RateImageView_image_rate, 1f);
        mRateOrientation = a.getInt(R.styleable.RateImageView_image_rate_orientation, HORIZONTAL);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mRateOrientation == HORIZONTAL) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpec = MeasureSpec.makeMeasureSpec(Math.round(width * mRate), MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightSpec);
        } else {
            int height = MeasureSpec.getSize(heightMeasureSpec);
            int widthSpec = MeasureSpec.makeMeasureSpec(Math.round(height * mRate), MeasureSpec.EXACTLY);
            super.onMeasure(widthSpec, heightMeasureSpec);
        }

    }


}
