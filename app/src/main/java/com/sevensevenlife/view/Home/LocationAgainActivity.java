package com.sevensevenlife.view.Home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.interfaces.addressUtilsInterface;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.utils.addressUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.ListDialog;
import com.sevensevenlife.view.DiaLog.TitleDialog;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.LocationAgainActivityLayoutBinding;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.sevensevenlife.utils.MemoryBean;

import java.util.List;

public class LocationAgainActivity extends BaseActivity implements OnClickListener {

    private Marker marker;

    private TencentMap tencentMap;

    private LocationAgainActivityLayoutBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.location_again_activity_layout);
        binding.title.title.setText("修改位置");
        if (TextUtils.isEmpty(PreferencesUtil.getString("map_one_start"))) {
            new TitleDialog().SHOW(mContext, "定位不准？您可以点击地图修改定位地址", "确定", new DialogListener() {
                @Override
                public void buttType(int ButtType) {

                }
            }, false);
            PreferencesUtil.putString("map_one_start", "1");
        }
        binding.title.imgBack.setOnClickListener(this);
        binding.searchBtn.setOnClickListener(this);
        tencentMap = binding.mapView.getMap();
        tencentMap.setCenter(new LatLng(MemoryBean.Latitude, MemoryBean.Longitude));
        setIcon(new LatLng(MemoryBean.Latitude, MemoryBean.Longitude), binding.mapView);
        tencentMap.setZoom(18);
        tencentMap.setOnMapClickListener(new TencentMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                setIcon(latLng, binding.mapView);
                MemoryBean.Latitude = latLng.getLatitude();
                MemoryBean.Longitude = latLng.getLongitude();
                if (TextUtils.isEmpty(addressUtils.get(latLng.getLatitude(), latLng.getLongitude()))) {
                    addressUtils.get(latLng.getLatitude(), latLng.getLongitude());
                } else {
                    ToastUtils.show("修改为：" + addressUtils.get(latLng.getLatitude(), latLng.getLongitude()));
                    MemoryBean.address = addressUtils.get(latLng.getLatitude(), latLng.getLongitude());
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.search_btn:
                if (TextUtils.isEmpty(binding.input.getText())) {
                    ToastUtils.show("请输入地址");
                    return;
                }
                ToastUtils.show(MemoryBean.city);
                addressUtils.get(MemoryBean.city, binding.input.getText() + "", new addressUtilsInterface() {
                    @Override
                    public void back(final List<String> strings, final List<String> title) {
                        ListDialog.show(mContext, strings, title, new ListItemListener() {
                            @Override
                            public void Item(int position) {
                                MemoryBean.address = strings.get(position);
                                MemoryBean.addressTitle = title.get(position);
                                ToastUtils.show("修改成功");
                            }

                            @Override
                            public void ChildView(View v, int position) {

                            }
                        }, true);
                    }
                });

                break;
        }
    }

    @Override
    protected void onDestroy() {
        binding.mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        binding.mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        binding.mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        binding.mapView.onStop();
        super.onStop();
    }

    private void setIcon(LatLng latLng, MapView mapView) {
        if (marker != null) {
            marker.remove();
        }
        marker = mapView.getMap().addMarker(new MarkerOptions()
                .position(latLng)
                .title("您的位置")
                .icon(BitmapDescriptorFactory.defaultMarker())
                .draggable(true));

        marker.showInfoWindow();
    }
}
