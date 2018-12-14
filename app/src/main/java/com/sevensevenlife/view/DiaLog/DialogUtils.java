package com.sevensevenlife.view.DiaLog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sevensevenlife.interfaces.CheckBoxListener;
import com.sevensevenlife.interfaces.DialogListener;
import com.example.youxiangshenghuo.R;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class DialogUtils {
    private static DialogUtils utils;
    private static Context mContext;
    private static Dialog dialog;
    private static TextView title, titleTwo, titleThree, one, two;
    private static View line;
    private static CheckBox checkBox;
    private String[] titles;

    public static DialogUtils init(Context context) {
        utils = new DialogUtils();
        mContext = context;
        dialog = new Dialog(mContext, R.style.dialog);
        dialog.setContentView(R.layout.dialog_list_layout);
        title = (TextView) dialog.findViewById(R.id.tv_title);
        titleTwo = (TextView) dialog.findViewById(R.id.tv_title_two);
        titleThree = (TextView) dialog.findViewById(R.id.tv_title_three);
        one = (TextView) dialog.findViewById(R.id.tv_determine);
        two = (TextView) dialog.findViewById(R.id.tv_cancel);
        checkBox = (CheckBox) dialog.findViewById(R.id.no_prompt);
        line = dialog.findViewById(R.id.line);
        title.setVisibility(View.GONE);
        one.setVisibility(View.GONE);
        two.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        dialog.show();
        return utils;
    }

    public DialogUtils setTitle(String s) {
        if (title != null) {
            title.setText(s);
            title.setVisibility(View.VISIBLE);
        }
        return this;
    }


    public DialogUtils setTitle(String[] s, final DialogListener listener) {
        titles = s;
        if (title != null) {
            title.setVisibility(View.VISIBLE);
            titleTwo.setVisibility(View.VISIBLE);
            titleThree.setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.line_layout).setVisibility(View.GONE);
            title.setText(s[0]);
            titleTwo.setText(s[1]);
            titleThree.setText(s[2]);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.buttType(0);
                    dialog.dismiss();
                }
            });
            titleTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.buttType(1);
                    dialog.dismiss();
                }
            });
            titleThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.buttType(2);
                    dialog.dismiss();
                }
            });

        }
        return this;
    }


    public DialogUtils setOne(String s, final DialogListener listener) {
        if (one != null) {
            one.setText(s);
            one.setVisibility(View.VISIBLE);
            one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.buttType(1);
                    }
                    dialog.dismiss();
                }
            });
            if (two.getVisibility() == View.VISIBLE) {
                line.setVisibility(View.VISIBLE);
            }else {
                one.setBackgroundResource(R.drawable.bg_click_dialog_lift_and_rigth_gray);
            }
        }
        return this;
    }

    public DialogUtils setTwo(String s, final DialogListener listener) {
        if (two != null) {
            two.setText(s);
            two.setVisibility(View.VISIBLE);
            two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.buttType(1);
                    }
                    dialog.dismiss();
                }
            });
            line.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public DialogUtils setOne(String s) {
        if (one != null) {
            one.setText(s);
            one.setVisibility(View.VISIBLE);
            if (two.getVisibility() == View.VISIBLE) {
                line.setVisibility(View.VISIBLE);
            }
        }
        return this;
    }

    public DialogUtils setTwo(String s) {
        if (two != null) {
            two.setText(s);
            two.setVisibility(View.VISIBLE);
            if (one.getVisibility() == View.VISIBLE) {
                line.setVisibility(View.VISIBLE);
            }
        }
        return this;
    }

    public DialogUtils isTouchcCancel(final boolean isdismiss) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(isdismiss);
            dialog.setCancelable(isdismiss);

        }
        return this;
    }

    public DialogUtils isChecked(final CheckBoxListener checkBoxListener) {
        if (checkBox != null) {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBoxListener.isCheck(checkBox.isChecked());
                }
            });
        }
        return this;
    }

    public DialogUtils setDialogListener(final DialogListener checkBoxListener) {
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxListener.buttType(1);
                dialog.dismiss();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxListener.buttType(2);
                dialog.dismiss();
            }
        });
        return this;
    }

    public DialogUtils setTitleStyleBold() {
        title.getPaint().setFakeBoldText(true);//加粗
        return this;
    }
}
