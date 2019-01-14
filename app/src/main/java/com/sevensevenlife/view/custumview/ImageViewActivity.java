package com.sevensevenlife.view.custumview;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.utils.ImgLoadUtils;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ImageViewActivityLayoutBinding;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class ImageViewActivity extends BaseActivity {
    private ImageViewActivityLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.image_view_activity_layout);
        ImgLoadUtils.Load(mContext, getIntent().getStringExtra("uri"), binding.image, false);
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void Show(Context mContext, String uri) {
        mContext.startActivity(new Intent(mContext, ImageViewActivity.class).putExtra("uri", uri));
    }
}
