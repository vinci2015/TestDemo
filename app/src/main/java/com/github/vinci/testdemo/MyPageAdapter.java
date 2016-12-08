package com.github.vinci.testdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/12/8 0008.
 */

public class MyPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] names = {"消息","发现"};
    public MyPageAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.fragments = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
