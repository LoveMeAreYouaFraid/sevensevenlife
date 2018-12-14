package com.sevensevenlife.view.Order;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.CommonCommentMode;
import com.sevensevenlife.model.httpmodel.OrderListItemMode;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ActivityPingfenInfoBinding;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_APPRAISAL;
import static com.sevensevenlife.http.RequestUtils.URI.GET_COMMON_COMMENT;

public class PingFenActivity extends BaseActivity implements
        MyHttpCallBack, View.OnClickListener {

    private ActivityPingfenInfoBinding binding;
    private OrderListItemMode mode;
    private List<TextView> textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pingfen_info);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.title.setText("评分");
        textViews = new ArrayList<>();
        textViews.add(binding.tvOne);
        textViews.add(binding.tvTwo);
        textViews.add(binding.tvThree);
        textViews.add(binding.tvFor);
        binding.tvOne.setOnClickListener(this);
        binding.tvTwo.setOnClickListener(this);
        binding.tvThree.setOnClickListener(this);
        binding.tvFor.setOnClickListener(this);

        binding.submit.setOnClickListener(this);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));

        mode = (OrderListItemMode) getIntent().getSerializableExtra("orderInfo");
        if (mode != null) {
            ImgLoadUtils.Load(mContext, mode.getService_pic(), binding.topImage, true);
            binding.paoduiName.setText(mode.getService_name() + "\n" +
                    mode.getProject_name() + "服务");
            MyRequest.POST(POST, new String[]{"projectid"}, new String[]{mode.getProject_id()}, GET_COMMON_COMMENT,
                    CommonCommentMode.class.getName(), 99, this);

        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 99:
                try {
                    CommonCommentMode mode = (CommonCommentMode) backMode;
                    String[] a = mode.getRows();
                    for (int i = 0; i < a.length; i++) {
                        textViews.get(i).setText(a[i]);
                    }
                } catch (Exception e) {
                    LogUtils.e(123, e.getMessage());
                }
                break;
            case 123:
                ToastUtils.show("评价成功");
                finish();
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(binding.edComment.getText())) {
                    ToastUtils.show("客官说两句呗~");
                    return;
                }

                DecimalFormat df = new DecimalFormat("###");
                MyRequest.POST(POST, new String[]{
                                "sessionId",
                                "serviceId",
                                "attitude",
                                "profession",
                                "punctual",
                                "orderId",
                                "content",
                                "isAnonymity",
                        }, new String[]{
                                MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                mode.getService_id(),
                                df.format(binding.tdRatingbar.getRating()) + "",
                                df.format(binding.zyRatingbar.getRating()) + "",
                                df.format(binding.ssRatingbar.getRating()) + "",
                                mode.getId(),
                                binding.edComment.getText() + "",
                                "0"
                        }, ADD_APPRAISAL,
                        PublicMode.class.getName(), 123, this);
                break;
            case R.id.tv_one:
                if (TextUtils.isEmpty(binding.edComment.getText())) {
                    binding.edComment.setText(binding.tvOne.getText());
                } else {
                    binding.edComment.setText(binding.edComment.getText() + "," + binding.tvOne.getText());
                }

                break;
            case R.id.tv_two:
                if (TextUtils.isEmpty(binding.edComment.getText())) {
                    binding.edComment.setText(binding.tvTwo.getText());
                } else {
                    binding.edComment.setText(binding.edComment.getText() + "," + binding.tvTwo.getText());
                }

                break;
            case R.id.tv_three:
                if (TextUtils.isEmpty(binding.edComment.getText())) {
                    binding.edComment.setText(binding.tvThree.getText());
                } else {
                    binding.edComment.setText(binding.edComment.getText() + "," + binding.tvThree.getText());
                }

                break;
            case R.id.tv_for:
                if (TextUtils.isEmpty(binding.edComment.getText())) {
                    binding.edComment.setText(binding.tvFor.getText());
                } else {
                    binding.edComment.setText(binding.edComment.getText() + "," + binding.tvFor.getText());
                }
                break;
        }
    }
}
