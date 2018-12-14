package com.sevensevenlife.view.Home;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ActivityMoreBinding;

public class MoreActivity extends Activity implements View.OnClickListener {
    private ActivityMoreBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_more);
        binding.title.imgBack.setOnClickListener(this);
    }


    public void back(View v) {
        finish();
    }

    public void callService(View v) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:0736-7777777"));
        startActivity(intent);//方法内部自动为intent添加类别android.intent.category.DEFAULT
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
