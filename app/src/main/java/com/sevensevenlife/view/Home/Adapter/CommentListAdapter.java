package com.sevensevenlife.view.Home.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sevensevenlife.model.httpmodel.CommentListMode;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.TextViewParser;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.CommentListItemBinding;

import java.util.List;

public class CommentListAdapter extends BaseRecyclerAdapter<CommentListAdapter.CommentViewHolder> {

    private Context mContext;
    private List<CommentListMode.RowsBean> list;
    private CommentListItemBinding binding;

    public CommentListAdapter(Context mContext, List<CommentListMode.RowsBean> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_list_item, parent, false);
        return new CommentViewHolder(binding.getRoot());
    }

    @Override
    public CommentViewHolder getViewHolder(View view) {
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position, boolean isItem) {
        CommentListMode.RowsBean mo = list.get(position);
        if (mo.getReplyContent() == null || mo.getReplyContent().equals("")) {
            holder.replyContent.setVisibility(View.GONE);
        } else {
            holder.replyContent.setVisibility(View.VISIBLE);
            holder.replyContent.setText(mo.getReplyContent());
        }
        ImgLoadUtils.Load(mContext, mo.getCustPic(), holder.topImage, true);
        holder.commentDate.setText(mo.getCreateDate());
        TextViewParser textViewParser = new TextViewParser();
        textViewParser.append(mo.getCustName(), 30, ContextCompat.getColor(mContext, R.color.black));
        textViewParser.append("\n" + mo.getContent(), 30, ContextCompat.getColor(mContext, R.color.txt_black));
        textViewParser.parse(holder.commentName);
    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentName;
        TextView commentDate;
        TextView replyContent;
        ImageView topImage;

        public CommentViewHolder(View v) {
            super(v);
            commentName = (TextView) v.findViewById(R.id.comment_name);
            commentDate = (TextView) v.findViewById(R.id.comment_date);
            replyContent = (TextView) v.findViewById(R.id.reply_content);
            topImage = (ImageView) v.findViewById(R.id.top_image);
        }
    }
}
