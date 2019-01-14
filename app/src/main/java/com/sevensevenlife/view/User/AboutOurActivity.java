package com.sevensevenlife.view.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sevensevenlife.utils.MyApplication;
import com.example.youxiangshenghuo.R;


public class AboutOurActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutour);
        findViewById(R.id.img_back).setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.tv_version_number);
        textView.setText("优享七七生活" + MyApplication.getInstance().getVersionCode());

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
