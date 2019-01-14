package com.sevensevenlife.view.DiaLog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.SevenServiceDialogListItemBinding;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class ListDialogUtils {
    private static ListDialogUtils utils;
    private static Context mContext;
    private static Dialog dialog;
    private List<String> titles;
    private DialogListener listener;
    private static RecyclerView recyclerView;

    public static ListDialogUtils init(Context context) {
        utils = new ListDialogUtils();
        mContext = context;
        dialog = new Dialog(mContext, R.style.dialog);
        dialog.setContentView(R.layout.dialog_list_layout_layout);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);
        return utils;
    }


    public ListDialogUtils setData(final List<String> titles) {
        this.titles = titles;
        return this;
    }

    public ListDialogUtils OnClickListener(final DialogListener listener) {
        this.listener = listener;
        return this;
    }

    public ListDialogUtils show() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));
        recyclerView.setAdapter(new PublicAdapter(titles.size(), R.layout.seven_service_dialog_list_item, new BindViewInterface() {
            @Override
            public void bandView(ViewDataBinding binding, final int position) {
                SevenServiceDialogListItemBinding bind = (SevenServiceDialogListItemBinding) binding;
                bind.txt.setText(titles.get(position));
                bind.txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.buttType(position);
                        dialog.dismiss();
                    }
                });
            }
        }));
        dialog.show();
        return this;
    }

    public ListDialogUtils isTouchcCancel(final boolean isdismiss) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(isdismiss);
            dialog.setCancelable(isdismiss);

        }
        return this;
    }


}
