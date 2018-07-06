package com.winson.ui.widget.listview;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ListView;

import com.winson.ui.widget.CommonAdapter;

import java.util.List;

/**
 * Created by Winson on 2016/3/7.
 */
public class PageListViewHelper<T> {

    private final PageListView mPageListView;
    private CommonAdapter<T> mAdapter;
    private int mLoadDuration = 800;
    private boolean mCancel;

    public PageListViewHelper(PageListView mPageListView, CommonAdapter<T> mAdapter) {
        this.mPageListView = mPageListView;
        this.mAdapter = mAdapter;
        this.mPageListView.getListView().setAdapter(mAdapter);
    }

    public CommonAdapter<T> getAdapter() {
        return mAdapter;
    }

    public void setAdapter(CommonAdapter<T> adapter) {
        mAdapter = adapter;
        mPageListView.getListView().setAdapter(mAdapter);
    }

    public void notifyDataSetChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setRefreshing(boolean refreshing) {
        mPageListView.setRefreshing(refreshing);
    }

    public void setPageSize(int pageSize) {
        mPageListView.setPageSize(pageSize);
    }

    public void reset() {
        mPageListView.reset();
    }

    public ListView getListView() {
        return mPageListView.getListView();
    }


    public void setOnPageLoadListener(PageListView.OnPageLoadListener onPageLoadListener) {
        mPageListView.setOnPageLoadListener(onPageLoadListener);
    }

    public int getPageSize() {
        return mPageListView.getPageSize();
    }

    public int getPageIndex() {
        return mPageListView.getPageIndex();
    }

    public void setLoadDuration(int duration) {
        mLoadDuration = duration;
    }

    public void refreshData(List<T> datas) {
        mPageListView.refreshData(datas == null ? 0 : datas.size());
        if (mAdapter.getData() == null) {
            mAdapter.replaceData(datas);
        } else {
            if (mAdapter.getData() != datas) {
                mAdapter.getData().clear();
                mAdapter.addData(datas);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void addPageData(final List<T> datas) {
        if (mCancel) {
            return;
        }
        Runnable addPageDataRun = new Runnable() {
            @Override
            public void run() {
                if (mCancel) {
                    return;
                }
                mPageListView.addPageData(datas == null ? 0 : datas.size());
                mAdapter.addData(datas);
            }
        };
        mPageListView.setAddPageDataRunnable(addPageDataRun);
        mPageListView.postDelayed(addPageDataRun, mLoadDuration);
    }

    public void onPageLoadError() {
        mPageListView.onPageLoadError();
    }

    public void cancel() {
        mCancel = true;
        mPageListView.cancel();
    }

    public void setLoadFinish() {
        mPageListView.loadFinish();
    }

}
