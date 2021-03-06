package com.example.xiangmu.orderform;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.xiangmu.R;
import com.example.xiangmu.adapter.StringAdapter;
import com.example.xiangmu.bean.OrderBean;
import com.example.xiangmu.bean.RegBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragObligation extends Fragment implements IView {
    @BindView(R.id.allordersRecycle)
    RecyclerView allordersRecycle;
    Unbinder unbinder;
    private StringAdapter adapter;
    PersenterImpl persenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obligation, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        persenter = new PersenterImpl(this);
        allordersRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        adapter = new StringAdapter(getActivity(),1);
        allordersRecycle.setAdapter(adapter);
        loadData();
        adapter.setShopCarListener(new StringAdapter.ShopCarListener() {
            @Override
            public void callBack(OrderBean.OrderListBean list) {
                Map<String, String> map = new HashMap<>();
                map.put("orderId", list.getOrderId());
                map.put("payType", 1 + "");
                persenter.sendMessage(Constant.ZHIFU, map, RegBean.class);
            }
        });
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof OrderBean) {
            OrderBean bean = (OrderBean) data;
            if (bean == null) {
                Toast.makeText(getActivity(), bean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                adapter.setData(bean.getOrderList());
            }
        }
        if (data instanceof RegBean) {
            RegBean regBean = (RegBean) data;
            Toast.makeText(getActivity(), regBean.getMessage(), Toast.LENGTH_SHORT).show();
            loadData();
        }
    }

    private void loadData() {
        persenter.sendGet(Constant.QUANBU + "?status=1&page=1&count=5", OrderBean.class);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}