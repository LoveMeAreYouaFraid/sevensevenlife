package com.sevensevenlife.view.custumview.zxing;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.ToastUtils;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ZxingCaptureBinding;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import static com.sevensevenlife.model.KEY.BAND_CAR;
import static com.sevensevenlife.model.KEY.CAR_RENTAL;

/**
 *
 */
public class CaptureActivity extends Activity {
    private CaptureManager capture;
    private CompoundBarcodeView barcodeScannerView;
    private ZxingCaptureBinding binding;
    private boolean islight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barcodeScannerView = initializeContent();
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }

    /**
     * Override to use a different layout.
     *
     * @return the CompoundBarcodeView
     */
    protected CompoundBarcodeView initializeContent() {
        binding = DataBindingUtil.setContentView(this, R.layout.zxing_capture);
        if (!TextUtils.isEmpty(PreferencesUtil.getString(CAR_RENTAL))) {
            if (PreferencesUtil.getString(CAR_RENTAL).equals(BAND_CAR)) {
                binding.title.title.setText("绑卡租车");
            } else {
                binding.title.title.setText("押金租车");
            }
        } else {
            ToastUtils.show("请选择租车方式");
            finish();
        }
        binding.title.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.title.rightButton.setText("打开闪光灯");

        binding.title.rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!islight) {
                    barcodeScannerView.setTorchOn();
                    binding.title.rightButton.setText("关闭闪光灯");
                    islight = true;
                } else {
                    barcodeScannerView.setTorchOff();
                    binding.title.rightButton.setText("打开闪光灯");
                    islight = false;
                }
            }
        });
        return (CompoundBarcodeView) findViewById(R.id.zxing_barcode_scanner);
    }


    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
