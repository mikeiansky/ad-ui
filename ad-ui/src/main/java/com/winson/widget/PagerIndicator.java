package com.winson.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by wenxiang on 2015/5/22.
 */
public class PagerIndicator extends View {

    public static final String TAG = PagerIndicator.class.getSimpleName();

    Paint mNormalPaint;
    Paint mSelectPaint;
    int mCount;
    int mSelectPosition;

    public PagerIndicator(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        int normalColor = getResources().getColor(R.color.indicator_normal);
        int selectColor = getResources().getColor(R.color.indicator_select);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PagerIndicator);
            normalColor = a.getColor(R.styleable.PagerIndicator_IndicatorNormal, normalColor);
            selectColor = a.getColor(R.styleable.PagerIndicator_IndicatorSelect, selectColor);
            a.recycle();
        }

        mNormalPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mNormalPaint.setColor(normalColor);

        mSelectPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mSelectPaint.setColor(selectColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void updateData(int count) {
        mCount = count;
        invalidate();
    }

    public void setSelect(int position) {
        mSelectPosition = position;
        invalidate();
    }

    private float caculateX(float radius, float gap, int position) {
        return radius + (2 * radius + gap) * position;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float tranx = getMeasuredWidth() / 2f;
        float radius = getMeasuredHeight() / 2f;
        float gap = radius;

        float width = radius * 2 * mCount + gap * (mCount - 1);

        canvas.save();
        canvas.translate(tranx - width / 2f, 0);

//        canvas.translate(-(caculateX(radius, mCount - 1) + radius) / 2, 0);

        for (int i = 0; i < mCount; i++) {
            float cx = caculateX(radius, gap, i);
            if (i == mSelectPosition) {
                canvas.drawCircle(cx, radius, radius, mSelectPaint);
            } else {
                canvas.drawCircle(cx, radius, radius, mNormalPaint);
            }
        }
        canvas.restore();
    }
}
