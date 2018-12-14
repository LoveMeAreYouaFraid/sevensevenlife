package com.sevensevenlife.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Administrator on 2017/1/1.
 */

public class FileUtils {

    /**
     * @return new File
     */
    public static void DPngFile() {
        File[] files = MyApplication.getInstance().
                getExternalCacheDir().listFiles();
        if (files.length == 0) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }

    }

    /**
     * @return new File
     */
    public static File newPngFile() {
        File imageUri = new File(MyApplication.getInstance().
                getExternalCacheDir(),
                System.currentTimeMillis() + ".png");
        return imageUri;
    }

    /**
     * @return new File
     */
    public static File newApkFile() {
        File imageUri = new File(MyApplication.getInstance().
                getExternalCacheDir(),
                System.currentTimeMillis() + ".apk");
        return imageUri;
    }

    /**
     * bitmap 转 File
     *
     * @param bitmap
     * @return File
     */
    public static File saveBitmapFile(Bitmap bitmap, File file) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);
            } catch (NullPointerException e) {
                ToastUtils.showLong("无法获取这张图片");
            }

            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File saveBitmapFile(Bitmap bitmap) {
        File file = newPngFile();//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);
            } catch (NullPointerException e) {
                ToastUtils.showLong("无法获取这张图片");
            }

            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 0, 1920);//最大分辨率
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap getSmallBitmap(String filePath, int w, int h) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, w, h);//最大分辨率
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * Compress Picture Save to CacheCatalog
     * 压缩图片保存到缓存目录
     *
     * @return 压缩后的文件
     */
    public static File CPSCC(String filePath) {
        return saveBitmapFile(getSmallBitmap(filePath));
    }

    public static File CPSCC(String filePath, int w, int h) {
        return saveBitmapFile(getSmallBitmap(filePath, w, h));
    }

    public static Bitmap FiletoBitmap(File file) {
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, getBitmapOption(1)); //将图片的长和宽缩小味原来的1/2
        return bitmap;
    }

    private static BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    /**
     * 计算压缩比例值(改进版 by touch_ping)
     * <p>
     * 原版2>4>8...倍压缩
     * 当前2>3>4...倍压缩
     *
     * @param options   解析图片的配置信息
     * @param reqWidth  所需图片压缩尺寸最小宽度O
     * @param reqHeight 所需图片压缩尺寸最小高度
     * @return
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}

