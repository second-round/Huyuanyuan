package com.example.moni2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.moni2.adapter.MainPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.fragent)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        pager.setAdapter(new MainPageAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tab);
        //使tabLayout和contents结合起来
        tabLayout.setupWithViewPager(pager);


    }
}
