package com.sevensevenlife.view.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sevensevenlife.model.httpmodel.UserInfo;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.Find.InvitationCourtesyActivity;
import com.sevensevenlife.view.Home.AppMsg.AppMsgActivity;
import com.sevensevenlife.view.Home.MyHome.MyHomeActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.UserFragmentBinding;
import com.sevensevenlife.utils.MemoryBean;


/**
 * Created by Administrator on 2017/1/25 0025.
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    private UserFragmentBinding binding;

    private Context mContext;

    private Intent intent;

    @Override
    public void onStart() {
        super.onStart();
        setViewValue();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_fragment, container, false);

        mContext = getActivity();
        setValue();

        return binding.getRoot();
    }


    public void setValue() {

        binding.userBalance.setOnClickListener(this);

        binding.userDiscount.setOnClickListener(this);

        binding.integral.setOnClickListener(this);

        binding.waiter.setOnClickListener(this);

        binding.service.setOnClickListener(this);

        binding.userHome.setOnClickListener(this);

        binding.userInvitation.setOnClickListener(this);

        binding.userMsg.setOnClickListener(this);

        binding.userOpinion.setOnClickListener(this);

        binding.userSettled.setOnClickListener(this);

        binding.userSetup.setOnClickListener(this);

        binding.userInfo.setOnClickListener(this);
        if (MyApplication.getInstance().isLogin()) {
            binding.nickName.setText(MyApplication.getInstance().getUserInfo().getRows().getNickName());
            binding.loginPhone.setText(MyApplication.getInstance().getUserInfo().getRows().getPhone().substring(0, 4) + "****" +
                    MyApplication.getInstance().getUserInfo().getRows().getPhone().substring(7, 11));
            ImgLoadUtils.Load(mContext, MyApplication.getInstance().getUserInfo().getRows().getHeadPic(), binding.userHeadImg, true);
        } else {
            binding.nickName.setText("未登录");
            binding.loginPhone.setText("");
            binding.userHeadImg.setBackgroundResource(R.drawable.touxiang);
        }
    }


    @Override
    public void onClick(View v) {
        if (!MyApplication.getInstance().isLogin()) {
            intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            return;
        }
        switch (v.getId()) {
            case R.id.user_info:
                Intent userIntent = new Intent(mContext, MyInfoActivity.class);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), new Pair[]{
                                Pair.create(binding.userHeadImg, "touxiang"),
                                Pair.create(binding.loginPhone, "loginPhone"),
                                Pair.create(binding.nickName, "nickName")});
                ActivityCompat.startActivityForResult(getActivity(), userIntent, 1, options.toBundle());
                break;
            case R.id.user_balance:
                ToastUtils.show(getResources().getString(R.string.qidai));
                break;

            case R.id.user_discount:
                if (MyApplication.getInstance().isLogin()) {
                    startActivity(new Intent(mContext, CouponListActivity.class));
                }
                break;

            case R.id.integral:
                ToastUtils.show(getResources().getString(R.string.qidai));
                break;

            case R.id.waiter:
                if (MemoryBean.industyListMap) {
                    Intent intent;
                    intent = new Intent(mContext, CollectListActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtils.show("请稍等，当前网络较慢");
                }
                break;

            case R.id.service:

                ToastUtils.show(getResources().getString(R.string.qidai));

                break;
            case R.id.user_home://我的小区
                if (MyApplication.getInstance().isLogin()){
                    startActivity(new Intent(mContext, MyHomeActivity.class));
                }else {
                    ToastUtils.show("请先登录~");
                }
//                ToastUtils.show(getResources().getString(R.string.qidai));

                break;
            case R.id.user_invitation://邀请
                startActivity(new Intent(getActivity(), InvitationCourtesyActivity.class));
                break;
            case R.id.user_msg://消息中心
                intent = new Intent(mContext, AppMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.user_opinion://意见
                intent = new Intent(mContext, SuggestioinActivity.class);
                startActivity(intent);
                break;
            case R.id.user_setup://设置
                Intent intent = new Intent(mContext, SheZhiActivity.class);
                startActivity(intent);
                break;
            case R.id.user_settled://入驻
                Intent intents = new Intent();
                intents.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://218.75.134.187:7777/yxsh-api/download.jsp?type=service");
                intents.setData(content_url);
                startActivity(intents);
                break;
        }
    }

    private UserInfo userInfo;

    private void setViewValue() {

        if (MyApplication.getInstance().isLogin()) {
            userInfo = MyApplication.getInstance().getUserInfo();
            binding.nickName.setText(userInfo.getRows().getNickName());
            binding.loginPhone.setText(userInfo.getRows().getPhone().substring(0, 4) + "****" +
                    userInfo.getRows().getPhone().substring(7, 11));
            ImgLoadUtils.Load(mContext, userInfo.getRows().getHeadPic(), binding.userHeadImg, true);
        } else {
            binding.nickName.setText("未登录");
            binding.loginPhone.setText("");
            binding.userHeadImg.setImageResource(R.drawable.touxiang);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    binding.nickName.setText(MyApplication.getInstance().getUserInfo().getRows().getNickName());
                    ImgLoadUtils.Load(mContext, MyApplication.getInstance().getUserInfo().getRows().getHeadPic(), binding.userHeadImg, true);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
