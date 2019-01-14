package com.sevensevenlife.utils.RecyclerViewUtils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public class RecyclerItemMargin extends RecyclerView.ItemDecoration {

    private int space;
    private boolean t;

    public RecyclerItemMargin(int space) {
        this.space = space;
    }

    public RecyclerItemMargin(boolean t) {
        this.t = t;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (t) {
            outRect.set(0, 0, 0, 1);
        } else {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;

            }
        }

    }
}