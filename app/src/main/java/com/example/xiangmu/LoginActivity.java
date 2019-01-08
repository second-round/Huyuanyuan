package com.example.xiangmu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import com.example.xiangmu.app.Addevent;
import com.example.xiangmu.app.DismissEvent;
import com.example.xiangmu.fragment.FragmentHome;
import com.example.xiangmu.fragment.FragmentMine;
import com.example.xiangmu.fragment.FragmentQuanZi;
import com.example.xiangmu.fragment.FragmentShopping;
import com.example.xiangmu.fragment.FragmentZhangDan;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.radio)
    RadioGroup radio;
    private FragmentManager mManager;
    private List<Fragment> list;
    private Fragment mCurFragment;
    private List<Fragment> fragments=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        mManager = getSupportFragmentManager();

//        pager.setCurrentItem(2);
    }

    private void initView() {
        //2.添加数据
        list = new ArrayList<Fragment>();
        list.add(new FragmentHome());
        list.add(new FragmentQuanZi());
        list.add(new FragmentShopping());
        list.add(new FragmentZhangDan());
        list.add(new FragmentMine());
        //3.创建适配器

        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                radio.check(radio.getChildAt(i).getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        //点击切换 Fragment
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.home:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.quanzi:
                        pager.setCurrentItem(1);
                        break;
                    case R.id.shopping:
                        pager.setCurrentItem(2);
                        break;
                    case R.id.zhangdan:
                        pager.setCurrentItem(3);
                        break;
                    case R.id.mine:
                        pager.setCurrentItem(4);
                        break;

                    default:
                        break;
                }
            }
        });

    }

    //Eventbus置换Fragment
    @Subscribe
    public void eventAdd(Addevent event) {
        if (pager.getVisibility() != View.GONE) {
            pager.setVisibility(View.GONE);
        }
        FragmentTransaction transaction = mManager.beginTransaction();
        if (mCurFragment != null) {
            transaction.hide(mCurFragment);
        }
        mCurFragment = event.getAddFragment();
        fragments.add(mCurFragment);
     //   transaction.add(R.id.fragmentw, mCurFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Subscribe
    public void eventDismiss(DismissEvent event) {
        Fragment needDismiss = event.getDismissFragment();
        fragments.remove(needDismiss);
        mCurFragment = null;
        if (fragments.size() > 0) {
            mCurFragment = fragments.get(fragments.size() - 1);
        } else if (pager.getVisibility() != View.VISIBLE) {
            pager.setVisibility(View.VISIBLE);
        }
        mManager.popBackStack();
    }

    private void clearALL(int position) {
        for (int i = 0; i < fragments.size(); i++) {
            mManager.popBackStack();
        }
        mCurFragment = null;
        fragments.clear();
        pager.setVisibility(View.VISIBLE);
        pager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragments.remove(mCurFragment);
        mCurFragment = null;
        if (fragments.size() > 0) {
            mCurFragment = fragments.get(fragments.size() - 1);
        } else if (pager.getVisibility() != View.VISIBLE) {
            pager.setVisibility(View.VISIBLE);
        }
    }


}
