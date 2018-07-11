package com.winson.ui.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.winson.ui.R;


/**
 * Created by Winson on 2016/2/5.
 */
public class EmptyViewUtils {

    public static final int IGNORE_COLOR = Integer.MAX_VALUE;
    public static final int IGNORE_TEXT_SIZE = -1;

    static void show(ViewGroup parent, View child) {
        if (parent instanceof FrameLayout
                || parent instanceof AbsoluteLayout
                || parent instanceof RelativeLayout) {
            child.bringToFront();
        }
        child.setVisibility(View.VISIBLE);
    }

    static ViewGroup.LayoutParams getLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static View showLoadingView(ViewGroup parent, View customLoadingView) {
        return showLoadingView(parent, customLoadingView, null);
    }

    public static View showLoadingView(ViewGroup parent, View customLoadingView, View.OnClickListener clickListener) {
        if (parent == null) {
            return null;
        }
        removeAllView(parent);
//        View loadingView = parent.findViewById(R.id.loading_view);
//        if (customLoadingView != loadingView) {
        customLoadingView.setId(R.id.loading_view);
        customLoadingView.setLayoutParams(getLayoutParams());
        customLoadingView.setOnClickListener(clickListener);
        parent.addView(customLoadingView, 0);
//        }
        show(parent, customLoadingView);
        return customLoadingView;
    }

    public static View showLoadingView(ViewGroup parent) {
        return showLoadingView(parent, 0, IGNORE_COLOR);
    }

    public static View showLoadingView(ViewGroup parent, int pbSize, int backgroundColor) {
        if (parent == null) {
            return null;
        }
        removeAllView(parent);
        View loadingView = parent.findViewById(R.id.loading_view);
//        if (loadingView == null) {
        loadingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_view, parent, false);
        loadingView.setId(R.id.loading_view);
        loadingView.setLayoutParams(getLayoutParams());
        parent.addView(loadingView, 0);
//        }
        if (pbSize != 0) {
            ProgressBar pb = (ProgressBar) loadingView.findViewById(R.id.progress_bar);
            if (pb != null) {
                ViewGroup.LayoutParams lp = pb.getLayoutParams();
                lp.width = pbSize;
                lp.height = pbSize;
                pb.setLayoutParams(lp);
            }
        }
        if (backgroundColor != IGNORE_COLOR) {
            loadingView.setBackgroundColor(backgroundColor);
        }
        show(parent, loadingView);
        return loadingView;
    }

    public static View showErrorView(ViewGroup parent, View customErrorView, View.OnClickListener clickListener) {
        if (parent == null) {
            return null;
        }
        removeAllView(parent);
//        View errorView = parent.findViewById(R.id.error_view);
//        if (customErrorView != errorView) {
        customErrorView.setId(R.id.error_view);
        customErrorView.setLayoutParams(getLayoutParams());
        customErrorView.setOnClickListener(clickListener);
        parent.addView(customErrorView, 0);
//        }
        return customErrorView;
    }

    public static View showErrorView(ViewGroup parent, View customErrorView, int tag) {
        return showErrorView(parent, customErrorView, null);
    }

    public static View showErrorView(ViewGroup parent, String msg, int backgroundColor, float textSize, int textColor, View.OnClickListener listener) {
        if (parent == null) {
            return null;
        }
        removeAllView(parent);
        View errorView = parent.findViewById(R.id.error_view);
//        if (errorView == null) {
        errorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.error_view, parent, false);
        errorView.setLayoutParams(getLayoutParams());
        errorView.setId(R.id.error_view);
        parent.addView(errorView, 0);
//        }
        TextView msgText = (TextView) errorView.findViewById(R.id.msg);
        if (msg != null) {
            msgText.setText(msg);
        }
        if (textSize != IGNORE_TEXT_SIZE) {
            msgText.setTextSize(textSize);
        }
        if (textColor != IGNORE_COLOR) {
            msgText.setTextColor(textColor);
        }
        if (backgroundColor != IGNORE_COLOR) {
            errorView.setBackgroundColor(backgroundColor);
        }
        errorView.setOnClickListener(listener);
        show(parent, errorView);
        return errorView;
    }

    public static View showErrorView(ViewGroup parent, float textSize, int textColor, View.OnClickListener listener) {
        return showErrorView(parent, null, IGNORE_COLOR, textSize, textColor, listener);
    }

    public static View showErrorView(ViewGroup parent, View.OnClickListener listener) {
        return showErrorView(parent, null, IGNORE_COLOR, IGNORE_TEXT_SIZE, Integer.MAX_VALUE, listener);
    }

    public static View showErrorView(ViewGroup parent, String msg, View.OnClickListener listener) {
        return showErrorView(parent, msg, IGNORE_COLOR, IGNORE_TEXT_SIZE, Integer.MAX_VALUE, listener);
    }

    public static View showErrorView(ViewGroup parent, int backgroundColor, View.OnClickListener listener) {
        return showErrorView(parent, null, backgroundColor, IGNORE_TEXT_SIZE, Integer.MAX_VALUE, listener);
    }

    public static View showEmptyView(ViewGroup parent, View customEmptyView, View.OnClickListener clickListener) {
        if (parent == null) {
            return null;
        }
        removeAllView(parent);
//        View errorView = parent.findViewById(R.id.empty_view);
//        if (customEmptyView != errorView) {
        customEmptyView.setId(R.id.empty_view);
        customEmptyView.setLayoutParams(getLayoutParams());
        customEmptyView.setOnClickListener(clickListener);
        parent.addView(customEmptyView, 0);
//        }
        return customEmptyView;
    }

    public static View showEmptyView(ViewGroup parent, View customEmptyView, int tag) {
        return showEmptyView(parent, customEmptyView, null);
    }

    public static View showEmptyView(ViewGroup parent, String msg, int backgroundColor, float textSize, int textColor, View.OnClickListener listener) {
        if (parent == null) {
            return null;
        }
        removeAllView(parent);
        View emptyView = parent.findViewById(R.id.empty_view);
        if (emptyView == null) {
            emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            emptyView.setLayoutParams(getLayoutParams());
            emptyView.setId(R.id.empty_view);
            emptyView.setOnClickListener(listener);
            parent.addView(emptyView, 0);
        }
        TextView msgText = (TextView) emptyView.findViewById(R.id.msg);
        if (msg != null) {
            msgText.setText(msg);
        }
        if (textSize != IGNORE_TEXT_SIZE) {
            msgText.setTextSize(textSize);
        }
        if (textColor != IGNORE_COLOR) {
            msgText.setTextColor(textColor);
        }
        if (backgroundColor != IGNORE_COLOR) {
            emptyView.setBackgroundColor(backgroundColor);
        }
        show(parent, emptyView);
        return emptyView;
    }

    public static View showEmptyView(ViewGroup parent, View.OnClickListener listener) {
        return showEmptyView(parent, null, IGNORE_COLOR, IGNORE_TEXT_SIZE, IGNORE_COLOR, listener);
    }

    public static View showEmptyView(ViewGroup parent, int backgroundColor, View.OnClickListener listener) {
        return showEmptyView(parent, null, backgroundColor, IGNORE_TEXT_SIZE, IGNORE_COLOR, listener);
    }

    public static View showEmptyView(ViewGroup parent, String msg, View.OnClickListener listener) {
        return showEmptyView(parent, msg, IGNORE_COLOR, IGNORE_TEXT_SIZE, IGNORE_COLOR, listener);
    }

    public static View showEmptyView(ViewGroup parent, float textSize, int textColor, View.OnClickListener listener) {
        return showEmptyView(parent, null, IGNORE_COLOR, textSize, textColor, listener);
    }

//    public static void removeEmptyView(ViewGroup parent) {
//        if (parent == null) {
//            return;
//        }
//        parent.removeView(parent.findViewById(R.id.empty_view));
//    }
//
//    public static void removeErrorView(ViewGroup parent) {
//        if (parent == null) {
//            return;
//        }
//        parent.removeView(parent.findViewById(R.id.error_view));
//    }
//
//    public static void removeLoadingView(final ViewGroup parent, boolean animation) {
//        if (parent == null) {
//            return;
//        }
//        final View loadingView = parent.findViewById(R.id.loading_view);
//        parent.removeView(loadingView);
//    }

    public static void removeAllView(ViewGroup parent) {
        if (parent == null) {
            return;
        }
        parent.removeView(parent.findViewById(R.id.empty_view));
        parent.removeView(parent.findViewById(R.id.loading_view));
        parent.removeView(parent.findViewById(R.id.error_view));
    }

}
