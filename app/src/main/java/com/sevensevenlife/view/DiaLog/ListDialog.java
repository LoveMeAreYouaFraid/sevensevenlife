package com.sevensevenlife.view.DiaLog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sevensevenlife.interfaces.ListItemListener;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ListDialogLayoutBinding;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class ListDialog {
    private static RecyclerView recyclerView;
    private static Dialog dialog;

    public static Dialog getDialog() {
        return dialog;
    }


    public static void show(Context context, List<String> address, List<String> titles, final ListItemListener listItemListener, final boolean is) {
        ListDialogAdapter adapter;
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialig_list_layout);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));
        adapter = new ListDialogAdapter(context, address, titles);
        recyclerView.setAdapter(adapter);
        adapter.setListItemListener(new ListItemListener() {
            @Override
            public void Item(int position) {
                listItemListener.Item(position);
                dialog.dismiss();

            }

            @Override
            public void ChildView(View v, int position) {

            }
        });
        dialog.setCanceledOnTouchOutside(is);
        dialog.show();
    }


}

class ListDialogAdapter extends BaseRecyclerAdapter<ViewHead> {
    private Context mContext;
    private List<String> list;
    private ListDialogLayoutBinding binding;
    private ListItemListener Listener;
    private List<String> titles;

    public void setListItemListener(ListItemListener listItemListener) {
        Listener = listItemListener;
    }

    public ListDialogAdapter(Context context, List<String> lists, List<String> title) {
        mContext = context;
        list = lists;
        titles = title;
    }

    @Override
    public ViewHead getViewHolder(View view) {
        return new ViewHead(view);
    }

    @Override
    public ViewHead onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_dialog_layout, parent, false);

        return new ViewHead(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHead holder, final int position, boolean isItem) {
        holder.dialogText.setText(list.get(position));
        holder.dialogDetail.setText(titles.get(position));
        holder.dialogDetail.setVisibility(View.VISIBLE);
        holder.dialogText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listener.Item(position);
            }
        });
    }


    @Override
    public int getAdapterItemCount() {
        return list.size();
    }
}

class ViewHead extends RecyclerView.ViewHolder {
    TextView dialogText;
    TextView dialogDetail;

    public ViewHead(View v) {
        super(v);
        dialogText = (TextView) v.findViewById(R.id.dialog_text);
        dialogDetail = (TextView) v.findViewById(R.id.dialog_detail);
    }
}