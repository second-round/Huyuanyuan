package com.example.xiangmu.fragment;

import android.content.Intent;
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
import com.example.xiangmu.TJDDActivity;
import com.example.xiangmu.adapter.MyShoppingAdapter;
import com.example.xiangmu.bean.EventBean;
import com.example.xiangmu.bean.GoodsBean;
import com.example.xiangmu.bean.ShopCarAddBean;
import com.example.xiangmu.bean.ShowShoppingBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentShopping extends Fragment implements IView {
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
    private PersenterImpl persenter;


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
        persenter=new PersenterImpl(this);
        showShopping();
        shoppingAdapter.setShopCarListener(new MyShoppingAdapter.ShopCarListener() {
            @Override
            public void callBack(List<ShowShoppingBean.ResuleBean> mlist) {
                showShoppingBean.getResult().clear();
                String s = new Gson().toJson(mlist);
                Map<String,String> map=new HashMap<>();
                map.put("data",s);
                persenter.onPutStartRequest(Constant.TOBUSHOP,map,ShopCarAddBean.class);
                showShoppingBean.getResult().addAll(mlist);
                totalMoney();
            }
        });


    }
    private void totalMoney() {
        int money=0;
        boolean ischeck=true;
        for (int i = 0; i < showShoppingBean.getResult().size(); i++) {
            if (showShoppingBean.getResult().get(i).isItem_check()){
                money+=showShoppingBean.getResult().get(i).getPrice()*showShoppingBean.getResult().get(i).getCount();
            }else {
                ischeck=false;
            }
        }
        totalPrice.setText("￥"+money+"");
        quanxuan.setChecked(ischeck);
        back();
    }
    private void showShopping() {
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            persenter.sendGet(Constant.QUERYSHOP,ShowShoppingBean.class);
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
                quanxuan();
                break;
            case R.id.qjs:
                ShowShoppingBean lists=new ShowShoppingBean();
                for (int i = 0; i < showShoppingBean.getResult().size(); i++) {
                    if (showShoppingBean.getResult().get(i).isItem_check()){
                        lists.addResult(showShoppingBean.getResult().get(i));
                    }
                }
                EventBus.getDefault().postSticky(new EventBean("list",lists));
                Intent intent = new Intent(getActivity(), TJDDActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    private void quanxuan() {
        back();
        for (int i = 0; i < showShoppingBean.getResult().size(); i++) {
            showShoppingBean.getResult().get(i).setItem_check(quanxuan.isChecked());
        }
        shoppingAdapter.setList(showShoppingBean.getResult());
    }
    private void back() {
        if (quanxuan.isChecked()){
            quanxuan.setBackgroundResource(R.mipmap.common_icon_finish);
        }else {
            quanxuan.setBackgroundResource(R.mipmap.ic_action_unselected);
        }
    }
    @Override
    public void requesta(Object data) {
        if (data instanceof ShowShoppingBean){
            showShoppingBean = (ShowShoppingBean) data;
            shoppingAdapter.setList(showShoppingBean.getResult());
        }
    }
}