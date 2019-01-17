package com.example.xiangmu.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.xiangmu.R;
import com.example.xiangmu.adapter.CircleAdapter;
import com.example.xiangmu.bean.CircleBean;
import com.example.xiangmu.bean.RegBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentQuanZi extends Fragment implements IView {
    @BindView(R.id.recycle)
    XRecyclerView recycle;
    Unbinder unbinder;
    private PersenterImpl persenter;
    int page;
    private CircleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_quanzi, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        page=1;
        persenter = new PersenterImpl(this);
        persenter.sendGet(Constant.CIRCLEPATH, CircleBean.class);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(manager);
        adapter = new CircleAdapter(getActivity());
        recycle.setAdapter(adapter);
        getData();
        adapter.setGreatClick(new CircleAdapter.GreatClick() {
            @Override
            public void click(int circleId, boolean isGreat) {
                Map<String,String> map=new HashMap<>();
                map.put("circleId",circleId+"");
                if (isGreat){
                    persenter.sendMessage(Constant.DIANZAN,map,RegBean.class);
                }else {
                    persenter.sendMessageDelete(Constant.QUXIAO,map,RegBean.class);
                }
                adapter.notifyDataSetChanged();

            }
        });
        recycle.setPullRefreshEnabled(true);
        recycle.setLoadingMoreEnabled(true);
        recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getData();
            }

            @Override
            public void onLoadMore() {
                getData();
            }
        });


    }

    private void getData() {
        persenter.sendGet(Constant.CIRCLEPATH+"?page="+page+"&count="+"5",CircleBean.class);
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof CircleBean) {
            CircleBean circleBean = (CircleBean) data;

            if (page==1){
                adapter.setList(circleBean.getResult());
            }else {
                adapter.addList(circleBean.getResult());
            }
            page++;
            recycle.refreshComplete();
            recycle.loadMoreComplete();

        }
        if (data instanceof RegBean){
            RegBean regBean= (RegBean) data;
            Toast.makeText(getActivity(),regBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}