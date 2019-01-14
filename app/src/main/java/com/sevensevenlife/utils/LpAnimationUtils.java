package com.sevensevenlife.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Administrator on 2017/2/23 0023.
 */

public class LpAnimationUtils {

    public static Animation mTranslateAnimation(double fromXDelta, double toXDelta,
                                                double fromYDelta, double toYDelta, double timeS, int repeatNum) {

        TranslateAnimation animation = new TranslateAnimation(
                (float) fromXDelta, (float) toXDelta, (float) fromYDelta, (float) toYDelta);
        animation.setDuration((long) timeS * 1000);//设置动画持续时间
        animation.setRepeatCount(repeatNum);//设置重复次数
        return animation;

    }

    public static Animation mAlphaAnimation(double fromAlpha, double toAlpha, double timeS, int repeatNum) {

        AlphaAnimation animation = new AlphaAnimation((float) fromAlpha, (float) toAlpha);
        animation.setDuration((long) timeS * 1000);//设置动画持续时间
        animation.setRepeatCount(repeatNum);//设置重复次数
        return animation;

    }

    public static Animation mRotateAnimation(double fromDegrees, double toDegrees, double timeS, int repeatNum) {

        RotateAnimation animation = new RotateAnimation((float) fromDegrees, (float) toDegrees);
        animation.setDuration((long) timeS * 1000);//设置动画持续时间
        animation.setRepeatCount(repeatNum);//设置重复次数
        return animation;

    }

    public static Animation mScaleAnimation(double fromX, double toX,
                                            double fromY, double toY, double timeS, int repeatNum) {

        ScaleAnimation animation = new ScaleAnimation((float) fromX, (float) toX, (float) fromY, (float) toY);
        animation.setDuration((long) timeS * 1000);//设置动画持续时间
        animation.setRepeatCount(repeatNum);//设置重复次数
        return animation;

    }
}
