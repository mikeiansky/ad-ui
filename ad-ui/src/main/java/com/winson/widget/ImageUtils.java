package com.winson.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

public class ImageUtils {

    public static Bitmap convertRoundBitmap(Context context, int resId, float radius) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        return convertRoundBitmap(bitmap, radius);
    }

    public static Bitmap convertRoundBitmap(Bitmap bitmap, float radius) {
        if (bitmap == null) {
            return null;
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rectF, radius, radius, paint);

        PorterDuffXfermode mode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint.setXfermode(mode);

        canvas.drawBitmap(bitmap, new Rect(0, 0, (int)rectF.width(), (int)rectF.height()), rectF, paint);

        bitmap.recycle();

        return output;
    }

}
