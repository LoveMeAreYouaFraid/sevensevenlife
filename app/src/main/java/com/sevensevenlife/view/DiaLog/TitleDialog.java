package com.sevensevenlife.view.DiaLog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.sevensevenlife.interfaces.DialogListener;
import com.example.youxiangshenghuo.R;


/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class TitleDialog {
    private static Context mContext;
    private static Dialog dialog;
    private static DialogView dv;


    private TitleDialog getDialogView() {
        dv = new DialogView();
        dialog = new Dialog(mContext, R.style.dialog);
        dialog.getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_list_layout);
        dv.tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        dv.tvDetermine = (TextView) dialog.findViewById(R.id.tv_determine);
        dv.tvCancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        dv.line = dialog.findViewById(R.id.line);
        return this;
    }

    public TitleDialog SHOW(Context context, final String titleString, String leftButtString,
                            String rightButtString, final DialogListener itemChoiceListener,
                            final boolean isdismiss) {
        mContext = context;

        getDialogView();

        if (!leftButtString.equals("")) {
            dv.tvDetermine.setText(leftButtString);
        }
        if (!rightButtString.equals("")) {
            dv.tvCancel.setText(rightButtString);
        }

        dv.tvDetermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemChoiceListener.buttType(0);
                dialog.dismiss();
            }
        });
        dv.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemChoiceListener.buttType(1);
                dialog.dismiss();
            }
        });
        dv.tvTitle.setText(titleString);
        dialog.setCanceledOnTouchOutside(isdismiss);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return isdismiss;
            }
        });

        dialog.show();
        return this;
    }

    public TitleDialog SHOW(Context context, final String[] strings, final DialogListener itemChoiceListener,
                            final boolean isdismiss) {
        mContext = context;

        getDialogView();

        if (strings.length == 2) {
            dv.tvTitle.setText(strings[0]);
            if (!strings[1].equals("")) {
                dv.tvCancel.setText(strings[1]);
            } else {
                dv.tvCancel.setVisibility(View.GONE);
            }
            dv.line.setVisibility(View.GONE);
            dv.tvDetermine.setVisibility(View.GONE);
        } else if (strings.length == 3) {
            dv.line.setVisibility(View.VISIBLE);
            dv.tvTitle.setText(strings[0]);
            if (!strings[1].equals("")) {
                dv.tvDetermine.setText(strings[1]);
            } else {
                dv.tvDetermine.setVisibility(View.GONE);
                dv.line.setVisibility(View.GONE);
            }
            if (!strings[2].equals("")) {
                dv.tvCancel.setText(strings[2]);
            } else {
                dv.line.setVisibility(View.GONE);
                dv.tvCancel.setVisibility(View.GONE);
            }
        }


        dv.tvDetermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemChoiceListener.buttType(0);
                dialog.dismiss();
            }
        });
        dv.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemChoiceListener.buttType(1);
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(isdismiss);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return isdismiss;
            }
        });

        dialog.show();
        return this;
    }

    public TitleDialog SHOW(Context context, final String titleString, String leftButtString,
                            final DialogListener itemChoiceListener,
                            final boolean isdismiss) {
        mContext = context;

        getDialogView();

        if (!leftButtString.equals("")) {
            dv.tvDetermine.setText(leftButtString);
        }
        dv.line.setVisibility(View.GONE);
        dv.tvCancel.setVisibility(View.GONE);
        dv.tvDetermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemChoiceListener.buttType(0);
                dialog.dismiss();
            }
        });
        dv.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemChoiceListener.buttType(1);
                dialog.dismiss();
            }
        });
        dv.tvTitle.setText(titleString);
        dialog.setCanceledOnTouchOutside(isdismiss);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return isdismiss;
            }
        });

        dialog.show();
        return this;
    }

    public TitleDialog setTitleStyleBold() {
        dv.tvTitle.getPaint().setFakeBoldText(true);//加粗
        dialog.dismiss();
        dialog.show();
        return this;
    }

    private static class DialogView {
        TextView tvTitle;
        TextView tvDetermine;
        TextView tvCancel;
        View line;
    }

}
