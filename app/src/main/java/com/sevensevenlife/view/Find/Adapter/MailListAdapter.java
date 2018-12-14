package com.sevensevenlife.view.Find.Adapter;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.model.httpmodel.FriendListRows;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.view.custumview.CircleImageView;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ContactsListItemBinding;

import java.util.List;

public class MailListAdapter extends RecyclerView.Adapter<mViewHolder> {
    private List<FriendListRows> list;
    private ContactsListItemBinding binding;

    private ListItemListener listener;

    public void setListener(ListItemListener listener) {
        this.listener = listener;
    }

    public MailListAdapter(List<FriendListRows> list) {
        this.list = list;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.contacts_list_item, parent, false);
        return new mViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, final int position) {
        ImgLoadUtils.Load(holder.imageView.getContext(), list.get(position).getHead_pic(), holder.imageView, true);
        String word = list.get(position).getPinyin();
        holder.tv_word.setText(word);
        holder.tv_name.setText(list.get(position).getReal_name());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ChildView(v, position);
            }
        });
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.Item(position);
            }
        });
//        //将相同字母开头的合并在一起
        if (position == 0) {
            //第一个是一定显示的
            holder.tv_word.setVisibility(View.VISIBLE);
        } else {
            //后一个与前一个对比,判断首字母是否相同，相同则隐藏
            String headerWord = list.get(position - 1).getPinyin();
            if (word.equals(headerWord)) {
                holder.tv_word.setVisibility(View.GONE);
            } else {
                holder.tv_word.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = inflater.inflate(R.layout.list_item, null);
//            holder.tv_word = (TextView) convertView.findViewById(R.id.tv_word);
//            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        String word = list.get(position).getHeaderWord();
//        holder.tv_word.setText(word);
//        holder.tv_name.setText(list.get(position).getName());
//        //将相同字母开头的合并在一起
//        if (position == 0) {
//            //第一个是一定显示的
//            holder.tv_word.setVisibility(View.VISIBLE);
//        } else {
//            //后一个与前一个对比,判断首字母是否相同，相同则隐藏
//            String headerWord = list.get(position - 1).getHeaderWord();
//            if (word.equals(headerWord)) {
//                holder.tv_word.setVisibility(View.GONE);
//            } else {
//                holder.tv_word.setVisibility(View.VISIBLE);
//            }
//        }
//        return convertView;
//    }


}

class mViewHolder extends RecyclerView.ViewHolder {
    TextView tv_word;
    TextView tv_name;
    CircleImageView imageView;
    View call, rootView;

    public mViewHolder(View v) {
        super(v);
        tv_word = (TextView) v.findViewById(R.id.tv_word);
        tv_name = (TextView) v.findViewById(R.id.tv_name);
        imageView = (CircleImageView) v.findViewById(R.id.user_head_img);
        call = v.findViewById(R.id.call);
        rootView = v.findViewById(R.id.rootView);
    }
}