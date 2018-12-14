package com.sevensevenlife.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;

import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.view.DiaLog.TitleDialog;

import java.io.File;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class ImgChoiceUtils {

    private static final int IMAGE = 109;
    public static int RequestCamera = 110;

    public static int permissionCAMERA = 1011;

    private Activity activity;

    private Context context;

    public boolean isCamera = false;

    private File file;

    private Intent intent;

    private String[] dialogisCamera = new String[]{"选择上传方式", "拍照", "相册上传"};

    private String[] dialogNoCamera = new String[]{"选择上传方式", "相册上传"};

    private String[] strings;


    public static ImgChoiceUtils isNew(Activity activity) {
        return new ImgChoiceUtils(activity);
    }

    ImgChoiceUtils(Activity activity) {
        this.activity = activity;
        context = activity;
    }

    public ImgChoiceUtils isCamera(boolean isCameras) {
        isCamera = isCameras;
        return this;
    }

    public ImgChoiceUtils show() {
        if (isCamera) {
            strings = dialogisCamera;
        } else {
            strings = dialogNoCamera;
        }

        new TitleDialog().SHOW(context, strings, new DialogListener() {
            @Override
            public void buttType(int ButtType) {
                file = FileUtils.newPngFile();
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (ButtType == 0) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != 0 ||
                                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != 0) {
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, permissionCAMERA);
                        } else {
                            showCamera();
                        }
                    } else {
                        showCamera();
                    }


                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activity.startActivityForResult(intent, IMAGE);

                }
            }
        }, true);
        return this;

    }

    public ImgChoiceUtils showCamera() {
        if (Build.VERSION.SDK_INT < 24) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            activity.startActivityForResult(intent, RequestCamera);
        } else {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, RequestCamera);
        }

        return this;
    }


    public void onPermissions(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1011:
                if (grantResults[0] == 0) {
                    showCamera();
                } else {
                    new TitleDialog().SHOW(context, "没有相机权限，请去设置给我权限，否则无法使用此功能", "设置", "不用此功能", new DialogListener() {
                        @Override
                        public void buttType(int ButtType) {
                            if (ButtType == 0) {
                                AppUtils.startAppSettings();
                            }
                        }
                    }, true);
                }
                break;
        }
    }

    public File onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == activity.RESULT_OK) {
            if (requestCode == RequestCamera) {
                return FileUtils.CPSCC(file.getPath());
            } else if (requestCode == IMAGE) {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = activity.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                return FileUtils.CPSCC(picturePath);

            }

        }
        return null;
    }
}
