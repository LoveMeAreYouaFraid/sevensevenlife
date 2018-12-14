package com.sevensevenlife.interfaces;

import android.view.View;

/**
 * Created by 10237 on 2016/12/24.
 */

public interface ListItemListener {
    void Item(int position);

    void ChildView(View v, int position);

}
