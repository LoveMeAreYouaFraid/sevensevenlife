package com.sevensevenlife.view.DiaLog;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.sevensevenlife.interfaces.EditDialogListener;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.example.youxiangshenghuo.R;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class EdItDialog {
    public static final int PASS_WORD = 0;
    public static final int NAME = 1;
    public static final int ID_CAR = 2;
    public static final int NICK_NAME = 3;
    public static final int CarNum = 4;
    public static final int comment = 5;


    public static String[] strings = new String[]{"", "真实姓名", "身份证号", "昵称", "车身编号", "你的神回复"};


    public static void show(Context context, final EditDialogListener dialogListener, final int type) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_update_password);
        final EditText one, two, three;
        View confirm, lineTwo, lineOne;
        one = (EditText) dialog.findViewById(R.id.ed_one);
        lineOne = dialog.findViewById(R.id.layout_one);
        lineTwo = dialog.findViewById(R.id.layout_two);
        two = (EditText) dialog.findViewById(R.id.ed_two);
        three = (EditText) dialog.findViewById(R.id.ed_three);
        confirm = dialog.findViewById(R.id.bt_confirm);
        if (type == NAME || type == ID_CAR || type == NICK_NAME || type == CarNum || type == comment) {
            lineOne.setVisibility(View.GONE);
            lineTwo.setVisibility(View.GONE);
        }
        one.setHint("请输入" + strings[type]);
        if (type != PASS_WORD) {
            one.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        try {
            if (type == ID_CAR) {

                one.setText(MyApplication.getInstance().getUserInfo().getRows().getCardNo());
                one.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18),
                        new InputFilter.AllCaps() {
                        }});
            }
            if (type == NAME) {
                one.setText(MyApplication.getInstance().getUserInfo().getRows().getRealName());
            }

        } catch (Exception e) {

        }
        confirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (type == PASS_WORD) {
                            if (!TextUtils.isEmpty(one.getText()) ||
                                    !TextUtils.isEmpty(two.getText()) ||
                                    !TextUtils.isEmpty(three.getText())) {
                                if (!two.getText().toString().equals(three.getText().toString())) {
                                    ToastUtils.show("两次输入密码不匹配");
                                    return;
                                }
                                dialogListener.click(type, new String[]{one.getText() + "",
                                        two.getText() + "", three.getText() + ""});
                                LogUtils.e(123, two.getText() + "" + three.getText() + "");
                                dialog.dismiss();
                            } else {
                                ToastUtils.show("请补全信息");
                            }

                        } else {
                            if (TextUtils.isEmpty(one.getText())) {
                                ToastUtils.show(strings[type] + "不能为空");
                                return;
                            }
                            if (type == ID_CAR) {
                                if (one.getText().length() != 18) {
                                    ToastUtils.show("身份证号不足18位");
                                    return;
                                }
                            }
                            dialogListener.click(type, new String[]{one.getText() + ""});
                            dialog.dismiss();
                        }
                    }
                }
        );

        dialog.show();
    }
}
