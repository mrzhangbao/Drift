package com.kevin.drift.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

/**
 * Created by Benson_Tom on 2016/4/27.
 */
public class ToRoundBitmap {

    public Bitmap toRoundBitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int r = 0;
        if (width > height) {
            r=height;
        }else{
            r = width;
        }

        Bitmap backGroundBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(backGroundBmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        RectF rectF = new RectF(0, 0, r, r);
        canvas.drawRoundRect(rectF, r/2, r/2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null,rectF, paint);
        return backGroundBmp;

    }
}
