package com.winson.ui.listview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.winson.ui.R;

/**
 * Created by Winson on 2016/3/7.
 */
public class PageListView extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    public static final String TAG = PageListView.class.getSimpleName();

    public interface OnPageLoadListener {

        void onRefreshData();

        void onLoadPageData();

    }

    private int mPageIndex;
    private int mPageSize = 20;
    private View mLoadingFootView;
    private View mErrorView;
    private View mLoadingView;
    private ListView mListView;
    private AbsListView.OnScrollListener mOnScrollListener;
    private OnPageLoadListener mOnPageLoadListener;
    private boolean mHasMoreData;
    private boolean mOnLoading;
    private Runnable mAddPageDataRunnable;

    public PageListView(Context context) {
        super(context);
        init(context, null);
    }

    public PageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOnRefreshListener(this);
        setProgressViewOffset(false, 0, (int) (getResources().getDisplayMetrics().density * 24));
        mPageIndex = 1;
        mListView = new ListView(context);
        mListView.setDivider(getResources().getDrawable(R.color.divider));
        mListView.setDividerHeight(1);
        mListView.setOnScrollListener(this);
        mListView.setFooterDividersEnabled(false);
        mLoadingFootView = LayoutInflater.from(getContext()).inflate(R.layout.page_list_loading_foot, mListView, false);
        mListView.addFooterView(mLoadingFootView);
        mLoadingFootView.setVisibility(View.GONE);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mListView.setLayoutParams(lp);
        addView(mListView);

        mErrorView = mLoadingFootView.findViewById(R.id.error);
        mLoadingView = mLoadingFootView.findViewById(R.id.loading);
        showError(false);
        mErrorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showError(false);
                if (mOnPageLoadListener != null) {
                    mOnPageLoadListener.onLoadPageData();
                }
            }
        });
        mLoadingFootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void setAddPageDataRunnable(Runnable runnable) {
        this.mAddPageDataRunnable = runnable;
    }

    private void showError(boolean error) {
        mErrorView.setVisibility(error ? View.VISIBLE : View.GONE);
        mLoadingView.setVisibility(error ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        if (refreshing) {
            removeCallbacks(mAddPageDataRunnable);
        }
    }

    void cancel() {
        removeCallbacks(mAddPageDataRunnable);
    }

    void onPageLoadError() {
        showError(true);
        mOnLoading = false;
    }

    void setOnPageLoadListener(OnPageLoadListener onPageLoadListener) {
        mOnPageLoadListener = onPageLoadListener;
    }

    ListView getListView() {
        return mListView;
    }

    void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    int getPageSize() {
        return mPageSize;
    }

    int getPageIndex() {
        return mPageIndex;
    }

    void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    @Override
    public void onRefresh() {
        mOnLoading = true;
        if (mOnPageLoadListener != null) {
            mOnPageLoadListener.onRefreshData();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
        if (firstVisibleItem != 0
                && visibleItemCount != 0
                && mHasMoreData
                && !mOnLoading
                && (firstVisibleItem + visibleItemCount + 1) >= totalItemCount) {
            mOnLoading = true;
            if (mOnPageLoadListener != null) {
                mOnPageLoadListener.onLoadPageData();
            }
        }
    }

    void reset() {
        mOnLoading = false;
        mHasMoreData = false;
        mPageIndex = 1;
    }

    void loadFinish() {
        mHasMoreData = false;
        mOnLoading = false;
//        if (mListView.indexOfChild(mLoadingFootView) != -1) {
//            mListView.removeFooterView(mLoadingFootView);
//        }
        mListView.removeFooterView(mLoadingFootView);
        setRefreshing(false);
    }

    void refreshData(int size) {
        setRefreshing(false);
        mOnLoading = false;
        mPageIndex = 2;
        mOnLoading = false;
        if (size < mPageSize) {
            mHasMoreData = false;
            loadFinish();
        } else {
            mHasMoreData = true;
//            if(mListView.indexOfChild(mLoadingFootView) != -1){
//                mListView.removeFooterView(mLoadingFootView);
//            }
//            if (mListView.indexOfChild(mLoadingFootView) == -1) {
//                mListView.addFooterView(mLoadingFootView);
//            }
//            try {
            mListView.removeFooterView(mLoadingFootView);
            mListView.addFooterView(mLoadingFootView);
            mLoadingFootView.setVisibility(View.VISIBLE);
//            }catch (Exception e){
//                e.printStackTrace();
//            }

        }
    }

    void addPageData(int size) {
        setRefreshing(false);
        mPageIndex++;
        mOnLoading = false;
        if (size < mPageSize) {
            mHasMoreData = false;
            loadFinish();
        } else {
            mHasMoreData = true;
        }
    }

}
