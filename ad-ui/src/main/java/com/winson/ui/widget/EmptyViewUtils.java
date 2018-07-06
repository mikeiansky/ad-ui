package com.winson.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.winson.ui.R;

import org.w3c.dom.Text;

/**
 * Created by Winson on 2016/2/5.
 */
public class EmptyViewUtils {

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

    public static View showCustomLoadingView(ViewGroup parent, View customLoadingView) {
        if (parent == null) {
            return null;
        }
        removeEmptyView(parent);
        removeErrorView(parent);
        View loadingView = parent.findViewById(R.id.loading_view);
        if (customLoadingView != loadingView) {
            removeLoadingView(parent, false);
            customLoadingView.setId(R.id.loading_view);
            customLoadingView.setLayoutParams(getLayoutParams());
            parent.addView(customLoadingView, 0);
        }
        return customLoadingView;
    }

    public static View showLoadingView(ViewGroup parent) {
        return showLoadingView(parent, 0, 0);
    }

    public static View showLoadingView(ViewGroup parent, int pbSize, int color) {
        if (parent == null) {
            return null;
        }
        removeEmptyView(parent);
        removeErrorView(parent);
        View loadingView = parent.findViewById(R.id.loading_view);
        if (loadingView == null) {
            loadingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_view, parent, false);
            loadingView.setId(R.id.loading_view);
            loadingView.setLayoutParams(getLayoutParams());
            parent.addView(loadingView, 0);
        }
        if (pbSize != 0) {
            ProgressBar pb = (ProgressBar) loadingView.findViewById(R.id.progress_bar);
            if(pb!=null){
                ViewGroup.LayoutParams lp = pb.getLayoutParams();
                lp.width = pbSize;
                lp.height = pbSize;
                pb.setLayoutParams(lp);
            }
        }
        loadingView.setBackgroundColor(color);
        show(parent, loadingView);
        return loadingView;
    }

    public static View showCustomErrorView(ViewGroup parent, View customErrorView) {
        if (parent == null) {
            return null;
        }
        removeEmptyView(parent);
        removeLoadingView(parent, false);
        View errorView = parent.findViewById(R.id.error_view);
        if (customErrorView != errorView) {
            removeErrorView(parent);
            customErrorView.setId(R.id.error_view);
            customErrorView.setLayoutParams(getLayoutParams());
            parent.addView(customErrorView, 0);
        }
        return customErrorView;
    }

    public static View showErrorView(ViewGroup parent, View.OnClickListener listener) {
        return showErrorView(parent, null, 0, 0, 0, listener);
    }

    public static View showErrorView(ViewGroup parent, String msg, View.OnClickListener listener) {
        return showErrorView(parent, msg, 0, 0, 0, listener);
    }

    public static View showErrorView(ViewGroup parent, int color, View.OnClickListener listener) {
        return showErrorView(parent, null, color, 0, 0, listener);
    }

    public static View showErrorView(ViewGroup parent, float textSize, int textColor, View.OnClickListener listener) {
        return showErrorView(parent, null, 0, textSize, textColor, listener);
    }

    public static View showErrorView(ViewGroup parent, String msg, int color, float textSize, int textColor, View.OnClickListener listener) {
        if (parent == null) {
            return null;
        }
        removeEmptyView(parent);
        removeLoadingView(parent, false);
        View errorView = parent.findViewById(R.id.error_view);
        if (errorView == null) {
            errorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.error_view, parent, false);
            errorView.setLayoutParams(getLayoutParams());
            errorView.setId(R.id.error_view);
            parent.addView(errorView, 0);
        }
        TextView msgText = (TextView) errorView.findViewById(R.id.msg);
        if (msg != null) {
            msgText.setText(msg);
        }
        if (textSize != 0) {
            msgText.setTextSize(textSize);
        }
        if (textColor != 0) {
            msgText.setTextColor(textColor);
        }
        errorView.setOnClickListener(listener);
        errorView.setBackgroundColor(color);
        show(parent, errorView);
        return errorView;
    }

    public static View showCustomEmptyView(ViewGroup parent, View customEmptyView) {
        if (parent == null) {
            return null;
        }
        removeErrorView(parent);
        removeLoadingView(parent, false);
        View errorView = parent.findViewById(R.id.empty_view);
        if (customEmptyView != errorView) {
            removeEmptyView(parent);
            customEmptyView.setId(R.id.empty_view);
            customEmptyView.setLayoutParams(getLayoutParams());
            parent.addView(customEmptyView, 0);
        }
        return customEmptyView;
    }

    public static View showEmptyView(ViewGroup parent, View.OnClickListener listener) {
        return showEmptyView(parent, null, 0, 0, 0, listener);
    }

    public static View showEmptyView(ViewGroup parent, int color, View.OnClickListener listener) {
        return showEmptyView(parent, null, color, 0, 0, listener);
    }

    public static View showEmptyView(ViewGroup parent, String msg, View.OnClickListener listener) {
        return showEmptyView(parent, msg, 0, 0, 0, listener);
    }

    public static View showEmptyView(ViewGroup parent, float textSize, int textColor, View.OnClickListener listener) {
        return showEmptyView(parent, null, 0, textSize, textColor, listener);
    }

    public static View showEmptyView(ViewGroup parent, String msg, int color, float textSize, int textColor, View.OnClickListener listener) {
        if (parent == null) {
            return null;
        }
        removeErrorView(parent);
        removeLoadingView(parent, false);
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
        if (textSize != 0) {
            msgText.setTextSize(textSize);
        }
        if (textColor != 0) {
            msgText.setTextColor(textColor);
        }
        emptyView.setBackgroundColor(color);
        show(parent, emptyView);
        return emptyView;
    }

    public static void removeEmptyView(ViewGroup parent) {
        if (parent == null) {
            return;
        }
        parent.removeView(parent.findViewById(R.id.empty_view));
    }

    public static void removeErrorView(ViewGroup parent) {
        if (parent == null) {
            return;
        }
        parent.removeView(parent.findViewById(R.id.error_view));
    }

    public static void removeLoadingView(final ViewGroup parent, boolean animation) {
        if (parent == null) {
            return;
        }
        final View loadingView = parent.findViewById(R.id.loading_view);
        parent.removeView(loadingView);
    }

    public static void removeAllView(ViewGroup parent) {
        if (parent == null) {
            return;
        }
        parent.removeView(parent.findViewById(R.id.empty_view));
        parent.removeView(parent.findViewById(R.id.loading_view));
        parent.removeView(parent.findViewById(R.id.error_view));
    }

}
