package com.sevensevenlife.view.DiaLog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sevensevenlife.interfaces.OneEditDialogListener;
import com.example.youxiangshenghuo.R;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class OneEdItDialog {
    private Context mContext;
    private Dialog dialog;
    private String hintString, titleSting = "";
    private OneEditDialogListener listener;

    public OneEdItDialog init(Context context) {
        mContext = context;
        return this;
    }

    public OneEdItDialog setHintString(String hintString) {
        this.hintString = hintString;
        return this;
    }

    public OneEdItDialog setTitleSting(String titleSting) {
        this.titleSting = titleSting;
        return this;
    }

    public OneEdItDialog setListener(OneEditDialogListener listener) {
        this.listener = listener;
        return this;
    }

    public void show() {
        dialog = new Dialog(mContext, R.style.dialog);
        dialog.setContentView(R.layout.one_editext_dialog);
        final EditText editText;
        TextView confirm, title;
        editText = (EditText) dialog.findViewById(R.id.ed_one);
        title = (TextView) dialog.findViewById(R.id.tv_title);
        confirm = (TextView) dialog.findViewById(R.id.bt_confirm);

        if (TextUtils.isEmpty(titleSting)) {
            title.setVisibility(View.GONE);
        } else {
            title.setText(titleSting);
        }
        editText.setHint(hintString);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && !TextUtils.isEmpty(editText.getText())) {
                    listener.click(editText.getText() + "");
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
