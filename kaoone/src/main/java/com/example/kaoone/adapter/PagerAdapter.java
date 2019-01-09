package com.example.kaoone.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kaoone.fragment.CartFragment;
import com.example.kaoone.fragment.CategoryFragment;
import com.example.kaoone.fragment.FindFragment;
import com.example.kaoone.fragment.HomeFragment;
import com.example.kaoone.fragment.MineFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private String[] strings=new String[]{"首页","分类","觅Me","购物车","我的"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 1:
                return new HomeFragment();
            case 2:
                return new CategoryFragment();
            case 3:
                return new FindFragment();
            case 4:
                return new CartFragment();
            case 5:
                return new MineFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strings[position];
    }
}
