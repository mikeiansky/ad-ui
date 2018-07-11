package com.winson.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Winson on 2016/1/19.
 */
public class GridLinearLayout extends ViewGroup {

    public static final String TAG = GridLinearLayout.class.getSimpleName();

    private static final int DIVIDER_MIDDLE = 1;

    private int mColumnCount;
    private int mHorizonalSpace;
    private int mVerticalSpace;
    private int mCount = -1;
    private int mHorizonalDividerMode;
    private int mVerticalDividerMode;
    private boolean mChildCountChanged;
    private GridLLAdapter mAdapter;

    public GridLinearLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public GridLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public GridLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GridLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GridLinearLayout);
        mColumnCount = a.getInt(R.styleable.GridLinearLayout_column_count, 3);
        mHorizonalSpace = a.getDimensionPixelSize(R.styleable.GridLinearLayout_horizonal_space, 0);
        mVerticalSpace = a.getDimensionPixelSize(R.styleable.GridLinearLayout_vertical_space, 0);
        mHorizonalDividerMode = a.getInteger(R.styleable.GridLinearLayout_horizonal_divider_mode, 0);
        mVerticalDividerMode = a.getInteger(R.styleable.GridLinearLayout_vertical_divider_mode, 0);
        a.recycle();
    }

    public void setAdapter(GridLLAdapter adapter) {
        this.mAdapter = adapter;
        this.mAdapter.startGetView(this);
    }

    void childCountChanged(boolean childChanged){
        mChildCountChanged = childChanged;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        if (count != mCount) {
            mChildCountChanged = true;
            mCount = count;
        }

        if (count > 0) {

            int lineCount = ((count - 1) / mColumnCount + 1);

            //
            int paddingleft = getPaddingLeft();
            int paddingRight = getPaddingRight();

            int horizonalSpaceCount = 0;
            if (mHorizonalDividerMode == DIVIDER_MIDDLE) {
                horizonalSpaceCount = mColumnCount - 1;
            } else {
                horizonalSpaceCount = mColumnCount + 1;
            }

            int remanentWidth = MeasureSpec.getSize(widthMeasureSpec) - horizonalSpaceCount * mHorizonalSpace
                    - paddingleft - paddingRight;

            // child mode - at most
            // child size - (parent width - padding )  / column count
            int itemWidth = Math.max(0, Math.round(remanentWidth / mColumnCount));

            int widthSpec = MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY);

            if (mVerticalDividerMode == DIVIDER_MIDDLE) {
                lineCount = lineCount - 1;
            } else {
                lineCount = lineCount + 1;
            }

            int maxHeight = mVerticalSpace * lineCount + getPaddingTop() + getPaddingBottom();

            int lineMaxHeight = 0;
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);

                LayoutParams lp = child.getLayoutParams();

                int childWidthSpec = getChildMeasureSpec(widthSpec, 0, lp.width);
                int childHeightSpec = getChildMeasureSpec(
                        MeasureSpec.makeMeasureSpec(
                                MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.UNSPECIFIED
                        ), 0, lp.height);
                child.measure(childWidthSpec, childHeightSpec);

                lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());

                if (i % mColumnCount == (mColumnCount - 1)) {
                    maxHeight += lineMaxHeight;
                    lineMaxHeight = 0;
                } else if (i == count - 1) {
                    // the last one child
                    maxHeight += lineMaxHeight;
                    lineMaxHeight = 0;
                }
            }

            setMeasuredDimension(widthMeasureSpec, resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed || mChildCountChanged) {
            mChildCountChanged = false;
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();

            int startTop = getPaddingTop();
            int startLeft = 0;
            int count = getChildCount();
            int lineMaxHeight = 0;

            for (int i = 0; i < count; i++) {

                // new line
                if (i % mColumnCount == 0) {

                    startLeft = paddingLeft;

                    if (mHorizonalDividerMode != DIVIDER_MIDDLE) {
                        startLeft += mHorizonalSpace;
                    }

                    if (i == 0 && mVerticalDividerMode == DIVIDER_MIDDLE) {
                        startTop += 0;
                    } else {
                        startTop += (lineMaxHeight + mVerticalSpace);
                    }
                    lineMaxHeight = 0;
                } else {
                    startLeft += mHorizonalSpace;
                }

                View child = getChildAt(i);
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();

                lineMaxHeight = Math.max(lineMaxHeight, height);

                child.layout(startLeft, startTop, startLeft + width, startTop + height);

                startLeft += width;
            }
        }
    }

}
