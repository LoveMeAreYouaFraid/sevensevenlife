package com.sevensevenlife.view.DiaLog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sevensevenlife.interfaces.DialogListener;
import com.example.youxiangshenghuo.R;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class CarLenDialog {
    private TextView one, two, three, four;
    private View.OnClickListener onClickListener;

    public void Show(Context context, final DialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.car_len_dialog_layout);
        one = (TextView) dialog.findViewById(R.id.one);
        two = (TextView) dialog.findViewById(R.id.two);
        three = (TextView) dialog.findViewById(R.id.three);
        four = (TextView) dialog.findViewById(R.id.four);
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.one:
                        listener.buttType(0);
                        dialog.dismiss();
                        break;
                    case R.id.two:
                        listener.buttType(1);
                        dialog.dismiss();
                        break;
                    case R.id.three:
                        listener.buttType(2);
                        dialog.dismiss();
                        break;
                    case R.id.four:
                        listener.buttType(3);
                        dialog.dismiss();
                        break;
                }
            }
        };
        one.setOnClickListener(onClickListener);
        two.setOnClickListener(onClickListener);
        three.setOnClickListener(onClickListener);
        four.setOnClickListener(onClickListener);
        if (dialog != null) {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
//            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//                @Override
//                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                    return false;
//                }
//            });

        }
        dialog.show();
    }
}
