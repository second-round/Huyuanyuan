package com.example.moni2.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.moni2.fragment.FragmentOne;
import com.example.moni2.fragment.FragmentThree;
import com.example.moni2.fragment.FragmentTwo;

public class MainPageAdapter extends FragmentPagerAdapter {
    //定义tabLayout的切换内容
    private String[] pageNames=new String[]{"商品","详情","评论"};
    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        //给Fragment对应的TabLayout赋值
        switch (i){
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();
            default:
                return new FragmentTwo();

        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //获取当前TabLayout的内容
        return pageNames[position];
    }
    //获取tabLayout的数组长度
    @Override
    public int getCount() {
        return pageNames.length;
    }
}
