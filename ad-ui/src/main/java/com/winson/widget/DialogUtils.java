package com.winson.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * @date on 2018/11/20
 * @Author Winson
 */
public class DialogUtils {

    public interface DialogCallback {

        void onNegativeClick(Dialog dialog);

        void onPositiveClick(Dialog dialog);
    }

    public static class IOSBuilder {

        private Context context;

        private int dialogWidth;
        private int maxContentWidth;

        private String titleText;
        private String messageText;
        private String negativeText;
        private String positiveText;

        private Drawable backgroundDrawable;
        private float titleTextSize = 16;
        private float messageTextSize = 14;
        private float actionTextSize = 16;

        private int titleTextColor;
        private int messageTextColor;
        private int actionTextColor;

        private int dialogStyle;
        private boolean cancelable = true;

        private DialogCallback dialogCallback;

        public IOSBuilder(Context context) {
            this.context = context;
            dialogStyle = R.style.IOSDialogStyle;
            negativeText = context.getString(R.string.cancel);
            positiveText = context.getString(R.string.sure);
            titleTextColor = context.getResources().getColor(R.color.dialog_title_text);
            messageTextColor = context.getResources().getColor(R.color.dialog_message_text);
            actionTextColor = context.getResources().getColor(R.color.dialog_action_text);
            dialogWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 245, context.getResources().getDisplayMetrics());
            maxContentWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 245, context.getResources().getDisplayMetrics());
        }

        public IOSBuilder setBackground(Drawable background) {
            this.backgroundDrawable = background;
            return this;
        }

        public IOSBuilder setBackground(@DrawableRes int background) {
            this.backgroundDrawable = context.getResources().getDrawable(background);
            return this;
        }

        public IOSBuilder setMaxContentWith(int maxContentWidth) {
            this.maxContentWidth = maxContentWidth;
            return this;
        }

        public IOSBuilder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public IOSBuilder setDialogWidth(int dialogWidth) {
            this.dialogWidth = dialogWidth;
            return this;
        }

        public IOSBuilder setDialogStyle(int dialogStyle) {
            this.dialogStyle = dialogStyle;
            return this;
        }

        public IOSBuilder setTitleText(String titleText) {
            this.titleText = titleText;
            return this;
        }

        public IOSBuilder setMessageText(String messageText) {
            this.messageText = messageText;
            return this;
        }

        public IOSBuilder setNegativeText(String negativeText) {
            this.negativeText = negativeText;
            return this;
        }

        public IOSBuilder setPositiveText(@StringRes int positiveText) {
            this.positiveText = context.getString(positiveText);
            return this;
        }

        public IOSBuilder setTitleText(@StringRes int titleText) {
            this.titleText = context.getString(titleText);
            return this;
        }

        public IOSBuilder setMessageText(@StringRes int messageText) {
            this.messageText = context.getString(messageText);
            return this;
        }

        public IOSBuilder setNegativeText(@StringRes int negativeText) {
            this.negativeText = context.getString(negativeText);
            return this;
        }

        public IOSBuilder setPositiveText(String positiveText) {
            this.positiveText = positiveText;
            return this;
        }

        public IOSBuilder setTitleTextSize(float titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public IOSBuilder setMessageTextSize(float messageTextSize) {
            this.messageTextSize = messageTextSize;
            return this;
        }

        public IOSBuilder setActionTextSize(float actionTextSize) {
            this.actionTextSize = actionTextSize;
            return this;
        }

        public IOSBuilder setTitleTextColor(@ColorRes int titleTextColor) {
            this.titleTextColor = context.getResources().getColor(titleTextColor);
            return this;
        }

        public IOSBuilder setMessageTextColor(@ColorRes int messageTextColor) {
            this.messageTextColor = context.getResources().getColor(messageTextColor);
            return this;
        }

        public IOSBuilder setActionTextColor(@ColorRes int actionTextColor) {
            this.actionTextColor = context.getResources().getColor(actionTextColor);
            return this;
        }

        public IOSBuilder setDialogCallback(DialogCallback dialogCallback) {
            this.dialogCallback = dialogCallback;
            return this;
        }

        public void show() {
            final Dialog dialog = new Dialog(context, dialogStyle);

            View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_ios, null);
            contentView.setMinimumWidth(dialogWidth);

            dialog.setContentView(contentView);
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.CENTER);
            if (maxContentWidth != 0) {
                contentView.getLayoutParams().width = maxContentWidth;
            }


            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0;
            lp.y = 0;
            dialogWindow.setAttributes(lp);

            final ImageView background = contentView.findViewById(R.id.background);
            final TextView title = contentView.findViewById(R.id.title);
            final TextView message = contentView.findViewById(R.id.message);
            final TextView negative = contentView.findViewById(R.id.negative);
            final TextView positive = contentView.findViewById(R.id.positive);

            if (backgroundDrawable != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    background.setBackground(backgroundDrawable);
                } else {
                    background.setBackgroundDrawable(backgroundDrawable);
                }
            }
            title.setTextSize(titleTextSize);
            title.setTextColor(titleTextColor);
            title.setText(titleText);
            message.setTextSize(messageTextSize);
            message.setTextColor(messageTextColor);
            message.setText(messageText);
            negative.setTextSize(actionTextSize);
            negative.setTextColor(actionTextColor);
            negative.setText(negativeText);
            positive.setTextSize(actionTextSize);
            positive.setTextColor(actionTextColor);
            positive.setText(positiveText);

            negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogCallback != null) {
                        dialogCallback.onNegativeClick(dialog);
                    }
                    dialog.dismiss();
                }
            });
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogCallback != null) {
                        dialogCallback.onPositiveClick(dialog);
                    }
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(cancelable);
            dialog.show();
        }

    }

}
