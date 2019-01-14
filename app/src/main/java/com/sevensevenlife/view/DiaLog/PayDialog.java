package com.sevensevenlife.view.DiaLog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.sevensevenlife.interfaces.DialogListener;
import com.example.youxiangshenghuo.R;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class PayDialog {

    public static void show(Context context, final DialogListener listener) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_pay);
        dialog.findViewById(R.id.go_aliay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buttType(0);
            }
        });
        dialog.findViewById(R.id.go_xianjin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buttType(1);
            }
        });
        dialog.findViewById(R.id.go_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buttType(2);
            }
        });

    }
}
