package com.winson.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Winson on 2016/1/25.
 */
public abstract class GridLLAdapter<T> {

    public static final String TAG = GridLLAdapter.class.getSimpleName();

    private Context mContext;
    private GridLinearLayout mGridLinearLayout;
    private final LayoutInflater mLayoutInflater;

    public GridLLAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    void startGetView(GridLinearLayout linearLayout) {
        mGridLinearLayout = linearLayout;
        int count = getCount();
        if (count == 0) {
            return;
        }
        for (int i = 0; i < count; i++) {
            linearLayout.addView(getView(mContext, mLayoutInflater, linearLayout, i, getItem(i)));
        }
        linearLayout.childCountChanged(true);
    }

    public void notifyDatasetChanged() {
        if (mGridLinearLayout != null) {
            mGridLinearLayout.removeAllViews();
            startGetView(mGridLinearLayout);
        }
    }

    public abstract int getCount();

    public abstract T getItem(int position);

    public abstract View getView(Context context, LayoutInflater inflater, ViewGroup group, int position, T t);

}
