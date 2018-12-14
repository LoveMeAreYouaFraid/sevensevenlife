package com.sevensevenlife.view.custumview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.view.User.CouponListActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.LpWebLayoutBinding;

import static com.sevensevenlife.model.KEY.TITLE;
import static com.sevensevenlife.model.KEY.URL;

/**
 * Created by llH on 2017/1/30.
 */
public class LpWebActivity extends Activity implements View.OnClickListener {
    private LpWebLayoutBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.lp_web_layout);
        binding.title.imgBack.setOnClickListener(this);
        initWebViewSettings();
//        binding.webView.loadUrl(getIntent().getStringExtra(URL));
        binding.webView.addJavascriptInterface(new JsInterface(), "app");
        if (!TextUtils.isEmpty(getIntent().getStringExtra(TITLE))) {
            binding.title.title.setText(getIntent().getStringExtra(TITLE));
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra(URL))) {
            binding.webView.loadUrl(getIntent().getStringExtra(URL));
        }

    }

    public void initWebViewSettings() {
        binding.webView.getSettings().setBuiltInZoomControls(false);
        binding.webView.getSettings().setDefaultTextEncodingName("UTF-8");// 设置默认为utf-8
        binding.webView.getSettings().setAppCacheEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                binding.webView.addJavascriptInterface(new JsInterface(), "app");
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }


    public class JsInterface {

        @JavascriptInterface
        public void gotoCouponList() {
            if (MyApplication.getInstance().isLogin()) {
                startActivity(new Intent(LpWebActivity.this, CouponListActivity.class));
            }
        }
    }
}