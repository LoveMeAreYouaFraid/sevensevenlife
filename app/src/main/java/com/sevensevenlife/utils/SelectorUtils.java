package com.sevensevenlife.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by llH on 2017/1/30.
 * 只用一张图片做一个点击效果
 */

public class SelectorUtils {
    private static Drawable changeBrightnessBitmap(Bitmap srcBitmap, View view) {
        Bitmap bmp = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        int brightness = 60 - 127;
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1,
                0, 0, brightness,// 改变亮度
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        Canvas canvas = new Canvas(bmp);
        // 在Canvas上绘制一个Bitmap
        canvas.drawBitmap(srcBitmap, 0, 0, paint);

        return new BitmapDrawable(view.getContext().getResources(), bmp);
    }

    /**
     * 设置Selector。
     */
    public static void set(View view) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = view.getBackground();
        Drawable pressed = changeBrightnessBitmap(((BitmapDrawable) view.getBackground()).getBitmap(), view);

        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(bg);
        }
    }

    /**
     * 设置Selector。
     */
    public static void set(ImageView view) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal;
        if (view.getDrawable() != null) {
            normal = view.getDrawable();
        } else {
            normal = view.getBackground();
        }

        Drawable pressed = changeBrightnessBitmap(((BitmapDrawable) view.getBackground()).getBitmap(), view);

        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(bg);
        }
    }

}
