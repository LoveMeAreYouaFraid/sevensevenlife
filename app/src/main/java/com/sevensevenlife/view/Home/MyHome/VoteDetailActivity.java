package com.sevensevenlife.view.Home.MyHome;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.httpmodel.VoteDetail;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.VoteDetailActivityLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_VOTE;
import static com.sevensevenlife.http.RequestUtils.URI.GET_VOTE_DETAIL;

/**
 * Created by Administrator on 2017/4/7 0007.
 */

public class VoteDetailActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {
    private VoteDetailActivityLayoutBinding binding;
    private List<CheckBox> checkBoxes = new ArrayList<>();
    private boolean oneChoice = true;//是否多选
    private String oneId = "", twoId = "", threeId = "", fourId = "", fiveId = "", sixId = "";
    private String ckId;
    private VoteDetail mo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.vote_detail_activity_layout);
        binding.title.imgBack.setOnClickListener(this);
        binding.voteBt.setOnClickListener(this);
        binding.voteTime.setTextColor(ContextCompat.getColor(mContext, R.color.style_color));
        checkBoxes.add(binding.one);
        checkBoxes.add(binding.two);
        checkBoxes.add(binding.three);
        checkBoxes.add(binding.four);
        checkBoxes.add(binding.five);
        checkBoxes.add(binding.six);
        getData();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.vote_bt:
                if (!binding.one.isChecked() &&
                        !binding.two.isChecked()
                        && !binding.three.isChecked()) {
                    ToastUtils.show("请选择一项再投票");
                    return;
                }
                binding.voteTime.setTextColor(ContextCompat.getColor(mContext, R.color.style_color_main));
                ckId = "";
                initID();
                MyRequest.POST(POST, new String[]{"sessionId", "voteId", "optionId"},
                        new String[]{
                                MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                getIntent().getStringExtra(KEY.ID),
                                ckId
                        },
                        ADD_VOTE,
                        PublicMode.class.getName(),
                        13,
                        this);
                break;
            case R.id.one:
                initCk(0);
                oneId = mo.getRows().getOptions().get(0).getId() + "";
                break;
            case R.id.two:
                initCk(1);
                twoId = mo.getRows().getOptions().get(1).getId() + "";
                break;
            case R.id.three:
                initCk(2);
                threeId = mo.getRows().getOptions().get(2).getId() + "";
                break;
            case R.id.four:
                initCk(3);
                fourId = mo.getRows().getOptions().get(3).getId() + "";
                break;
            case R.id.five:
                initCk(4);
                fiveId = mo.getRows().getOptions().get(4).getId() + "";
                break;
            case R.id.six:
                initCk(5);
                sixId = mo.getRows().getOptions().get(5).getId() + "";
                break;
        }

    }

    private void initID() {
        if (binding.one.isChecked()) {
            if (TextUtils.isEmpty(ckId)) {
                ckId = oneId;
            } else {
                ckId = ckId + "," + oneId;
            }

        }
        if (binding.two.isChecked()) {
            if (TextUtils.isEmpty(ckId)) {
                ckId = twoId;
            } else {
                ckId = ckId + "," + twoId;
            }

        }
        if (binding.three.isChecked()) {
            if (TextUtils.isEmpty(ckId)) {
                ckId = threeId;
            } else {
                ckId = ckId + "," + threeId;
            }

        }
        if (binding.four.isChecked()) {
            if (TextUtils.isEmpty(ckId)) {
                ckId = fourId;
            } else {
                ckId = ckId + "," + fourId;
            }

        }
        if (binding.five.isChecked()) {
            if (TextUtils.isEmpty(ckId)) {
                ckId = fiveId;
            } else {
                ckId = ckId + "," + fiveId;
            }

        }
        if (binding.six.isChecked()) {
            if (TextUtils.isEmpty(ckId)) {
                ckId = sixId;
            } else {
                ckId = ckId + "," + sixId;
            }

        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 12:
                mo = (VoteDetail) backMode;
                binding.voteTime.setText("结束时间:" + mo.getRows().getEnd_time());
                binding.voteDetail.setText(mo.getRows().getDescription());
                binding.voteTitle.setText(mo.getRows().getTitle());
                if (mo.getRows().getStatus() == 2) {
                    binding.voteBt.setOnClickListener(null);
                    binding.voteBt.setBackgroundResource(R.drawable.bg_hui_fillet);
                    binding.voteBt.setText("已结束");
                }
                if (mo.getRows().getMyChoice().size() != 0) {
                    binding.voteBt.setOnClickListener(null);
                    binding.voteBt.setBackgroundResource(R.drawable.bg_hui_fillet);
                    binding.voteBt.setText("已投票");
                    for (int i = 0; i < mo.getRows().getResults().size(); i++) {
                        checkBoxes.get(i).setText(mo.getRows().getResults().get(i).getOption_name() + "\t\t\t" +
                                mo.getRows().getResults().get(i).getVoteNumber() + "票");
                        checkBoxes.get(i).setVisibility(View.VISIBLE);
                        checkBoxes.get(i).setOnClickListener(null);
                        checkBoxes.get(i).setChecked(false);
                        checkBoxes.get(i).setClickable(false);
                    }

                } else {
                    for (int i = 0; i < mo.getRows().getOptions().size(); i++) {
                        checkBoxes.get(i).setText(mo.getRows().getOptions().get(i).getOption_name());
                        checkBoxes.get(i).setVisibility(View.VISIBLE);
                        checkBoxes.get(i).setOnClickListener(this);
                        checkBoxes.get(i).setChecked(true);
                        checkBoxes.get(i).setClickable(true);
                    }
                }
                if (mo.getRows().getOption_type() == 1) {
                    oneChoice = false;
                } else {
                    oneChoice = true;
                }

                break;
            case 13:
                ToastUtils.show("投票成功！");
                binding.voteBt.setBackgroundResource(R.drawable.bg_hui_fillet);
                binding.voteTime.setText("已投票");
                binding.voteBt.setClickable(false);
                getData();
                break;
        }


    }

    @Override
    public void error(String e, int uriType) {
        switch (uriType) {
            case 12:
                ToastUtils.show("投票失效");
                finish();
                break;
        }
    }

    private void initCk(int p) {
        if (!oneChoice) {
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (p != i) {
                    checkBoxes.get(i).setChecked(false);
                }
            }
        }

    }

    public void getData() {
        MyRequest.POST(GET, new String[]{"sessionId", "voteId"}, new String[]{
                MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                getIntent().getStringExtra(KEY.ID)
        }, GET_VOTE_DETAIL, VoteDetail.class.getName(), 12, this);
    }
}
