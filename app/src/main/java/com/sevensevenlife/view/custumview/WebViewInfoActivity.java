package com.sevensevenlife.view.custumview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.DiaLog.TitleDialog;
import com.sevensevenlife.view.Mian.MainActivityLp;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ActivityWebviewBinding;


@SuppressLint("SetJavaScriptEnabled")
public class WebViewInfoActivity extends Activity implements View.OnClickListener {

    private static final boolean TODO = true;
    private ActivityWebviewBinding binding;
    private boolean isCall = false;
    private Context mContext;

    private boolean IsAdvertisement = false;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        binding.title.imgBack.setOnClickListener(this);
        mContext = this;
//		clearWebViewCache();
        initWebView();
        if (!TextUtils.isEmpty(getIntent().getStringExtra("IsRecharge"))) {
            if (getIntent().getStringExtra("IsRecharge").equals("0")) {
               new  TitleDialog().SHOW(mContext, "点击   “我的余额”  进行充值”", "我懂了", new DialogListener() {
                    @Override
                    public void buttType(int ButtType) {
                        if (ButtType == 0) {

                        }
                    }
                }, true);
            } else if (getIntent().getStringExtra("IsRecharge").equals("1")) {

                binding.title.title.setText("服务协议");
            }
        }
        //进度条  start
        binding.webview.setWebChromeClient(new WebChromeClient() {
            @Override
            //自定义alert 选项
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                final MyDialogView myDialogView = new MyDialogView(WebViewInfoActivity.this);
                View v = View.inflate(WebViewInfoActivity.this, R.layout.dialog_alert_confirm, null);
                TextView alert_confirm = (TextView) v.findViewById(R.id.alert_confirm);
                alert_confirm.setVisibility(View.GONE);
                TextView alert_confirm2 = (TextView) v.findViewById(R.id.alert_confirm2);
                alert_confirm2.setVisibility(View.VISIBLE);
                alert_confirm2.setText(message);
                TextView submitThis = (TextView) v.findViewById(R.id.submitThis);
                TextView cancalThis = (TextView) v.findViewById(R.id.cancalThis);
                myDialogView.show(v);
                submitThis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialogView.dismiss();
                    }
                });
                cancalThis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialogView.dismiss();
                    }
                });

                result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url,
                                       String message, JsResult result) {
                // TODO Auto-generated method stub
                LogUtils.e(123123, message);
                return super.onJsConfirm(view, url, message, result);
            }

            //	    	  onjs
            //进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    binding.myProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == binding.myProgressBar.getVisibility()) {
                        binding.myProgressBar.setVisibility(View.VISIBLE);
                    }
                    binding.myProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        binding.webview.setDownloadListener(new MyWebViewDownLoadListener(WebViewInfoActivity.this));
        binding.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.webview.getSettings().setJavaScriptEnabled(true);// 开启Javascript支持
        binding.webview.getSettings().setLoadsImagesAutomatically(true);// 设置可以自动加载图片
        binding.webview.setHorizontalScrollBarEnabled(false);//设置水平滚动条
        binding.webview.setVerticalScrollBarEnabled(true);//设置竖直滚动条
        binding.webview.getSettings().setBuiltInZoomControls(false);
        binding.webview.getSettings().setSupportZoom(false);
        binding.webview.getSettings().setDisplayZoomControls(false);
        //解决页面放大问题
        binding.webview.getSettings().setUseWideViewPort(true);
        binding.webview.getSettings().setLoadWithOverviewMode(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(getIntent().getStringExtra("Advertisement"))) {
            IsAdvertisement = true;
            binding.webview.loadUrl(getIntent().getStringExtra("Advertisement"));
        } else if (!isCall) {
            if (TextUtils.isEmpty(getIntent().getStringExtra("UrlAddress").toString())) {
                ToastUtils.show("暂无数据");
                return;
            }
            binding.webview.loadUrl(getIntent().getStringExtra("UrlAddress").toString());
            LogUtils.e(123, "onStart\n" + getIntent().getStringExtra("UrlAddress").toString());
            binding.webview.setWebViewClient(new MyWebViewClient());
        }
        isCall = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                if (IsAdvertisement) {
                    startActivity(new Intent(mContext, MainActivityLp.class));
                    finish();
                } else {
                    goBack();
                }

                break;
        }

    }


    private class MyWebViewClient extends WebViewClient {
        //开始
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            /**自行车 start**/
            if (url.startsWith("token:")) {
                String token = url.substring(8, url.length());
                PreferencesUtil.putString("token", token);
                finish();
                ToastUtils.show("登录成功，请继续操作");
            }
            if (url.startsWith("failure:")) {
                ToastUtils.show("操作失败");
                finish();
            }
            if (url.startsWith("success:")) {
                ToastUtils.show("操作成功");
                finish();
            }
            if (url.startsWith("UserScan:")) {
                ToastUtils.show("付款成功");
                finish();
            }
            /**自行车 end**/
            //启动打电话程序
            if (url.startsWith("tel:")) {
//            	mypDialog.cancel();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                }
                startActivity(intent);
                binding.webview.goBack();   //后退
                isCall = true;//设置为打电话
            }
            return true;
        }

        //完成
        @Override
        public void onPageFinished(WebView view, String url) {
        }

        //出错
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            String data = "Page NO FOUND！<br>哎呀！页面出错了...";
            aa = 1;
            view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
        }
    }

    private int aa = 0;

    @Override

    protected void onDestroy() {
        if (binding.webview != null) {
            RelativeLayout webViewRL = (RelativeLayout) findViewById(R.id.web_layout);
            webViewRL.removeView(binding.webview);
            binding.webview.removeAllViews();
            binding.webview.destroy();
        }
        super.onDestroy();
    }


    //设置WebView 缓存模式
    private void initWebView() {

        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.getSettings().setRenderPriority(RenderPriority.HIGH);
    }


    //返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (IsAdvertisement) {
            startActivity(new Intent(mContext,MainActivityLp.class));
            finish();
            return true;
        } else if (keyCode == 4) {
            goBack();
            return true;
        } else {
            return false;
        }
    }


    private void goBack() {
        Integer backi = -1;
        WebBackForwardList list = binding.webview.copyBackForwardList();
        if (aa == 1) {
            finish();
        }
        if (list.getCurrentIndex() == 0) {
            finish();
        } else {
            if (getIntent().getStringExtra("QQ_MAP") != null) {
                finish();
            } else {
                binding.webview.goBack();
                binding.webview.goBack();
            }
        }
    }
}

 
