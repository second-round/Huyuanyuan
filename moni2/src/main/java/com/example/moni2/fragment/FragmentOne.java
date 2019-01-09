package com.example.moni2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.moni2.Apis;
import com.example.moni2.R;
import com.example.moni2.bean.PhoneBeans;
import com.example.moni2.bean.ShopBean;
import com.example.moni2.persenter.PersenterImpl;
import com.example.moni2.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentOne extends Fragment implements IView {
    @BindView(R.id.button2)
    Button button1;
    @BindView(R.id.button3)
    Button button2;
    private PersenterImpl persenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentont,container,false);
        ButterKnife.bind(this,view);
        persenter=new PersenterImpl(this);
        Map<String,String> map=new HashMap<>();
        map.put("pid","1");
        persenter.startRequest("getProductDetail?pid=1",map,ShopBean.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof ShopBean){
            ShopBean shopBean= (ShopBean) data;
            Toast.makeText(getActivity(),shopBean.getData().getTitle(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getDataFail(String error) {

    }
}