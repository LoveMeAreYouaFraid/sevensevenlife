package com.sevensevenlife.view.DiaLog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sevensevenlife.interfaces.SevenServiceDialogListener;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.SevenServiceDialogListItemBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class SevenServiceDialog {
    private SevenServiceDialogListener istener;
    private mAdapter adapterOne;
    private static mAdapter adapterTwo;
    private static String[] sort = new String[]{"默认排序", "距离优先", "评分优先", "成单量优先"};
    private String[] screenOne = new String[]{"全部", "平台认证", "未认证"};
    private String[] screenTwo = new String[]{"全部", "空闲", "服务中"};
    private Dialog dialog;
    private String positionOnes="0", positiontwos="0";


    /**
     * @param context
     * @param type       0 排序  1 各种服务  2筛选
     * @param stringsOne
     * @param
     */
    public void show(Context context, int type, List<String> stringsOne,
                     final SevenServiceDialogListener listItemListener) {
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.seven_service_dialog);
        View view = dialog.findViewById(R.id.line);
        view.setVisibility(View.GONE);
        istener = listItemListener;
        final RecyclerView recyclerViewOne = (RecyclerView) dialog.findViewById(R.id.list_one);
        RecyclerView recyclerViewTwo = (RecyclerView) dialog.findViewById(R.id.list_two);
        recyclerViewOne.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));
        recyclerViewTwo.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));

        switch (type) {
            case 0:
                adapterOne = new mAdapter(dialog, Arrays.asList(sort), type, listItemListener);
                recyclerViewOne.setAdapter(adapterOne);
                break;
            case 1:
                adapterOne = new mAdapter(dialog, stringsOne, type, listItemListener);
                recyclerViewOne.setAdapter(adapterOne);
                break;
            case 2:
                view.setVisibility(View.VISIBLE);
                adapterOne = new mAdapter(dialog, Arrays.asList(screenOne), 3, new SevenServiceDialogListener() {
                    @Override
                    public void item(String positionOne, String positionTwo) {
                        positionOnes = positionOne;

                    }
                });
                adapterTwo = new mAdapter(dialog, Arrays.asList(screenTwo), 4, new SevenServiceDialogListener() {
                    @Override
                    public void item(String positionOne, String positionTwo) {
                        positiontwos = positionTwo;
                        listItemListener.item(positionOnes, positiontwos);
                    }
                });
                recyclerViewOne.setAdapter(adapterOne);
                recyclerViewTwo.setAdapter(adapterTwo);
                recyclerViewTwo.setVisibility(View.VISIBLE);
                break;
        }
        dialog.show();
    }


}

class mAdapter extends BaseRecyclerAdapter<mViewHolder> {
    private List<String> stringList;
    private int type;
    private SevenServiceDialogListener listener;
    private SevenServiceDialogListItemBinding binding;
    private Dialog dialog;
    private List<TextView> views = new ArrayList<>();
    private boolean ck = true;

    public mAdapter(Dialog dialog, List<String> stringList, int type, SevenServiceDialogListener listener) {
        this.stringList = stringList;
        this.type = type;
        this.listener = listener;
        this.dialog = dialog;
    }


    @Override
    public mViewHolder getViewHolder(View view) {
        return new mViewHolder(view);
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.seven_service_dialog_list_item, parent, false);
        views.add(binding.txt);
        return new mViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position, boolean isItem) {
        holder.txt.setText(stringList.get(position));

        holder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type == 3) {
                    if (ck) {
                        views.get(position).setBackgroundColor(ContextCompat.getColor(
                                holder.txt.getContext(), R.color.hui));
                        ck = false;
                    } else {
                        views.get(position).setBackgroundResource(R.drawable.bg_click_withe);
                        ck=true;
                    }

                    listener.item(position + "", "");
                } else if (type == 4) {
                    listener.item("", position + "");
                    dialog.dismiss();
                } else {
                    listener.item(position + "", "");
                    dialog.dismiss();
                }

            }
        });
    }

    @Override
    public int getAdapterItemCount() {
        return stringList.size();
    }

}

class mViewHolder extends RecyclerView.ViewHolder {
    TextView txt;

    public mViewHolder(View v) {
        super(v);
        txt = (TextView) v.findViewById(R.id.txt);
    }
}