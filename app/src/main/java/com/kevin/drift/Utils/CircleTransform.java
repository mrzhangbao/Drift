package com.kevin.drift.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * Created by Benson_Tom on 2016/5/12.
 * 图片优化
 */
public class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
        //通过Canvas画布对图片进行处理
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();//画笔
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        //绘制
        canvas.drawCircle(r, r, r, paint);
        //回收资源
        squaredBitmap.recycle();
        return bitmap;
    }
    @Override
    public String key() {
        return "circle";
    }
}
