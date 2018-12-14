package com.sevensevenlife.view.custumview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.youxiangshenghuo.R;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class SpinnerTextView extends LinearLayout {
    private TextView tv;
    private View view;


    public View getView() {
        return view;
    }


    public SpinnerTextView(Context context) {
        super(context);
    }

    public SpinnerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpinnerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean isShow = false;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.spinner_text_view_layout, this);
        tv = (TextView) findViewById(R.id.tv);
        view = findViewById(R.id.view);
        isShow = true;

    }

    public void setText(String str) {
        if (isShow){
            tv.setText(str);
        }
    }
}
