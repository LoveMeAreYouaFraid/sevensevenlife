package com.sevensevenlife.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youxiangshenghuo.R;

/**
 * Created by Administrator on 2017/4/19 0019.
 */

public class GuideDialog {
    public GuideDialog(Context context) {
        final ImageView imageView, ckImg;
        TextView cktv;
        final Dialog dialog = new Dialog(context, R.style.GuideDialog);
        dialog.setContentView(R.layout.guide_dialog_layout);
        imageView = (ImageView) dialog.findViewById(R.id.rq_img);
        cktv = (TextView) dialog.findViewById(R.id.ck_tv);
        ckImg = (ImageView) dialog.findViewById(R.id.ck_imgs);
        dialog.findViewById(R.id.root_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ckImg.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_img_left_in));
        cktv.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_img_left_out));
        final ScaleAnimation in = new ScaleAnimation(1f, 1.4f, 1f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        in.setDuration(2000);//设置动画持续时间
        in.setRepeatCount(0);//设置重复次数
        in.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        in.setStartOffset(0);//执行前的等待时间

        final ScaleAnimation out = new ScaleAnimation(1.4f, 1f, 1.4f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        out.setDuration(2000);//设置动画持续时间
        out.setRepeatCount(0);//设置重复次数
        out.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        out.setStartOffset(0);//执行前的等待时间
        imageView.setAnimation(in);
        in.start();
        in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setAnimation(out);
                out.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setAnimation(in);
                in.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
    }
}
