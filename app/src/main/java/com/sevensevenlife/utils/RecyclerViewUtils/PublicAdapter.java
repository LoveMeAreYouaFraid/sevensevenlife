package com.sevensevenlife.utils.RecyclerViewUtils;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sevensevenlife.interfaces.BindViewInterface;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class PublicAdapter<T> extends BaseRecyclerAdapter<PublicViewHeader> {

    private BindViewInterface bindViewInterface;
    private int layoutId;
    private List<T> mDatas;
    private int size;
    private ViewDataBinding binding;

    public PublicAdapter(List<T> mData, int layoutId, BindViewInterface bindViewInterface) {
        this.mDatas = mData;
        this.layoutId = layoutId;
        this.bindViewInterface = bindViewInterface;
    }

    public PublicAdapter(int size, int layoutId, BindViewInterface bindViewInterface) {
        this.size = size;
        this.layoutId = layoutId;
        this.bindViewInterface = bindViewInterface;
    }


    @Override
    public PublicViewHeader onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                layoutId,
                parent,
                false);

        PublicViewHeader holder = new PublicViewHeader(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public PublicViewHeader getViewHolder(View view) {
        return new PublicViewHeader(view);
    }


    @Override
    public void onBindViewHolder(PublicViewHeader holder, int position, boolean ds) {
        bindViewInterface.bandView(holder.getBinding(), position);
    }

    @Override
    public int getAdapterItemCount() {
        if (mDatas == null) {
            return size;
        } else {
            return mDatas.size();
        }

    }

}

