package com.example.xiangmu.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.xiangmu.R;
import com.example.xiangmu.orderform.AllordersPage;
import com.example.xiangmu.orderform.BeevaluatedPage;
import com.example.xiangmu.orderform.FragCompleted;
import com.example.xiangmu.orderform.FragForCollection;
import com.example.xiangmu.orderform.FragObligation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentZhangDan extends Fragment {
    @BindView(R.id.Allorders)
    RadioButton Allorders;
    @BindView(R.id.Obligation)
    RadioButton Obligation;
    @BindView(R.id.ForCollection)
    RadioButton ForCollection;
    @BindView(R.id.beevaluated)
    RadioButton beevaluated;
    @BindView(R.id.Completed)
    RadioButton Completed;
    @BindView(R.id.group_divider)
    RadioGroup groupDivider;
    @BindView(R.id.PurchaseViewPage)
    ViewPager PurchaseViewPage;
    Unbinder unbinder;
    private List<Fragment> fragmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_zhangdan, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentList = new ArrayList<>();
        fragmentList.add(new AllordersPage());
        fragmentList.add(new FragObligation());
        fragmentList.add(new FragForCollection());
        fragmentList.add(new BeevaluatedPage());
        fragmentList.add(new FragCompleted());
        PurchaseViewPage.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        groupDivider.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.Allorders:
                        PurchaseViewPage.setCurrentItem(0);
                        break;
                    case R.id.Obligation:
                        PurchaseViewPage.setCurrentItem(1);
                        break;
                    case R.id.ForCollection:
                        PurchaseViewPage.setCurrentItem(2);
                        break;
                    case R.id.beevaluated:
                        PurchaseViewPage.setCurrentItem(3);
                        break;
                    case R.id.Completed:
                        PurchaseViewPage.setCurrentItem(4);
                        break;
                    default:
                        break;
                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}