package com.winson.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class ImageUtils {

    public static Bitmap convertRoundBitmap(Context context, int resId, float radius, ImageView.ScaleType scaleType) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        return convertRoundBitmap(bitmap, radius);
    }

    public static Bitmap convertRoundBitmap(Context context, int resId, int targetWidth, int targetHeight, float radius, ImageView.ScaleType scaleType) {
        if (targetWidth == 0 || targetHeight == 0) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
        return getRoundBitmap(bitmap, targetWidth, targetHeight, radius, scaleType);
    }

    public static Bitmap convertRoundBitmap(Bitmap bitmap, float radius) {
        return convertRoundBitmap(bitmap, radius, ImageView.ScaleType.CENTER_INSIDE);
    }

    public static Bitmap convertRoundBitmap(Bitmap bitmap, float radius, ImageView.ScaleType scaleType) {
        if (bitmap == null) {
            return null;
        }
        return getRoundBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), radius, ImageView.ScaleType.CENTER_INSIDE);
    }

    @NonNull
    private static Bitmap getRoundBitmap(Bitmap bitmap, int targetWidth, int targetHeight, float radius, ImageView.ScaleType scaleType) {
        int width;
        int height;
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        float sw = targetWidth * 1f / width;
        float sh = targetHeight * 1f / height;

        Matrix matrix = new Matrix();
        RectF rectF = new RectF();

        switch (scaleType) {
            case CENTER_CROP:
                float ss = sw < sh ? sh : sw;

                float rw = width * ss;
                float rh = height * ss;
                float tx = targetWidth / 2f - rw / 2f;
                float ty = targetHeight / 2f - rh / 2f;

                matrix.preTranslate(tx, ty);
                matrix.preScale(ss, ss);

                rectF.left = 0;
                rectF.top = 0;
                rectF.right = targetWidth;
                rectF.bottom = targetHeight;

                break;
            case FIT_XY:
                matrix.preScale(sw, sh);

                rectF.left = 0;
                rectF.top = 0;
                rectF.right = targetWidth;
                rectF.bottom = targetHeight;
                break;
            default:
                ss = sw > sh ? sh : sw;

                rw = width * ss;
                rh = height * ss;

                tx = targetWidth / 2f - rw / 2f;
                ty = targetHeight / 2f - rh / 2f;

                matrix.preTranslate(tx, ty);
                matrix.preScale(ss, ss);

                rectF.left = tx;
                rectF.top = ty;
                rectF.right = tx + rw;
                rectF.bottom = ty + rh;
                break;
        }

        Bitmap output = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        canvas.drawRoundRect(rectF, radius, radius, paint);

        PorterDuffXfermode mode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint.setXfermode(mode);

        canvas.drawBitmap(bitmap, matrix, paint);

        bitmap.recycle();

        return output;
    }

}
