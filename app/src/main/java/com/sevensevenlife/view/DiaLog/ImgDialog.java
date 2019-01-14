package com.sevensevenlife.view.DiaLog;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

import com.sevensevenlife.utils.ImgLoadUtils;
import com.example.youxiangshenghuo.R;

/**
 * Created by Administrator on 2017/4/13 0013.
 */

public class ImgDialog {
    private static ImgDialog imgDialog;
    private static Dialog dialog;
    private static Context context;
    private static ImageView imageView;

    public static ImgDialog init(Context contexts) {
        imgDialog = new ImgDialog();
        context = contexts;
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_image);
        imageView = (ImageView) dialog.findViewById(R.id.image);
        return imgDialog;
    }

    public ImgDialog setImg(String uri) {
        ImgLoadUtils.Load(context, uri, imageView, false);
        return this;
    }

    public ImgDialog show() {
        dialog.show();
        return this;
    }
}
