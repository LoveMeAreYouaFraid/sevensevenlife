package com.sevensevenlife.view.Find;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sevensevenlife.utils.FileUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.Util;
import com.example.youxiangshenghuo.databinding.LnvitationCourtesyActivityLayoutBinding;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;

import static com.example.youxiangshenghuo.wxapi.WXEntryActivity.app_id;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class InvitationCourtesyActivity extends BaseActivity implements View.OnClickListener {
    private LnvitationCourtesyActivityLayoutBinding binding;
    private File file;
    private String filePath;
    private static final int THUMB_SIZE = 150;
    private IWXAPI api;

    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.lnvitation_courtesy_activity_layout);
        binding.imgBack.setOnClickListener(this);
        binding.qq.setOnClickListener(this);
        binding.wx.setOnClickListener(this);
        binding.activityDetail.setOnClickListener(this);
        binding.rightButton.setOnClickListener(this);
        binding.title.setText("邀请有礼");

        if (TextUtils.isEmpty(PreferencesUtil.getString("filePath"))) {
            Resources res = getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.icon_03);
            file = FileUtils.saveBitmapFile(bmp, new File(MyApplication.getInstance().
                    getExternalCacheDir(),
                    "app.png"));
            PreferencesUtil.putString("filePath", file.getPath());
            filePath = file.getPath();
        } else {
            filePath = PreferencesUtil.getString("filePath");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.qq:
                break;
            case R.id.wx:
                break;
            case R.id.activity_detail:
                break;
            case R.id.right_button:
                api = WXAPIFactory.createWXAPI(this, app_id);
                WXWebpageObject webPage = new WXWebpageObject();
                webPage.webpageUrl = "http://111.8.133.24:7777/yxsh-api/download.jsp";
                WXMediaMessage msg = new WXMediaMessage(webPage);
                msg.title = "骑常德公共自行车，附近随时叫跑腿、搬家、清洁，尽在优享七七生活";
                msg.description = "在线押金租车，绑定租车卡租车，每次均享受2小时免费VIP待遇，更有周边生活服务，跑腿、搬家、清洁、维修、急开锁、保姆，一键获取，随叫随到。";
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_03);
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                bmp.recycle();
                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = mTargetScene;
                api.sendReq(req);
                break;
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
