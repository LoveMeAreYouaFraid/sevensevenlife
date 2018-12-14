package com.sevensevenlife.view.custumview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sevensevenlife.interfaces.DialogListener;
import com.example.youxiangshenghuo.R;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class ViewPagerTabLayout extends LinearLayout {

    private TextView tabOne, tabTwo;
    private DialogListener listener;

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    public ViewPagerTabLayout(Context context) {
        super(context);
    }

    public ViewPagerTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_pager_tab_layout, this);
        tabOne = (TextView) findViewById(R.id.tab_one);
        tabTwo = (TextView) findViewById(R.id.tab_two);
        tabOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tabOne.setTextColor(ContextCompat.getColor(getContext(), R.color.txt_black));
                tabOne.setBackgroundResource(R.drawable.bg_view_pager_tab_item_white_left);
                tabTwo.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tabTwo.setBackgroundResource(R.drawable.bg_view_pager_tab_item_red_right);

                if (listener != null) {
                    listener.buttType(0);
                }
            }
        });
        tabTwo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tabOne.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tabOne.setBackgroundResource(R.drawable.bg_view_pager_tab_item_blue_left);
                tabTwo.setTextColor(ContextCompat.getColor(getContext(), R.color.txt_black));
                tabTwo.setBackgroundResource(R.drawable.bg_view_pager_tab_item_white_right);
                if (listener != null) {
                    listener.buttType(1);
                }
            }
        });

    }
}
