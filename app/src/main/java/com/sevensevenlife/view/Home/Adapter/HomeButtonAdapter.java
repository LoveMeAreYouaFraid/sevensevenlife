package com.sevensevenlife.view.Home.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/21 0021.
 */

public class HomeButtonAdapter extends FragmentPagerAdapter {
    private List<Fragment> strings;

    public HomeButtonAdapter(FragmentManager fm, List<Fragment> strings) {
        super(fm);
        this.strings = strings;

    }

    @Override
    public Fragment getItem(int position) {
        return strings.get(position);
    }

    @Override
    public int getCount() {
        return strings.size();
    }
}
