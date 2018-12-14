package com.sevensevenlife.utils.RecyclerViewUtils;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public  class PublicViewHeader extends RecyclerView.ViewHolder {
    private ViewDataBinding viewDataBinding;

    public PublicViewHeader(View v) {
        super(v);
    }


    public ViewDataBinding getBinding() {
        return viewDataBinding;
    }

    public void setBinding(ViewDataBinding viewDataBinding) {
        this.viewDataBinding = viewDataBinding;
    }
}
