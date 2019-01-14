package com.sevensevenlife.view.Home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.CdyeeNews;
import com.sevensevenlife.model.httpmodel.CdyeeNewsRows;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.custumview.LpWebActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.FindListItemBinding;
import com.example.youxiangshenghuo.databinding.ListActivityBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.GET_CDYEE_NEWS;

/**
 * Created by Administrator on 2017/4/13 0013.
 */

public class NewsActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener {
    private ListActivityBinding binding;
    private List<CdyeeNewsRows> rows5 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.list_activity);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.title.setText("今日资讯");
        MyRequest.POST(GET, null, null, GET_CDYEE_NEWS, CdyeeNews.class.getName(), 100, this);

    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 100:
                CdyeeNews cdyeeNews = (CdyeeNews) backMode;
                rows5.addAll(cdyeeNews.getRows());
                if (rows5.size() == 0) {
                    return;
                }
                new RViewUtils(mContext, binding.list)
                        .setAdapter(new PublicAdapter<>(1, R.layout.find_list_item, new BindViewInterface() {
                            @Override
                            public void bandView(ViewDataBinding b, final int position) {
                                FindListItemBinding bin = (FindListItemBinding) b;
                                bin.title.setText(rows5.get(position).getTitle());
                                ImgLoadUtils.Load(mContext, rows5.get(position).getImage(), bin.comprehensive, false);
                                bin.comprehensive.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(mContext, LpWebActivity.class)
                                                .putExtra(KEY.URL, rows5.get(position).getUrl()));
                                    }
                                });

                            }
                        }));
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
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
