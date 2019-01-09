package com.example.kaoone.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kaoone.R;
import com.example.kaoone.persenter.PersenterImpl;
import com.example.kaoone.userbean.TwoBean;
import com.example.kaoone.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements IView {
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.recy)
    XRecyclerView recy;
    Unbinder unbinder;
    private PersenterImpl persenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        persenter=new PersenterImpl(this);
        Map<String,String> map=new HashMap<>();
        persenter.sendMessage("getCatagory",TwoBean.class,map);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void data(Object o) {
        if (o instanceof TwoBean){
            TwoBean twoBean= (TwoBean) o;



        }
    }
}