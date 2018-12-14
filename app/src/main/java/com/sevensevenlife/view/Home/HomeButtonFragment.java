package com.sevensevenlife.view.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.model.httpmodel.FirstLevelProjecMode;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.HomeButtonFragmentLayoutBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/21 0021.
 */
@SuppressLint("ValidFragment")
public class HomeButtonFragment extends Fragment implements View.OnClickListener {
    private HomeButtonFragmentLayoutBinding binding;
    private Context mContext;
    private List<FirstLevelProjecMode> list;
    private List<ImageView> imgs = new ArrayList<>();
    private List<TextView> title = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private static ListItemListener listItemListener;

    public HomeButtonFragment(List<FirstLevelProjecMode> list) {
        this.list = list;
    }

    public HomeButtonFragment setListItemListener(ListItemListener l) {
        listItemListener = l;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_button_fragment_layout, container, false);
        mContext = getActivity();
        initList();

        for (int i = 0; i < list.size(); i++) {
            ImgLoadUtils.Load(mContext, list.get(i).getIcon(), imgs.get(i), false);
            title.get(i).setText(list.get(i).getTypeName());
            views.get(i).setOnClickListener(this);
            views.get(i).setVisibility(View.VISIBLE);
        }
        return binding.getRoot();
    }

    private void initList() {
        imgs.add(binding.one.titleImage);
        imgs.add(binding.two.titleImage);
        imgs.add(binding.three.titleImage);
        imgs.add(binding.four.titleImage);
        imgs.add(binding.five.titleImage);
        imgs.add(binding.six.titleImage);
        imgs.add(binding.seven.titleImage);
        imgs.add(binding.eight.titleImage);

        title.add(binding.one.title);
        title.add(binding.two.title);
        title.add(binding.three.title);
        title.add(binding.four.title);
        title.add(binding.five.title);
        title.add(binding.six.title);
        title.add(binding.seven.title);
        title.add(binding.eight.title);

        views.add(binding.one.item);
        views.add(binding.two.item);
        views.add(binding.three.item);
        views.add(binding.four.item);
        views.add(binding.five.item);
        views.add(binding.six.item);
        views.add(binding.seven.item);
        views.add(binding.eight.item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one:
                startAct(0);
                break;
            case R.id.two:
                startAct(1);
                break;
            case R.id.three:
                startAct(2);
                break;
            case R.id.four:
                startAct(3);
                break;
            case R.id.five:
                startAct(4);
                break;
            case R.id.six:
                startAct(5);
                break;
            case R.id.seven:
                startAct(6);
                break;
            case R.id.eight:
                startAct(7);
                break;
        }
    }

    private void startAct(int lin) {
        if (listItemListener != null) {
            listItemListener.Item(lin);
        }

    }
}
