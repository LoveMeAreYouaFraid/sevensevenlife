package com.sevensevenlife.view.Home.PublicBicycle;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;

import com.sevensevenlife.utils.PreferencesUtil;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.CarRentalActivityLayoutBinding;

import static com.sevensevenlife.model.KEY.BAND_CAR;
import static com.sevensevenlife.model.KEY.CAR_RENTAL;
import static com.sevensevenlife.model.KEY.MONEY;

/**
 * Created by 10237 on 2016/12/23.
 */

public class CarRentalActivity extends Activity implements View.OnClickListener {
    private CarRentalActivityLayoutBinding binding;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.car_rental_activity_layout);
        binding.title.title.setText("租车方式");
        binding.title.imgBack.setOnClickListener(this);
        binding.moneyCarRadio.setOnClickListener(this);
        binding.cardCarRadio.setOnClickListener(this);
        binding.moneyCarLayout.setOnClickListener(this);
        binding.cardCarLayout.setOnClickListener(this);
        ViewCompat.setTransitionName(binding.title.title, "touxiang");
        if (!TextUtils.isEmpty(PreferencesUtil.getString(CAR_RENTAL))) {
            if (PreferencesUtil.getString(CAR_RENTAL).equals(BAND_CAR)) {
                binding.moneyCarRadio.setChecked(true);
                binding.cardCarRadio.setChecked(false);
            } else {
                binding.moneyCarRadio.setChecked(false);
                binding.cardCarRadio.setChecked(true);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
               ActivityCompat.finishAfterTransition(this);
                break;
            case R.id.money_car_layout:
            case R.id.money_car_radio:
                binding.moneyCarRadio.setChecked(true);
                binding.cardCarRadio.setChecked(false);
                PreferencesUtil.putString(CAR_RENTAL, BAND_CAR);
                setResult(RESULT_OK);
                ActivityCompat.finishAfterTransition(this);
                break;
            case R.id.card_car_layout:
            case R.id.card_car_radio:
                binding.moneyCarRadio.setChecked(false);
                binding.cardCarRadio.setChecked(true);
                PreferencesUtil.putString(CAR_RENTAL, MONEY);
                setResult(RESULT_OK);
                ActivityCompat.finishAfterTransition(this);
                break;

        }
    }
}
