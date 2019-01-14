package com.sevensevenlife.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.example.youxiangshenghuo.R;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by 10237 on 2016/12/7.
 */

public class ImgLoadUtils {

    public static void Load(Context context, String url, final ImageView img, boolean isYuan) {
        Glide glide = Glide.get(context);

        glide.register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(new OkHttpClient()));
        if (isYuan) {
            glide.with(context)
                    .load(url)
                    .error(R.drawable.ic_error_image)
                    .thumbnail(0.5f)
                    .animate(R.anim.item_alpha_in)
                    .transform(new GlideCircleTransform(context))
                    .crossFade()
                    .into(img);
        } else {
            glide.with(context)
                    .load(url)
                    .error(R.drawable.ic_error_image)
                    .crossFade()
                    .thumbnail(0.5f)
                    .animate(R.anim.item_alpha_in)
                    .into(img);
        }
    }


    public static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

}
