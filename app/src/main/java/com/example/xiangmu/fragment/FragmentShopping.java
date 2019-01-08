package com.example.xiangmu.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.xiangmu.R;
import com.example.xiangmu.adapter.MyShoppingAdapter;
import com.example.xiangmu.bean.EventBean;
import com.example.xiangmu.bean.GoodsBean;
import com.example.xiangmu.bean.ShowShoppingBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentShopping extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.quanxuan)
    CheckBox quanxuan;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.qjs)
    TextView qjs;
    Unbinder unbinder;
    private GoodsBean goodsBean;
    private MyShoppingAdapter shoppingAdapter;
    private ShowShoppingBean showShoppingBean;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_shopping, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        shoppingAdapter=new MyShoppingAdapter(getContext());
        recyclerView.setAdapter(shoppingAdapter);
        showShopping();
        shoppingAdapter.setShopCarListener(new MyShoppingAdapter.ShopCarListener() {
            @Override
            public void callBack(List<ShowShoppingBean.ResuleBean> mlist) {
                showShoppingBean.getResult().clear();
                showShoppingBean.getResult().addAll(mlist);
                totalMoney();
            }
        });
    }

    private void totalMoney() {
        int money=0;
        for (int i = 0; i < showShoppingBean.getResult().size(); i++) {
            if (showShoppingBean.getResult().get(i).isItem_check()){
                money+=showShoppingBean.getResult().get(i).getPrice()*showShoppingBean.getResult().get(i).getCount();
            }

        }
        totalPrice.setText("￥"+money+"");
    }

    private void showShopping() {
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onEvent(EventBean evBean) {
        if (evBean.getName().equals("myDialog")) {
            goodsBean = (GoodsBean) evBean.getO();

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.quanxuan, R.id.qjs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quanxuan:
                break;
            case R.id.qjs:
                break;
            default:
                break;
        }
    }
}