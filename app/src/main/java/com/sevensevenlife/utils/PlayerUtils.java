package com.sevensevenlife.utils;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Administrator on 2016/12/31.
 */

public class PlayerUtils {

    public static void playOgg(Context context, int id) {
        MediaPlayer mediaPlayer01;
        mediaPlayer01 = MediaPlayer.create(context, id);
        mediaPlayer01.start();
    }

}
